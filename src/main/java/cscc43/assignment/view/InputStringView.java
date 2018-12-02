package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.Document;

public class InputStringView implements View {
    private final String label;
    private JTextField textFieldView;
    private String defaultText;
    private Document document;

    public InputStringView(String label) {
        this.label = label;
        this.defaultText = "";
        this.document = null;
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
        if (this.document != null) {
            textFieldView.setDocument(this.document);
        }
        textFieldView.setPreferredSize(new Dimension(300, 26));
        panelView.add(textFieldView);

        return panelView;
    }

    public InputStringView setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        return this;
    }

    public InputStringView setDocument(Document document) {
        this.document = document;
        return this;
    }

    public String getValue() {
        return textFieldView.getText();
    }
}
