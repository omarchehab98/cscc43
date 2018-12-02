package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.model.Music;
import cscc43.assignment.model.MusicPerson;
import cscc43.assignment.model.MusicSinger;
import cscc43.assignment.model.Person;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMusicTracksView implements View {
    private JPanel panel;
    private List<MusicTrackView> musicTrackViews;
    private List<Music> defaultMusicTracks;
    private int numberOfTracks = 0;
    private boolean isPrimaryKeyEditable = true;
    private final int minTracks = 1;
    private final int maxTracks = Integer.MAX_VALUE;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.musicTrackViews = new ArrayList<MusicTrackView>();

        panel.add(new ButtonView("Add Track", new InsertTrackAction()).render());
        panel.add(new ButtonView("Remove Track", new RemoveTrackAction()).render());

        if (defaultMusicTracks == null) {
            insertTrack();
        } else {
            for (Music music : defaultMusicTracks) {
                insertTrack(music);
            }
        }

        return panel;
    }

    public InputMusicTracksView setDefaultMusic(List<Music> musicTracks) {
        this.defaultMusicTracks = musicTracks;
        return this;
    }

    public InputMusicTracksView setPrimaryKeyIsEditable(boolean isPrimaryKeyEditable) {
        this.isPrimaryKeyEditable = isPrimaryKeyEditable;
        return this;
    }

    private void insertTrack() {
        insertTrack(new Music().initialize());
    }

    private void insertTrack(Music music) {
        if (numberOfTracks < maxTracks) {
            panel.add(new HeadingView(String.format("Track %d", ++numberOfTracks)).render(), panel.getComponentCount() - 2);
            MusicTrackView musicTrackView = new MusicTrackView(
                new InputStringView("Name")
                    .setDefaultText(music.getMusicName())
                    .setIsEditable(isPrimaryKeyEditable),
                new InputStringView("Language")
                    .setDefaultText(music.getLanguage()),
                new InputDropdownView("Disk type", new String[]{"CD", "Vinyle"})
                    .setDefaultOption(Music.DiskType.toString(music.getDiskType())),
                new InputStringView("First name")
                    .setDefaultText(music.getMusicPersonComposer().getPerson().getFirstName()),
                new InputStringView("Middle name")
                    .setDefaultText(music.getMusicPersonComposer().getPerson().getMiddleName()),
                new InputStringView("Last name")
                    .setDefaultText(music.getMusicPersonComposer().getPerson().getLastName()),
                new InputStringView("First name")
                    .setDefaultText(music.getMusicPersonArranger().getPerson().getFirstName()),
                new InputStringView("Middle name")
                    .setDefaultText(music.getMusicPersonArranger().getPerson().getMiddleName()),
                new InputStringView("Last name")
                    .setDefaultText(music.getMusicPersonArranger().getPerson().getLastName()),
                new InputStringView("First name")
                    .setDefaultText(music.getMusicPersonSongwriter().getPerson().getFirstName()),
                new InputStringView("Middle name")
                    .setDefaultText(music.getMusicPersonSongwriter().getPerson().getMiddleName()),
                new InputStringView("Last name")
                    .setDefaultText(music.getMusicPersonSongwriter().getPerson().getLastName()),
                new InputMusicSingersView()
                    .setDefaultMusicSingers(music.getMusicSingers())
            );
            this.musicTrackViews.add(musicTrackView);
            panel.add(musicTrackView.nameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.languageView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.diskTypeView.render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Composer").render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.composerFirstNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.composerMiddleNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.composerLastNameView.render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Arranger").render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.arrangerFirstNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.arrangerMiddleNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.arrangerLastNameView.render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Song Writer").render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.songwriterFirstNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.songwriterMiddleNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.songwriterLastNameView.render(), panel.getComponentCount() - 2);
            panel.add(new HeadingView("Singers").render(), panel.getComponentCount() - 2);
            panel.add(musicTrackView.musicSingersView.render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeTrack() {
        if (numberOfTracks > minTracks) {
            numberOfTracks--;
            this.musicTrackViews.remove(this.musicTrackViews.size() - 1);
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
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    public List<Music> getValue() {
        List<Music> musicTracks = new ArrayList<Music>();
        for (MusicTrackView musicTrackView : this.musicTrackViews) {
            Music musicTrack = new Music();
            musicTrack.setMusicName(musicTrackView.nameView.getValue());
            musicTrack.setLanguage(musicTrackView.languageView.getValue());
            musicTrack.setDiskType(Music.DiskType.fromString(musicTrackView.diskTypeView.getValue()));
            List<MusicPerson> musicPersons = new ArrayList<MusicPerson>();
            musicPersons.add(musicTrackView.getMusicPersonSongwriter());
            musicPersons.add(musicTrackView.getMusicPersonComposer());
            musicPersons.add(musicTrackView.getMusicPersonArranger());
            musicTrack.setMusicPersons(musicPersons);
            musicTrack.setMusicSingers(musicTrackView.getMusicSingers());
            musicTracks.add(musicTrack);
        }
        return musicTracks;
    }

    private class MusicTrackView {
        private InputStringView nameView;
        private InputStringView languageView;
        private InputDropdownView diskTypeView;
        private InputStringView composerFirstNameView;
        private InputStringView composerMiddleNameView;
        private InputStringView composerLastNameView;
        private InputStringView arrangerFirstNameView;
        private InputStringView arrangerMiddleNameView;
        private InputStringView arrangerLastNameView;
        private InputStringView songwriterFirstNameView;
        private InputStringView songwriterMiddleNameView;
        private InputStringView songwriterLastNameView;
        private InputMusicSingersView musicSingersView;

        public MusicTrackView(
            InputStringView nameView,
            InputStringView languageView,
            InputDropdownView diskTypeView,
            InputStringView composerFirstNameView,
            InputStringView composerMiddleNameView,
            InputStringView composerLastNameView,
            InputStringView arrangerFirstNameView,
            InputStringView arrangerMiddleNameView,
            InputStringView arrangerLastNameView,
            InputStringView songwriterFirstNameView,
            InputStringView songwriterMiddleNameView,
            InputStringView songwriterLastNameView,
            InputMusicSingersView musicSingersView
        ) {
            this.nameView = nameView;
            this.languageView = languageView;
            this.diskTypeView = diskTypeView;
            this.composerFirstNameView = composerFirstNameView;
            this.composerMiddleNameView = composerMiddleNameView;
            this.composerLastNameView = composerLastNameView;
            this.arrangerFirstNameView = arrangerFirstNameView;
            this.arrangerMiddleNameView = arrangerMiddleNameView;
            this.arrangerLastNameView = arrangerLastNameView;
            this.songwriterFirstNameView = songwriterFirstNameView;
            this.songwriterMiddleNameView = songwriterMiddleNameView;
            this.songwriterLastNameView = songwriterLastNameView;
            this.musicSingersView = musicSingersView;
        }

        public MusicPerson getMusicPersonSongwriter() {
            Person person = new Person();
            person.setFirstName(this.songwriterFirstNameView.getValue());
            person.setMiddleName(this.songwriterMiddleNameView.getValue());
            person.setLastName(this.songwriterLastNameView.getValue());
            return new MusicPerson(1, 0, 0, new Music(), person);
        }

        public MusicPerson getMusicPersonComposer() {
            Person person = new Person();
            person.setFirstName(this.composerFirstNameView.getValue());
            person.setMiddleName(this.composerMiddleNameView.getValue());
            person.setLastName(this.composerLastNameView.getValue());
            return new MusicPerson(0, 1, 0, new Music(), person);
        }

        public MusicPerson getMusicPersonArranger() {
            Person person = new Person();
            person.setFirstName(this.arrangerFirstNameView.getValue());
            person.setMiddleName(this.arrangerMiddleNameView.getValue());
            person.setLastName(this.arrangerLastNameView.getValue());
            return new MusicPerson(0, 0, 1, new Music(), person);
        }

        public List<MusicSinger> getMusicSingers() {
            List<MusicSinger> musicSingers = musicSingersView.getValue();
            return musicSingers;
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
