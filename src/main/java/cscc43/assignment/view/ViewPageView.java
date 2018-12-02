package cscc43.assignment.view;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cscc43.assignment.App;
import cscc43.assignment.store.AppState;
import cscc43.assignment.viewmodel.SearchResult;

public class ViewPageView implements Observer, View {
    public Component render() {
        AppState state = App.getStore().getState();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        SearchResult searchResult = state.getSearchResult();
        JTable table = new JTable(new Object[][]{{
            searchResult.getName(),
            searchResult.getYear(),
            searchResult.getType(),
            searchResult.getPerson(),
        }}, new String[]{
            "Product's Name",
            "Year",
            "Type",
            searchResult.getPersonColumnName()
        });
        JScrollPane scrollPane = new JScrollPane(table);
        table.setDefaultEditor(Object.class, null);
        table.setFillsViewportHeight(true);
        panel.add(scrollPane);

        return panel;
    }

    public void update(Observable o, Object arg) {

    }
}
