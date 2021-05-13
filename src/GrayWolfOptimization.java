/** GrayWolfOptimizatio.java
 *
 * Solves the N-Queens puzzle using Gray Wolf Optimization Algorithm

 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GrayWolfOptimization {
    /* GRAY WOLF PARAMETERS */
    private int MAX_LENGTH; // N number of queens

    Random R = new Random();
    private int LOWERBOUND;
    private int UPPERBOUND;
    private int dim;
    private int searchAgents_no;
    private int MAX_EPOCHS;
    private int PACK_NUMBER;
    
    /****** VARIABLES FOR Python implementation for GWO **********/
    float[] Alpha_pos = new float[dim]; // pos init arrays
    float[] Beta_pos = new float[dim];
    float[] Delta_pos = new float[dim];

    int Alpha_score = Integer.MAX_VALUE; // set to inf
    int Beta_score = Integer.MAX_VALUE;
    int Delta_score = Integer.MAX_VALUE;

    float[] arr_lb = new float[dim]; // init arr bounds
    float[] arr_ub = new float[dim];

    //int[] Convergence_curve = new int[max_iter];
    int[] Convergence_curve;
    float[][] Positions; // Todo: Positions(?)
    int fitness;
    /****** VARIABLES FOR Python implementation for GWO **********/

    private ArrayList<Wolf> wolves;
    private ArrayList<Wolf> solutions;
    private int epoch;

    private int min_shuffle;
    private int max_shuffle;

    public GrayWolfOptimization(int n){
        MAX_LENGTH = n;
        LOWERBOUND = -100;
        UPPERBOUND = 100;
        dim = 30; PACK_NUMBER = 30;
        searchAgents_no = 5;
        MAX_EPOCHS = 1000;
        min_shuffle = 8;
        max_shuffle = 20;

        Convergence_curve = new int[MAX_EPOCHS];
    }
    public GrayWolfOptimization(int n, int[] inputs){
        //                                  0               1               2               3               4                   5                   6               7           8                   9
//      private JTextField[] fields = {maxLengthField, trialLimitField, maxEpochField, lowerBoundField, upperBoundField, packNumberField, searchAgents_field, maxIterField, minShuffleField, maxShuffleField};
        MAX_LENGTH = n;
        LOWERBOUND = inputs[3];
        UPPERBOUND = inputs[4];
        dim = inputs[5]; PACK_NUMBER = inputs[5];
        searchAgents_no = inputs[6];
        MAX_EPOCHS = inputs[2];
        min_shuffle = inputs[8];
        max_shuffle = inputs[9];

        Convergence_curve = new int[MAX_EPOCHS];
    }

    /*
    Todo: Insert Documentation here
     */
    public void initializePositions(){
        Positions = new float[searchAgents_no][dim];
        for (int i = 0; i < searchAgents_no; i++) {
            for (int j = 0; j < dim; j++) {
                Positions[i][j] = (R.nextFloat() * (UPPERBOUND - LOWERBOUND)) + LOWERBOUND ;
            }
        }
    }
    /*
    Todo: Insert Documentation here
     */
    public boolean algorithm(){
        wolves = new ArrayList<>();
        solutions = new ArrayList<>();

        epoch = 0;
        boolean done = false;
        Wolf aWolf;

        initialize();
        initializePositions(); // Initialize the positions of search agents

        while(!done){
            if(epoch < MAX_EPOCHS){
                for (int h = 0; h< PACK_NUMBER; h++ ){
                    aWolf = wolves.get(h);
                    aWolf.computeConflicts();
                    if(aWolf.getConflicts() == 0){
                        done = true;
                    }
                    // TODO: ALGORITHM STARTS HERE. Variables from here on out are all instance variables of GWO

                    // setBounds(); TODO: Not Necessary?
                    //System.out.println("GWO is optimizing "); // loop counter TODO: DOUBLE LOOP
                    // Main Loop
//                    for(int epoch=0; epoch<max_iter; epoch++){ // TODO: Not MAX_ITER = MAX_EPOCHS
                    for (int i = 0; i < searchAgents_no; i++) {
                        // Return back the search agents that go beyond the boundaries of the search space
                        for (int j = 0; j < dim; j++) { Positions[i][j] = clip(Positions[i][j], LOWERBOUND, UPPERBOUND); }
                        fitness = getFitness(Positions[i]);
                        compareFitness(i);
                    }
                    float a = 2 - epoch * ((2) / MAX_EPOCHS); // a decreases linearly from 2 to 0
                    // Update the position of search agents including omegas
                    for (int i = 0; i < searchAgents_no; i++) {
                        for (int j = 0; j < dim; j++) {
                            Random R = new Random();
                            float r1 = R.nextFloat(); // Random [0,1]
                            float r2 = R.nextFloat(); // Random [0,1]
                            float A1 = 2 * a * r1 - a;
                            float C1 = 2 * r2;
                            float D_alpha = Math.abs(C1 * Alpha_pos[i] - Positions[i][j]);
                            float X1 = Alpha_pos[i] - A1 * D_alpha;
                            r1 = R.nextFloat(); // Random [0,1]
                            r2 = R.nextFloat(); // Random [0,1]
                            float A2 = 2 * a * r1 - a;
                            float C2 = 2 * r2;
                            float D_beta = Math.abs(C2 * Beta_pos[i] - Positions[i][j]);
                            float X2 = Beta_pos[i] - A2 * D_beta;
                            r1 = R.nextFloat(); // Random [0,1]
                            r2 = R.nextFloat(); // Random [0,1]
                            float A3 = 2 * a * r1 - a;
                            float C3 = 2 * r2;
                            float D_delta = Math.abs(C3 * Delta_pos[i] - Positions[i][j]);
                            float X3 = Delta_pos[i] - A3 * D_delta;
                            Positions[i][j] = (X1 + X2 + X3) / 3;
                        }
                    }
                    Convergence_curve[epoch] = Alpha_score;
                }
                /** End of snippet **/
                epoch++;

            }
            else{
                done = true;
            }
        }
        System.out.println("done");
        if(epoch == MAX_EPOCHS){
            System.out.println("No Solution Found");
            done = false;
        }
        for( Wolf w : wolves){
            if(w.getConflicts() == 0){
                System.out.println("SOLUTION");
                solutions.add(w);
                printSolution(w);
                System.out.println("conflicts: " + w.getConflicts());
            }
        }
        return done;
    }
    /*
    Todo: Insert Documentation here
     */
    public void initialize(){
        int shuffles = 0;
        Wolf newWolf = null;
        int newWolfIndex = 0;
        for(int i=0; i < dim; i++){
            newWolf = new Wolf(MAX_LENGTH);
            wolves.add(newWolf);
            newWolfIndex = wolves.indexOf(newWolf);

            shuffles = getRandomNumber(min_shuffle, max_shuffle);

            for( int j=0; j< shuffles; j++){
                randomlyArrange(newWolfIndex);
            }

            wolves.get(newWolfIndex).computeConflicts();
        }
    }
    /* Gets a random number in the range of the parameters
     *
     * @param: the minimum random number
     * @param: the maximum random number
     * @return: random number
     */
    public int getRandomNumber(int low, int high) {
        return (int)Math.round((high - low) * R.nextDouble() + low);
    }
    /* Gets a random number with the exception of the parameter
     *
     * @param: the maximum random number
     * @param: number to to be chosen
     * @return: random number
     */
    public int getExclusiveRandomNumber(int high, int except) {
        boolean done = false;
        int getRand = 0;

        while(!done) {
            getRand = R.nextInt(high);
            if(getRand != except){
                done = true;
            }
        }

        return getRand;
    }
    /* Prints the nxn board with the queens
     *
     * @param: a particle
     */
    public void compareFitness(int i){
        if (fitness < Alpha_score) {
            Delta_score = Beta_score;
            Delta_pos = Beta_pos.clone();
            Beta_score = Alpha_score;
            Beta_pos = Alpha_pos.clone();
            Alpha_score = fitness;
            Alpha_pos = Positions[i].clone(); // Update alpha
        }
        if (fitness > Alpha_score && fitness < Beta_score) {
            Delta_score = Beta_score; // Update delta
            Delta_pos = Beta_pos.clone();
            Beta_score = fitness; // Update beta
            Beta_pos = Positions[i].clone();
        }
        if (fitness > Alpha_score && fitness > Beta_score && fitness < Delta_score) {
            Delta_score = fitness; // update delta
            Delta_pos = Positions[i].clone();
        }
    }
    /*
    Todo: Insert Documentation here
     */
    public void printSolution(Wolf solution) {
        String board[][] = new String[MAX_LENGTH][MAX_LENGTH];

        // Clear the board.
        for(int x = 0; x < MAX_LENGTH; x++) {
            for(int y = 0; y < MAX_LENGTH; y++) {
                board[x][y] = "";
            }
        }

        for(int x = 0; x < MAX_LENGTH; x++) {
            board[x][solution.getPos(x)] = "Q";
        }

        // Display the board.
        System.out.println("Board:");
        for(int y = 0; y < MAX_LENGTH; y++) {
            for(int x = 0; x < MAX_LENGTH; x++) {
                if(board[x][y] == "Q") {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.print("\n");
        }
    }
    /* Changes a position of the queens in a particle by swapping a randomly selected position
     *
     * @param: index of the particle
     */
    public void randomlyArrange(int index) { //randomly swap 2 positions
        int positionA = getRandomNumber(0, MAX_LENGTH - 1);
        int positionB = getExclusiveRandomNumber(MAX_LENGTH - 1, positionA);
        Wolf thisWolf = wolves.get(index);

        int temp = thisWolf.getPos(positionA);
        thisWolf.setPos(positionA, thisWolf.getPos(positionB));
        thisWolf.setPos(positionB, temp);
    }
    /*
    Todo: Insert Documentation here
     */
    public int getFitness(float[] solution){
        //thisFood.setFitness((worstScore - thisFood.getConflicts()) * 100.0 / bestScore);
        // Todo: Set Fitness here
        int sum = 0;

        for(float i: solution){
            sum += i;
        }
        return sum / solution.length;
    }
    /*
    Todo: Insert Documentation here
     */
    public float clip(float x, float min, float max){
        if(x < min) x = min;
        if(x > max) x = max;
        return x;
    }
    /*
    Todo: Insert Documentation here
     */
    public int getEpoch() {
        return epoch;
    }
    /*
    Todo: Insert Documentation here
     */
    public int getPopSize(){
        return wolves.size();
    }
    /*
        Todo: Insert Documentation here
         */
    public ArrayList<Wolf> getWolves() {
        return wolves;
    }
}
