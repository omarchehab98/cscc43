package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class FillerView implements View {
    public Component render() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 360));
        return panel;
    }
}