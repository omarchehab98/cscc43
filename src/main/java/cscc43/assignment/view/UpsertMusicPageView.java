package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.model.Music;
import cscc43.assignment.model.Person;
import cscc43.assignment.controller.MusicController;
import cscc43.assignment.throwable.MusicUpsertException;

public class UpsertMusicPageView implements Observer, View {
    private InputStringView nameView;
    private InputIntegerView yearPublishedView;
    private InputStringView producerFirstNameView;
    private InputStringView producerMiddleNameView;
    private InputStringView producerLastNameView;
    private InputMusicTracksView musicTrackViews;

    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        AppState state = App.getStore().getState();
        List<Music> musicTracks = state.getMusicTracks();
        Music musicAlbum = musicTracks.get(0);

        panel.add(new HeadingView("Music").render());
        panel.add(new HeadingView("Album").render());

        nameView = new InputStringView("Name")
            .setDefaultText(musicAlbum.getAlbumName())
            .setIsEditable(isInsert());
        panel.add(nameView.render());

        yearPublishedView = new InputIntegerView("Year published")
            .setDefaultNumber(musicAlbum.getYear())
            .setMin(0)
            .setIsEditable(isInsert());
        panel.add(yearPublishedView.render());

        panel.add(new HeadingView("Producer").render());
        
        producerFirstNameView = new InputStringView("First name")
            .setDefaultText(musicAlbum.getProducer().getFirstName());
        panel.add(producerFirstNameView.render());

        producerMiddleNameView = new InputStringView("Middle name")
            .setDefaultText(musicAlbum.getProducer().getMiddleName());
        panel.add(producerMiddleNameView.render());

        producerLastNameView = new InputStringView("Last name")
            .setDefaultText(musicAlbum.getProducer().getLastName());
        panel.add(producerLastNameView.render());

        musicTrackViews = new InputMusicTracksView()
            .setDefaultMusic(musicTracks)
            .setPrimaryKeyIsEditable(isInsert());
        panel.add(musicTrackViews.render());

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
            Music musicAlbum = state.getMusicTracks().get(0);
            
            musicAlbum.setAlbumName(nameView.getValue());
            musicAlbum.setYear(yearPublishedView.getValue());
            Person producer = musicAlbum.getProducer();
            producer.setFirstName(producerFirstNameView.getValue());
            producer.setMiddleName(producerMiddleNameView.getValue());
            producer.setLastName(producerLastNameView.getValue());
            musicAlbum.setProducer(producer);
            List<Music> musicTracks = new ArrayList<Music>();
            musicTracks.addAll(musicTrackViews.getValue());
            for (Music music : musicTracks) {
                music.setAlbumName(musicAlbum.getAlbumName());
                music.setYear(musicAlbum.getYear());
                music.setProducer(musicAlbum.getProducer());
            }
            
            try {
                if (isInsert()) {
                    MusicController.getInstance().insert(musicTracks);
                } else {
                    MusicController.getInstance().update(musicTracks);
                }
                MenuBarController.getInstance().setPage(-1);
            } catch (MusicUpsertException err) {
                System.out.println(err);
            }
        }
    }

    private class CancelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBarController.getInstance().setPage(-1);
        }
    }

    private boolean isInsert() {
        AppState state = App.getStore().getState();
        Music musicAlbum = state.getMusicTracks().get(0);
        return musicAlbum.getAlbumName().equals("");
    }
}
