package GWO;

import javax.swing.*;

public class GWOparamForm extends JFrame{
    //<editor-fold desc="TextFields">
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JTextField packNumberField;
    private JTextField searchAgents_field;
    private JTextField maxIterField;
    private JTextField minShuffleField;
    private JTextField maxShuffleField;
    private JTextField maxLengthField;
    private JTextField trialLimitField;
    private JTextField maxEpochField;
    private JButton submitButton;
    private JPanel mainPanel;
    private JButton clearButton;
    //</editor-fold>
    private JTextField[] fields = {maxLengthField, trialLimitField, maxEpochField, lowerBoundField, upperBoundField, packNumberField, searchAgents_field, maxIterField, minShuffleField, maxShuffleField};
    private int[] inputs;
    public GWOparamForm(){
        this.setTitle("Initialize Parameters");
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);

        inputs = new int[fields.length];
        clearButton.addActionListener(e -> {
            for(JTextField F: fields){
                F.setText("");
            }
        });
        submitButton.addActionListener(e -> {
            int i=0;
            for (JTextField F: fields){
                inputs[i] = Integer.parseInt(F.getText());
                i++;
            }
            OutputForm Output = new OutputForm(inputs);
            Output.setVisible(true);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        GWOparamForm form = new GWOparamForm();
        form.setVisible(true);
    }
}
