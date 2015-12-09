package trivia;

import java.util.Random;
import java.io.*;

public class Maze implements Serializable
{
   private final char WALL = (char)178, PLAYER = 'P', DOOR = 'D', OPENDOOR = 'U',
		   OPENSPACE = ' ', WINTILE = 'F', WINPATH = '#';
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
   
   public Maze(ObjectInputStream in) throws IOException, ClassNotFoundException
   {
	   readObject(in);
   }
   
   public Room[][] getGrid()
   {
	   return this.grid;
   }
   public char[][] getCharGrid()
   {
	   return this.charGrid;
   }
   
   public int getCurRow()
   {
	   return this.curRow;
   }
   
   public int getCurCol()
   {
	   return this.curCol;
   }
   
   public int getWinRow()
   {
	   return this.winRow;
   }
   
   public int getWinCol()
   {
	   return this.winCol;
   }
   
   public boolean cheatPath()
   {
	   Room[][] gridClone = copyGrid();
	   boolean solvable = traverseMaze(gridClone, this.curCol, this.curRow, -1, true);
	   this.charGrid[(this.curRow*2)+1][(this.curCol*2)+1] = PLAYER;
	   return solvable;
   }
   
   public boolean mazeIsSolvable()
   {
      Room[][] gridClone = copyGrid();
      return traverseMaze(gridClone, this.curCol, this.curRow, -1, false);
   }
   
   private boolean traverseMaze(Room[][] gridClone, int col, int row, int direction, boolean cheating)
   {
      boolean pathFound = false;
      if (validDoor(gridClone,col,row,direction))
      {
         if (col == this.winCol && row == this.winRow)
         {
        	if (cheating)
        	{
            cheatCharGrid((col*2)+1, (row*2)+1, direction);
        		this.grid[row][col].adjustLock(direction, 0);
        	}
            return true;
         }
         if (direction != -1)
         {
            gridClone[row][col].adjustLock(direction, 4);
         }
         gridClone[row][col].adjustLock(1,4);
         pathFound = traverseMaze(gridClone, col+1, row, 3, cheating);
         if (cheating && pathFound)
         {
        	 this.grid[row][col].adjustLock(1,0);
         }
         if (!pathFound)
         {
            gridClone[row][col].adjustLock(2,4);
            pathFound = traverseMaze(gridClone, col, row+1, 0, cheating);
            if (cheating && pathFound)
            {
            	this.grid[row][col].adjustLock(2,0);
            }
         }
         if (!pathFound)
         {
            gridClone[row][col].adjustLock(3,4);
            pathFound = traverseMaze(gridClone, col-1, row, 1, cheating);
            if (cheating && pathFound)
            {
            	this.grid[row][col].adjustLock(3,0);
            }
         }
         if (!pathFound)
         {
            gridClone[row][col].adjustLock(0,4);
            pathFound = traverseMaze(gridClone, col, row-1, 2, cheating);
            if (cheating && pathFound)
            {
            	this.grid[row][col].adjustLock(0,0);
            }
         }
      }
      if (cheating && pathFound)
      {
         this.charGrid[(row*2)+1][(col*2)+1] = WINPATH;
         cheatCharGrid((col*2)+1, (row*2)+1, direction);
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
         newGrid = new Room[9][9];
      }
      else if (difficulty.toLowerCase().equals("hard"))
      {
         newGrid = new Room[11][11];
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
   
   private void cheatCharGrid(int col, int row, int direction)
   {
	   if (direction == 0)
	   {
		   this.charGrid[row-1][col] = WINPATH;
	   }
	   else if (direction == 1)
	   {
		   this.charGrid[row][col+1] = WINPATH;
	   }
	   else if (direction == 2)
	   {
		   this.charGrid[row+1][col] = WINPATH;
	   }
	   else if (direction == 3)
	   {
		   this.charGrid[row][col-1] = WINPATH;
	   }
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
			  printGrid[i][j] = WALL;
		  }
	  }
      for (int i = 0; i < this.grid.length; i++)
      {
         for (int j = 0; j < this.grid[i].length; j++)
         {
            if (this.grid[i][j].allLocked())
            {
               printGrid[(i*2)+1][(j*2)+1] = WALL;
            }
            else if (this.curCol == j && this.curRow == i)
            {
               printGrid[(i*2)+1][(j*2)+1] = PLAYER;
            }
            else if (this.winCol == j && this.curRow == i)
            {
               printGrid[(i*2)+1][(j*2)+1] = WINTILE;
            }
            else
            {
               printGrid[(i*2)+1][(j*2)+1] = OPENSPACE;
            }
            if (this.grid[i][j].getSouthDoor() == 2)
            {
            	printGrid[(i*2)+2][(j*2)+1] = WALL;
            }
            else if (this.grid[i][j].getSouthDoor() == 0)
            {
            	printGrid[(i*2)+2][(j*2)+1] = OPENSPACE;
            }
            else
            {
            	printGrid[(i*2)+2][(j*2)+1] = DOOR;
            }
            if (this.grid[i][j].getEastDoor() == 2)
            {
            	printGrid[(i*2)+1][(j*2)+2] = WALL;
            }
            else if (this.grid[i][j].getEastDoor() == 0)
            {
            	printGrid[(i*2)+1][(j*2)+2] = OPENSPACE;
            }
            else
            {
            	printGrid[(i*2)+1][(j*2)+2] = DOOR;
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
   public boolean isADoor(int direction)
   {
	   if (this.grid[curRow][curCol].checkDirection(direction) == 1)
	   {
		   return true;
	   }
	   return false;
   }
   
   public void updateMaze(int direction, boolean correct)
   {
	   int charCurRow = (this.curRow * 2) + 1;
	   int charCurCol = (this.curCol * 2) + 1;
	   if (direction == 0)
	   {
		   if (charGrid[charCurRow+1][charCurCol] != WALL)
		   {
			   if (charGrid[charCurRow+1][charCurCol] == DOOR)
			   {
				   if (correct)
				   {
					   charGrid[charCurRow+1][charCurCol] = OPENDOOR;
					   this.grid[curRow][curCol].adjustLock(0,0);
					   this.grid[curRow-1][curCol].adjustLock(2,0);
				   }
				   else
				   {
					   charGrid[charCurRow+1][charCurCol] = WALL;
					   this.grid[curRow][curCol].adjustLock(0,2);
					   this.grid[curRow-1][curCol].adjustLock(2,2);
				   }
			   }
		   }
	   }
	   else if (direction == 1)
	   {
		   if (charGrid[charCurRow][charCurCol+1] != WALL)
		   {
			   if (charGrid[charCurRow][charCurCol+1] == DOOR)
			   {
				   if (correct)
				   {
					   charGrid[charCurRow][charCurCol+1] = OPENDOOR;
					   this.grid[curRow][curCol].adjustLock(1,0);
					   this.grid[curRow][curCol+1].adjustLock(3,0);
				   }
				   else
				   {
					   charGrid[charCurRow][charCurCol+1] = WALL;
					   this.grid[curRow][curCol].adjustLock(1,2);
					   this.grid[curRow][curCol+1].adjustLock(3,2);
				   }
			   }
		   }
	   }
	   else if (direction == 2)
	   {
		   if (charGrid[charCurRow-1][charCurCol] != WALL)
		   {
			   if (charGrid[charCurRow-1][charCurCol] == DOOR)
			   {
				   if (correct)
				   {
					   charGrid[charCurRow-1][charCurCol] = OPENDOOR;
					   this.grid[curRow][curCol].adjustLock(2,0);
					   this.grid[curRow-1][curCol].adjustLock(0,0);
				   }
				   else
				   {
					   charGrid[charCurRow-1][charCurCol] = WALL;
					   this.grid[curRow][curCol].adjustLock(2,2);
					   this.grid[curRow-1][curCol+1].adjustLock(0,2);
				   }
			   }
		   }
	   }
	   else if (direction == 3)
	   {
		   if (charGrid[charCurRow][charCurCol-1] != WALL)
		   {
			   if (charGrid[charCurRow][charCurCol-1] == DOOR)
			   {
				   if (correct)
				   {
					   charGrid[charCurRow][charCurCol-1] = OPENDOOR;
					   this.grid[curRow][curCol].adjustLock(3,0);
					   this.grid[curRow][curCol-1].adjustLock(1,0);
					   this.curCol -= 1;
				   }
				   else
				   {
					   charGrid[charCurRow-1][charCurCol] = WALL;
					   this.grid[curRow][curCol].adjustLock(3,2);
					   this.grid[curRow-1][curCol+1].adjustLock(1,2);
				   }
			   }
		   }
	   }
   }
   public boolean winningTile()
   {
	   if (this.curCol == this.winCol && this.curRow == this.winRow)
	   {
		   return true;
	   }
	   return false;
   }
   
   public void movePlayer(int direction)
   {
	   int charCurRow = (this.curRow * 2) + 1;
	   int charCurCol = (this.curCol * 2) + 1;
	   if (direction == 0)
	   {
		   if (charGrid[charCurRow+1][charCurCol] != WALL && charGrid[charCurRow+1][charCurCol] != DOOR)
		   {
			   this.charGrid[charCurRow][charCurCol] = OPENSPACE;
			   this.charGrid[charCurRow+2][charCurCol] = PLAYER;
			   this.curRow += 1;
		   }
	   }
	   else if (direction == 1)
	   {
		   if (charGrid[charCurRow][charCurCol+1] != WALL && charGrid[charCurRow][charCurCol+1] != DOOR)
		   {
			   this.charGrid[charCurRow][charCurCol] = OPENSPACE;
			   this.charGrid[charCurRow][charCurCol+2] = PLAYER;
			   this.curCol += 1;
		   }
	   }
	   else if (direction == 2)
	   {
		   if (charGrid[charCurRow-1][charCurCol] != WALL && charGrid[charCurRow-1][charCurCol] != DOOR)
		   {
			   this.charGrid[charCurRow][charCurCol] = OPENSPACE;
			   this.charGrid[charCurRow-2][charCurCol] = PLAYER;
			   this.curRow -= 1;
		   }
	   }
	   else if (direction == 3)
	   {
		   if (charGrid[charCurRow][charCurCol-1] != WALL && charGrid[charCurRow][charCurCol-1] != DOOR)
		   {
			   this.charGrid[charCurRow][charCurCol] = OPENSPACE;
			   this.charGrid[charCurRow][charCurCol-2] = PLAYER;
			   this.curCol -= 1;
		   }
	   }
   }
   
   private void writeObject(ObjectOutputStream out) throws IOException
   {
	   try
	   {
		   Maze outObject = new Maze(this.grid,this.curCol,this.curRow,this.winCol,this.winRow);
		   out.writeObject(outObject);  
	   }
	   catch (IOException e)
	   {
		   e.printStackTrace();
	   }
   }
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
   {
	   try
	   {
		   Maze inObject = (Maze)in.readObject();
		   this.grid = inObject.getGrid();
		   this.grid = copyGrid();
		   this.charGrid = inObject.getCharGrid();
		   this.curRow = inObject.getCurRow();
		   this.curCol = inObject.getCurCol();
		   this.winRow = inObject.getWinRow();
		   this.winCol = inObject.getWinCol();
	   }
	   catch (IOException e)
	   {
		   e.printStackTrace();
	   }
	   catch (ClassNotFoundException e)
	   {
		   e.printStackTrace();
	   }
   }
   private void readObjectNoData() throws ObjectStreamException
   {
	   
   }
}