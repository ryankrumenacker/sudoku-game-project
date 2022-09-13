/*  Name: Ryan Krumenacker
*  PennKey: ryankrum 
*  Recitation: 204
*
*  Description: the Tile class defines the Tile object and contains one complex
*               function that is used to draw an indivudal Tile to the PennDraw
*               screen. A Tile contains a total of 9 parameters. Three are integers
*               that are never changed and simply store the row, column, and grid
*               index. One is a String that stores the current value of the Tile.
*               The remaining 5 are booleans which help track the current state of
*               of the Tile as the user interacts with the game. These booleans
*               are used to determined the colors with which the Tile is draw in 
*               the Tile.draw() function. 
*
*/
public class Tile {

    public static final double HALF_TILE_LENGTH = 0.5;

    private int row;
    private int column;

    // a grid is a 3x3 block of Tiles
    // top left is grid 1, top middle is grid 2, ..., bottom right is grid 9
    private int grid;

    // value is the number currently stored within the Tile
    private String value;

    // a Tile is active if the user has clicked on it
    private boolean active;

    // a Tile is flagged if it belongs to a grid, row, or column that contains
    // a Sudoku error
    private boolean flagged;

    // a Tile is doubeFlagged it is the direct source of a Sudoku error
    private boolean doubleFlagged;

    // a Tile is beingHovered if the mouse is over it, but it has not been clicked
    private boolean beingHovered;

    // a Tile is considered original if it was assigned a value from the text file
    private boolean original;

    // Tile constructor
    public Tile(int row, int column, int grid, String value, boolean original) {
        this.row  = row;
        this.column = column;
        this.grid = grid;
        this.value = value;
        this.active = false; 
        this.flagged = false;
        this.doubleFlagged = false;
        this.beingHovered = false;
        this.original = original;
    }

    // Getter Functions --------------------------------------------------------
    public String getValue() {
        return this.value;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public int getInverseRow() {
        return 10 - this.row - (int) (2 * HALF_TILE_LENGTH);  
    }

    public int getInverseColumn() {
        return this.column + (int) (2 * HALF_TILE_LENGTH);
    }

    public int getGrid() {
        return this.grid;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isOriginal() {
        return this.original;
    }

    // Setter Functions ------------------------------------------------------
    public void flag() {
        this.flagged = true;
    }

    public void unflag() {
        this.flagged = false; 
    }

    public void doubleFlag() {
        this.doubleFlagged = true;
    }

    public void unDoubleFlag() {
        this.doubleFlagged = false;
    }

    public void hover() {
        this.beingHovered = true;
    }

    public void unhover() {
        this.beingHovered = false; 
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }

    // Complex Functions ------------------------------------------------------
    /**
     * Inputs: None
     * Outputs: None
     * Description: draws an individual Tile to its relevant position on the 
     *              Board in the PennDraw canvas
     *
    */
    public void draw() {
        int tileXCenter = this.getInverseColumn();
        int tileYCenter = this.getInverseRow();

        PennDraw.setPenColor(PennDraw.WHITE);
        // when a Tile is hovered, change its color to light grey
        if (this.beingHovered) {
            PennDraw.setPenColor(220, 220, 220);
        }
        // when a Tile is flagged, change its color to light readable
        if (this.flagged) {
            PennDraw.setPenColor(240, 128, 128);
        }
        PennDraw.filledSquare(tileXCenter, tileYCenter, HALF_TILE_LENGTH);

        PennDraw.setPenColor(PennDraw.BLACK);
        // when a Tile has been clicked, change its border to green
        if (this.active) {
            PennDraw.setPenRadius(0.006);
            PennDraw.setPenColor(0, 255, 0);
        }
        PennDraw.square(tileXCenter, tileYCenter, HALF_TILE_LENGTH);

        PennDraw.setPenRadius();
        // if the Tile value exists, draw the text of that value in the Tile
        if (!this.value.equals("0")) {
            PennDraw.setPenColor(PennDraw.BLACK);
            // if a Tile is original, draw the value in blue
            if (this.original) {
                PennDraw.setPenColor(0, 0, 255);
            }
            if (this.doubleFlagged) {
                PennDraw.setPenColor(255, 0, 0);
            }
            PennDraw.text(tileXCenter, tileYCenter, this.value);
        }
    }
}