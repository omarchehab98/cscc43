package cscc43.assignment.model;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Movie")
public class Movie {
    @Id
    @Column(name="MovieName")
    private String name;
    @Id
    @Column(name="Year")
    private Integer year;
    @OneToMany(targetEntity=CrewMember.class, mappedBy="movie")
    private List<CrewMember> crewMembers;
    @OneToMany(targetEntity=Award.class, mappedBy="movie")
    private List<Award> awards;

    public Movie() {
        this("", 0, new ArrayList<CrewMember>(), new ArrayList<Award>());
    }

    public Movie(String name, Integer year, List<CrewMember> crewMembers, List<Award> awards) {
        this.name = name;
        this.year = year;
        this.crewMembers = crewMembers;
        this.awards = awards;
    }

    public Movie initialize() {
        this.crewMembers.add(new CrewMember().initialize());
        this.awards.add(new Award().initialize());
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCrewMembers(ArrayList<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public List<Award> getAwards() {
        return this.awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }

    public void setAwards(ArrayList<Award> awards) {
        this.awards = awards;
    }

    public List<CrewMember> getCrewMembersDirectors() {
        List<CrewMember> directors = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Director")) {
                directors.add(crewMember);
            }
        }
        return directors;
    }

    public List<CrewMember> getCrewMembersScriptWriters() {
        List<CrewMember> scriptWriters = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Script Writer")) {
                scriptWriters.add(crewMember);
            }
        }
        return scriptWriters;
    }

    public List<CrewMember> getCrewMembersCast() {
        List<CrewMember> scriptWriters = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Cast")) {
                scriptWriters.add(crewMember);
            }
        }
        return scriptWriters;
    }

    public List<CrewMember> getCrewMembersProducers() {
        List<CrewMember> producers = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Movie Producer")) {
                producers.add(crewMember);
            }
        }
        return producers;
    }

    public List<CrewMember> getCrewMembersComposers() {
        List<CrewMember> composers = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Composer")) {
                composers.add(crewMember);
            }
        }
        return composers;
    }

    public List<CrewMember> getCrewMembersEditors() {
        List<CrewMember> editors = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Movie Editor")) {
                editors.add(crewMember);
            }
        }
        return editors;
    }

    public List<CrewMember> getCrewMembersCostumeDesigners() {
        List<CrewMember> costumeDesigners = new ArrayList<CrewMember>();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getRole().getDescription().equals("Costume Designer")) {
                costumeDesigners.add(crewMember);
            }
        }
        return costumeDesigners;
    }

    public String toString() {
        return String.format(
            "Movie(%s, %d)",
            name,
            year
        );
    }
}
