package cscc43.assignment.service;

import java.sql.Connection;
import java.sql.SQLException;

import cscc43.assignment.database.Database;
import cscc43.assignment.model.Book;
import cscc43.assignment.model.BookAuthor;
import cscc43.assignment.model.BookKeyword;
import cscc43.assignment.model.Keyword;
import cscc43.assignment.model.Person;
import cscc43.assignment.repository.BookAuthorRepository;
import cscc43.assignment.repository.BookKeywordRepository;
import cscc43.assignment.repository.BookRepository;
import cscc43.assignment.repository.KeywordRepository;
import cscc43.assignment.repository.PersonRepository;
import cscc43.assignment.throwable.DatabaseException;

public class BookService {
    private static final BookRepository bookRepository = new BookRepository();
    private static final BookAuthorRepository bookAuthorRepository = new BookAuthorRepository();
    private static final PersonRepository personRepository = new PersonRepository();
    private static final BookKeywordRepository bookKeywordRepository = new BookKeywordRepository();
    private static final KeywordRepository keywordRepository = new KeywordRepository();

    public static Book insertOne(Book book) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            Book newBook = bookRepository.insertOne(book);
            insertForeign(book, newBook);
            Database.commit();
            return newBook;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    public static Book updateOne(Book book) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            Book newBook = bookRepository.updateOne(book);
            bookAuthorRepository.deleteByBook(book);
            bookKeywordRepository.deleteByBook(book);
            insertForeign(book, newBook);
            Database.commit();
            return newBook;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    public static boolean deleteByTitle(String title) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            Book book = bookRepository.findOneByTitle(title);
            if (book == null) {
                return false;
            }
            bookAuthorRepository.deleteByBook(book);
            bookKeywordRepository.deleteByBook(book);
            boolean isDeleted = bookRepository.deleteOne(book);
            Database.commit();
            return isDeleted;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    private static void insertForeign(Book book, Book newBook) {
        for (BookAuthor bookAuthor : book.getBookAuthors()) {
            Person newPerson = personRepository.upsertByName(bookAuthor.getPerson());
            bookAuthorRepository.insertOne(new BookAuthor(newBook, newPerson));
        }
        for (BookKeyword bookKeyword : book.getBookKeywords()) {
            Keyword keyword = bookKeyword.getKeyword();
            Keyword newKeyword = keywordRepository.findOneByTag(keyword);
            if (newKeyword == null) {
                newKeyword = keywordRepository.insertOne(keyword);
            }
            bookKeywordRepository.insertOne(new BookKeyword(newBook, newKeyword));
        }
    }
}
