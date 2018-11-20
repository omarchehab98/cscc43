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
import cscc43.assignment.view.util.JTextFieldLimit;

public class InsertMoviePageView implements Observer, View {
    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new HeadingView("Movie").render());
        panel.add(new InputStringView("Name").render());
        panel.add(new InputIntegerView("Year of release").setMin(0).render());
        panel.add(new InputStringView("Description").setDocument(new JTextFieldLimit(5000)).render());
        panel.add(new InputMovieCrewMembersView("Director").setMaxCrewMembers(3).render());
        panel.add(new InputMovieCrewMembersView("Script Writer").setMaxCrewMembers(3).render());
        panel.add(new InputMovieCrewMembersView("Cast").setMaxCrewMembers(10).render());
        panel.add(new InputMovieCrewMembersView("Producer").setMaxCrewMembers(3).render());
        panel.add(new InputMovieCrewMembersView("Composer").setMaxCrewMembers(3).render());
        panel.add(new InputMovieCrewMembersView("Editor").setMaxCrewMembers(3).render());
        panel.add(new InputMovieCrewMembersView("Costume Designer").setMaxCrewMembers(3).render());
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