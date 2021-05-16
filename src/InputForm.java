import javax.swing.*;
import java.awt.*;

public class InputForm extends JFrame implements  ColorsUI{
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
    private JPanel inputPanel;
    //</editor-fold>
    private JTextField[] fields = {maxLengthField, trialLimitField, maxEpochField, lowerBoundField, upperBoundField, packNumberField, searchAgents_field, maxIterField, minShuffleField, maxShuffleField};
    private JButton[] buttons = {submitButton, clearButton};
    private int[] inputs;
    public InputForm(){
        this.setTitle("Initialize Parameters");
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlaySound PS = new PlaySound();
        PS.play();
        inputs = new int[fields.length];
        ColorsUI.button_init(buttons, 2);
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
        InputForm form = new InputForm();
        form.setVisible(true);
    }
}
