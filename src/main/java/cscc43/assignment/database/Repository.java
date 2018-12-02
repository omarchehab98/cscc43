package cscc43.assignment.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.GeneratedValue;
import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.JoinColumns;
import cscc43.assignment.throwable.DatabaseException;

public class Repository<T> {
    protected final Class<?> entity;
    protected final String tableName;

    public Repository() {
        Type superclass = getClass().getGenericSuperclass(); 
        ParameterizedType parameterized = (ParameterizedType) superclass;
        // with nested generic types, this becomes a little more complicated
        entity = (Class<?>) parameterized.getActualTypeArguments()[0];
        tableName = entity.getAnnotation(Entity.class).name();
    }

    public T findOne(T key) {
        return findOneWhere(String.join(" AND ", getIdConditionList()), getIdValues(key));
    }

    public List<T> findAll() {
        return findWhere(null);
    }

    public T findOneWhere(String where, Object... parameters) {
        List<T> result = findWhere(String.format("%s LIMIT 1", where), parameters);
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public List<T> findWhere(String where, Object... parameters) {
        Database.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List<T> result = new ArrayList<T>();
            
            String fields = String.join("`, `", getColumnList());
            String query = String.format(
                "SELECT `%s` FROM `%s` %s",
                fields,
                this.tableName,
                where != null && where.length() > 0
                ? "WHERE " + where
                : ""
            );

            preparedStatement = Database.getConnection().prepareStatement(query);
            int i = 1;
            for (Object param : parameters) {
                preparedStatement.setObject(i++, param);
            }
            
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(instantiateOne(resultSet));
            }
            return result;
        } catch (SQLException|InstantiationException|IllegalAccessException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        } finally {
            tryClose(preparedStatement, resultSet);
            Database.disconnect();
        }
    }

    public List<T> findByForeignEntity(String fieldName, Object foreignEntity) {
        String[] columnNames = columnNames(entity, fieldName);
        String[] referencedColumnNames = referencedColumnNames(entity, fieldName);
        Object[] columnValues = new Object[referencedColumnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = String.format("`%s`=?", columnNames[i]);
            columnValues[i] = invokeGetter(foreignEntity, getFieldNameFromColumnName(foreignEntity, referencedColumnNames[i]));
        }
        String where = String.join(" AND ", columnNames);
        return findWhere(where, columnValues);
    }

    public T insertOne(T row) {
        Database.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            T result = null;

            String fields = String.join("`, `", getInsertableColumnList());
            String values = String.join(", ", mapToQuestionMark(getInsertableColumnList()));
            String query = String.format(
                "INSERT INTO `%s` (`%s`) VALUES (%s)",
                this.tableName,
                fields,
                values
            );
            
            preparedStatement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            populateValues(preparedStatement, 1, row);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                result = findOne(instantiateOneFromGeneratedKeys(resultSet));
            } else {
                result = findOne(row);
            }

            return result;
        } catch (SQLException|InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        } finally {
            tryClose(preparedStatement, resultSet);
            Database.disconnect();
        }
    }

    public T updateOne(T row) {
        Database.connect();
        PreparedStatement preparedStatement = null;

        try {
            List<String> updatableExpressionList = getUpdatableExpressionList();
            String fieldsAndValues = String.join(", ", updatableExpressionList);
            String conditions = String.join(" AND ", getIdConditionList());
            String query = String.format(
                "UPDATE `%s` SET %s WHERE %s",
                this.tableName,
                fieldsAndValues,
                conditions
            );
            
            preparedStatement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            populateValues(preparedStatement, 1, row);
            Object[] idValues = getIdValues(row);
            for (int i = 0; i < idValues.length; i++) {
                preparedStatement.setObject(updatableExpressionList.size() + 1 + i, idValues[i]);
            }

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            return findOne(row);
        } catch (SQLException|IllegalAccessException|NoSuchMethodException|InvocationTargetException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        } finally {
            tryClose(preparedStatement, null);
            Database.disconnect();
        }
    }

    public boolean deleteOne(T key) {
        return deleteWhere(String.join(" AND ", getIdConditionList()), getIdValues(key));
    }

    public boolean deleteOneWhere(String where, Object... parameters) {
        return deleteWhere(String.format("%s LIMIT 1", where), parameters);
    }

    public boolean deleteWhere(String where, Object... parameters) {
        Database.connect();
        PreparedStatement preparedStatement = null;

        try {
            String query = String.format(
                "DELETE FROM `%s` %s",
                this.tableName,
                where != null && where.length() > 0
                ? "WHERE " + where
                : ""
            );

            preparedStatement = Database.getConnection().prepareStatement(query);
            int i = 1;
            for (Object param : parameters) {
                preparedStatement.setObject(i++, param);
            }
            
            System.out.println(preparedStatement);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        } finally {
            tryClose(preparedStatement, null);
            Database.disconnect();
        }
    }

    public boolean deleteByForeignEntity(String fieldName, Object foreignEntity) {
        String[] columnNames = columnNames(entity, fieldName);
        String[] referencedColumnNames = referencedColumnNames(entity, fieldName);
        Object[] columnValues = new Object[referencedColumnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = String.format("`%s`=?", columnNames[i]);
            columnValues[i] = invokeGetter(foreignEntity, getFieldNameFromColumnName(foreignEntity, referencedColumnNames[i]));
        }
        String where = String.join(" AND ", columnNames);
        return deleteWhere(where, columnValues);
    }

    protected T instantiateOne(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        T row = (T) entity.newInstance();
        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                Object value = resultSet.getObject(column.name());
                if (value instanceof Integer && field.getType().equals(Long.class)) {
                    value = new Long((Integer) value);
                }
                field.set(row, value);
            }
        }
        return row;
    }

    protected T instantiateOneFromGeneratedKeys(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        T row = (T) entity.newInstance();
        int i = 1;
        for (Field field : entity.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null && column != null) {
                field.setAccessible(true);
                Object value = resultSet.getObject(i++);
                field.set(row, value);
            }
        }
        return row;
    }

    protected Object[] getIdValues(T row) {
        List<Object> result = new ArrayList<Object>();
        for (Field field : entity.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (id != null) {
                if (column != null) {
                    result.add(invokeGetter(row, field.getName()));
                } else if (joinColumn != null) {
                    Object foreignObject = invokeGetter(row, field.getName());
                    String foreignFieldName = getFieldNameFromColumnName(foreignObject, joinColumn.referencedColumnName());
                    result.add(invokeGetter(foreignObject, foreignFieldName));
                } else if (joinColumns != null) {
                    for (JoinColumn aJoinColumn : joinColumns.value()) {
                        Object foreignObject = invokeGetter(row, field.getName());
                        String foreignFieldName = getFieldNameFromColumnName(foreignObject, aJoinColumn.referencedColumnName());
                        result.add(invokeGetter(foreignObject, foreignFieldName));
                    }
                }
            }
        }
        return result.toArray();
    }

    protected void populateValues(PreparedStatement preparedStatement, int index, T row) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int i = 0;
        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (generatedValue == null) {
                if (column != null) {
                    preparedStatement.setObject(index + i++, invokeGetter(row, field.getName()));
                } else if (joinColumn != null) {
                    Object foreignObject = invokeGetter(row, field.getName());
                    String foreignFieldName = getFieldNameFromColumnName(foreignObject, joinColumn.referencedColumnName());
                    preparedStatement.setObject(index + i++, invokeGetter(foreignObject, foreignFieldName));
                } else if (joinColumns != null) {
                    for (JoinColumn aJoinColumn : joinColumns.value()) {
                        Object foreignObject = invokeGetter(row, field.getName());
                        String foreignFieldName = getFieldNameFromColumnName(foreignObject, aJoinColumn.referencedColumnName());
                        preparedStatement.setObject(index + i++, invokeGetter(foreignObject, foreignFieldName));
                    }
                }
            }
        }
    }

    protected List<String> getIdConditionList() {
        List<String> conditions = new ArrayList<String>();
        for (String column : getIdColumnNames()) {
            conditions.add(String.format("`%s`=?", column));
        }
        return conditions;
    }
    
    protected List<String> getIdColumnNames() {
        List<String> fieldList = new ArrayList<String>();
        for (Field field : entity.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (id != null) {
                if (column != null) {
                    fieldList.add(column.name());
                } else if (joinColumn != null) {
                    fieldList.add(joinColumn.name());
                } else if (joinColumns != null) {
                    for (JoinColumn aJoinColumn : joinColumns.value()) {
                        fieldList.add(aJoinColumn.name());
                    }
                }
            }
        }
        return fieldList;
    }

    protected List<String> getColumnList() {
        List<String> fieldList = new ArrayList<String>();
        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (column != null) {
                fieldList.add(column.name());
            } else if (joinColumn != null) {
                fieldList.add(joinColumn.name());
            } else if (joinColumns != null) {
                for (JoinColumn aJoinColumn : joinColumns.value()) {
                    fieldList.add(aJoinColumn.name());
                }
            }
        }
        return fieldList;
    }

    protected String getFieldNameFromColumnName(Object object, String columnName) {
        System.out.println(String.format("object %s", object));
        System.out.println(String.format("columnName %s", columnName));
        for (Field field : object.getClass().getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.name().equals(columnName)) {
                return field.getName();
            }
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            if (joinColumn != null && joinColumn.name().equals(columnName)) {
                return joinColumn.name();
            }
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (joinColumns != null) {
                for (JoinColumn aJoinColumn : joinColumns.value()) {
                    if (aJoinColumn != null && aJoinColumn.name().equals(columnName)) {
                        return aJoinColumn.name();
                    }
                }
            }
        }
        throw new IllegalArgumentException(String.format("%s %s", object, columnName));
    }

    protected List<String> getInsertableColumnList() {
        List<String> fieldList = new ArrayList<String>();
        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (generatedValue == null) {
                if (column != null) {
                    fieldList.add(column.name());
                } else if (joinColumn != null) {
                    fieldList.add(joinColumn.name());
                } else if (joinColumns != null) {
                    for (JoinColumn aJoinColumn : joinColumns.value()) {
                        fieldList.add(aJoinColumn.name());
                    }
                }
            }
        }
        return fieldList;
    }

    protected List<String> getUpdatableExpressionList() {
        List<String> fieldList = new ArrayList<String>();
        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (column != null && generatedValue == null) {
                fieldList.add(String.format("`%s`=?", column.name()));
            }
        }
        return fieldList;
    }

    protected List<String> mapToQuestionMark(List<String> strings) {
        List<String> result = new ArrayList<String>();
        for (String string : strings) {
            result.add("?");
        }
        return result;
    }

    protected Object invokeGetter(Object instance, String name) {
        try {
            Method method = instance.getClass().getMethod(String.format(
                "get%s",
                name.substring(0, 1).toUpperCase() + name.substring(1)
            ));
            return method.invoke(instance);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        }
    }
    
    protected void tryClose(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    protected static String columnName(Class entity, String field) {
        try {
            Column column = entity.getDeclaredField(field).getAnnotation(Column.class);
            if (column != null) {
                return column.name();
            }
            JoinColumn joinColumn = entity.getDeclaredField(field).getAnnotation(JoinColumn.class);
            if (joinColumn != null) {
                return joinColumn.name();
            }
            throw new IllegalArgumentException();
        } catch (NoSuchFieldException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        }
    }

    protected static String[] columnNames(Class entity, String fieldName) {
        try {
            Field field = entity.getDeclaredField(fieldName);
            List<String> result = new ArrayList<String>();
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (joinColumns != null) {
                for (JoinColumn aJoinColumn : joinColumns.value()) {
                    result.add(aJoinColumn.name());
                }
                String[] resultArr = new String[result.size()];
                return result.toArray(resultArr);
            }
            throw new IllegalArgumentException();
        } catch (NoSuchFieldException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        }
    }

    protected static String[] referencedColumnNames(Class entity, String fieldName) {
        try {
            Field field = entity.getDeclaredField(fieldName);
            List<String> result = new ArrayList<String>();
            JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
            if (joinColumns != null) {
                for (JoinColumn aJoinColumn : joinColumns.value()) {
                    result.add(aJoinColumn.referencedColumnName());
                }
                String[] resultArr = new String[result.size()];
                return result.toArray(resultArr);
            }
            throw new IllegalArgumentException();
        } catch (NoSuchFieldException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        }
    }
}
