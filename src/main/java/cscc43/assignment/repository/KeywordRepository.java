package cscc43.assignment.repository;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Keyword;

public class KeywordRepository extends Repository<Keyword> {
    public Keyword findOneByTag(String tag) {
        String where = String.format("`%s`=?", columnName(Keyword.class, "tag"));
        return findOneWhere(where, tag);
    }

    public Keyword findOneByTag(Keyword keyword) {
        return findOneByTag(keyword.getTag());
    }
}
