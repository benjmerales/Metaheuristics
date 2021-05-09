package GWO;

import java.util.Random;

public class GWO__ {

    Random R = new Random(); // import random
    private int lb;
    private int ub;
    private int dim;
    private int SearchAgents_no;
    private int Max_iter;

    public GWO__(int lb, int ub, int dim, int SearchAgents_no, int Max_inter){
        this.lb = lb;
        this.ub = ub;
        this.dim = dim;
        this.SearchAgents_no = SearchAgents_no;
        this.Max_iter = Max_inter;

    }
    public int objf(float[] solution){
        //thisFood.setFitness((worstScore - thisFood.getConflicts()) * 100.0 / bestScore);
        int sum = 0;
        for(float i: solution){
            sum += i;
        }
        return sum / solution.length;
    }
    public int greyWolfAlgo(){
        float[] Alpha_pos = new float[dim]; // pos init arrays
        float[] Beta_pos = new float[dim];
        float[] Delta_pos = new float[dim];

        int Alpha_score = Integer.MAX_VALUE; // set to inf
        int Beta_score = Integer.MAX_VALUE;
        int Delta_score = Integer.MAX_VALUE;

        float[] arr_lb = new float[dim]; // init arr bounds
        float[] arr_ub = new float[dim];

        for(int i=0; i<dim; i++){
            arr_lb[i] = lb;
            arr_ub[i] = ub;
        } // is instance portion

        float[][] Positions = new float[SearchAgents_no][dim]; // Initialize the positions of search agents
        for (int i = 0; i < SearchAgents_no; i++) {
            // Positions[:, i] = (numpy.random.uniform(0,1, SearchAgents_no) * (ub[i] - lb[i]) + lb[i]
            for (int j = 0; j < dim; j++) {
                Positions[i][j] = (R.nextFloat() * (arr_ub[i] - arr_lb[i])) + arr_lb[i] ;
            }
        }
        int[] Convergence_curve = new int[Max_iter];

        System.out.println("GWO is optimizing "); // loop counter
        long startTime = System.nanoTime();

        // Main Loop
        for(int l=0; l<Max_iter; l++){
            for (int i = 0; i < SearchAgents_no; i++) {

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
            float a = 2 - l * ((2) / Max_iter); // a decreases linearly from 2 to 0
            // Update the position of search agents including omegas
            for (int i = 0; i < SearchAgents_no; i++) {
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
        return Alpha_score;
    }
    public float clip(float x, float min, float max){
            if(x < min) x = min;
            if(x > max) x = max;
        return x;
    }
    /*public static void main(String[] args) {
        GWO__ G = new GWO__(-100,100,30,5, 1000);
        G.greyWolfAlgo();
    }*/
}
