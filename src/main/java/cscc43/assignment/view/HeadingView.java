package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeadingView implements View {
    private final String text;

    public HeadingView(String text) {
        this.text = text;
    }

    public Component render() {
        JPanel panelView = new JPanel();
        panelView.setPreferredSize(new Dimension(450, 26));
        panelView.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel fillerView = new JLabel("");
        fillerView.setPreferredSize(new Dimension(150, 16));
        panelView.add(fillerView);

        JLabel labelView = new JLabel(this.text);
        Font font = labelView.getFont();
        labelView.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
        labelView.setPreferredSize(new Dimension(300, 16));
        panelView.add(labelView);

        return panelView;
    }
}
