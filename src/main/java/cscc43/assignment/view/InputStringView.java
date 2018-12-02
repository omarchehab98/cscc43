package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InputStringView implements View {
    private final String label;
    private JTextField textFieldView;
    private String defaultText;
    private boolean isEditable;

    public InputStringView(String label) {
        this.label = label;
        this.defaultText = "";
        this.isEditable = true;
    }

    public Component render() {
        JPanel panelView = new JPanel();
        JLabel labelView = new JLabel();
        textFieldView = new JTextField();
        
        panelView.setPreferredSize(new Dimension(450, 26));
        panelView.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelView.setText(this.label);
        labelView.setHorizontalAlignment(SwingConstants.RIGHT);
        labelView.setPreferredSize(new Dimension(150, 16));
        panelView.add(labelView);

        textFieldView.setText(this.defaultText);
        textFieldView.setPreferredSize(new Dimension(300, 26));
        textFieldView.setEditable(isEditable);
        panelView.add(textFieldView);

        return panelView;
    }

    public InputStringView setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        return this;
    }

    public InputStringView setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
        return this;
    }

    public String getValue() {
        return textFieldView.getText();
    }
}
