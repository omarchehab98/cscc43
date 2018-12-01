package cscc43.assignment.model;

import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.ManyToOne;

@Entity(name="BookKeyword")
public class BookKeyword {
    @ManyToOne(targetEntity=Book.class)
    @JoinColumn(name="ISBN", referencedColumnName="ISBN")
    private Book book;
    @ManyToOne(targetEntity=Keyword.class)
    @JoinColumn(name="Keyword_ID", referencedColumnName="ID")
    private Keyword keyword;

    public BookKeyword() {
        this(null, null);
    }

    public BookKeyword(Book book, Keyword keyword) {
        this.book = book;
        this.keyword = keyword;
    }

    public BookKeyword initialize() {
        this.setBook(new Book());
        this.setKeyword(new Keyword());
        return this;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Keyword getKeyword() {
        return this.keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
}
