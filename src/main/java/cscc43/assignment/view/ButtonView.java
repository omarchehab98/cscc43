package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonView implements View {
    private final String text;
    private final ActionListener actionListener;

    public ButtonView(String text, ActionListener actionListener) {
        this.text = text;
        this.actionListener = actionListener;
    }

    public Component render() {
        JPanel panelView = new JPanel();
        panelView.setPreferredSize(new Dimension(450, 26));
        panelView.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton buttonView = new JButton(this.text);
        buttonView.addActionListener(this.actionListener);
        panelView.add(buttonView);

        return panelView;
    }
}
