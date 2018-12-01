package cscc43.assignment.model;

import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.JoinColumns;
import cscc43.assignment.persistence.ManyToOne;

@Entity(name="CrewMember")
public class CrewMember {
    @ManyToOne(targetEntity=Person.class)
    @JoinColumn(name="PeopleInvolved_ID", referencedColumnName="ID")
    private Person person;
    @ManyToOne(targetEntity=Movie.class)
    @JoinColumns({
        @JoinColumn(name="MovieName", referencedColumnName="MovieName"),
        @JoinColumn(name="ReleaseYear", referencedColumnName="Year")
    })
    private Movie movie;
    @ManyToOne(targetEntity=Role.class)
    @JoinColumn(name="Role_ID", referencedColumnName="ID")
    private Role role;

    public CrewMember() {
        this(null, null, null);
    }

    public CrewMember(Person person, Movie movie, Role role) {
        this.person = person;
        this.movie = movie;
        this.role = role;
    }

    public CrewMember initialize() {
        this.person = new Person();
        this.movie = new Movie();
        this.role = new Role();
        return this;
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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
