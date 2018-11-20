package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMovieCrewMembersView implements View {
    private String role;
    private JPanel panel;
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

        return panel;
    }

    public InputMovieCrewMembersView setMaxCrewMembers(int maxCrewMembers) {
        this.maxCrewMembers = maxCrewMembers;
        return this;
    }

    private void insertCrewMember() {
        if (numberOfCrewMembers < maxCrewMembers) {
            panel.add(new HeadingView(String.format("%s %d", role, ++numberOfCrewMembers)).render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name").render(), panel.getComponentCount() - 2);
            panel.add(new InputDropdownView("Gender", new String[]{"Male", "Female"}).render(), panel.getComponentCount() - 2);
            panel.add(new InputDropdownView("Won Prize", new String[]{"No", "Yes"}).render(), panel.getComponentCount() - 2);
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