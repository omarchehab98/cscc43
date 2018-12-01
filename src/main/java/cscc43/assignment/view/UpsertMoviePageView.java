package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.model.Movie;

public class UpsertMoviePageView implements Observer, View {
    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        AppState state = App.getStore().getState();
        Movie movie = state.getMovie();

        panel.add(new HeadingView("Movie").render());
        panel.add(new InputStringView("Name")
            .setDefaultText(movie.getMovieName())
            .render());
        panel.add(new InputIntegerView("Year of release")
            .setDefaultNumber(movie.getYear())
            .setMin(0)
            .render());
        panel.add(new InputMovieCrewMembersView("Director")
            .setDefaultMovieCrewMembers(movie.getCrewMembersDirectors())
            .setMaxCrewMembers(3)
            .render());
        panel.add(new InputMovieCrewMembersView("Script Writer")
            .setDefaultMovieCrewMembers(movie.getCrewMembersScriptWriters())
            .setMaxCrewMembers(3)
            .render());
        panel.add(new InputMovieCrewMembersView("Cast")
            .setDefaultMovieCrewMembers(movie.getCrewMembersCast())
            .setMaxCrewMembers(10)
            .render());
        panel.add(new InputMovieCrewMembersView("Producer")
            .setDefaultMovieCrewMembers(movie.getCrewMembersProducers())
            .setMaxCrewMembers(3)
            .render());
        panel.add(new InputMovieCrewMembersView("Composer")
            .setDefaultMovieCrewMembers(movie.getCrewMembersComposers())
            .setMaxCrewMembers(3)
            .render());
        panel.add(new InputMovieCrewMembersView("Editor")
            .setDefaultMovieCrewMembers(movie.getCrewMembersEditors())
            .setMaxCrewMembers(3)
            .render());
        panel.add(new InputMovieCrewMembersView("Costume Designer")
            .setDefaultMovieCrewMembers(movie.getCrewMembersCostumeDesigners())
            .setMaxCrewMembers(3)
            .render());
        panel.add(new ButtonView("Submit", new SubmitAction()).render());
        panel.add(new ButtonView("Cancel", new CancelAction()).render());
        panel.add(new FillerView().render());

        scrollPane.setViewportView(panel);

        return scrollPane;
    }

    public void update(Observable o, Object arg) {

    }

    private class SubmitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(-1);
        }
    }

    private class CancelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(-1);
        }
    }
}