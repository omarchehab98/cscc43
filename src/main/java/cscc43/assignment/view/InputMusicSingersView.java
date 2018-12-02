package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.model.Person;
import cscc43.assignment.model.MusicSinger;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputMusicSingersView implements View {
    private JPanel panel;
    private List<MusicSingerView> musicSingerViews;
    private List<MusicSinger> defaultMusicSingers;
    private int numberOfMusicSingers = 0;
    private final int minMusicSingers = 0;
    private int maxMusicSingers = 2;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.musicSingerViews = new ArrayList<MusicSingerView>();

        panel.add(new ButtonView("Add Singer", new InsertMusicSingerAction()).render());
        panel.add(new ButtonView("Remove Singer", new RemoveMusicSingerAction()).render());

        if (defaultMusicSingers == null) {
            insertMusicSinger();
        } else {
            for (MusicSinger musicSinger : defaultMusicSingers) {
                insertMusicSinger(musicSinger);
            }
        }

        return panel;
    }

    public InputMusicSingersView setDefaultMusicSingers(List<MusicSinger> musicSingers) {
        this.defaultMusicSingers = musicSingers;
        return this;
    }

    private void insertMusicSinger() {
        insertMusicSinger(new MusicSinger().initialize());
    }

    private void insertMusicSinger(MusicSinger musicSinger) {
        if (numberOfMusicSingers < maxMusicSingers) {
            panel.add(new HeadingView(String.format("Singer %d", ++numberOfMusicSingers)).render(), panel.getComponentCount() - 2);
            MusicSingerView musicSingerView = new MusicSingerView(
                new InputStringView("First name")
                    .setDefaultText(musicSinger.getPerson().getFirstName()),
                new InputStringView("Middle name")
                    .setDefaultText(musicSinger.getPerson().getMiddleName()),
                new InputStringView("Last name")
                    .setDefaultText(musicSinger.getPerson().getLastName())
            );
            this.musicSingerViews.add(musicSingerView);
            panel.add(musicSingerView.firstNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicSingerView.middleNameView.render(), panel.getComponentCount() - 2);
            panel.add(musicSingerView.lastNameView.render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeMusicSinger() {
        if (numberOfMusicSingers > minMusicSingers) {
            numberOfMusicSingers--;
            this.musicSingerViews.remove(this.musicSingerViews.size() - 1);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    public List<MusicSinger> getValue() {
        List<MusicSinger> musicSingers = new ArrayList<MusicSinger>();
        for (MusicSingerView musicSingerView : musicSingerViews) {
            MusicSinger musicSinger = new MusicSinger().initialize();
            musicSinger.getPerson().setFirstName(musicSingerView.firstNameView.getValue());
            musicSinger.getPerson().setMiddleName(musicSingerView.middleNameView.getValue());
            musicSinger.getPerson().setLastName(musicSingerView.lastNameView.getValue());
            musicSingers.add(musicSinger);
        }
        return musicSingers;
    }

    private class MusicSingerView {
        public InputStringView firstNameView;
        public InputStringView middleNameView;
        public InputStringView lastNameView;

        public MusicSingerView(
            InputStringView firstNameView,
            InputStringView middleNameView,
            InputStringView lastNameView
        ) {
            this.firstNameView = firstNameView;
            this.middleNameView = middleNameView;
            this.lastNameView = lastNameView;
        }
    }

    private class InsertMusicSingerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            insertMusicSinger();
        }
    }

    private class RemoveMusicSingerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeMusicSinger();
        }
    }
}
