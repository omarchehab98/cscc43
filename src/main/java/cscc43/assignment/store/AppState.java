package cscc43.assignment.store;

import java.util.List;

import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;
import cscc43.assignment.viewmodel.SearchResult;

public class AppState {
    private int page;
    private Book book;
    private Movie movie;
    private List<Music> musicTracks;
    private SearchResult searchResult;

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
    
    public SearchResult getSearchResult() {
        return this.searchResult;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public static class Pages {
        public static final int INSERT_BOOK = 0;
        public static final int INSERT_MUSIC = 1;
        public static final int INSERT_MOVIE = 2;
        public static final int UPDATE_BOOK = 3;
        public static final int UPDATE_MUSIC = 4;
        public static final int UPDATE_MOVIE = 5;
        public static final int VIEW = 6;
        public static final int REPORT = 7;
    }
}
