/**     Sample Board
 *
 *      0   1   2   3
 *  0   -   -   -   -
 *  1   -   -   -   -
 *  2   -   -   -   -
 *  3   -   -   -   -
 *
 *  The sample board shows the index values for the columns and rows
 */

import java.util.*;
import java.io.*;

/*
 * Modified by Xuanpei Ouyang
 * Date: Feb 2, 2015
 * File: Board.java
 * 
 * This program can create a new board for 2048 game with specified grid 
 * size, specific random number and with the started score 0. The program
 * can also load saved game by reading the string which saves the old game
 * information.  And this program can save the current board, score and the
 * size of board of the current game to a output file which we can use to load
 * and continue the game later. 
 * When create a board, there will be two random tiles in the board with value
 * 2 or 4. The probability that the value of tile added is 2 is 90% and the 
 * probability of value of tile added is 4 is 10%. This program can also add 
 * a random tiles in the board according the probability. This program can also
 * check if the game is over by checking if the tiles in the board can not 
 * move in any direction. This program can check if the tiles in the board can 
 * move in a specific direction and will return true if can move and return 
 * false if it cannot move. The program can also get score, the reference 
 * to the grid and print out all the value in the grid.
 */

/* 
 * Name : Board
 * Purpose: This class has final variable to indicate the number of start
 * tiles which is 2 and the probability that the value of tile added by 
 * the add random tile method is 2 is 90% and the probability of value 
 * of tile added is 4 is 10%. The value of random tiles is depends on the final         
 * probability variable.
 * The program can create a new board for 2048 game with specified grid size, 
 * specific random number and with the start score is 0 and can also load the 
 * game by reading string which contains saved board information. 
 * The save method in this class can save the size the board of the current 
 * game, the score the current game and the value in the grid. This program can
 * also check if the game is over by checking if the tiles in the board can not       
 * move in any direction. This program can check if the tiles in the board can   
 * move in a specific direction and will return true if can move and return      
 * false if it cannot move. The program can also get score, the reference        
 * to the grid and print out all the value in the grid.  
 */
public class Board
{
  // instance variables
  // the number of random tiles in the board when game starts
  public final int NUM_START_TILES = 2;

  // the probability which indicates the occurrance of tile with value two
  public final int TWO_PROBABILITY = 90;

  // the size of the grid 
  public final int GRID_SIZE;

  // the random value used to create a board  
  private final Random random;

  // declare a two dimensional array to store the value in the board
  private int[][] grid;

  // the two-dimensional array to store the previous board
  private int[][] prevGrid;

  // value to store the score of player
  private int score;

  /** 
   * Method for constructing a fresh board with two random tiles in the board
   * by the input boardSize and set the random to be the random the board uses
   * to generate random number
   *
   * @param boardSize the size of the board 
   * @param random random to be the random the board uses to generate random 
   *               number
   */
  public Board(int boardSize, Random random){

    // assign input random to be the random the board uses to generate 
    // random number
    this.random = random;

    // assign the input boardSize to the size of grid to the constant 
    // GRID_SIZE
    GRID_SIZE = boardSize;  

    // create a two dimension integer array list
    grid = new int[GRID_SIZE][GRID_SIZE];

    // initialize the score to zero
    score = 0;

    // add the random tiles in this board, the number of random tiles added 
    // in this board is determined by the constant NUM_START_TILES
    for(int i = 0; i < NUM_START_TILES; i++){
      addRandomTile();
    }
    prevGrid = grid;
  }

  /** 
   * Method for constructing a board based off of an input file and set random 
   * to be the random the board uses to generate random number
   *
   * @param inputBoard the information of the board 
   * @param random random to be the random the board uses to generate 
   *               random number
   */
  public Board(String inputBoard, Random random) throws IOException{

    // create a Scanner object to read input string
    Scanner input = new Scanner(new File(inputBoard));

    // assign input random to be the random the board uses to generate 
    // random number
    this.random = random;

    // read the integer in the inputBoard and assign it to the 
    // constant GRID_SIZE
    GRID_SIZE = input.nextInt();

    // read and integer in the inputBoard and assign it to score
    score = input.nextInt();

    // create a two dimension integer array list
    grid = new int[GRID_SIZE][GRID_SIZE];
    for(int row = 0; row < grid.length; row++){
      for(int column = 0; column < grid[row].length; column++){

        // read the each element from the inputBoard and assign it to each 
        // entry of the board
        grid[row][column] = input.nextInt();
      }
    }

    prevGrid = grid; 
  } 

  /** 
   * Method for saving the current board to a file. The method will save the 
   * size of board, score and each value in the board.
   *
   * @param outputBoard the string which store the information of games, the 
   * size of board, the current score and each value in the board
   */
  public void saveBoard(String outputBoard) throws IOException
  {
    // create a PrintWriter object to write information into a file 
    PrintWriter writer = new PrintWriter(new File(outputBoard));

    // save the GRID_SIZE to the file
    writer.println(GRID_SIZE); 

    // save the score to the file
    writer.println(score);

    // save each element from the cuurent board into the file
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        writer.print(grid[row][column]+" ");
      }
      writer.println();
    }

    // save the file properly 
    writer.close();
  }

  /** 
   * Method for adding a random tile of value two or four to a random empty
   * space on the board. Probability that the value of tile added by the add 
   * random tile method is 2 is 90% and the probability of value of tile added
   * is 4 is 10%.
   */
  public void addRandomTile()
  {    
    // declare a variable to count the number of empty tiles
    int countOfOpenTiles = 0;

    // go through the two dimension array and increase the coun by 1 it if 
    // there is a empty tile 
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] == 0)
          countOfOpenTiles++;
      }
    }

    // if there is no empty tile in this board, quit this method immediately
    if(countOfOpenTiles == 0)
      return;

    // declare a variable to count the number of tiles that alreay be checked
    int numberOfStep = 0;

    // generate a random number betweem 0 and the number of empty tiles - 1
    // and assign the value to variable location
    int location = random.nextInt(countOfOpenTiles)+1;
    

    // declare a value 100 for generate random number and decide the value of 
    // tile should added
    int randomMax = 100;

    // generate a random value between 0 and 99 and assign the random value 
    int value = random.nextInt(randomMax);

    // when the random generated value is less than 90, then the value of tile
    // is 2
    int valueOfNinetyPossible = 2; 

    // when the random generated value is larger than 90, then the value of 
    // the tile is 4
    int valueOfTwentyPossible = 4;

    // if the random generated value is less then the constant TWO_PROBABILITY
    // which is 90, then assign the value to 2, otherwise assign value to 4
    if(value < TWO_PROBABILITY){
      value = valueOfNinetyPossible;
    }   
    else {
      value = valueOfTwentyPossible;
    }   


    // loop over the grid and check if the index of the is equals the location
    // and if yes, then the change the value of that index to the value
    // variable
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] == 0) {
          numberOfStep++;
        }
        if(numberOfStep == location){
          grid[row][column] = value;
          return;
        }
      }
    }
  }

  /** 
   * Method for checking to see if we have a game over
   *
   * @return a boolean value, return true if the game over and return
   *         false if the game is not over
   */
  public boolean isGameOver()
  {
    // a boolean value that indicates whether the game is over
    boolean result = false;

    // check if the tiles in the board can move up
    boolean canMoveUp = canMove(Direction.UP);

    // check if the tiles in the board can move down
    boolean canMoveDown = canMove(Direction.DOWN);

    // check if the tiles in the board can move left
    boolean canMoveLeft = canMove(Direction.LEFT);

    // check if the tiles in the board can move right
    boolean canMoveRight = canMove(Direction.RIGHT);

    // check the tiles in the board can move up, down, left or right, if any 
    // of these is true, return false to indicate the game is not over
    if(canMoveUp == true || canMoveDown == true
        || canMoveLeft == true || canMoveRight == true){ 
      result = false;
    } 

    // check if the tiles in the board can move up, down, left and right, if 
    // tiles can not move in any direction, then shows that Game Over and 
    // return true
    else if(canMoveUp == false && canMoveDown == false && canMoveLeft == false
        && canMoveRight == false){
      System.out.println("Game Over!");
      result = true;
    }

    // return the boolean value to show if game is over
    return result;

  }


  /**
   * Method for checking if the board can be moved in a certain direction.
   * If yes, then perform a move operation and add a random tile.
   * @return return if the move is successful, else return false
   */ 
  public boolean moveAndAdd(Direction direction){

    prevGrid = grid;

    if(move(direction)){
      addRandomTile();
      return true;
    }
    return false;
  }

  /**
   * Method for restoring the previous board
   * @return void
   */ 
  public void undo(){

    grid = prevGrid;
  }

  /**
   * Method for rotating the board according to the inpur parameter
   * @return void
   */ 
  public void rotate(boolean rotateClockwise) {

    prevGrid = grid;
    int[][] newBoard = new int[GRID_SIZE][GRID_SIZE];

    if (rotateClockwise) {
      for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
          newBoard[j][GRID_SIZE - 1 - i] = grid[i][j];
        }
      }

      grid = newBoard;
    } 
    else {
      for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
          newBoard[GRID_SIZE - 1 - j][i] = grid[i][j];
        }
      }
      grid = newBoard;
    }
  }

  /** 
   * Method for determining if we can move in a given direction
   *
   * @return return true if we can move in a given direction, return false if 
   *         we can not move in a given direction
   */
  public boolean canMove(Direction direction)
  {
    // declare a boolean 
    boolean result = false;

    // switch to different method to check if we can move in a given 
    // direction according to the input direction
    switch(direction){
      case UP:

        // check if the tiles in the board can move up and assign the result
        // to the result variable
        result = canMoveUp();
        break;
      case DOWN:

        // check if the tiles in the board can move down and assign the result     
        // to the result variable 
        result = canMoveDown();
        break;
      case LEFT:

        // check if the tiles in the board can move left and assign the result     
        // to the result variable 
        result = canMoveLeft();
        break;
      case RIGHT:

        // check if the tiles in the board can move right and assign the result     
        // to the result variable 
        result = canMoveRight();
        break;
    }

    // return the boolean value to show if the tiles can move
    return result;
  }

  /** 
   * Method for determining if we can move to the right
   *
   * @return return true if it can move right, and return false if cannot
   *         move right
   */
  private boolean canMoveRight(){
    // create a new grid to store the board value with all the empty
    // tiles in the left part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the left part of 
    // board            
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newRow = GRID_SIZE;         

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int row = GRID_SIZE-1; row >= 0; row--){                  
      newRow--;                                                           
      int newColumn = GRID_SIZE-1;                                                  
      for(int column = GRID_SIZE-1;column >= 0; column--){                                    
        if(grid[row][column] != 0){                                                                   
          newGrid1[newRow][newColumn] = grid[row][column];                                                  
          newColumn--;                                                                                          
        }
      }
    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same                                                  
    // and not zero. If the adjacent tiles are the same, then times 2 to the                                                          
    // left one tiles and set the tiles in the right to zero                                                                                  
    for(int row = GRID_SIZE-1; row >= 0; row--){                                                                                                        
      for(int column = GRID_SIZE-1; column > 0; column--){                                                                                                        
        if(newGrid1[row][column-1] == newGrid1[row][column] && 
            newGrid1[row][column] != 0){                                                                               
          newGrid1[row][column-1] = (newGrid1[row][column])*2;                                                                                                                  
          newGrid1[row][column] = 0;                                                                                                                                                
        }                                                                 
      }            
    }                  

    // declare a row index for newGrid2
    newRow = GRID_SIZE;        

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly                                                                                                                                          
    // check the other elements in the newGrid1        
    for(int row = GRID_SIZE-1; row >= 0; row--){             
      newRow--;                                                      
      int newColumn = GRID_SIZE-1;                                             
      for(int column = GRID_SIZE-1; column >= 0; column--){                              
        if(newGrid1[row][column] != 0){                                                          
          newGrid2[newRow][newColumn] = newGrid1[row][column];                                         
          newColumn--;                                                                                     
        }                                                                                                  
      }
    }

    // loop over the original grid and the newGrid2 to check if there is any 
    // difference between this two grid. If yes, then the tiles in the board
    // can move in this direction
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] != newGrid2[row][column])
          return true;
      }
    }
    return false;
  }


  /** 
   * Method for determining if we can move to the left
   *
   * @return return true if can move left, return false if it cannot 
   *         move left
   */
  private boolean canMoveLeft(){
    // create a new grid to store the board value with all the empty
    // tiles in the right part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the right part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newRow = -1;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int row = 0; row < GRID_SIZE; row++){
      newRow++;
      int newColumn = 0;
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newColumn++;
        }
      }

    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 1; column < GRID_SIZE; column++){
        if(newGrid1[row][column-1] == newGrid1[row][column] && 
            newGrid1[row][column] != 0){
          newGrid1[row][column-1] = (newGrid1[row][column])*2;
          newGrid1[row][column] = 0;
        }
      }
    }

    // declare a row index for newGrid2
    newRow = -1;

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    for(int row = 0; row < GRID_SIZE; row++){
      newRow++;
      int newColumn = 0;
      for(int column = 0; column < GRID_SIZE; column++){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newColumn++;
        }
      }
    }

    // loop over the original grid and the newGrid2 to check if there is any 
    // difference between this two grid. If yes, then the tiles in the board
    // can move in this direction
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] != newGrid2[row][column])
          return true;
      }
    }
    return false;
  }



  /** 
   * Method for determining if we can move up
   *
   * @return return true if can move up, return false if it cannot 
   *         move up
   */ 
  private boolean canMoveUp(){
    // create a new grid to store the board value with all the empty
    // tiles in the lower part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the lower part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newColumn = -1;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int column = 0; column < GRID_SIZE; column++){
      newColumn++;
      int newRow = 0;
      for(int row = 0; row < GRID_SIZE; row++){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newRow++;
        }

      }
    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int column = 0; column < GRID_SIZE; column++){
      for(int row = 1; row < GRID_SIZE; row++){
        if(newGrid1[row-1][column] == newGrid1[row][column] && 
            newGrid1[row][column] != 0){
          newGrid1[row-1][column] = (newGrid1[row][column])*2;
          newGrid1[row][column] = 0;
        }
      }
    }

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    newColumn = -1;
    for(int column = 0; column < GRID_SIZE; column++){
      newColumn++;
      int newRow = 0;
      for(int row = 0; row < GRID_SIZE; row++){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newRow++;
        }
      }
    }

    // loop over the original grid and the newGrid2 to check if there is any 
    // difference between this two grid. If yes, then the tiles in the board
    // can move in this direction
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] != newGrid2[row][column])
          return true;
      }
    }
    return false;
  }


  /** 
   * Method for determining if we can move down
   *
   * @return return true if can move down, return false if cannot
   *         move down
   */
  public boolean canMoveDown(){
    // create a new grid to store the board value with all the empty
    // tiles in the upper part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the upper part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newColumn = GRID_SIZE;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int column = GRID_SIZE-1; column >= 0; column--){
      newColumn--;
      int newRow = GRID_SIZE-1;
      for(int row = GRID_SIZE-1; row >= 0; row--){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newRow--;
        }
      }

    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int column = GRID_SIZE-1; column >= 0; column--){
      for(int row = GRID_SIZE-1; row > 0; row--){
        if(newGrid1[row-1][column] == newGrid1[row][column] &&
            newGrid1[row][column] != 0){
          newGrid1[row-1][column] = (newGrid1[row][column])*2;
          newGrid1[row][column] = 0;
        }
      }
    }


    // declare a row index for newGrid2
    newColumn = GRID_SIZE;

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    for(int column = GRID_SIZE-1; column >= 0; column--){
      newColumn--;
      int newRow = GRID_SIZE-1;
      for(int row = GRID_SIZE-1; row >= 0; row--){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newRow--;
        }
      }
    }

    // loop over the original grid and the newGrid2 to check if there is any 
    // difference between this two grid. If yes, then the tiles in the board
    // can move in this direction
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] != newGrid2[row][column])
          return true;
      }
    }
    return false;
  }

  /** 
   * Method for Performing a move Operation. If input is direction 
   * to up, then the method will check if the tiles in the current
   * board can move, if yes, then the method will move the tiles
   * and return true, otherwise, it will return false
   *
   * @param direction the direction that all the tiles should move to
   * @return the boolean value that return true if the 
   *         tiles in the board can move the the input direction, 
   *         return false if it cannot
   */
  public boolean move(Direction direction){

    prevGrid = grid;
    // declare a boolean 
    boolean result = false;

    // switch to different method to check if we can move in a given 
    // direction according to the input direction
    switch(direction){
      case UP:

        // check if the tiles in the board can move up, if they can, then move 
        // up
        if(canMoveUp()){
          moveUp();
          result = true;
        }
        break;
      case DOWN:

        // check if the tiles in the board can move down, if they can, then  
        // move down 
        if(canMoveDown()){
          moveDown();
          result = true;
        }
        break;
      case LEFT:

        // check if the tiles in the board can move left, if they can, then      
        // move left
        if(canMoveLeft()){
          moveLeft();
          result = true;
        }
        break;
      case RIGHT:

        // check if the tiles in the board can move right, if they can, then      
        // move right 
        if(canMoveRight()){
          moveRight();
          result = true;
        }
        break;
    }

    // return the boolean value to show if the tiles can move 
    return result;
  }

  /** 
   * Method for moving all the tiles in the current board to the left and shows 
   * if all the tiles can move to the left
   *
   * @return the boolean value, if can move left, then return true, if not, 
   * then return false
   */
  public boolean moveLeft(){

    // create a new grid to store the board value with all the empty
    // tiles in the right part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the right part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newRow = -1;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int row = 0; row < GRID_SIZE; row++){
      newRow++;
      int newColumn = 0;
      for(int column = 0; column < GRID_SIZE; column++){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newColumn++;
        }
      }

    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int row = 0; row < GRID_SIZE; row++){
      for(int column = 1; column < GRID_SIZE; column++){
        if(newGrid1[row][column-1] == newGrid1[row][column] && 
            newGrid1[row][column] != 0){
          newGrid1[row][column-1] = (newGrid1[row][column])*2;

          // add up the score 
          score = score + newGrid1[row][column-1];
          newGrid1[row][column] = 0;
        }
      }
    }

    // declare a row index for newGrid2
    newRow = -1;

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    for(int row = 0; row < GRID_SIZE; row++){
      newRow++;
      int newColumn = 0;
      for(int column = 0; column < GRID_SIZE; column++){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newColumn++;
        }
      }
    }

    // assign newGird2 to the reference grid
    grid = newGrid2;

    // if the tiles in the board can move left, then return true, else return
    // false
    if(canMoveLeft())
      return true;

    return false;
  }


  /** 
   * Method for moving all the tiles in the current board to the right and
   *shows if all the tiles can move to the right
   *
   * @return the boolean value, if can move to the right, then
   * return true, if not, then return false
   */
  public boolean moveRight(){

    // create a new grid to store the board value with all the empty
    // tiles in the left part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the left part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newRow = GRID_SIZE;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int row = GRID_SIZE-1; row >= 0; row--){
      newRow--;
      int newColumn = GRID_SIZE-1;
      for(int column = GRID_SIZE-1;column >= 0; column--){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newColumn--; 
        }
      }
    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int row = GRID_SIZE-1; row >= 0; row--){
      for(int column = GRID_SIZE-1; column > 0; column--){
        if(newGrid1[row][column-1] == newGrid1[row][column] &&
            newGrid1[row][column] != 0){
          newGrid1[row][column-1] = (newGrid1[row][column])*2;

          // add up the score 
          score = score + newGrid1[row][column-1];
          newGrid1[row][column] = 0;
        }
      }
    }

    // declare a row index for newGrid2
    newRow = GRID_SIZE;

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    for(int row = GRID_SIZE-1; row >= 0; row--){
      newRow--;
      int newColumn = GRID_SIZE-1;
      for(int column = GRID_SIZE-1; column >= 0; column--){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newColumn--;
        }
      }
    }

    // assign newGird2 to the reference grid
    grid = newGrid2;

    // if the tiles in the board can move right, then return true, else return
    // false
    if(canMoveRight())
      return true;

    return false;
  }

  /** 
   * Method for moving all the tiles in the current board to down and shows 
   * if all the tiles can move down
   *
   * @return the boolean value, if can move down, then return true, if not,
   * then return false
   */
  public boolean moveDown(){

    // create a new grid to store the board value with all the empty
    // tiles in the upper part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the upper part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newColumn = GRID_SIZE;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int column = GRID_SIZE-1; column >= 0; column--){
      newColumn--;
      int newRow = GRID_SIZE-1;
      for(int row = GRID_SIZE-1; row >= 0; row--){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newRow--;
        }
      }

    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int column = GRID_SIZE-1; column >= 0; column--){
      for(int row = GRID_SIZE-1; row > 0; row--){
        if(newGrid1[row-1][column] == newGrid1[row][column] && 
            newGrid1[row][column] != 0){
          newGrid1[row-1][column] = (newGrid1[row][column])*2;

          // add up the score 
          score = score + newGrid1[row-1][column];
          newGrid1[row][column] = 0;
        }
      }
    }


    // declare a row index for newGrid2
    newColumn = GRID_SIZE;

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    for(int column = GRID_SIZE-1; column >= 0; column--){
      newColumn--;
      int newRow = GRID_SIZE-1;
      for(int row = GRID_SIZE-1; row >= 0; row--){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newRow--;
        }
      }
    }

    // assign newGird2 to the reference grid
    grid = newGrid2;

    // if the tiles in the board can move down, then return true, else return
    // false
    if(canMoveDown())
      return true;

    return false;
  }

  /** 
   * Method for moving all the tiles in the current board to up and shows 
   * if all the tiles can move up
   *
   * @return the boolean value, if can move up, then return true, if not, then
   * return false
   */
  public boolean moveUp(){

    // create a new grid to store the board value with all the empty
    // tiles in the lower part of board
    int[][] newGrid1 = new int[GRID_SIZE][GRID_SIZE];

    // create a new grid to store the board value with all the same 
    // empty combined together and all the empty tiles in the lower part of 
    // board
    int[][] newGrid2 = new int[GRID_SIZE][GRID_SIZE];

    // declare a row index for newGrid1
    int newColumn = -1;

    // loop over the original grid and check each element in the board, 
    // if the element is not zero, then set the first entry in the newGrid1
    // to the value of the first nonzero value in the original grid and 
    // similarly check the other elements in the original grid
    for(int column = 0; column < GRID_SIZE; column++){
      newColumn++;
      int newRow = 0;
      for(int row = 0; row < GRID_SIZE; row++){
        if(grid[row][column] != 0){
          newGrid1[newRow][newColumn] = grid[row][column];
          newRow++;
        }

      }
    }

    // loop over the newGrid1 and check if the the adjacent tiles are the same
    // and not zero. If the adjacent tiles are the same, then times 2 to the 
    // left one tiles and set the tiles in the right to zero
    for(int column = 0; column < GRID_SIZE; column++){
      for(int row = 1; row < GRID_SIZE; row++){
        if(newGrid1[row-1][column] == newGrid1[row][column] &&
            newGrid1[row][column] != 0){
          newGrid1[row-1][column] = (newGrid1[row][column])*2;

          // add up the score 
          score = score + newGrid1[row-1][column];
          newGrid1[row][column] = 0;
        }
      }
    }

    // loop over the newGrid1 to check each element in the board,
    // if the element is not zero, then set the first entry in the newGrid2
    // to the value of the first nonzero value in the newGrid1 and similarly
    // check the other elements in the newGrid1
    newColumn = -1;
    for(int column = 0; column < GRID_SIZE; column++){
      newColumn++;
      int newRow = 0;
      for(int row = 0; row < GRID_SIZE; row++){
        if(newGrid1[row][column] != 0){
          newGrid2[newRow][newColumn] = newGrid1[row][column];
          newRow++;  
        }
      }
    }

    // assign newGird2 to the reference grid
    grid = newGrid2;

    // if the tiles in the board can move up, then return true, else return
    // false
    if(canMoveUp())
      return true;

    return false;
  }


  /** 
   * Method for returning the reference to the 2048 Grid
   *
   * @return the reference to the grid which is two dimensional array
   */
  public int[][] getGrid()
  {
    return grid;
  }

  /**                                                                            
   * Method for returning the current score                      
   *                                                                             
   * @return return a integer value which is the score          
   */  
  public int getScore()
  {
    return score;
  }

  @Override
    public String toString()
    {
      StringBuilder outputString = new StringBuilder();
      outputString.append(String.format("Score: %d\n", score));
      for (int row = 0; row < GRID_SIZE; row++)
      {
        for (int column = 0; column < GRID_SIZE; column++){
          outputString.append(grid[row][column] == 0 ? "    -" :
              String.format("%5d", grid[row][column]));
        }
        outputString.append("\n");
      } 
      return outputString.toString();
    }
}
