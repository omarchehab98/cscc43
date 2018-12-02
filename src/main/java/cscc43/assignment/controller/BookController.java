package cscc43.assignment.controller;

import cscc43.assignment.model.Book;
import cscc43.assignment.service.BookService;
import cscc43.assignment.throwable.BookUpsertException;
import cscc43.assignment.throwable.DatabaseException;

public class BookController {
    private static final BookController instance = new BookController();

    private BookController() {}

    public void insert(Book book) throws BookUpsertException {
        try {
            BookService.insertOne(book);
        } catch (DatabaseException err) {
            throw new BookUpsertException(err.toString());
        }
    }

    public void update(Book book) throws BookUpsertException {
        try {
            BookService.updateOne(book);
        } catch (DatabaseException err) {
            throw new BookUpsertException(err.toString());
        }
    }

    public static BookController getInstance() {
        return instance;
    }
}
