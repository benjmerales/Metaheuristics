package GWO;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OutputForm extends JFrame {
    private TesterGWO tester;
    private JPanel mainPanel;
    private JButton previousRunButton;
    private JButton nextRunButton;
    private JPanel resultPanel;
    private JLabel runLabel;
    private JLabel runtimeLabel;
    private JLabel epochLabel;
    private JButton nextBoardButton;
    private JButton previousBoardButton;
    private JTextPane solutionPane;
    private JLabel boardsCntLabel;
    private ComponentWriter CW;

    private ArrayList<String> solutions;
    private int boardIndex;
    private int runIndex;
    public OutputForm(int[] inputs){
//        private JTextField[] fields = {maxLengthField, trialLimitField, maxEpochField, lowerBoundField, upperBoundField, packNumberField, searchAgents_field, maxIterField, minShuffleField, maxShuffleField};
        this.setTitle("N-Queens Solution Using Swarm Intelligence: Grey Wolf Optimization Algorithm");
        this.setContentPane(mainPanel);
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
        solutions = new ArrayList<>();
        tester = new TesterGWO();
        tester.test(inputs[0], inputs[1], inputs[2]);
        String filepath = "GWO-N" + inputs[0] + "-" + inputs[1] + "-" + inputs[2] + ".txt";
        CW = new ComponentWriter(filepath);
        CW.parseFile();
        boardIndex = 0;
        runIndex = 0;
        display(runIndex);
        previousRunButton.addActionListener(e -> {
            runIndex = decrease(runIndex, CW.getNumberOfRuns());
            display(runIndex);
        });
        nextRunButton.addActionListener(e -> {
            runIndex = increase(runIndex, CW.getNumberOfRuns());
            display(runIndex);
        });

        previousBoardButton.addActionListener(e -> {
            boardIndex = decrease(boardIndex, solutions.size()); // TODO: I think second parameter is not working
            getSolution(boardIndex);
        });
        nextBoardButton.addActionListener(e -> {
            boardIndex = increase(boardIndex, solutions.size());
            getSolution(boardIndex);
        });
    }

    public void display(int index){
        runLabel.setText("Run: " + CW.getRunID(index) + "");
        runtimeLabel.setText("Runtime: " + CW.getRuntime(index) + "");
        epochLabel.setText("Found at Epoch: " + CW.getEpochID(index) + "");
        solutions = CW.getSolutions(index);
        boardsCntLabel.setText("Number of Correct Solutions: " + solutions.size());
        getSolution(0);
    }
    public void getSolution(int index){
        //<editor-fold desc="Style to Center Pane">
        StyledDocument doc = solutionPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        //</editor-fold>
        solutionPane.setText("\n\nBoard "+ (index+1) + ": \n\n\n" + solutions.get(index));
    }

    private int decrease(int x, int limit){
        x--;
        if(x < 0) x = limit;
        return x;
    }
    private int increase(int x, int limit){
        x++;
        if(x >= limit) x = 0;
        return x;
    }

    public static void main(String[] args) {
        int[] data_test = {4,4,1000,-100,100,30,5,1000,8,20};
        OutputForm O = new OutputForm(data_test);
        O.setVisible(true);
    }
}
