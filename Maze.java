package trivia;

import java.util.Random;
import java.util.Scanner;

public class Maze
{
   private Room[][] grid;
   private char[][] charGrid;
   private int curCol, curRow, winCol, winRow;
   
   public Maze(Room[][] grid, int curCol, int curRow, int winCol, int winRow)
   {
      if (curCol < 0 || curRow < 0 || curRow >= grid.length || curCol >= grid[curCol].length)
      {
         throw new IndexOutOfBoundsException();
      }
      this.grid = grid;
      this.curCol = curCol;
      this.curRow = curRow;
      this.winCol = winCol;
      this.winRow = winRow;
      createCharGrid();
   }
   
   public Maze(String difficulty)
   {
      generateMaze(difficulty);
      createCharGrid();    
   }
   
   public boolean mazeIsSolvable()
   {
      Room[][] gridClone = copyGrid();
      return traverseMaze(gridClone, this.curCol, this.curRow, -1);
   }
   
   private boolean traverseMaze(Room[][] gridClone, int col, int row, int direction)
   {
      boolean pathFound = false;
      if (validDoor(gridClone,col,row,direction))
      {
         if (col == this.winCol && row == this.winRow)
         {
            return true;
         }
         if (direction != -1)
         {
            gridClone[row][col].adjustLock(direction, 4);
         }
         gridClone[row][col].adjustLock(1,4);
         pathFound = traverseMaze(gridClone,col+1, row, 3);
         if (!pathFound)
         {
            gridClone[row][col].adjustLock(2,4);
            pathFound = traverseMaze(gridClone,col, row+1, 0);
         }
         if (!pathFound)
         {
            gridClone[row][col].adjustLock(3,4);
            pathFound = traverseMaze(gridClone,col-1,row, 1);
         }
         if (!pathFound)
         {
            gridClone[row][col].adjustLock(0,4);
            pathFound = traverseMaze(gridClone,col,row-1, 2);
         }
      }
      return pathFound;
   }
   
   private boolean validDoor(Room[][] gridClone, int col, int row, int direction)
   {
      if (direction == -1)
      {
         return true;
      }
      if ((col >= 0 && row >= 0 && row < gridClone.length && col < gridClone[row].length) && (gridClone[row][col].isPassable(direction) && gridClone[row][col].checkDirection(direction) != 4))
      {
         return true;
      }
      return false;
   }
   
   private Room[][] copyGrid()
   {
      Room[][] newGrid = new Room[this.grid.length][];
      for (int i = 0; i < newGrid.length; i++)
      {
         newGrid[i] = new Room[this.grid[i].length];
         for (int j = 0; j < newGrid[i].length; j++)
         {
            newGrid[i][j] = new Room(this.grid[i][j].getNorthDoor(),this.grid[i][j].getEastDoor(),this.grid[i][j].getSouthDoor(),this.grid[i][j].getWestDoor());
         }
      }
      return newGrid;
   }
   
   private void generateMaze(String difficulty)
   {
      Room[][] newGrid;
      boolean[][] visited;
      if (difficulty.toLowerCase().equals("regular"))
      {
         newGrid = new Room[14][14];
      }
      else if (difficulty.toLowerCase().equals("hard"))
      {
         newGrid = new Room[20][20];
      }
      else
      {
         newGrid = new Room[7][7];
      }
      visited = new boolean[newGrid.length][newGrid.length];
      for (int i = 0; i < visited.length; i++)
      {
         for (int j = 0; j < visited[i].length; j++)
         {
            newGrid[i][j] = new Room(2,2,2,2);
            visited[i][j] = false;
         }
      }
      recursiveBacktracker(newGrid,visited,0,0,-1);
      Random rng = new Random();
      this.curCol = rng.nextInt(newGrid.length);
      this.curRow = rng.nextInt(newGrid.length);
      this.winCol = rng.nextInt(newGrid.length);
      this.winRow = rng.nextInt(newGrid.length);
      this.grid = newGrid;
   }
   
   private void recursiveBacktracker(Room[][] grid, boolean[][] visited, int col, int row, int prevDoor)
   {
      if (!(row >= 0) && (col >= 0) && (row < visited.length) && (col < visited.length))
      {
         return;
      }
      if (visited[row][col] == true)
      {
         return;
      }
      if (prevDoor != -1)
      {
         grid[row][col].adjustLock(prevDoor, 1);
      }
      visited[row][col] = true;
      int direction = unvisitedNeighbor(col, row, visited);
      while (direction != -1)
      {
           grid[row][col].adjustLock(direction, 1);
           if (direction == 0)
           {
              recursiveBacktracker(grid,visited,col,row-1,2);
           }
           else if (direction == 1)
           {
              recursiveBacktracker(grid,visited,col+1,row,3);
           }
           else if (direction == 2)
           {
              recursiveBacktracker(grid,visited,col,row+1,0);
           }
           else if (direction == 3)
           {
              recursiveBacktracker(grid,visited,col-1,row,1);
           }
           direction = unvisitedNeighbor(col,row,visited);
      }
   }
   
   private int unvisitedNeighbor(int col, int row, boolean[][] visited)
   {
      boolean[] neighbors = new boolean[4];
      neighbors[0] = false;
      neighbors[1] = false;
      neighbors[2] = false;
      neighbors[3] = false;
      int neighbor = -1;
      if (row - 1 >= 0 &&  visited[row-1][col] == false)
      {
         neighbors[0] = true;
         neighbor = -2;   
      }
      if (col + 1 < visited[row].length && visited[row][col+1] == false)
      {
         neighbors[1] = true;
         neighbor = -2;
      }
      if (row + 1 < visited.length && visited[row+1][col] == false)
      {
         neighbors[2] = true;
         neighbor = -2;
      }
      if (col - 1 >= 0 && visited[row][col-1] == false)
      {
         neighbors[3] = true;
         neighbor = -2;
      }
      if (neighbor == -1)
      {
         return neighbor;
      }
      while (neighbor == -2)
      {
         Random rng = new Random();
         neighbor = rng.nextInt(4);
         if (neighbors[neighbor] == true)
         {
            break;
         }
         neighbor = -2;
      }
      return neighbor;
   }
   
   private void createCharGrid()
   {
	  char[][] printGrid = new char[(this.grid.length*2)+1][(this.grid.length*2)+1];
	  for (int i = 0; i < printGrid.length; i++)
	  {
		  for (int j = 0; j < printGrid.length; j++)
		  {
			  printGrid[i][j] = 'W';
		  }
	  }
      for (int i = 0; i < this.grid.length; i++)
      {
         for (int j = 0; j < this.grid[i].length; j++)
         {
            if (this.grid[i][j].allLocked())
            {
               printGrid[(i*2)+1][(j*2)+1] = 'W';
            }
            else if (this.curCol == j && this.curRow == i)
            {
               printGrid[(i*2)+1][(j*2)+1] = 'P';
            }
            else if (this.winCol == j && this.curRow == i)
            {
               printGrid[(i*2)+1][(j*2)+1] = 'F';
            }
            else
            {
               printGrid[(i*2)+1][(j*2)+1] = 'O';
            }
            if (this.grid[i][j].getSouthDoor() == 2)
            {
            	printGrid[(i*2)+2][(j*2)+1] = 'W';
            }
            else if (this.grid[i][j].getSouthDoor() == 0)
            {
            	printGrid[(i*2)+2][(j*2)+1] = 'O';
            }
            else
            {
            	printGrid[(i*2)+2][(j*2)+1] = 'D';
            }
            if (this.grid[i][j].getEastDoor() == 2)
            {
            	printGrid[(i*2)+1][(j*2)+2] = 'W';
            }
            else if (this.grid[i][j].getEastDoor() == 0)
            {
            	printGrid[(i*2)+1][(j*2)+2] = 'O';
            }
            else
            {
            	printGrid[(i*2)+1][(j*2)+2] = 'D';
            }
         }
      }
      this.charGrid = printGrid;
   }
   
   public void printMaze()
   {
      for (int i = 0; i < this.charGrid.length; i++)
      {
         for (int j = 0; j < this.charGrid[i].length; j++)
         {
            System.out.print(this.charGrid[i][j]);
         }
         System.out.print("\n");
      }
   }
   
   public void playGame()
   {
	   boolean youWon = false;
	   int charCurRow, charCurCol;
	   while (mazeIsSolvable())
	   {
		   charCurRow = (this.curRow * 2) + 1;
		   charCurCol = (this.curCol * 2) + 1;
		   if (curRow == winRow && curCol == winCol)
		   {
			   youWon = true;
			   break;
		   }
		   printMaze();
		   // user input through function
		   int direction = 0;
		   if (direction == 0)
		   {
			   if (charGrid[charCurRow+1][charCurCol] != 'W')
			   {
				   if (charGrid[charCurRow+1][charCurCol] == 'D')
				   {
					   if (correctAns())
					   {
						   charGrid[charCurRow+1][charCurCol] = 'U';
						   this.grid[curRow][curCol].adjustLock(0,0);
						   this.grid[curRow-1][curCol].adjustLock(2,0);
						   this.curRow += + 1;
					   }
					   else
					   {
						   charGrid[charCurRow+1][charCurCol] = 'W';
						   this.grid[curRow][curCol].adjustLock(0,2);
						   this.grid[curRow-1][curCol].adjustLock(2,2);
					   }
				   }
				   else
				   {
					   this.curRow += 1;
				   }
			   }
		   }
		   else if (direction == 1)
		   {
			   if (charGrid[charCurRow][charCurCol+1] != 'W')
			   {
				   if (charGrid[charCurRow][charCurCol+1] == 'D')
				   {
					   if (correctAns())
					   {
						   charGrid[charCurRow][charCurCol+1] = 'U';
						   this.grid[curRow][curCol].adjustLock(1,0);
						   this.grid[curRow][curCol+1].adjustLock(3,0);
						   this.curCol += 1;
					   }
					   else
					   {
						   charGrid[charCurRow][charCurCol+1] = 'W';
						   this.grid[curRow][curCol].adjustLock(1,2);
						   this.grid[curRow][curCol+1].adjustLock(3,2);
					   }
				   }
				   else
				   {
					   this.curCol += 1;
				   }
			   }
		   }
		   else if (direction == 2)
		   {
			   if (charGrid[charCurRow-1][charCurCol] != 'W')
			   {
				   if (charGrid[charCurRow-1][charCurCol] == 'D')
				   {
					   if (correctAns())
					   {
						   charGrid[charCurRow-1][charCurCol] = 'U';
						   this.grid[curRow][curCol].adjustLock(2,0);
						   this.grid[curRow-1][curCol].adjustLock(0,0);
						   this.curRow -= 1;
					   }
					   else
					   {
						   charGrid[charCurRow-1][charCurCol] = 'W';
						   this.grid[curRow][curCol].adjustLock(2,2);
						   this.grid[curRow-1][curCol+1].adjustLock(0,2);
					   }
				   }
				   else
				   {
					   this.curRow -= 1;
				   }
			   }
		   }
		   else if (direction == 3)
		   {
			   if (charGrid[charCurRow][charCurCol-1] != 'W')
			   {
				   if (charGrid[charCurRow][charCurCol-1] == 'D')
				   {
					   if (correctAns())
					   {
						   charGrid[charCurRow][charCurCol-1] = 'U';
						   this.grid[curRow][curCol].adjustLock(3,0);
						   this.grid[curRow][curCol-1].adjustLock(1,0);
						   this.curCol -= 1;
					   }
					   else
					   {
						   charGrid[charCurRow-1][charCurCol] = 'W';
						   this.grid[curRow][curCol].adjustLock(3,2);
						   this.grid[curRow-1][curCol+1].adjustLock(1,2);
					   }
				   }
				   else
				   {
					   this.curCol -= 1;
				   }
			   }
		   }
	   }
   }
}