package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cscc43.assignment.App;
import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.controller.ReportController;
import cscc43.assignment.controller.ViewController;
import cscc43.assignment.store.AppState;
import cscc43.assignment.viewmodel.SearchResult;
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
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Book book = new Book().initialize();
                MenuBarController.getInstance().setBook(book);
                MenuBarController.getInstance().setPage(AppState.Pages.INSERT_BOOK);
            }
        });
        submenuView.add(menuItemView);
        // Menu > Data > Insert > Music
        menuItemView = new JMenuItem("Music");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                List<Music> musicTracks = new ArrayList<Music>();
                musicTracks.add(new Music().initialize());
                MenuBarController.getInstance().setMusicTracks(musicTracks);
                MenuBarController.getInstance().setPage(AppState.Pages.INSERT_MUSIC);
            }
        });
        submenuView.add(menuItemView);
        // Menu > Data > Insert > Movie
        menuItemView = new JMenuItem("Movie");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Movie movie = new Movie().initialize();
                MenuBarController.getInstance().setMovie(movie);
                MenuBarController.getInstance().setPage(AppState.Pages.INSERT_MOVIE);
            }
        });
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
        menuButtonView.addActionListener(new ViewDialogAction());
        menuBarView.add(menuButtonView);

        // Menu > Report
        menuView = new JMenu("Report");
        menuBarView.add(menuView);

        // Menu > Report > R1
        menuItemView = new JMenuItem("Authors' Publications");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                InputStringView firstNameView = new InputStringView("First name");
                panel.add(firstNameView.render());
                InputStringView middleNameView = new InputStringView("Middle name");
                panel.add(middleNameView.render());
                InputStringView lastNameView = new InputStringView("Last name");
                panel.add(lastNameView.render());
                panel.revalidate();
                panel.repaint();
          
                int result = JOptionPane.showConfirmDialog(
                    App.getFrame(),
                    panel,
                    "Authors' Publications",
                    JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    ReportController.getInstance().authorsPublications(
                        firstNameView.getValue(),
                        middleNameView.getValue(),
                        lastNameView.getValue()
                    );
                    MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
                }
            }
        });
        menuView.add(menuItemView);
        
        // Menu > Report > R2
        menuItemView = new JMenuItem("Publications in one Year");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                try {
                    String year = JOptionPane.showInputDialog(
                        App.getFrame(),
                        "Year",
                        "Publications in one Year",
                        JOptionPane.PLAIN_MESSAGE);
                    if (year != null) {
                        ReportController.getInstance().publicationsInOneYear(Integer.parseInt(year));
                        MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(App.getFrame(), "Year must be a number");
                    actionPerformed(e);
                }
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R3
        menuItemView = new JMenuItem("Books with Similar Topic");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                String subject = (String) JOptionPane.showInputDialog(
                    App.getFrame(),
                    "String representing the subject",
                    "Books with Similar Topic",
                    JOptionPane.PLAIN_MESSAGE);
                if (subject != null) {
                    ReportController.getInstance().booksWithSimilarTopic(subject);
                    MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
                }
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R4
        menuItemView = new JMenuItem("Frequent Publishers");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().frequentPublishers();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R5
        menuItemView = new JMenuItem("Most Popular Subjects");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().mostPopularSubjects();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R6
        menuItemView = new JMenuItem("Multi Skills Movie Crew");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().multiSkillsMovieCrew();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R7
        menuItemView = new JMenuItem("Award Winning Movies");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().awardWinningMovies();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R8
        menuItemView = new JMenuItem("Music with Similar Name");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().musicWithSimilarName();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R9
        menuItemView = new JMenuItem("Multi Skills Music Crew");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().multiSkillsMusicCrew();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
        menuView.add(menuItemView);

        // Menu > Report > R10
        menuItemView = new JMenuItem("Similar Names");
        menuItemView.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBarController.getInstance().setPage(-1);
                ReportController.getInstance().similarNames();
                MenuBarController.getInstance().setPage(AppState.Pages.REPORT);
            }
        });
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

    private class UpdateDialogAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(-1);
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
            MenuBarController.getInstance().setPage(-1);
            String s = (String)JOptionPane.showInputDialog(App.getFrame(), "Entity name", "Remove", JOptionPane.PLAIN_MESSAGE);
            if (s != null) {
                if (MenuBarController.getInstance().deleteEntityByName(s)) {
                    JOptionPane.showMessageDialog(App.getFrame(), "Entity has been deleted");
                } else {
                    JOptionPane.showMessageDialog(App.getFrame(), "Entity not found");
                }
            }
        }
    }

    private class ViewDialogAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(-1);
            String type = (String) JOptionPane.showInputDialog(
                App.getFrame(),
                "What are you searching for?",
                "Search",
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Book", "Music", "Movie"},
                "Book");
            if (type != null) {
                String term = (String) JOptionPane.showInputDialog(
                    App.getFrame(),
                    String.format("Enter %s name (or part of name)", type),
                    "Search",
                    JOptionPane.PLAIN_MESSAGE);
                if (term != null) {
                    if (ViewController.getInstance().search(type, term) != null) {
                        MenuBarController.getInstance().setPage(AppState.Pages.VIEW);
                    } else {
                        JOptionPane.showMessageDialog(App.getFrame(), String.format("No %s Entity matches the name you entered", type));
                    }
                }
            }
        }
    }
}
