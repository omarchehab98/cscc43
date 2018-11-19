package cscc43.assignment.store;

public class AppState {
    private int page;

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
