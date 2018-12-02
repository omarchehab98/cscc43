package cscc43.assignment.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ViewPageView implements Observer, View {
    public Component render() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JTextArea("View"));
        return panel;
    }

    public void update(Observable o, Object arg) {

    }
}
