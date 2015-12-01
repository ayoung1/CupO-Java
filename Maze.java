import java.util.Random;

public class Maze
{
   private Room[][] grid;
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
   }
   
   public Maze(String difficulty)
   {
      generateMaze(difficulty);     
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
            return neighbor;
         }
         neighbor = -2;
      }
      return -1;
   }
   
   public void printMaze()
   {
      for (int i = 0; i < this.grid.length; i++)
      {
         for (int j = 0; j < this.grid[i].length; j++)
         {
            grid[i][j].printRoom();
         }
         System.out.print("\n");
      }
   }   
}