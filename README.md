# Sudoku Game Project in Java

<p align="center">
  <img src="https://user-images.githubusercontent.com/113403062/189804694-b18c1327-fb4d-4f52-bfaa-02901b5aff05.gif" alt="animated" width=700 height=700/>
</p>

This project was made in May 2022. There are a total of 3 Java files that are necessary to play the game. However, since this was a school project, it uses a closed source software package known as **PennDraw** for all visualizations and cannot be run on all systems.

### Sudoku.java

**Sudoku.java** is the class that controls the game. After initializing the Board
object, which defines the Board and all 81 Tiles associated with it, this class
simply waits for input from the user before calling more complex functions
that are located within the **Board.java class**. If the user has won the game, the 
**Sudoku.java** class disables the animation and draws a "congratulations" message 
to the screen indicating that the user has won the game. 


### Board.java

**Board.java** is by far the most important class needed to run my implementation of
the game. It defines the **Board** object, which controls all 81 Tiles that the
user sees on the screen while playing the game. When the user has not clicked
the screen, the **hoverEffect()** function of the Board class is utilized to color
Tiles that are being hovered over in a light shade of grey. Once the user has 
clicked some Tile on the Board, the **update()** function of the Board class is 
utilized to determine which Tile was clicked and subsequently highlight the border
of that Tile in a shade of green. Following this action, depending on the next
user input, the function either updates the value of the Tile, deactivates the
Tile, or activates a new Tile. At the end of each game iteration, the 
**boardCheck()** function is called to determine whether or not there currently
exists an error within the Sudoku board. If an error exists, the region is
highlighted in red and the values of the Tiles causing the error are drawn
in a bright red. **boardCheck()** is called together with **gameOver()**. If there is 
no error and each Tile contains a value, then the user has won the game.

Note that outside of these major functions within the Board class, there are also
a number of private helper functions listed at the bottom of the class. Their 
descriptions are included above each individual function. 

### Tile.java

**Tile.java** defines the Tile object. The main purpose of the Tile object is to
control the large possiblity of game states that any given Tile can have 
at a certain time. For example, in my implementation, a Tile can be **original,
active, inactive, flagged, unflagged, doubleFlagged, undoubleflagged, hovered,
or unhovered**. These parameters all correspond to different states of each 
Tile in the game. Many of these parameters are used for visual purposes.
Some of these parameters are permanent, such as **original**, while others are 
constantly changing, such as **hovered** and **active**. 
