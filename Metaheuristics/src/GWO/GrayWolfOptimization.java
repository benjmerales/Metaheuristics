/** GrayWolfOptimizatio.java
 *
 * Solves the N-Queens puzzle using Gray Wolf Optimization Algorithm

 */
package GWO;
import PSO.Particle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collection;

public class GrayWolfOptimization {
    /* GRAY WOLF PARAMETERS */
    private int MAX_LENGTH; // N number of queens

    Random R = new Random();
    private int lowerBound;
    private int upperBound;
    private int dim;
    private int searchAgents_no;
    private int max_iter;

    private ArrayList<Wolf> wolves;
    private ArrayList<Wolf> solutions;
    private int epoch;

    private int min_shuffle;
    private int max_shuffle;

    public GrayWolfOptimization(int n){
        MAX_LENGTH = n;
        lowerBound = -100;
        upperBound = 100;
        dim = 30;
        searchAgents_no = 5;
        max_iter = 1000;
        min_shuffle = 8;
        max_shuffle = 20;
    }


    public boolean algorithm(){
        boolean done = true;
        float[] Alpha_pos = new float[dim]; // pos init arrays
        float[] Beta_pos = new float[dim];
        float[] Delta_pos = new float[dim];

        int Alpha_score = Integer.MAX_VALUE; // set to inf
        int Beta_score = Integer.MAX_VALUE;
        int Delta_score = Integer.MAX_VALUE;

        float[] arr_lb = new float[dim]; // init arr bounds
        float[] arr_ub = new float[dim];

        for(int i=0; i<dim; i++){
            arr_lb[i] = lowerBound;
            arr_ub[i] = upperBound;
        } // is instance portion

        float[][] Positions = new float[searchAgents_no][dim]; // Initialize the positions of search agents
        for (int i = 0; i < searchAgents_no; i++) {
            for (int j = 0; j < dim; j++) {
                Positions[i][j] = (R.nextFloat() * (arr_ub[i] - arr_lb[i])) + arr_lb[i] ;
            }
        }
        int[] Convergence_curve = new int[max_iter];

        System.out.println("GWO is optimizing "); // loop counter
        long startTime = System.nanoTime();

        // Main Loop
        for(int l=0; l<max_iter; l++){
            for (int i = 0; i < searchAgents_no; i++) {

                // Return back the search agents that go beyond the boundaries of the search space
                for (int j = 0; j < dim; j++) {
                    Positions[i][j] = clip(Positions[i][j], arr_lb[i], arr_ub[i]);
                }

                int fitness = objf(Positions[i]);

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
            float a = 2 - l * ((2) / max_iter); // a decreases linearly from 2 to 0
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
            Convergence_curve[l] = Alpha_score;

            System.out.println("At iteration " + l + " the best fitness is " + Alpha_score);
        }
        long endTime = System.nanoTime();
        System.out.println("Total runtime is " + (float)(endTime-startTime)/1000000000 + " seconds.");

        return done;
    }
    public void initialize(){
        int shuffles = 0;
        Wolf newWolf = null;
        int wolfIndex = 0;
        for(int i=0; i < dim; i++){
            newWolf = new Wolf(MAX_LENGTH);
            wolves.add(newWolf);
            wolfIndex = wolves.indexOf(newWolf);

            shuffles = getRandomNumber(min_shuffle, max_shuffle);

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

    public int objf(float[] solution){
        //thisFood.setFitness((worstScore - thisFood.getConflicts()) * 100.0 / bestScore);
        int sum = 0;
        for(float i: solution){
            sum += i;
        }
        return sum / solution.length;
    }
    public float clip(float x, float min, float max){
        if(x < min) x = min;
        if(x > max) x = max;
        return x;
    }

    public int getEpoch() {
        return epoch;
    }
    public int getPopSize(){
        return wolves.size();
    }

    public ArrayList<Wolf> getWolves() {
        return wolves;
    }
}
