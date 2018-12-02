package cscc43.assignment.model;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.JoinColumns;
import cscc43.assignment.persistence.ManyToOne;

@Entity(name="PeopleInvolvedMusic")
public class MusicPerson {
    @Column(name="IsSongWriter")
    private Integer isSongwriter;
    @Column(name="IsComposer")
    private Integer isComposer;
    @Column(name="IsArranger")
    private Integer isArranger;
    @Id
    @ManyToOne(targetEntity=Music.class)
    @JoinColumns({
        @JoinColumn(name="AlbumName", referencedColumnName="AlbumName"),
        @JoinColumn(name="Year", referencedColumnName="Year"),
        @JoinColumn(name="MusicName", referencedColumnName="MusicName")
    })
    private Music music;
    @Id
    @ManyToOne(targetEntity=Person.class)
    @JoinColumn(name="PeopleInvolved_ID", referencedColumnName="ID")
    private Person person;

    public MusicPerson() {
        this(0, 0, 0, null, null);
    }

    public MusicPerson(Integer isSongwriter, Integer isComposer, Integer isArranger, Music music, Person person) {
        this.isSongwriter = isSongwriter;
        this.isComposer = isComposer;
        this.isArranger = isArranger;
        this.music = music;
        this.person = person;
    }

    public Integer getIsSongwriter() {
        return this.isSongwriter;
    }

    public void setIsSongwriter(Integer isSongwriter) {
        this.isSongwriter = isSongwriter;
    }

    public Integer getIsComposer() {
        return this.isComposer;
    }

    public void setIsComposer(Integer isComposer) {
        this.isComposer = isComposer;
    }

    public Integer getIsArranger() {
        return this.isArranger;
    }

    public void setIsArranger(Integer isArranger) {
        this.isArranger = isArranger;
    }

    public Music getMusic() {
        return this.music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
