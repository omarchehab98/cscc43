package cscc43.assignment.view;

import java.util.List;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import cscc43.assignment.model.BookKeyword;
import cscc43.assignment.model.Keyword;

public class InputBookKeywordsView implements View {
    private JPanel panel;
    private List<BookKeywordView> bookKeywordViews;
    private List<BookKeyword> defaultKeywords;
    private int numberOfKeywords = 0;
    private final int minKeywords = 0;
    private final int maxKeywords = 20;

    public Component render() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.bookKeywordViews = new ArrayList<BookKeywordView>();

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

    public InputBookKeywordsView setDefaultKeywords(List<BookKeyword> keywords) {
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
            BookKeywordView bookKeywordView = new BookKeywordView(
                new InputStringView("Tag")
                    .setDefaultText(keyword.getKeyword().getTag())
            );
            this.bookKeywordViews.add(bookKeywordView);
            panel.add(bookKeywordView.tagView.render(), panel.getComponentCount() - 2);

            panel.revalidate();
        }
    }

    private void removeKeyword() {
        if (numberOfKeywords > minKeywords) {
            numberOfKeywords--;
            this.bookKeywordViews.remove(this.bookKeywordViews.size() - 1);
            panel.remove(panel.getComponentCount() - 3);
            panel.remove(panel.getComponentCount() - 3);
            panel.revalidate();
        }
    }

    public List<BookKeyword> getValue() {
        List<BookKeyword> bookKeywords = new ArrayList<BookKeyword>();
        for (BookKeywordView bookKeywordView : bookKeywordViews) {
            BookKeyword bookKeyword = new BookKeyword();
            Keyword keyword = new Keyword();
            keyword.setTag(bookKeywordView.tagView.getValue());
            bookKeyword.setKeyword(keyword);
            bookKeywords.add(bookKeyword);
        }
        return bookKeywords;
    }

    private class BookKeywordView {
        public InputStringView tagView;

        public BookKeywordView(InputStringView tagView) {
            this.tagView = tagView;
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
