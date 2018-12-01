package cscc43.assignment.view;

import cscc43.assignment.model.Music;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMusicTracksView implements View {
    private JPanel panel;
    private Iterable<Music> defaultMusics;
    private int numberOfTracks = 0;
    private final int minTracks = 0;
    private final int maxTracks = Integer.MAX_VALUE;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new ButtonView("Add Track", new InsertTrackAction()).render());
        panel.add(new ButtonView("Remove Track", new RemoveTrackAction()).render());

        if (defaultMusics == null) {
            insertTrack();
        } else {
            for (Music music : defaultMusics) {
                insertTrack(music);
            }
        }

        return panel;
    }

    public InputMusicTracksView setDefaultMusic(Iterable<Music> musics) {
        this.defaultMusics = musics;
        return this;
    }

    private void insertTrack() {
        insertTrack(new Music().initialize());
    }

    private void insertTrack(Music music) {
        if (numberOfTracks < maxTracks) {
            panel.add(new HeadingView(String.format("Track %d", ++numberOfTracks)).render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Name")
                .setDefaultText(music.getMusicName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Language")
                .setDefaultText(music.getLanguage())
                .render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Composer").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name")
                .setDefaultText(music.getMusicPersonComposer().getPerson().getFirstName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name")
                .setDefaultText(music.getMusicPersonComposer().getPerson().getMiddleName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name")
                .setDefaultText(music.getMusicPersonComposer().getPerson().getLastName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Arranger").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name")
                .setDefaultText(music.getMusicPersonArranger().getPerson().getFirstName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name")
                .setDefaultText(music.getMusicPersonArranger().getPerson().getMiddleName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name")
                .setDefaultText(music.getMusicPersonArranger().getPerson().getLastName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Song Writer").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name")
                .setDefaultText(music.getMusicPersonSongwriter().getPerson().getFirstName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name")
                .setDefaultText(music.getMusicPersonSongwriter().getPerson().getMiddleName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name")
                .setDefaultText(music.getMusicPersonSongwriter().getPerson().getLastName())
                .render(), panel.getComponentCount() - 2);
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