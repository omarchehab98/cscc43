package cscc43.assignment.model;

import java.util.List;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;
import cscc43.assignment.persistence.OneToOne;

@Entity(name="PeopleInvolved")
public class Person {
    @Column(name="ID")
    private Integer id;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="MiddleName")
    private String middleName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="Gender")
    private String gender;
    @OneToMany(targetEntity=BookAuthor.class, mappedBy="person")
    private List<BookAuthor> bookAuthors;
    @OneToMany(targetEntity=CrewMember.class, mappedBy="person")
    private List<CrewMember> crewMembers;
    @OneToMany(targetEntity=Award.class, mappedBy="person")
    private List<Award> awards;
    @OneToMany(targetEntity=MusicSinger.class, mappedBy="person")
    private List<MusicSinger> musicSingers;
    @OneToMany(targetEntity=MusicPerson.class, mappedBy="person")
    private List<MusicPerson> musicPersons;
    @OneToOne(targetEntity=Music.class, mappedBy="producer")
    private Music producedMusic;

    public Person(Integer id, String firstName, String middleName, String lastName, String gender, List<BookAuthor> bookAuthors, List<CrewMember> crewMembers, List<Award> awards, List<MusicSinger> musicSingers, List<MusicPerson> musicPersons, Music producedMusic) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.bookAuthors = bookAuthors;
        this.crewMembers = crewMembers;
        this.awards = awards;
        this.musicSingers = musicSingers;
        this.musicPersons = musicPersons;
        this.producedMusic = producedMusic;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<BookAuthor> getBookAuthors() {
        return this.bookAuthors;
    }

    public void setBookAuthors(List<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
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

    public List<MusicSinger> getMusicSingers() {
        return this.musicSingers;
    }

    public void setMusicSingers(List<MusicSinger> musicSingers) {
        this.musicSingers = musicSingers;
    }

    public List<MusicPerson> getMusicPersons() {
        return this.musicPersons;
    }

    public void setMusicPersons(List<MusicPerson> musicPersons) {
        this.musicPersons = musicPersons;
    }

    public Music getProducedMusic() {
        return this.producedMusic;
    }

    public void setProducedMusic(Music producedMusic) {
        this.producedMusic = producedMusic;
    }
}
