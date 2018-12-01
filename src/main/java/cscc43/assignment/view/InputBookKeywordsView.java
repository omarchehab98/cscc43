package cscc43.assignment.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import cscc43.assignment.model.BookKeyword;

public class InputBookKeywordsView implements View {
    private JPanel panel;
    private Iterable<BookKeyword> defaultKeywords;
    private int numberOfKeywords = 0;
    private final int minKeywords = 0;
    private final int maxKeywords = 20;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new ButtonView("Add Keyword", new InsertKeywordAction()).render());
        panel.add(new ButtonView("Remove Keyword", new RemoveKeywordAction()).render());

        if (defaultKeywords == null) {
            insertKeyword();
        } else {
            for (BookKeyword keyword : defaultKeywords) {
                insertKeyword(keyword);
            }
        }

        return panel;
    }

    public InputBookKeywordsView setDefaultKeywords(Iterable<BookKeyword> keywords) {
        this.defaultKeywords = keywords;
        return this;
    }

    private void insertKeyword() {
        insertKeyword(new BookKeyword().initialize());
    }

    private void insertKeyword(BookKeyword keyword) {
        if (numberOfKeywords < maxKeywords) {
            panel.add(new HeadingView(String.format("Keyword %d", ++numberOfKeywords))
                .render(), panel.getComponentCount() - 2);
            panel.add(new InputStringView("Tag")
                .setDefaultText(keyword.getKeyword().getTag())
                .render(), panel.getComponentCount() - 2);
            panel.revalidate();
        }
    }

    private void removeKeyword() {
        if (numberOfKeywords > minKeywords) {
            numberOfKeywords--;
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    private class InsertKeywordAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            insertKeyword();
        }
    }

    private class RemoveKeywordAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeKeyword();
        }
    }
}