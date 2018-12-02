package cscc43.assignment.model;

import java.util.List;

import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.GeneratedValue;
import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Keyword")
public class Keyword {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="Tag")
    private String tag;
    @OneToMany(targetEntity=BookKeyword.class, mappedBy="keyword")
    private List<BookKeyword> bookKeywords;

    public Keyword() {
        this(-1l, "", null);
    }

    public Keyword(Long id, String tag, List<BookKeyword> bookKeywords) {
        this.id = id;
        this.tag = tag;
        this.bookKeywords = bookKeywords;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<BookKeyword> getBookKeywords() {
        return this.bookKeywords;
    }

    public void setBookKeywords(List<BookKeyword> bookKeywords) {
        this.bookKeywords = bookKeywords;
    }
}
