package cscc43.assignment.controller;

import java.util.List;

import cscc43.assignment.model.Music;
import cscc43.assignment.service.MusicService;
import cscc43.assignment.throwable.MusicUpsertException;
import cscc43.assignment.throwable.DatabaseException;

public class MusicController {
    private static final MusicController instance = new MusicController();

    private MusicController() {}

    public void insert(List<Music> musicTracks) throws MusicUpsertException {
        try {
            MusicService.insertMany(musicTracks);
        } catch (DatabaseException err) {
            throw new MusicUpsertException(err.toString());
        }
    }

    public void update(List<Music> musicTracks) throws MusicUpsertException {
        try {
            MusicService.updateMany(musicTracks);
        } catch (DatabaseException err) {
            throw new MusicUpsertException(err.toString());
        }
    }

    public static MusicController getInstance() {
        return instance;
    }
}
