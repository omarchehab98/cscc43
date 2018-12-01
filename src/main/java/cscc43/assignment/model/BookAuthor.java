package cscc43.assignment.model;

import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.ManyToOne;

@Entity(name="BookAuthor")
public class BookAuthor {
    @ManyToOne(targetEntity=Book.class)
    @JoinColumn(name="ISBN", referencedColumnName="ISBN")
    private Book book;
    @ManyToOne(targetEntity=Person.class)
    @JoinColumn(name="Author_ID", referencedColumnName="ID")
    private Person person;

    public BookAuthor() {
        this(null, null);
    }

    public BookAuthor(Book book, Person person) {
        this.book = book;
        this.person = person;
    }

    public BookAuthor initialize() {
        this.setBook(new Book());
        this.setPerson(new Person());
        return this;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
