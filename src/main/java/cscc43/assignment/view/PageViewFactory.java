package cscc43.assignment.view;

import cscc43.assignment.store.AppState;
import cscc43.assignment.view.InsertBookPageView;
import cscc43.assignment.view.InsertMoviePageView;
import cscc43.assignment.view.InsertMusicPageView;
import cscc43.assignment.view.ReportPageView;
import cscc43.assignment.view.UpdateBookPageView;
import cscc43.assignment.view.UpdateMoviePageView;
import cscc43.assignment.view.UpdateMusicPageView;
import cscc43.assignment.view.ViewPageView;
import cscc43.assignment.view.View;

public class PageViewFactory {
    public static View make(int page) {
        switch (page) {
            case AppState.Pages.INSERT_BOOK:
                return new InsertBookPageView();
            case AppState.Pages.INSERT_MOVIE:
                return new InsertMoviePageView();
            case AppState.Pages.INSERT_MUSIC:
                return new InsertMusicPageView();
            case AppState.Pages.REPORT:
                return new ReportPageView();
            case AppState.Pages.UPDATE_BOOK:
                return new UpdateBookPageView();
            case AppState.Pages.UPDATE_MOVIE:
                return new UpdateMoviePageView();
            case AppState.Pages.UPDATE_MUSIC:
                return new UpdateMusicPageView();
            case AppState.Pages.VIEW:
                return new ViewPageView();
        }
        throw new IllegalArgumentException();
    }
}