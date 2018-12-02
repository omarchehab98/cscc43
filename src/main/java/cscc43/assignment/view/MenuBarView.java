package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import cscc43.assignment.App;
import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.store.AppState;
import cscc43.assignment.model.Book;
import cscc43.assignment.model.Movie;
import cscc43.assignment.model.Music;

public class MenuBarView implements View {
    public Component render() {
        JMenuBar menuBarView = new JMenuBar();
        JMenu menuView, submenuView;
        JMenuItem menuItemView;
        JButton menuButtonView;
        
        // Menu > Data
        menuView = new JMenu("Data");
        menuBarView.add(menuView);

        // Menu > Data > Insert
        submenuView = new JMenu("Insert");
        menuView.add(submenuView);
        
        // Menu > Data > Insert > Book
        menuItemView = new JMenuItem("Book");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.INSERT_BOOK));
        menuItemView.addActionListener(new SetBookAction());
        submenuView.add(menuItemView);
        // Menu > Data > Insert > Music
        menuItemView = new JMenuItem("Music");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.INSERT_MUSIC));
        menuItemView.addActionListener(new SetMusicTracksAction());
        submenuView.add(menuItemView);
        // Menu > Data > Insert > Movie
        menuItemView = new JMenuItem("Movie");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.INSERT_MOVIE));
        menuItemView.addActionListener(new SetMovieAction());
        submenuView.add(menuItemView);
        
        // Menu > Data > Update
        menuItemView = new JMenuItem("Update");
        menuItemView.addActionListener(new UpdateDialogAction());
        menuView.add(menuItemView);
        
        // Menu > Data > Remove
        menuItemView = new JMenuItem("Remove");
        menuItemView.addActionListener(new RemoveDialogAction());
        menuView.add(menuItemView);
        
        // Menu > View
        menuButtonView = new JButton("View");
        menuButtonView.addActionListener(new SetPageAction(AppState.Pages.VIEW));
        menuBarView.add(menuButtonView);

        // Menu > Report
        menuView = new JMenu("Report");
        menuBarView.add(menuView);

        // Menu > Report > R1
        menuItemView = new JMenuItem("R1");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);
        
        // Menu > Report > R2
        menuItemView = new JMenuItem("R2");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R3
        menuItemView = new JMenuItem("R3");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R4
        menuItemView = new JMenuItem("R4");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R5
        menuItemView = new JMenuItem("R5");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R6
        menuItemView = new JMenuItem("R6");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R7
        menuItemView = new JMenuItem("R7");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R8
        menuItemView = new JMenuItem("R8");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R9
        menuItemView = new JMenuItem("R9");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        // Menu > Report > R10
        menuItemView = new JMenuItem("R10");
        menuItemView.addActionListener(new SetPageAction(AppState.Pages.REPORT));
        menuView.add(menuItemView);

        return menuBarView;
    }

    private class SetPageAction implements ActionListener {
        private int page;

        public SetPageAction(int page) {
            this.page = page;
        }

        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(this.page);
        }
    }

    private class SetBookAction implements ActionListener {
        private Book book;

        public SetBookAction() {
            this.book = new Book().initialize();
        }

        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setBook(this.book);
        }
    }

    private class SetMovieAction implements ActionListener {
        private Movie movie;

        public SetMovieAction() {
            this.movie = new Movie().initialize();
        }

        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setMovie(this.movie);
        }
    }

    private class SetMusicTracksAction implements ActionListener {
        private List<Music> musicTracks;

        public SetMusicTracksAction() {
            this.musicTracks = new ArrayList<Music>();
            this.musicTracks.add(new Music().initialize());
        }

        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setMusicTracks(this.musicTracks);
        }
    }

    private class UpdateDialogAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = (String)JOptionPane.showInputDialog(App.getFrame(), "Entity name", "Update", JOptionPane.PLAIN_MESSAGE);
            if (s != null) {
                Object entity = MenuBarController.getInstance().findEntityByName(s);
                if (entity != null) {
                    if (entity instanceof Book) {
                        MenuBarController.getInstance().setBook((Book) entity);
                        MenuBarController.getInstance().setPage(AppState.Pages.UPDATE_BOOK);
                    } else if (entity instanceof Movie) {
                        MenuBarController.getInstance().setMovie((Movie) entity);
                        MenuBarController.getInstance().setPage(AppState.Pages.UPDATE_MOVIE);
                    } else if (entity instanceof List) {
                        MenuBarController.getInstance().setMusicTracks((List<Music>) entity);
                        MenuBarController.getInstance().setPage(AppState.Pages.UPDATE_MUSIC);
                    }
                } else {
                    JOptionPane.showMessageDialog(App.getFrame(), "Entity not found");
                }
            }
        }
    }

    private class RemoveDialogAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = (String)JOptionPane.showInputDialog(App.getFrame(), "Entity name", "Remove", JOptionPane.PLAIN_MESSAGE);
            if (s != null) {
                if (MenuBarController.getInstance().deleteEntityByName(s)) {
                    MenuBarController.getInstance().setPage(AppState.Pages.VIEW);
                } else {
                    JOptionPane.showMessageDialog(App.getFrame(), "Entity not found");
                }
            }
        }
    }
}
