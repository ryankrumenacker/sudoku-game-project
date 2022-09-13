/*  Name: Ryan Krumenacker
*  PennKey: ryankrum 
*  Recitation: 204
*
*  Description: the Board class defines the Board object and contains all of the
*               complex functions that are used to implement the game in the 
*               Sudoku class. the Board object contains a 9x9 2D array of Tile
*               objects, along with two booleans values that help to track the 
*               current state of the Board. 
*
*/
public class Board {

    public static final char[] VALID_CHARACTERS = {'1', '2', '3', '4', '5', '6', 
                                                   '7', '8', '9', ' '};

    // gameBoard is the 9x9 2D array of Tiles that makes up the Board
    private Tile[][] gameBoard;

    // a Board is active if a Tile within the gameBoard has been clicked on
    private boolean active; 

    // a Board is flagged if a Tile within the gameBoard belongs to a Sudoku error
    private boolean flagged;

    public Board() {
        this.gameBoard = null;
        this.active = false;
        this.flagged = false;
    }

    // Getter Methods -----------------------------------------------------------
    public boolean isActive() {
        return this.active;
    }

    // Setter Methods -----------------------------------------------------------
    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    // Complex Functions-------------------------------------------------------
    /**
     * Inputs: None
     * Outputs: None
     * Description: prints the current values of the gameBoard to the compiler in
     *              a readable format
     *
    */
    public void printBoard() {
        String message = " ";
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                System.out.print(gameBoard[i][j].getGrid() + " ");
            }
            System.out.println();
            message = "";
        }
    }

    /**
     * Inputs: a text file name as a String
     * Outputs: None
     * Description: initializes the 9x9 Tile gameBoard that belongs to the Board 
     *              object given a filename as a String
     *
    */
    public void initializeGameBoard(String filename) {
        Tile[][] gameBoard = new Tile[9][9];
        In inStream = new In(filename);
        // variables to help determine the grid number of each Tile
        int gridRow = 0;
        int gridColumn = 0;
        boolean original;
        boolean characterIsValid;
        for (int i = 0; i < gameBoard.length; i++) {
            String line = inStream.readLine();
            // ensuring there are at least 9 characters on each line
            if (line.length() < 9) {
                throw new IllegalArgumentException("Invalid Input Board");
            }
            if (i % 3 == 0 && i != 0) {
                gridRow += 3; 
            }
            for (int j = 0; j < gameBoard[i].length; j++) {
                original = true;
                characterIsValid = false;
                char character = line.charAt(j);
                // checking whether the original characters are valid
                for (char validChar : VALID_CHARACTERS) {
                    if (character == validChar) {
                        characterIsValid = true;
                    }
                }
                if (!characterIsValid) {
                    throw new IllegalArgumentException("Invalid Character");
                }
                String tileValue =  "" + character;
                // if the Tile is empty, its value will be initialized to zero
                if (tileValue.equals(" ")) {
                    tileValue = "0";
                    original = false;
                }
                if (j >= 0 && j <= 2) {
                    gridColumn = 1;
                } else if (j >= 3 && j <= 5) {
                    gridColumn = 2;
                } else {
                    gridColumn = 3;
                }
                Tile newTile = new Tile(i, j, gridRow + gridColumn, tileValue, 
                                        original);
                gameBoard[i][j] = newTile;
            }
        }
        this.gameBoard = gameBoard;
    }

    /**
     * Inputs: None
     * Outputs: a boolean determining whether the Board contains Sudoku errors
     * Description: this function checks for Sudoku errors by checking the 
     *              requirements of the three possible errors. if two values within
     *              the same grid are the same, then there is a Sudoku error. if 
     *              two values within the same row are the same, then there is a 
     *              Sudoku error. if two values within the same column are the same,
     *              then there is a Sudoku error. returns true if there is no 
     *              Sudoku error across the entire Board, and false if there is
     *
     * How: it loops through all tiles, adding all 9 Tiles of a given grid, row, or
     *      column to an array. it then loops through the array to check whether
     *      any two Tiles within that array have the same value. if they do, then
     *      there is an error in that grid, row, or column
     *              
    */
    public boolean boardCheck() {
        boolean gridCheck = true;
        boolean rowCheck = true;
        boolean columnCheck = true;
        
        // checking if any two values within one grid, row, or column are the same
        Tile[] gridArray = new Tile[9];
        Tile[] rowArray = new Tile [9];
        Tile[] columnArray = new Tile[9];
        int currentGridArrayIndex;
        int currentRowArrayIndex;
        int currentColumnArrayIndex;
        for (int i = 0; i < this.gameBoard.length; i++) {
            currentGridArrayIndex = 0;
            currentRowArrayIndex = 0;
            currentColumnArrayIndex = 0;
            for (int j = 0; j < this.gameBoard.length; j++) {
                for (int k = 0; k < this.gameBoard[j].length; k++) {
                    Tile currentTile = this.gameBoard[j][k];
                    if (currentTile.getGrid() == i + 1) {
                        gridArray[currentGridArrayIndex] =  currentTile;
                        currentGridArrayIndex++;
                    }
                    if (currentTile.getRow() == i) {
                        rowArray[currentRowArrayIndex] = currentTile;
                        currentRowArrayIndex++;
                    }
                    if (currentTile.getColumn() == i) {
                        columnArray[currentColumnArrayIndex] = currentTile;
                        currentColumnArrayIndex++;
                    }
                }
            }
            // check if values within rowArray or columnArray are the same
            for (int p = 0; p < gridArray.length; p++) {
                for (int q = p + 1; q < gridArray.length; q++) {
                    Tile firstTile = gridArray[p];
                    Tile secondTile = gridArray[q];
                    if (!(firstTile.getValue().equals("0"))) {
                        if (firstTile.getValue().equals(secondTile.getValue())) {
                            this.flagGrid(i + 1);
                            firstTile.doubleFlag();
                            secondTile.doubleFlag();
                            gridCheck = false;                   
                        }
                    }
                    firstTile = rowArray[p];
                    secondTile = rowArray[q];
                    if (!(firstTile.getValue().equals("0"))) {
                        if (firstTile.getValue().equals(secondTile.getValue())) {
                            this.flagRow(i);
                            firstTile.doubleFlag();
                            secondTile.doubleFlag();
                            rowCheck = false;                   
                        }
                    }
                    firstTile = columnArray[p];
                    secondTile = columnArray[q];
                    if (!(firstTile.getValue().equals("0"))) {
                        if (firstTile.getValue().equals(secondTile.getValue())) {
                            this.flagColumn(i);
                            firstTile.doubleFlag();
                            secondTile.doubleFlag();
                            columnCheck = false;                   
                        }
                    }
                }
            } 
        }
        return gridCheck && rowCheck && columnCheck;
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: draws the board to the Penn Draw canvas with all of its current
     *              Tiles and their current states
     *
    */
    public void draw() {
        PennDraw.clear();
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setPenRadius();
        PennDraw.square(5, 5, 4.5);
        // individually drawing each Tile in the gameBoard
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                currentTile.draw();
            }
        }
        // drawing lines between each 3x3 grid for better visual
        PennDraw.setPenRadius(0.006);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.line(3.5, 9.5, 3.5, 0.5);
        PennDraw.line(6.5, 9.5, 6.5, 0.5);
        PennDraw.line(0.5, 3.5, 9.5, 3.5);
        PennDraw.line(0.5, 6.5, 9.5, 6.5);
        // drawing clear message
        PennDraw.text(5, 9.75, "Press SPACE to clear the board");
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: the hover effect is implemented when the user has not clicked a
     *              specific Tile, but is instead hovering over them. when a 
     *              Tile is being hovered over, it will appear a light shade of 
     *              gray.
    */
    public void hoverEffect() {
        this.active = false;
        int[] indicies = this.setUpHelper(PennDraw.mouseX(), PennDraw.mouseY());
        // a null indicies value means the mouse was not on the board
        if (indicies == null) {
            return;
        }
        int rowIndex = indicies[0];
        int columnIndex = indicies[1];
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                int tileRow = currentTile.getInverseRow();
                int tileColumn = currentTile.getInverseColumn();
                if (tileRow == rowIndex && tileColumn == columnIndex) {
                    currentTile.hover();
                }
            }
        }
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: this is the one of the most complex functions of the game.
     *              it is entered once the mouse has been pressed. it checks what 
     *              Tile has been clicked, if any, and activates that Tile. it then
     *              waits for the next move of the user. if the user types a value,
     *              the function updates the value of the active Tile to that value.
     *              if the user clicks again, the function recalls itself
    */
    public void update() {
        this.active = false;
        // activating the Tile that has been clicked
        int[] indicies = this.setUpHelper(PennDraw.mouseX(), PennDraw.mouseY());
        // if the click was not on the Board, do not execute the function
        if (indicies == null) {
            return;
        }
        int rowIndex = indicies[0];
        int columnIndex = indicies[1];
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                int tileRow = currentTile.getInverseRow();
                int tileColumn = currentTile.getInverseColumn();
                if (tileRow == rowIndex && tileColumn == columnIndex && 
                    !currentTile.isOriginal()) {
                    this.activate();
                    currentTile.activate();
                    currentTile.draw();
                    PennDraw.advance();
                }
            }
        }
        Tile activeTile = this.findActiveTile();
        // if the user has clicked an original Tile, do not execute the function
        if (activeTile == null) {
            return;
        }
        while (this.isActive()) {
            // if the user types a new integer, update the value of the active Tile
            if (PennDraw.hasNextKeyTyped()) {
                // if the key is a valid integer, update the value of the Tile
                char keyTyped = PennDraw.nextKeyTyped();
                for (char validChar : VALID_CHARACTERS) {
                    if (keyTyped == validChar && keyTyped != ' ') {
                        activeTile.setValue("" + keyTyped);
                    }
                }
                // if the person types a backspace, update the value to empty
                if (keyTyped == '\b') {
                    activeTile.setValue("0");
                }
                activeTile.draw();
                this.deactivate();
                activeTile.deactivate();
            } else if (PennDraw.mousePressed()) {
                // if the user pressed the mouse again, recall update
                activeTile.deactivate();
                this.deactivate();
                this.update();
            }
        }
    }

    /**
     * Inputs: None
     * Outputs: a boolean determining the state of the Tiles
     * Description: this function is used to check whether or not the game is 
     *              over by checking if every Tile on the Board has been assigned
     *              a value. if every Tile has a value and there are no errors,
     *              then the game is over and the user has won
    */
    public boolean gameOver() {
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                if (currentTile.getValue().equals("0")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: this function clears all numbers inputted by the user by 
     *              setting the value of every Tile that is not an original Tile
     *              to "0"
    */
    public void clearBoard() {
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                if (!currentTile.isOriginal()) {
                    currentTile.setValue("0");
                }
            }
        }
    }
    
    // Helper Functions --------------------------------------------------------
    /**
     * Inputs: the current x-position and current y-position of the mouse
     * Outputs: an int array containing the conversions of that mouse position to
     *          the relative positions on the gameBoard
     * Description: this function completes three tasks, setting up the use of 
     *              other functions within the Board class. first, it checks 
     *              whether or not the mouse click was within the gameBoard. 
     *              second, it unhovers, unflags, and undoubleflags all Tiles. 
     *              third, it takes the x-position and y-position of the mouse 
     *              and converts them to integers that fit relative to the 
     *              gameBoard
    */
    private int[] setUpHelper(double xPos, double yPos) {
        PennDraw.clear();
        this.draw();

        // 1) if the mouse is not over the Board, no Tiles can be accessed
        if (xPos > 9.5 || xPos < 0.5 || yPos > 9.5 || yPos < 0.5) {
            return null;
        }

        this.flagged = false;
        // 2) all Tiles must be unhovered, unflagged, and undoubleflagged
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                currentTile.unhover();
                currentTile.unflag();
                currentTile.unDoubleFlag();
            }
        }

        // 3) converting mouse position to relative indicies on the gameBoard
        int rowIndex = (int) yPos;
        int columnIndex = (int) xPos;
        if (Math.abs(xPos - (int) xPos) > 0.5) {
            columnIndex = (int) xPos + 1;
        } 
        if (Math.abs(yPos - (int) yPos) > 0.5) {
            rowIndex = (int) yPos + 1;
        }
        int[] relativeIndices = {rowIndex, columnIndex};
        return relativeIndices;
    }

    /**
     * Inputs: None
     * Outputs: the currently active Tile in the Board
     * Description: this function finds the currently active Tile within the Board
     *              and returns its reference. this function will return null if
     *              there is no active tile.
    */
    private Tile findActiveTile() {
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = this.gameBoard[i][j];
                if (currentTile.isActive()) {
                    return currentTile;
                }
            }
        } 
        return null;
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: flags every Tile within a given grid
    */
    private void flagGrid(int grid) {
        this.flagged = true;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = gameBoard[i][j];
                if (currentTile.getGrid() == grid) {
                    currentTile.flag();
                }
            }
        }
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: flags every Tile within a given row
    */
    private void flagRow(int row) {
        this.flagged = true;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = gameBoard[i][j];
                if (currentTile.getRow() == row) {
                    currentTile.flag();
                }
            }
        }
    }

    /**
     * Inputs: None
     * Outputs: None
     * Description: flags every Tile within a given column
    */
    private void flagColumn(int column) {
        this.flagged = true;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Tile currentTile = gameBoard[i][j];
                if (currentTile.getColumn() == column) {
                    currentTile.flag();
                }
            }
        }
    }
}