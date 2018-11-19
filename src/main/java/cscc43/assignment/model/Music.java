package cscc43.assignment.model;

import java.util.List;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.JoinColumn;
import cscc43.assignment.persistence.OneToMany;
import cscc43.assignment.persistence.OneToOne;

@Entity(name="Music")
public class Music {
    @Column(name="AlbumName")
    private String albumName;
    @Column(name="Year")
    private Integer year;
    @Column(name="MusicName")
    private String musicName;
    @Column(name="Language")
    private String language;
    @Column(name="DiskType")
    private Integer diskType;
    @OneToOne(targetEntity=Person.class)
    @JoinColumn(name="Producer_ID")
    private Person producer;
    @OneToMany(targetEntity=MusicSinger.class, mappedBy="music")
    private List<MusicSinger> musicSingers;
    @OneToMany(targetEntity=MusicPerson.class, mappedBy="music")
    private List<MusicPerson> musicPersons;

    public Music(String albumName, Integer year, String musicName, String language, Integer diskType, Person producer, List<MusicSinger> musicSingers, List<MusicPerson> musicPersons) {
        this.albumName = albumName;
        this.year = year;
        this.musicName = musicName;
        this.language = language;
        this.diskType = diskType;
        this.producer = producer;
        this.musicSingers = musicSingers;
        this.musicPersons = musicPersons;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMusicName() {
        return this.musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDiskType() {
        return this.diskType;
    }

    public void setDiskType(Integer diskType) {
        this.diskType = diskType;
    }

    public Person getProducer() {
        return this.producer;
    }

    public void setProducer(Person producer) {
        this.producer = producer;
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
}
