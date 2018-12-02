package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Music;
import cscc43.assignment.model.MusicSinger;

public class MusicSingerRepository extends Repository<MusicSinger> {
    public List<MusicSinger> findByMusic(Music music) {
        return findByForeignEntity("music", music);
    }

    public boolean deleteByMusic(Music music) {
        return deleteByForeignEntity("music", music);
    }
}
