package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import cscc43.assignment.model.BookAuthor;
import cscc43.assignment.model.Person;

public class InputBookAuthorsView implements View {
    private JPanel panel;
    private List<BookAuthorView> bookAuthorViews;
    private List<BookAuthor> defaultAuthors;
    private int numberOfAuthors = 0;
    private final int minAuthors = 1;
    private final int maxAuthors = 5;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.bookAuthorViews = new ArrayList<BookAuthorView>();

        panel.add(new ButtonView("Add Author", new InsertAuthorAction()).render());
        panel.add(new ButtonView("Remove Author", new RemoveAuthorAction()).render());

        if (defaultAuthors == null) {
            insertAuthor();
        } else {
            for (BookAuthor author : defaultAuthors) {
                insertAuthor(author);
            }
        }

        return panel;
    }

    public InputBookAuthorsView setDefaultAuthors(List<BookAuthor> authors) {
        this.defaultAuthors = authors;
        return this;
    }

    private void insertAuthor() {
        insertAuthor(new BookAuthor().initialize());
    }

    private void insertAuthor(BookAuthor author) {
        if (numberOfAuthors < maxAuthors) {
            panel.add(new HeadingView(String.format("Author %d", ++numberOfAuthors))
                .render(), panel.getComponentCount() - 2);
            BookAuthorView bookAuthorView = new BookAuthorView(
                new InputStringView("First name")
                    .setDefaultText(author.getPerson().getFirstName()),
                new InputStringView("Middle name")
                    .setDefaultText(author.getPerson().getMiddleName()),
                new InputStringView("Last name")
                    .setDefaultText(author.getPerson().getLastName())
            );
            this.bookAuthorViews.add(bookAuthorView);
            panel.add(bookAuthorView.firstNameView.render(), panel.getComponentCount() - 2);
            panel.add(bookAuthorView.middleNameView.render(), panel.getComponentCount() - 2);
            panel.add(bookAuthorView.lastNameView.render(), panel.getComponentCount() - 2);
            
            panel.revalidate();
        }
    }

    private void removeAuthor() {
        if (numberOfAuthors > minAuthors) {
            numberOfAuthors--;
            this.bookAuthorViews.remove(this.bookAuthorViews.size() - 1);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    public List<BookAuthor> getValue() {
        List<BookAuthor> bookAuthors = new ArrayList<BookAuthor>();
        for (BookAuthorView bookAuthorView : bookAuthorViews) {
            BookAuthor bookAuthor = new BookAuthor();
            Person person = new Person();
            person.setFirstName(bookAuthorView.firstNameView.getValue());
            person.setMiddleName(bookAuthorView.middleNameView.getValue());
            person.setLastName(bookAuthorView.lastNameView.getValue());
            bookAuthor.setPerson(person);
            bookAuthors.add(bookAuthor);
        }
        return bookAuthors;
    }

    private class BookAuthorView {
        public InputStringView firstNameView;
        public InputStringView middleNameView;
        public InputStringView lastNameView;

        public BookAuthorView(InputStringView firstNameView, InputStringView middleNameView, InputStringView lastNameView) {
            this.firstNameView = firstNameView;
            this.middleNameView = middleNameView;
            this.lastNameView = lastNameView;
        }
    }

    private class InsertAuthorAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            insertAuthor();
        }
    }

    private class RemoveAuthorAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeAuthor();
        }
    }
}
