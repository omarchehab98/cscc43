package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Book;
import cscc43.assignment.model.BookAuthor;

public class BookAuthorRepository extends Repository<BookAuthor> {
    public List<BookAuthor> findByBook(Book book) {
        String where = String.format("`%s`=?", columnName(BookAuthor.class, "book"));
        return findWhere(where, book.getIsbn());
    }

    public boolean deleteByBook(Book book) {
        String where = String.format("`%s`=?", columnName(BookAuthor.class, "book"));
        return deleteWhere(where, book.getIsbn());
    }
}
