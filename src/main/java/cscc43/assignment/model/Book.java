package cscc43.assignment.model;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Book")
public class Book {
    @Id
    @Column(name="ISBN")
    private String isbn;
    @Column(name="Title")
    private String title;
    @Column(name="Publisher")
    private String publisher;
    @Column(name="NumberOfPages")
    private Integer numberOfPages;
    @Column(name="YearOfPublication")
    private Integer yearOfPublication;
    @Column(name="EditionNumber")
    private Integer editionNumber;
    @Column(name="Abstract")
    private String description;
    @OneToMany(targetEntity=BookAuthor.class, mappedBy="book")
    private List<BookAuthor> bookAuthors;
    @OneToMany(targetEntity=BookKeyword.class, mappedBy="book")
    private List<BookKeyword> bookKeywords;

    public Book() {
        this("", "", "", 0, 0, 0, "", new ArrayList<BookAuthor>(), new ArrayList<BookKeyword>());
    }

    public Book(String isbn) {
        this(isbn, "", "", 0, 0, 0, "", new ArrayList<BookAuthor>(), new ArrayList<BookKeyword>());
    }

    public Book(String isbn, String title, String publisher, Integer numberOfPages, Integer yearOfPublication, Integer editionNumber, String description, List<BookAuthor> bookAuthors, List<BookKeyword> bookKeywords) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.yearOfPublication = yearOfPublication;
        this.editionNumber = editionNumber;
        this.description = description;
        this.bookAuthors = bookAuthors;
        this.bookKeywords = bookKeywords;
    }

    public Book initialize() {
        this.bookAuthors.add(new BookAuthor().initialize());
        this.bookKeywords.add(new BookKeyword().initialize());
        return this;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getYearOfPublication() {
        return this.yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Integer getEditionNumber() {
        return this.editionNumber;
    }

    public void setEditionNumber(Integer editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BookAuthor> getBookAuthors() {
        return this.bookAuthors;
    }

    public void setBookAuthors(List<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public void setBookAuthors(ArrayList<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public List<BookKeyword> getBookKeywords() {
        return this.bookKeywords;
    }

    public void setBookKeywords(List<BookKeyword> bookKeywords) {
        this.bookKeywords = bookKeywords;
    }

    public void setBookKeywords(ArrayList<BookKeyword> bookKeywords) {
        this.bookKeywords = bookKeywords;
    }

    public String toString() {
        return String.format(
            "Book(%s, %s, %s, %d, %d, %d, %s)",
            isbn, title, publisher, numberOfPages, yearOfPublication, editionNumber, description
        );
    }
}
