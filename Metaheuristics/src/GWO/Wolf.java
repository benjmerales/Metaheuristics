package GWO;

public class Wolf {
    private int MAX_LENGTH;
    private int conflicts;
    private int pos[];


    /* Instantiates a wolf
    *
    * @param: size of n
    * */
    public Wolf(int n){
        this.MAX_LENGTH = n;
    }

    /* Compares two wolf.
     *
     * @param: a wolf to compare with
     */
    public int compareTo(Wolf w){ return this.conflicts - w.getConflicts();}

    /* Computes the conflicts in the nxn board.
     *
     */
    public void computeConflicts() { //compute the number of conflicts to calculate fitness
        String board[][] = new String[MAX_LENGTH][MAX_LENGTH]; //initialize board
        int x = 0; //row
        int y = 0; //column
        int tempx = 0; //temprow
        int tempy = 0; //temcolumn

        int dx[] = new int[] {-1, 1, -1, 1}; //to check for diagonal
        int dy[] = new int[] {-1, 1, 1, -1}; //paired with dx to check for diagonal

        boolean done = false; //used to check is checking fo diagonal is out of bounds
        int conflicts = 0; //number of conflicts found

        clearBoard(board); //clears the board into empty strings
        plotQueens(board); // plots the Q in the board

        // Walk through each of the Queens and compute the number of conflicts.
        for(int i = 0; i < MAX_LENGTH; i++) {
            x = i;
            y = pos[i];

            // Check diagonals.
            for(int j = 0; j < 4; j++) { // because of dx and dy where there are 4 directions for diagonal searching for conflicts
                tempx = x;
                tempy = y; // store coordinate in temp
                done = false;

                while(!done) {//traverse the diagonals
                    tempx += dx[j];
                    tempy += dy[j];

                    if((tempx < 0 || tempx >= MAX_LENGTH) || (tempy < 0 || tempy >= MAX_LENGTH)) { //if exceeds board
                        done = true;
                    } else {
                        if(board[tempx][tempy].equals("Q")) {
                            conflicts++;
                        }
                    }
                }
            }
        }
        this.conflicts = conflicts; //set conflicts of this particle
    }

    /* Plots the queens in the board.
     *
     * @param: a nxn board
     */
    public String[][] plotQueens(String[][] board) {
        for(int i = 0; i < MAX_LENGTH; i++) {
            board[i][this.pos[i]] = "Q";
        }
        return board;
    }

    /* Clears the board.
     *
     * @param: a nxn board
     */
    public String[][] clearBoard(String[][] board) {
        // Clear the board.
        for(int i = 0; i < MAX_LENGTH; i++) {
            for(int j = 0; j < MAX_LENGTH; j++) {
                board[i][j] = "";
            }
        }
        return board;
    }

    /* Initializes the wolves into diagonal queens */
    public void initPos(){
        for (int i=0; i< MAX_LENGTH; i++){
            pos[i] = i;
        }
    }

    /* Gets the data on a specified index.
     *
     * @param: index of pos
     * @return: position of queen
     */
    public int getPos(int index) {
        return pos[index];
    }

    /* Sets the data on a specified index.
     *
     * @param: index of data
     * @param: new position of queen
     */
    public void setPos(int index, int value) {
        this.pos[index] = value;
    }

    /* Gets the conflicts of the wolf.
     *
     * @return: number of conflicts of the wolf
     */
    public int getConflicts() {
        return conflicts;
    }

    /* Sets the conflicts of the particle.
     *
     * @param: new number of conflicts
     */
    public void setConflicts(int conflicts) {
        this.conflicts = conflicts;
    }

    /* Gets the max length

        @return: max length
     */
    public int getMaxLength() {
        return MAX_LENGTH;
    }

}
