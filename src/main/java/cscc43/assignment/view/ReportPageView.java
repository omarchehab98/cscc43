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
import cscc43.assignment.viewmodel.Report;

public class ReportPageView implements Observer, View {
    private JPanel panel;

    public Component render() {
        AppState state = App.getStore().getState();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Report report = state.getReport();
        JTable table = new JTable(report.getData(), report.getHeader());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setDefaultEditor(Object.class, null);
        table.setFillsViewportHeight(true);
        panel.add(scrollPane);

        return panel;
    }

    public void update(Observable o, Object arg) {

    }
}
