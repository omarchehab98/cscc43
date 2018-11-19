package cscc43.assignment;

import javax.swing.JFrame;

import cscc43.assignment.store.AppState;
import cscc43.assignment.store.Store;
import cscc43.assignment.view.AppView;

public class App {
    private static Store<AppState> store = new Store<AppState>(new AppState(-1));
    private static JFrame frame;

    public static void main(String[] args) {
        frame = (JFrame) new AppView(store).render();
    }

    public static Store<AppState> getStore() {
        return store;
    }

    public static JFrame getFrame() {
        return frame;
    }
}
