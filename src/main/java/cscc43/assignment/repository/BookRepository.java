package cscc43.assignment.repository;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Book;

public class BookRepository extends Repository<Book> {
    public Book findOneByTitle(String title) {
        String where = String.format("`%s`=?", columnName(Book.class, "title"));
        return findOneWhere(where, title);
    }
}
