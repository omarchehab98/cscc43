package cscc43.assignment.store;

import java.util.List;

import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;
import cscc43.assignment.viewmodel.Report;
import cscc43.assignment.viewmodel.SearchResult;

public class AppStateBuilder {
    private AppState state;

    public AppStateBuilder(AppState state) {
        this.state = new AppState();
        setPage(state.getPage());
        setBook(state.getBook());
        setMovie(state.getMovie());
        setMusicTracks(state.getMusicTracks());
        setSearchResult(state.getSearchResult());
        setReport(state.getReport());
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

    public AppStateBuilder setSearchResult(SearchResult searchResult) {
        this.state.setSearchResult(searchResult);
        return this;
    }

    public AppStateBuilder setReport(Report report) {
        this.state.setReport(report);
        return this;
    }

    public AppState build() {
        return this.state;
    }
}
