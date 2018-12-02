package cscc43.assignment.service;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

import cscc43.assignment.database.Database;
import cscc43.assignment.model.Music;
import cscc43.assignment.model.Person;
import cscc43.assignment.model.MusicSinger;
import cscc43.assignment.model.MusicPerson;
import cscc43.assignment.repository.MusicRepository;
import cscc43.assignment.repository.MusicSingerRepository;
import cscc43.assignment.repository.MusicPersonRepository;
import cscc43.assignment.repository.PersonRepository;
import cscc43.assignment.throwable.DatabaseException;

public class MusicService {
    private static final MusicRepository musicRepository = new MusicRepository();
    private static final MusicSingerRepository musicSingerRepository = new MusicSingerRepository();
    private static final MusicPersonRepository musicPersonRepository = new MusicPersonRepository();
    private static final PersonRepository personRepository = new PersonRepository();

    public static List<Music> insertMany(List<Music> musicTracks) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            List<Music> results = new ArrayList<Music>();
            Person producer = musicTracks.get(0).getProducer();
            producer.setId(personRepository.upsertByName(producer).getId());
            for (Music music : musicTracks) {
                Music newMusic = musicRepository.insertOne(music);
                insertForeign(music, newMusic);
                results.add(newMusic);
            }
            Database.commit();
            return results;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    public static List<Music> updateMany(List<Music> musicTracks) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            List<Music> results = new ArrayList<Music>();
            for (Music music : musicTracks) {
                Music newMusic = musicRepository.updateOne(music);
                musicSingerRepository.deleteByMusic(music);
                musicPersonRepository.deleteByMusic(music);
                insertForeign(music, newMusic);
                results.add(newMusic);
            }
            Database.commit();
            return results;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    public static boolean deleteByAlbumName(String albumName) {
        Connection connection = Database.connect();
        try {
            connection.setAutoCommit(false);
            boolean isDeleted = false;
            List<Music> musicTracks = musicRepository.findByAlbumName(albumName);
            for (Music music : musicTracks) {
                musicSingerRepository.deleteByMusic(music);
                musicPersonRepository.deleteByMusic(music);
                isDeleted |= musicRepository.deleteOne(music);
            }
            Database.commit();
            return isDeleted;
        } catch (SQLException|DatabaseException err) {
            err.printStackTrace();
            Database.rollback();
            throw new DatabaseException(err.getMessage());
        } finally {
            Database.disconnect();
        }
    }

    private static void insertForeign(Music music, Music newMusic) {
        for (MusicPerson musicPerson : music.getMusicPersons()) {
            Person newPerson = personRepository.upsertByName(musicPerson.getPerson());
            musicPerson.setPerson(newPerson);
        }
        List<MusicPerson> musicPersons = music.getMusicPersons();
        for (int i = 0; i < musicPersons.size(); i++) {
            MusicPerson musicPersonA = musicPersons.get(i);
            for (int j = i + 1; j < musicPersons.size(); j++) {
                MusicPerson musicPersonB = musicPersons.get(j);
                if (musicPersonA.getPerson().getId().equals(musicPersonB.getPerson().getId())) {
                    musicPersonA.setIsArranger(musicPersonA.getIsArranger() + musicPersonB.getIsArranger());
                    musicPersonA.setIsComposer(musicPersonA.getIsComposer() + musicPersonB.getIsComposer());
                    musicPersonA.setIsSongwriter(musicPersonA.getIsSongwriter() + musicPersonB.getIsSongwriter());
                    musicPersons.remove(j--);
                }
            }
        }
        for (MusicPerson musicPerson : music.getMusicPersons()) {
            musicPersonRepository.insertOne(new MusicPerson(musicPerson.getIsSongwriter(), musicPerson.getIsComposer(), musicPerson.getIsArranger(), newMusic, musicPerson.getPerson()));
        }

        for (MusicSinger musicSinger : music.getMusicSingers()) {
            Person newPerson = personRepository.upsertByName(musicSinger.getPerson());
            musicSingerRepository.insertOne(new MusicSinger(newMusic, newPerson));
        }
    }
}
