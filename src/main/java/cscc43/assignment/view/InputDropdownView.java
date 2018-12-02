package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class InputDropdownView implements View {
    private final String label;
    private final String[] options;
    private JComboBox<String> comboBox;
    private String defaultOption;

    public InputDropdownView(String label, String[] options) {
        this.label = label;
        this.options = options;
        this.defaultOption = null;
    }
    
    public Component render() {
        JPanel panelView = new JPanel();
        JLabel labelView = new JLabel();
        
        panelView.setPreferredSize(new Dimension(450, 26));
        panelView.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelView.setText(this.label);
        labelView.setHorizontalAlignment(SwingConstants.RIGHT);
        labelView.setPreferredSize(new Dimension(150, 16));
        panelView.add(labelView);

        comboBox = new JComboBox<String>(this.options);
        comboBox.setSelectedItem(this.defaultOption);
        panelView.add(comboBox);

        return panelView;
    }

    public InputDropdownView setDefaultOption(String option) {
        this.defaultOption = option;
        return this;
    }

    public String getValue() {
        return (String) comboBox.getSelectedItem();
    }
}
