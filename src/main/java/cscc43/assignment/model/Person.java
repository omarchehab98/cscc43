package cscc43.assignment.model;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.GeneratedValue;
import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;
import cscc43.assignment.persistence.OneToOne;

@Entity(name="PeopleInvolved")
public class Person {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="MiddleName")
    private String middleName;
    @Column(name="FamilyName")
    private String lastName;
    @Column(name="Gender")
    private Integer gender;
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

    public Person() {
        this(-1l, "", "", "", 0, new ArrayList<BookAuthor>(), new ArrayList<CrewMember>(), new ArrayList<Award>(), new ArrayList<MusicSinger>(), new ArrayList<MusicPerson>(), null);
    }

    public Person(Long id) {
        this(id, "", "", "", 0, new ArrayList<BookAuthor>(), new ArrayList<CrewMember>(), new ArrayList<Award>(), new ArrayList<MusicSinger>(), new ArrayList<MusicPerson>(), null);
    }

    public Person(String firstName, String middleName, String lastName, Integer gender) {
        this(-1l, firstName, middleName, lastName, gender, new ArrayList<BookAuthor>(), new ArrayList<CrewMember>(), new ArrayList<Award>(), new ArrayList<MusicSinger>(), new ArrayList<MusicPerson>(), null);
    }

    public Person(Long id, String firstName, String middleName, String lastName, Integer gender, List<BookAuthor> bookAuthors, List<CrewMember> crewMembers, List<Award> awards, List<MusicSinger> musicSingers, List<MusicPerson> musicPersons, Music producedMusic) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = new Long(id);
    }

    public void setId(Long id) {
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

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
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

    public String toString() {
        return String.format(
            "Person(%d, %s, %s, %s, %d)",
            id,
            firstName,
            middleName,
            lastName,
            gender
        );
    }

    public static class Gender {
        private Gender() {}

        public static String toString(Integer gender) {
            if (gender == 0) return "Female";
            if (gender == 1) return "Male";
            throw new UnsupportedOperationException();
        }

        public static Integer fromString(String gender) {
            if (gender.equals("Female")) return 0;
            if (gender.equals("Male")) return 1;
            throw new UnsupportedOperationException();
        }
    }
}
