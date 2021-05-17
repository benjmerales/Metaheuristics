import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class OutputForm extends JFrame {
    //<editor-fold desc="Components">
    private SolverGWO solver;
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
    private JLabel grid_0_0;
    private JLabel grid_0_1;
    private JLabel grid_0_2;
    private JLabel grid_0_3;
    private JLabel grid_0_4;
    private JLabel grid_0_5;
    private JLabel grid_0_6;
    private JLabel grid_0_7;
    private JLabel grid_1_0;
    private JLabel grid_1_1;
    private JLabel grid_1_2;
    private JLabel grid_1_3;
    private JLabel grid_1_4;
    private JLabel grid_1_5;
    private JLabel grid_1_6;
    private JLabel grid_1_7;
    private JLabel grid_2_0;
    private JLabel grid_2_1;
    private JLabel grid_2_2;
    private JLabel grid_2_3;
    private JLabel grid_2_4;
    private JLabel grid_2_5;
    private JLabel grid_2_6;
    private JLabel grid_2_7;
    private JLabel grid_3_0;
    private JLabel grid_3_1;
    private JLabel grid_3_2;
    private JLabel grid_3_3;
    private JLabel grid_3_4;
    private JLabel grid_3_5;
    private JLabel grid_3_6;
    private JLabel grid_3_7;
    private JLabel grid_4_0;
    private JLabel grid_4_1;
    private JLabel grid_4_2;
    private JLabel grid_4_3;
    private JLabel grid_4_4;
    private JLabel grid_4_5;
    private JLabel grid_4_6;
    private JLabel grid_4_7;
    private JLabel grid_5_0;
    private JLabel grid_5_1;
    private JLabel grid_5_2;
    private JLabel grid_5_3;
    private JLabel grid_5_4;
    private JLabel grid_5_5;
    private JLabel grid_5_6;
    private JLabel grid_5_7;
    private JLabel grid_6_0;
    private JLabel grid_6_1;
    private JLabel grid_6_2;
    private JLabel grid_6_3;
    private JLabel grid_6_4;
    private JLabel grid_6_5;
    private JLabel grid_6_6;
    private JLabel grid_6_7;
    private JLabel grid_7_0;
    private JLabel grid_7_1;
    private JLabel grid_7_2;
    private JLabel grid_7_3;
    private JLabel grid_7_4;
    private JLabel grid_7_5;
    private JLabel grid_7_6;
    private JLabel grid_7_7;
    private JLabel titleBoardNumber;
    private JPanel Board;
    private ComponentWriter CW;

    private ArrayList<String> solutions;
    private int boardIndex;
    private int runIndex;

    private JLabel grid_labels [][] = {
            {grid_0_0, grid_0_1, grid_0_2, grid_0_3, grid_0_4, grid_0_5, grid_0_6, grid_0_7},
            {grid_1_0, grid_1_1, grid_1_2, grid_1_3, grid_1_4, grid_1_5, grid_1_6, grid_1_7},
            {grid_2_0, grid_2_1, grid_2_2, grid_2_3, grid_2_4, grid_2_5, grid_2_6, grid_2_7},
            {grid_3_0, grid_3_1, grid_3_2, grid_3_3, grid_3_4, grid_3_5, grid_3_6, grid_3_7},
            {grid_4_0, grid_4_1, grid_4_2, grid_4_3, grid_4_4, grid_4_5, grid_4_6, grid_4_7},
            {grid_5_0, grid_5_1, grid_5_2, grid_5_3, grid_5_4, grid_5_5, grid_5_6, grid_5_7},
            {grid_6_0, grid_6_1, grid_6_2, grid_6_3, grid_6_4, grid_6_5, grid_6_6, grid_6_7},
            {grid_7_0, grid_7_1, grid_7_2, grid_7_3, grid_7_4, grid_7_5, grid_7_6, grid_7_7}
    };
    private BufferedImage image;
    private JButton[] buttons = {nextBoardButton, nextRunButton, previousBoardButton, previousRunButton};
    //</editor-fold>
    public OutputForm(int[] inputs){
//        private JTextField[] fields = {maxLengthField, trialLimitField, maxEpochField, lowerBoundField, upperBoundField, packNumberField, searchAgents_field, maxIterField, minShuffleField, maxShuffleField};
        this.setTitle("N-Queens Solution Using Swarm Intelligence: Grey Wolf Optimization Algorithm");
        this.setContentPane(mainPanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("src/resources/wolf1.png").getImage());
        solutions = new ArrayList<>();
        solver = new SolverGWO();
        restart_board();
        solver.test(inputs[0], inputs[1], inputs[2]);
        String filepath = "GWO-N" + inputs[0] + "-" + inputs[1] + "-" + inputs[2] + ".txt";
        CW = new ComponentWriter(filepath);
        CW.parseFile();
        boardIndex = 0;
        runIndex = 0;
        display(runIndex);
        constructBoard(runIndex);
        for(JButton i: buttons){
            i.setBackground(new Color(152,183,222));
            i.setBorder(null);
            i.setFocusable(false);
            i.setFocusPainted(false);
            i.setIgnoreRepaint(false);
            i.setRolloverIcon(new ImageIcon("src/resources/button_hover.gif"));

        }
        previousRunButton.addActionListener(e -> {
            runIndex = decrease(runIndex, CW.getNumberOfRuns());
            restart_board();
            display(runIndex);
            titleBoardNumber.setText("BOARD " + 1);
            constructBoard(0);
        });
        nextRunButton.addActionListener(e -> {
            runIndex = increase(runIndex, CW.getNumberOfRuns());
            restart_board();
            display(runIndex);
            titleBoardNumber.setText("BOARD " + 1);
            constructBoard(0);
        });

        previousBoardButton.addActionListener(e -> {
            boardIndex = decrease(boardIndex, solutions.size());
            titleBoardNumber.setText("BOARD " + (boardIndex+1));
            restart_board();
            constructBoard(boardIndex);
        });
        nextBoardButton.addActionListener(e -> {
            boardIndex = increase(boardIndex, solutions.size());
            titleBoardNumber.setText("BOARD " + (boardIndex+1));
            restart_board();
            constructBoard(boardIndex);
        });
        grid_7_7.setBackground(Color.red);
    }
    public void restart_board(){
        for (int i = 0; i < grid_labels.length; i++) {
            for (int j = 0; j < grid_labels.length; j++) {
                if( (i+j) % 2 == 0){
                    grid_labels[i][j].setIcon(new ImageIcon("src/resources/board1.png"));
                }
                else{
                    grid_labels[i][j].setIcon(new ImageIcon("src/resources/board2.png"));
                }
                grid_labels[i][j].setText("_");
            }
        }
    }
    public void display(int index){
        runLabel.setText("Run: " + CW.getRunID(index) + "");
        runtimeLabel.setText("Runtime: " + CW.getRuntime(index) + "");
        epochLabel.setText("Found at Epoch: " + CW.getEpochID(index) + "");
        solutions = CW.getSolutions(index);
        boardsCntLabel.setText("Number of Correct Solutions: " + solutions.size());


    }


    private int decrease(int x, int limit){
        x--;
        if(x < 0) x = limit-1;
        return x;
    }
    private int increase(int x, int limit){
        x++;
        if(x >= limit) x = 0;
        return x;
    }

    public char[][] queenStr(String str){
        int l = str.length();
        String[] n= str.split("\n");
        char[][] arr = new char[n.length][n.length];
        for (int i = 0; i < n.length; i++) {
            String[] T = n[i].split(" ");
            for (int j = 0; j < n.length; j++) {
                arr[i][j] =  T[j].charAt(0);
            }
        }
        return  arr;
    }
    public void constructBoard(int index){
        String board_str = solutions.get(index);
        char[][] board_chr = queenStr(board_str);
        for (int i = 0; i < board_chr.length; i++) {
            for (int j = 0; j < board_chr.length; j++) {
                if(board_chr[i][j] == '.'){
                    grid_labels[i][j].setText("");
                }
                else{
                    grid_labels[i][j].setText("");
                    grid_labels[i][j].setBackground(Color.GREEN);
                    grid_labels[i][j].setIcon(new ImageIcon("src/resources/wolf1_colored.png"));
                }
            }
        }
    }
    /*
    public static void main(String[] args) {
        int[] data_test = {4,4,1000,-100,100,30,5,1000,8,20};
        OutputForm O = new OutputForm(data_test);
        O.setVisible(true);
    }*/
}
