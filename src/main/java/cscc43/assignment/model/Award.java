package cscc43.assignment.model;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.JoinColumns;
import cscc43.assignment.persistence.ManyToOne;

@Entity(name="Award")
public class Award {
    @Column(name="Award")
    private Integer award;
    @ManyToOne(targetEntity=Person.class)
    @JoinColumn(name="PeopleInvolved_ID", referencedColumnName="ID")
    private Person person;
    @ManyToOne(targetEntity=Movie.class)
    @JoinColumns({
        @JoinColumn(name="MovieName", referencedColumnName="MovieName"),
        @JoinColumn(name="Year", referencedColumnName="Year")
    })
    private Movie movie;

    public Award(Integer award, Person person, Movie movie) {
        this.award = award;
        this.person = person;
        this.movie = movie;
    }

    public Integer getAward() {
        return this.award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
