package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import cscc43.assignment.model.BookAuthor;

public class InputBookAuthorsView implements View {
    private JPanel panel;
    private Iterable<BookAuthor> defaultAuthors;
    private int numberOfAuthors = 0;
    private final int minAuthors = 1;
    private final int maxAuthors = 5;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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

    public InputBookAuthorsView setDefaultAuthors(Iterable<BookAuthor> authors) {
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
            panel.add(new InputStringView("First name")
                .setDefaultText(author.getPerson().getFirstName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name")
                .setDefaultText(author.getPerson().getMiddleName())
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name")
                .setDefaultText(author.getPerson().getLastName())
                .render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeAuthor() {
        if (numberOfAuthors > minAuthors) {
            numberOfAuthors--;
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
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