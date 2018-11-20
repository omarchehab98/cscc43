package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputBookAuthorsView implements View {
    private JPanel panel;
    private int numberOfAuthors = 0;
    private final int minAuthors = 1;
    private final int maxAuthors = 5;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new ButtonView("Add Author", new InsertAuthorAction()).render());
        panel.add(new ButtonView("Remove Author", new RemoveAuthorAction()).render());

        insertAuthor();

        return panel;
    }

    private void insertAuthor() {
        if (numberOfAuthors < maxAuthors) {
            panel.add(new HeadingView(String.format("Author %d", ++numberOfAuthors)).render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("First name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Middle name").render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Last name").render(), panel.getComponentCount() - 2);
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