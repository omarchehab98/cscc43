package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import cscc43.assignment.App;
import cscc43.assignment.controller.BookController;
import cscc43.assignment.controller.MenuBarController;
import cscc43.assignment.model.Book;
import cscc43.assignment.store.AppState;
import cscc43.assignment.throwable.BookUpsertException;

public class UpsertBookPageView implements Observer, View {
    private InputStringView nameView;
    private InputStringView isbnView;
    private InputStringView publisherNameView;
    private InputIntegerView editionNumberView;
    private InputIntegerView numberOfPagesView;
    private InputIntegerView publicationYearView;
    private InputStringView descriptionView;
    private InputBookAuthorsView bookAuthorsView;
    private InputBookKeywordsView bookKeywordsView;

    public Component render() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        AppState state = App.getStore().getState();
        Book book = state.getBook();

        panel.add(new HeadingView("Book").render());

        nameView = new InputStringView("Name")
            .setDefaultText(book.getTitle());
        panel.add(nameView.render());
        
        isbnView = new InputStringView("ISBN")
            .setDefaultText(book.getIsbn())
            .setIsEditable(isInsert());
        panel.add(isbnView.render());

        publisherNameView = new InputStringView("Publisher name")
            .setDefaultText(book.getPublisher());
        panel.add(publisherNameView.render());
            
        editionNumberView = new InputIntegerView("Edition number")
            .setDefaultNumber(book.getEditionNumber())
            .setMin(0);
        panel.add(editionNumberView.render());
            
        numberOfPagesView = new InputIntegerView("Number of pages")
            .setDefaultNumber(book.getNumberOfPages())
            .setMin(0);
        panel.add(numberOfPagesView.render());
            
        publicationYearView = new InputIntegerView("Publication year")
            .setDefaultNumber(book.getYearOfPublication())
            .setMin(0);
        panel.add(publicationYearView.render());
        
        descriptionView = new InputStringView("Description")
            .setDefaultText(book.getDescription());
        panel.add(descriptionView.render());
            
        bookAuthorsView = new InputBookAuthorsView()
            .setDefaultAuthors(book.getBookAuthors());
        panel.add(bookAuthorsView.render());
            
        bookKeywordsView = new InputBookKeywordsView()
            .setDefaultKeywords(book.getBookKeywords());
        panel.add(bookKeywordsView.render());

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
            
            Book book = new Book();
            book.setTitle(nameView.getValue());
            book.setIsbn(isbnView.getValue());
            book.setPublisher(publisherNameView.getValue());
            book.setEditionNumber(editionNumberView.getValue());
            book.setNumberOfPages(numberOfPagesView.getValue());
            book.setYearOfPublication(publicationYearView.getValue());
            book.setDescription(descriptionView.getValue());
            book.setBookAuthors(bookAuthorsView.getValue());
            book.setBookKeywords(bookKeywordsView.getValue());
    
            try {
                if (isInsert()) {
                    BookController.getInstance().insert(book);
                } else {
                    BookController.getInstance().update(book);
                }
                MenuBarController.getInstance().setPage(-1);
            } catch (BookUpsertException err) {
                JOptionPane.showMessageDialog(App.getFrame(), err);
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
        return state.getBook().getIsbn().equals("");
    }
}
