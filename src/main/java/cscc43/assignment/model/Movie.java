package cscc43.assignment.model;

import java.util.List;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Movie")
public class Movie {
    @Column(name="MovieName")
    private String movieName;
    @Column(name="Year")
    private Integer year;
    @OneToMany(targetEntity=CrewMember.class, mappedBy="movie")
    private List<CrewMember> crewMembers;
    @OneToMany(targetEntity=Award.class, mappedBy="movie")
    private List<Award> awards;

    public Movie(String movieName, Integer year, List<CrewMember> crewMembers, List<Award> awards) {
        this.movieName = movieName;
        this.year = year;
        this.crewMembers = crewMembers;
        this.awards = awards;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<CrewMember> getCrewMembers() {
        return this.crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public List<Award> getAwards() {
        return this.awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }
}
