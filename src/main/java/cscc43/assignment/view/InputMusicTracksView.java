package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMusicTracksView implements View {
    private JPanel panel;
    private int numberOfTracks = 0;
    private final int minTracks = 0;
    private final int maxTracks = Integer.MAX_VALUE;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new ButtonView("Add Track", new InsertTrackAction()).render());
        panel.add(new ButtonView("Remove Track", new RemoveTrackAction()).render());

        return panel;
    }

    private void insertTrack() {
        if (numberOfTracks < maxTracks) {
            panel.add(new HeadingView(String.format("Track %d", ++numberOfTracks)).render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Language").render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Composer").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name").render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Arranger").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name").render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Song Writer").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name").render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeTrack() {
        if (numberOfTracks > minTracks) {
            numberOfTracks--;
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    private class InsertTrackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            insertTrack();
        }
    }

    private class RemoveTrackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeTrack();
        }
    }
}