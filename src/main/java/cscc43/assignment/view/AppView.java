package cscc43.assignment.view;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import cscc43.assignment.store.AppState;
import cscc43.assignment.store.Store;

public class AppView implements Observer, View {
    private JFrame frame;
    private JPanel panel;
    private int page;

    public AppView(Store<AppState> store) {
        store.addObserver(this);
        page = store.getState().getPage();
    }

    public Component render() {
        frame = new JFrame("CSCC43 Assignment");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setJMenuBar((JMenuBar) new MenuBarView().render());
        
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(1, 1));
        repaintPage();
        
        frame.setVisible(true);
        return frame;
    }

    public void update(Observable o, Object arg) {
        AppState state = (AppState) arg;
        int currPage = state.getPage();
        if (currPage != page) {
            page = currPage;
            repaintPage();
        }
    }

    private void repaintPage() {
        panel.removeAll();
        if (page != -1) {
            panel.add(PageViewFactory.make(page).render());
        }
        panel.revalidate();
        panel.repaint();
    }
}