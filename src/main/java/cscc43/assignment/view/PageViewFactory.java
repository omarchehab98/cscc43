package cscc43.assignment.view;

import cscc43.assignment.store.AppState;
import cscc43.assignment.view.UpsertBookPageView;
import cscc43.assignment.view.UpsertMoviePageView;
import cscc43.assignment.view.UpsertMusicPageView;
import cscc43.assignment.view.ReportPageView;
import cscc43.assignment.view.ViewPageView;
import cscc43.assignment.view.View;

public class PageViewFactory {
    public static View make(int page) {
        switch (page) {
            case AppState.Pages.INSERT_BOOK:
            case AppState.Pages.UPDATE_BOOK:
                return new UpsertBookPageView();
            case AppState.Pages.INSERT_MOVIE:
            case AppState.Pages.UPDATE_MOVIE:
                return new UpsertMoviePageView();
            case AppState.Pages.INSERT_MUSIC:
            case AppState.Pages.UPDATE_MUSIC:
                return new UpsertMusicPageView();
            case AppState.Pages.REPORT:
                return new ReportPageView();
            case AppState.Pages.VIEW:
                return new ViewPageView();
        }
        throw new IllegalArgumentException();
    }
}
