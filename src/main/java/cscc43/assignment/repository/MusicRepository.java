package cscc43.assignment.repository;

import java.util.List;

import cscc43.assignment.database.Repository;
import cscc43.assignment.model.Music;

public class MusicRepository extends Repository<Music> {
    public Music findOneByMusicName(String musicName) {
        String where = String.format("`%s`=?", columnName(Music.class, "musicName"));
        return findOneWhere(where, musicName);
    }

    public List<Music> findByAlbumName(String albumName) {
        String where = String.format("`%s`=?", columnName(Music.class, "albumName"));
        List<Music> musicTracks = findWhere(where, albumName);
        for (int i = 0; i < musicTracks.size(); i++) {
            musicTracks.set(i, (Music) populate(musicTracks.get(i), null));
        }
        return musicTracks;
    }

    public boolean deleteByAlbumName(String albumName) {
        String where = String.format("`%s`=?", columnName(Music.class, "albumName"));
        return deleteWhere(where, albumName);
    }
}
