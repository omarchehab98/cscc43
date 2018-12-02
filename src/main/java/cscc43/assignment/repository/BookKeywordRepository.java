package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Book;
import cscc43.assignment.model.BookKeyword;

public class BookKeywordRepository extends Repository<BookKeyword> {
    public List<BookKeyword> findByBook(Book book) {
        String where = String.format("`%s`=?", columnName(BookKeyword.class, "book"));
        return findWhere(where, book.getIsbn());
    }

    public boolean deleteByBook(Book book) {
        String where = String.format("`%s`=?", columnName(BookKeyword.class, "book"));
        return deleteWhere(where, book.getIsbn());
    }
}
