package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cscc43.assignment.repository.RoleRepository;
import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.controller.MovieController;
import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.model.Award;
import cscc43.assignment.model.CrewMember;
import cscc43.assignment.model.Movie;
import cscc43.assignment.throwable.MovieUpsertException;

public class UpsertMoviePageView implements Observer, View {
    private RoleRepository roleRepository = new RoleRepository();
    private InputStringView nameView;
    private InputIntegerView yearOfReleaseView;
    private InputMovieCrewMembersView directorView;
    private InputMovieCrewMembersView scriptWriterView;
    private InputMovieCrewMembersView castView;
    private InputMovieCrewMembersView producerView;
    private InputMovieCrewMembersView composerView;
    private InputMovieCrewMembersView editorView;
    private InputMovieCrewMembersView costumeDesignerView;

    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        AppState state = App.getStore().getState();
        Movie movie = state.getMovie();

        panel.add(new HeadingView("Movie").render());

        nameView = new InputStringView("Name")
            .setDefaultText(movie.getName());
        panel.add(nameView.render());
        
        yearOfReleaseView = new InputIntegerView("Year of release")
            .setDefaultNumber(movie.getYear())
            .setMin(0);
        panel.add(yearOfReleaseView.render());

        directorView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Director"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersDirectors())
            .setMaxCrewMembers(3);
        panel.add(directorView.render());

        scriptWriterView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Script Writer"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersScriptWriters())
            .setMaxCrewMembers(3);
        panel.add(scriptWriterView.render());

        castView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Cast"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersCast())
            .setMaxCrewMembers(10);
        panel.add(castView.render());

        producerView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Movie Producer"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersProducers())
            .setMaxCrewMembers(3);
        panel.add(producerView.render());
        
        composerView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Composer"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersComposers())
            .setMaxCrewMembers(3);
        panel.add(composerView.render());

        editorView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Movie Editor"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersEditors())
            .setMaxCrewMembers(3);
        panel.add(editorView.render());
        
        costumeDesignerView = new InputMovieCrewMembersView(roleRepository.findOneByDescription("Costume Designer"))
            .setDefaultMovieCrewMembers(movie.getCrewMembersCostumeDesigners())
            .setMaxCrewMembers(3);
        panel.add(costumeDesignerView.render());
        
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
            AppState state = App.getStore().getState();
            boolean isInsert = state.getMovie().getName().equals("");
            
            Movie movie = new Movie();
            movie.setName(nameView.getValue());
            movie.setYear(yearOfReleaseView.getValue());
            List<CrewMember> crewMembers = new ArrayList<CrewMember>();
            crewMembers.addAll(directorView.getValue());
            crewMembers.addAll(scriptWriterView.getValue());
            crewMembers.addAll(castView.getValue());
            crewMembers.addAll(producerView.getValue());
            crewMembers.addAll(composerView.getValue());
            crewMembers.addAll(editorView.getValue());
            crewMembers.addAll(costumeDesignerView.getValue());
            movie.setCrewMembers(crewMembers);
            List<Award> awards = crewMembers.stream()
                .map((crewMember) -> new Award(crewMember.getAward().getAward(), crewMember.getPerson(), movie))
                .collect(Collectors.toList());
            movie.setAwards(awards);
    
            try {
                if (isInsert) {
                    MovieController.getInstance().insert(movie);
                } else {
                    MovieController.getInstance().update(movie);
                }
                MenuBarController.getInstance().setPage(-1);
            } catch (MovieUpsertException err) {
                System.out.println(err);
            }
        }
    }

    private class CancelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(-1);
        }
    }
}
