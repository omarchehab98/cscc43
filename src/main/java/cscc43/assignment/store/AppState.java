package cscc43.assignment.store;

import java.util.List;

import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;

public class AppState {
    private int page;
    private Book book;
    private Movie movie;
    private List<Music> musicTracks;

    public AppState() {}

    public AppState(int page) {
        this.page = page;
    }

    protected void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Music> getMusicTracks() {
        return this.musicTracks;
    }

    public void setMusicTracks(List<Music> musicTracks) {
        this.musicTracks = musicTracks;
    }

    public static class Pages {
        public static final int UPSERT_BOOK = 0;
        public static final int UPSERT_MUSIC = 1;
        public static final int UPSERT_MOVIE = 2;
        public static final int VIEW = 3;
        public static final int REPORT = 4;
    }
}
