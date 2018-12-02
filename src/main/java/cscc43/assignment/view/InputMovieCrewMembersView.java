package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.model.Person;
import cscc43.assignment.model.CrewMember;
import cscc43.assignment.model.Role;
import cscc43.assignment.model.Award;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMovieCrewMembersView implements View {
    private Role role;
    private JPanel panel;
    private List<CrewMemberView> crewMemberViews;
    private List<CrewMember> defaultCrewMembers;
    private int numberOfCrewMembers = 0;
    private final int minCrewMembers = 0;
    private int maxCrewMembers;

    public InputMovieCrewMembersView(Role role) {
        this.role = role;
        this.maxCrewMembers = Integer.MAX_VALUE;
    }

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.crewMemberViews = new ArrayList<CrewMemberView>();

        panel.add(new ButtonView(String.format("Add %s", role.getDescription()), new InsertCrewMemberAction()).render());
        panel.add(new ButtonView(String.format("Remove %s", role.getDescription()), new RemoveCrewMemberAction()).render());

        if (defaultCrewMembers == null) {
            insertCrewMember();
        } else {
            for (CrewMember crewMember : defaultCrewMembers) {
                insertCrewMember(crewMember);
            }
        }

        return panel;
    }

    public InputMovieCrewMembersView setDefaultMovieCrewMembers(List<CrewMember> crewMembers) {
        this.defaultCrewMembers = crewMembers;
        return this;
    }

    public InputMovieCrewMembersView setMaxCrewMembers(int maxCrewMembers) {
        this.maxCrewMembers = maxCrewMembers;
        return this;
    }

    private void insertCrewMember() {
        insertCrewMember(new CrewMember().initialize());
    }

    private void insertCrewMember(CrewMember crewMember) {
        if (numberOfCrewMembers < maxCrewMembers) {
            panel.add(new HeadingView(String.format("%s %d", role.getDescription(), ++numberOfCrewMembers)).render(), panel.getComponentCount() - 2);
            CrewMemberView crewMemberView = new CrewMemberView(
                new InputStringView("First name")
                    .setDefaultText(crewMember.getPerson().getFirstName()),
                new InputStringView("Middle name")
                    .setDefaultText(crewMember.getPerson().getMiddleName()),
                new InputStringView("Last name")
                    .setDefaultText(crewMember.getPerson().getLastName()),
                new InputDropdownView("Gender", new String[]{"Male", "Female"})
                    .setDefaultOption(Person.Gender.toString(crewMember.getPerson().getGender())),
                new InputDropdownView("Won Prize", new String[]{"No", "Yes"})
                    .setDefaultOption(Award.toString(crewMember.getAward().getAward()))
            );
            this.crewMemberViews.add(crewMemberView);
            panel.add(crewMemberView.firstNameView.render(), panel.getComponentCount() - 2);
            panel.add(crewMemberView.middleNameView.render(), panel.getComponentCount() - 2);
            panel.add(crewMemberView.lastNameView.render(), panel.getComponentCount() - 2);
            panel.add(crewMemberView.genderView.render(), panel.getComponentCount() - 2);
            panel.add(crewMemberView.awardView.render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeCrewMember() {
        if (numberOfCrewMembers > minCrewMembers) {
            numberOfCrewMembers--;
            this.crewMemberViews.remove(this.crewMemberViews.size() - 1);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    public List<CrewMember> getValue() {
        List<CrewMember> crewMembers = new ArrayList<CrewMember>();
        for (CrewMemberView crewMemberView : crewMemberViews) {
            CrewMember crewMember = new CrewMember();
            Person person = new Person();
            person.setFirstName(crewMemberView.firstNameView.getValue());
            person.setMiddleName(crewMemberView.middleNameView.getValue());
            person.setLastName(crewMemberView.lastNameView.getValue());
            person.setGender(Person.Gender.fromString(crewMemberView.genderView.getValue()));
            crewMember.setPerson(person);
            crewMember.setRole(role);
            crewMember.setAward(new Award(Award.fromString(crewMemberView.awardView.getValue())));
            crewMembers.add(crewMember);
        }
        return crewMembers;
    }

    private class CrewMemberView {
        public InputStringView firstNameView;
        public InputStringView middleNameView;
        public InputStringView lastNameView;
        public InputDropdownView genderView;
        public InputDropdownView awardView;

        public CrewMemberView(
            InputStringView firstNameView,
            InputStringView middleNameView,
            InputStringView lastNameView,
            InputDropdownView genderView,
            InputDropdownView awardView
        ) {
            this.firstNameView = firstNameView;
            this.middleNameView = middleNameView;
            this.lastNameView = lastNameView;
            this.genderView = genderView;
            this.awardView = awardView;
        }
    }

    private class InsertCrewMemberAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            insertCrewMember();
        }
    }

    private class RemoveCrewMemberAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeCrewMember();
        }
    }
}
