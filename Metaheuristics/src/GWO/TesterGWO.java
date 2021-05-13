
package GWO;
public class TesterGWO {
    Writer logWriter;
    GrayWolfOptimization gwo;
    int MAX_RUN;
    int MAX_LENGTH;
    long[] runtimes;

    /* Instantiates the TesterABC class
     *
     */
    public TesterGWO() {
        logWriter = new Writer();
        MAX_RUN = 50;
        runtimes = new long[MAX_RUN];
        gwo = new GrayWolfOptimization(MAX_LENGTH);
    }

    /* Test method accepts the N/max length, and parameters mutation rate and max epoch to set for the ABC accordingly.
     *
     * @param: max length/n
     * @param: trial limit for ABC
     * @param: max epoch for ABC
     */
    public void test(int maxLength, int trialLimit, int maxEpoch) {
        MAX_LENGTH = maxLength;
        gwo = new GrayWolfOptimization(MAX_LENGTH);
        long testStart = System.nanoTime();
        String filepath = "GWO-N" + MAX_LENGTH + "-" + trialLimit + "-" + maxEpoch + ".txt";
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;
        int fail = 0;
        int success = 0;

        logParameters();

        for(int i = 0; i < MAX_RUN; ) {                                             //run 50 sucess to pass passing criteria
            startTime = System.nanoTime();
            if(gwo.algorithm()) {
                endTime = System.nanoTime();
                totalTime = endTime - startTime;

                System.out.println("Done");
                System.out.println("run "+(i+1));
                System.out.println("time in nanoseconds: "+totalTime);
                System.out.println("Success!");
                System.out.println("____________________________________________________");
                runtimes[i] = totalTime;
                i++;
                success++;

                //write to log
                logWriter.add((String)("Run: "+i));
                logWriter.add((String)("Runtime in nanoseconds: "+totalTime));
                logWriter.add((String)("Found at epoch: "+ gwo.getEpoch()));
                logWriter.add((String)("Population size: "+gwo.getPopSize()));
                logWriter.add("");

                for(Wolf w: gwo.getWolves()) {                              //write solutions to log file
                    if(w.getConflicts() == 0) {
                        logWriter.add(w);
                        logWriter.add("");
                    }
                }
                logWriter.add("___________________________________________");
            } else {                                                                //count failures for failing criteria
                fail++;
                System.out.println("Fail!");
            }

            if(fail >= 100) {
                System.out.println("Cannot find solution with these params");
                break;
            }
            startTime = 0;                                                          //reset time
            endTime = 0;
            totalTime = 0;
        }
        System.out.println("\n~~~~ Iteration Complete! ~~~~\n");
        System.out.println("Number of Success: " + success);
        System.out.println("Number of failures: "+ fail);
        logWriter.add("\n\tRuntime summary");
        logWriter.add("");

        for(int x = 0; x < runtimes.length; x++){                                   //print runtime summary
            logWriter.add("\t\t" + Long.toString(runtimes[x]));
        }

        long testEnd = System.nanoTime();
        logWriter.add(Long.toString(testStart));
        logWriter.add(Long.toString(testEnd));
        logWriter.add(Long.toString(testEnd - testStart));


        logWriter.writeFile(filepath);
        printRuntimes();
    }

    /* Converts the parameters of ABC to string and adds it to the string list in the writer class
     *
     */
    public void logParameters() {
        logWriter.add("Grey Wolf Optimization Algorithm");
        logWriter.add("Parameters");
        logWriter.add((String)("MAX_LENGTH/N: "+MAX_LENGTH));
        logWriter.add("");
    }

    /* Prints the runtime summary in the console
     *
     */
    public void printRuntimes() {
        for(long x: runtimes){
            System.out.println("run with time "+x+" nanoseconds");
        }   
    }

    public static void main(String[] args) {
        TesterGWO tester = new TesterGWO();
        tester.test(4, 4, 1000);
    }
}
