package cscc43.assignment.view;

import cscc43.assignment.model.Person;
import cscc43.assignment.model.CrewMember;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMovieCrewMembersView implements View {
    private String role;
    private JPanel panel;
    private Iterable<CrewMember> defaultCrewMembers;
    private int numberOfCrewMembers = 0;
    private final int minCrewMembers = 0;
    private int maxCrewMembers;

    public InputMovieCrewMembersView(String role) {
        this.role = role;
        this.maxCrewMembers = Integer.MAX_VALUE;
    }

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new ButtonView(String.format("Add %s", role), new InsertCrewMemberAction()).render());
        panel.add(new ButtonView(String.format("Remove %s", role), new RemoveCrewMemberAction()).render());

        if (defaultCrewMembers == null) {
            insertCrewMember();
        } else {
            for (CrewMember crewMember : defaultCrewMembers) {
                insertCrewMember(crewMember);
            }
        }

        return panel;
    }

    public InputMovieCrewMembersView setDefaultMovieCrewMembers(Iterable<CrewMember> crewMembers) {
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
            panel.add(new HeadingView(String.format("%s %d", role, ++numberOfCrewMembers)).render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name")
                .setDefaultText(crewMember.getPerson().getFirstName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name")
                .setDefaultText(crewMember.getPerson().getMiddleName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name")
                .setDefaultText(crewMember.getPerson().getLastName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputDropdownView("Gender", new String[]{"Male", "Female"})
                .setDefaultOption(Person.Gender.toString(crewMember.getPerson().getGender()))
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputDropdownView("Won Prize", new String[]{"No", "Yes"})
                .setDefaultOption(crewMember.getPerson().getAwards().size() > 0 ? "Yes" : "No")
                .render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeCrewMember() {
        if (numberOfCrewMembers > minCrewMembers) {
            numberOfCrewMembers--;
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
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