package cscc43.assignment.store;

public class AppStateBuilder {
    private AppState state;

    public AppStateBuilder(AppState state) {
        this.state = new AppState();
        setPage(state.getPage());
    }

    public AppStateBuilder setPage(int page) {
        this.state.setPage(page);
        return this;
    }

    public AppState build() {
        return this.state;
    }
}