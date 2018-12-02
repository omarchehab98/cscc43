package cscc43.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class InputIntegerView implements View {
    private final String label;
    private JSpinner spinnerView;
    private int defaultNumber;
    private int min;
    private int max;
    private boolean isEditable;

    public InputIntegerView(String label) {
        this.label = label;
        this.defaultNumber = 0;
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.isEditable = true;
    }

    public Component render() {
        JPanel panelView = new JPanel();
        JLabel labelView = new JLabel();
        spinnerView = new JSpinner();
        
        panelView.setPreferredSize(new Dimension(450, 26));
        panelView.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelView.setHorizontalAlignment(SwingConstants.RIGHT);
        labelView.setText(this.label);
        labelView.setPreferredSize(new Dimension(150, 16));
        panelView.add(labelView);

        spinnerView.setModel(new SpinnerNumberModel(this.defaultNumber, this.min, this.max, this.isEditable ? 1 : 0));
        ((JSpinner.DefaultEditor) spinnerView.getEditor())
            .getTextField()
            .setEditable(this.isEditable);
        spinnerView.setPreferredSize(new Dimension(300, 26));
        panelView.add(spinnerView);

        return panelView;
    }

    public InputIntegerView setDefaultNumber(int defaultNumber) {
        this.defaultNumber = defaultNumber;
        return this;
    }

    public InputIntegerView setMin(int min) {
        this.min = min;
        return this;
    }

    public InputIntegerView setMax(int max) {
        this.max = max;
        return this;
    }

    public InputIntegerView setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
        return this;
    }

    public Integer getValue() {
        return (Integer) spinnerView.getModel().getValue();
    }
}
