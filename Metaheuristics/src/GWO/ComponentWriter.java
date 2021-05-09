package GWO;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ComponentWriter {
    private ArrayList<Integer> run_IDs;
    private ArrayList<Integer> runtimes;
    private ArrayList<Integer> epoch_IDs;
    private ArrayList<Integer> population_sizes;
    private ArrayList<ArrayList<String>> all_boards;

    private int N;
    private String filepath;
    private String swarmIntellName;

    public ComponentWriter(String filepath)
    {
        this.filepath = filepath;
        run_IDs = new ArrayList<>();
        runtimes = new ArrayList<>();
        epoch_IDs = new ArrayList<>();
        population_sizes = new ArrayList<>();
        all_boards = new ArrayList<>();
    }

    public void parseFile(){
        try{
            File file = new File(filepath);
            Scanner S = new Scanner(file);
            swarmIntellName = S.nextLine();
            S.nextLine();
            N = findNumberOnString(S.nextLine());
            String board_temp = "";
            ArrayList<String> boards = new ArrayList<>();
            while(S.hasNextLine()){
                String data = S.nextLine();
                if(data.contains("Run: ")){ run_IDs.add(findNumberOnString(data)); }
                if(data.contains("Runtime")){ if(!data.contains("summary")) runtimes.add(findNumberOnString(data)); }
                if(data.contains("Found")){ epoch_IDs.add(findNumberOnString(data)); }
                if(data.contains("Population")){ population_sizes.add(findNumberOnString(data)); }
                if(data.contains("Q")){ // Point where reader discovered a board
                    board_temp += data + "\n";
                }
                if(data.equals("")){
                    if(!board_temp.equals("")){
                        boards.add(board_temp);
                        board_temp = "";
                    }
                }
                if(data.contains("__")){
                    all_boards.add((ArrayList) boards.clone());
                    boards.clear();
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }
    public void getDataAtIndex(int index){

        System.out.println("Run: " + run_IDs.get(index));
        System.out.println("Board Size: " + N);
        System.out.println("Runtime at nanoseconds " + runtimes.get(index));
        System.out.println("Found at Epoch: " + epoch_IDs.get(index));
        System.out.println("Population size: " + population_sizes.get(index));
        int counter = 1;
        for(String boards: all_boards.get(index)){
            System.out.println("Board " + counter++ + ": ");
            System.out.println(boards);
        }
    }
    public int findNumberOnString(String temp){
        return Integer.parseInt(temp.replaceAll("[^0-9]+", ""));
    }
    public int getRunID(int index){
        return run_IDs.get(index);
    }
    public int getRuntime(int index){
        return runtimes.get(index);
    }
    public int getEpochID(int index){
        return epoch_IDs.get(index);
    }
    public int getPopulation(int index){
        return population_sizes.get(index);
    }
    public ArrayList<String> getSolutions(int index){
        return all_boards.get(index);
    }
    public int getNumberOfRuns(){
        return run_IDs.size();
    }
    public int getNumberOfSolutions(int index){
        return all_boards.get(index).size();
    }
}
