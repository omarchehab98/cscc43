package cscc43.assignment.controller;

import java.util.List;

import cscc43.assignment.App;
import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;
import cscc43.assignment.repository.BookRepository;
import cscc43.assignment.repository.MovieRepository;
import cscc43.assignment.repository.MusicRepository;
import cscc43.assignment.service.BookService;
import cscc43.assignment.service.MovieService;
import cscc43.assignment.service.MusicService;
import cscc43.assignment.store.AppState;
import cscc43.assignment.store.AppStateBuilder;
import cscc43.assignment.store.Store;

public class MenuBarController {
    private static final MenuBarController instance = new MenuBarController();
    private static final Store<AppState> store = App.getStore();
    private static final BookRepository bookRepository = new BookRepository();
    private static final MovieRepository movieRepository = new MovieRepository();
    private static final MusicRepository musicRepository = new MusicRepository();

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

    public Object findEntityByName(String name) {
        Book book = bookRepository.findOneByTitle(name);
        if (book != null) {
            return book;
        }
        Movie movie = movieRepository.findOneByName(name);
        if (movie != null) {
            return movie;
        }
        List<Music> music = musicRepository.findByAlbumName(name);
        if (music.size() > 0) {
            return music;
        }
        return null;
    }

    public boolean deleteEntityByName(String name) {
        return BookService.deleteByTitle(name) ||
            MovieService.deleteByName(name) ||
            MusicService.deleteByAlbumName(name);
    }

    public static MenuBarController getInstance() {
        return instance;
    }
}
