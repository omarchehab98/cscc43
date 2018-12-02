package cscc43.assignment.model;

import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.JoinColumns;
import cscc43.assignment.persistence.ManyToOne;

@Entity(name="MusicSinger")
public class MusicSinger {
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

    public MusicSinger() {
        this(null, null);
    }

    public MusicSinger(Music music, Person person) {
        this.music = music;
        this.person = person;
    }

    public MusicSinger initialize() {
        this.music = new Music();
        this.person = new Person();
        return this;
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

    public String toString() {
        return String.format(
            "MusicSinger(%s, %s)",
            music,
            person
        );
    }
}
