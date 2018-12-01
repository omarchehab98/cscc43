package cscc43.assignment.controller;

import java.util.List;

import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.store.AppStateBuilder;
import cscc43.assignment.store.Store;
import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;

public class MenuBarController {
    private static final MenuBarController instance = new MenuBarController();
    private static final Store<AppState> store = App.getStore();

    private MenuBarController() {}

    public void setPage(int page) {
        store.setState(new AppStateBuilder(store.getState())
            .setPage(page)
            .build());
    }

    public void setBook(Book book) {
        store.setState(new AppStateBuilder(store.getState())
            .setBook(book)
            .build());
    }

    public void setMovie(Movie movie) {
        store.setState(new AppStateBuilder(store.getState())
            .setMovie(movie)
            .build());
    }

    public void setMusicTracks(List<Music> musicTracks) {
        store.setState(new AppStateBuilder(store.getState())
            .setMusicTracks(musicTracks)
            .build());
    }

    public static MenuBarController getInstance() {
        return instance;
    }
}
