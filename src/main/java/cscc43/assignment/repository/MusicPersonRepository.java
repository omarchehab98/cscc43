package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Music;
import cscc43.assignment.model.MusicPerson;

public class MusicPersonRepository extends Repository<MusicPerson> {
    public List<MusicPerson> findByMusic(Music music) {
        return findByForeignEntity("music", music);
    }

    public boolean deleteByMusic(Music music) {
        return deleteByForeignEntity("music", music);
    }
}
