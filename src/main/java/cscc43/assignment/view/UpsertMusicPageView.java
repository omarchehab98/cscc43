package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.model.Music;

public class UpsertMusicPageView implements Observer, View {
    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        AppState state = App.getStore().getState();
        List<Music> musicTracks = state.getMusicTracks();
        Music musicAlbum = musicTracks.get(0);

        panel.add(new HeadingView("Music").render());
        panel.add(new HeadingView("Album").render());
        panel.add(new InputStringView("Name")
            .setDefaultText(musicAlbum.getAlbumName())
            .render());
        panel.add(new InputIntegerView("Year published")
            .setDefaultNumber(musicAlbum.getYear())
            .setMin(0)
            .render());
        panel.add(new HeadingView("Producer").render());
        panel.add(new InputStringView("First name")
            .setDefaultText(musicAlbum.getProducer().getFirstName())
            .render());
        panel.add(new InputStringView("Middle name")
            .setDefaultText(musicAlbum.getProducer().getMiddleName())
            .render());
        panel.add(new InputStringView("Last name")
            .setDefaultText(musicAlbum.getProducer().getLastName())
            .render());
        panel.add(new InputMusicTracksView()
            .setDefaultMusic(musicTracks)
            .render());
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