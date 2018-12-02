package cscc43.assignment.repository;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Person;

public class PersonRepository extends Repository<Person> {
    public Person findOneByName(String firstName, String middleName, String lastName) {
        String where = String.join("AND",
            String.format("`%s`=?", columnName(Person.class, "firstName")),
            String.format("`%s`=?", columnName(Person.class, "middleName")),
            String.format("`%s`=?", columnName(Person.class, "lastName"))
        );
        return findOneWhere(where, firstName, middleName, lastName);
    }

    public Person findOneByName(Person person) {
        return findOneByName(person.getFirstName(), person.getMiddleName(), person.getLastName());
    }

    public Person upsertByName(Person person) {
        Person newPerson = findOneByName(person);
        if (newPerson == null) {
            newPerson = insertOne(person);
        } else {
            newPerson.setGender(person.getGender());
            updateOne(newPerson);
        }
        return newPerson;
    }
}
