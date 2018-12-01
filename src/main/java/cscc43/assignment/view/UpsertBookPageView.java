package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cscc43.assignment.App;
import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.view.util.JTextFieldLimit;
import cscc43.assignment.model.Book;
import cscc43.assignment.store.AppState;

public class UpsertBookPageView implements Observer, View {
    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        AppState state = App.getStore().getState();
        Book book = state.getBook();

        panel.add(new HeadingView("Book").render());
        panel.add(new InputStringView("Name")
            .setDefaultText(book.getTitle())
            .render());
        panel.add(new InputStringView("ISBN")
            .setDefaultText(book.getIsbn())
            .render());
        panel.add(new InputStringView("Publisher name")
            .setDefaultText(book.getPublisher())
            .render());
        panel.add(new InputIntegerView("Edition number")
            .setDefaultNumber(book.getEditionNumber())
            .setMin(0)
            .render());
        panel.add(new InputIntegerView("Number of pages")
            .setDefaultNumber(book.getNumberOfPages())
            .setMin(0)
            .render());
        panel.add(new InputIntegerView("Publication year")
            .setDefaultNumber(book.getYearOfPublication())
            .setMin(0)
            .render());
        panel.add(new InputStringView("Description")
            .setDefaultText(book.getDescription())
            .setDocument(new JTextFieldLimit(5000))
            .render());
        panel.add(new InputBookAuthorsView()
            .setDefaultAuthors(book.getBookAuthors())
            .render());
        panel.add(new InputBookKeywordsView()
            .setDefaultKeywords(book.getBookKeywords())
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