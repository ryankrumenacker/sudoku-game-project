public class Sudoku {

    public static void main(String[] args) {
        PennDraw.setScale(0, 10);
        String filename = args[0];
        
        // creating Board object
        Board board = new Board();
        board.initializeGameBoard(filename);

        // checking if the Board contains a Sudoku error to begin with
        if (!board.boardCheck()) {
            throw new IllegalArgumentException("Invalid Input Board");
        } 

        PennDraw.enableAnimation(30);

        // game loop
        while (true) {
            board.draw();

            if (PennDraw.mousePressed()) {
                // throwing away all keys pressed while a Tile is not active
                while (PennDraw.hasNextKeyTyped()) {
                    PennDraw.nextKeyTyped();
                }
                board.update();
            } else {
                board.hoverEffect();
            }

            // if there are no errors and every Tile is filled, the user has won
            if (board.boardCheck() && board.gameOver()) {
                PennDraw.advance();
                break;
            }

            // if the user presses the space bar, clear all inputed Board values
            if (PennDraw.hasNextKeyTyped()) {
                if (PennDraw.nextKeyTyped() == ' ') {
                    board.clearBoard();
                }
            }
            PennDraw.advance();  
        } 
        // printing the congratulations message
        PennDraw.disableAnimation();
        PennDraw.clear();
        PennDraw.setFontSize(30);
        PennDraw.text(5, 5, "Congratulations! You have won!");
        PennDraw.setFontSize(15);
        PennDraw.text(5, 4, "Press n to view the completed board");
        while (true) {
            if (PennDraw.hasNextKeyTyped()) {
                char key = PennDraw.nextKeyTyped();
                if (key == 'n') {
                    PennDraw.clear();
                    board.draw();
                }
            }
        }
    }
}
