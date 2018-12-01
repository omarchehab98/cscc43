package cscc43.assignment.store;

import java.util.List;

import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;

public class AppStateBuilder {
    private AppState state;

    public AppStateBuilder(AppState state) {
        this.state = new AppState();
        setPage(state.getPage());
        setBook(state.getBook());
        setMovie(state.getMovie());
        setMusicTracks(state.getMusicTracks());
    }

    public AppStateBuilder setPage(int page) {
        this.state.setPage(page);
        return this;
    }

    public AppStateBuilder setBook(Book book) {
        this.state.setBook(book);
        return this;
    }

    public AppStateBuilder setMovie(Movie movie) {
        this.state.setMovie(movie);
        return this;
    }

    public AppStateBuilder setMusicTracks(List<Music> musicTracks) {
        this.state.setMusicTracks(musicTracks);
        return this;
    }

    public AppState build() {
        return this.state;
    }
}