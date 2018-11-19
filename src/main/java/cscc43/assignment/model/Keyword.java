package cscc43.assignment.model;

import java.util.List;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Keyword")
public class Keyword {
    @Column(name="ID")
    private Integer id;
    @Column(name="Tag")
    private String tag;
    @OneToMany(targetEntity=BookKeyword.class, mappedBy="keyword")
    private List<BookKeyword> bookKeywords;

    public Keyword(Integer id, String tag, List<BookKeyword> bookKeywords) {
        this.id = id;
        this.tag = tag;
        this.bookKeywords = bookKeywords;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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
