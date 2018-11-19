package cscc43.assignment.controller;

import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.store.AppStateBuilder;
import cscc43.assignment.store.Store;

public class MenuBarController {
    private static final MenuBarController instance = new MenuBarController();
    private static final Store<AppState> store = App.getStore();

    private MenuBarController() {}

    public void setPage(int page) {
        store.setState(new AppStateBuilder(store.getState())
            .setPage(page)
            .build());
    }

    public static MenuBarController getInstance() {
        return instance;
    }
}
