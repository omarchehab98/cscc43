package cscc43.assignment.repository;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Role;

public class RoleRepository extends Repository<Role> {
    public Role findOneByDescription(String description) {
        String where = String.format("`%s`=?", columnName(Role.class, "description"));
        return findOneWhere(where, description);
    }
}
