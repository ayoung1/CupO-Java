/*
*In order to test the path-finding algorithm values for the maze are used as follows:
*   [0 = Traversable], [1 = Wall], [2 = End], [3 = Traversed]
*/

public class Maze
{
   private Room[][] grid;
   private int curCol, curRow, winCol, winRow;
   
   public Maze(Room[][] grid, int curCol, int curRow)
   {
      if (curCol < 0 || curRow < 0 || curRow >= grid.length || curCol >= grid[curCol].length)
      {
         throw new IndexOutOfBoundsException();
      }
      this.grid = grid;
      this.curCol = curCol;
      this.curRow = curRow;
   }
   
//    public Maze()
//    {
//       this.grid = generateMaze();     
//    }
   
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
         pathFound = traverseMaze(gridClone,col+1, row, 3);
         if (!pathFound)
         {
            pathFound = traverseMaze(gridClone,col, row+1, 2);
         }
         if (!pathFound)
         {
            pathFound = traverseMaze(gridClone,col-1,row, 1);
         }
         if (!pathFound)
         {
            pathFound = traverseMaze(gridClone,col,row-1, 0);
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
      if ((col >= 0 && row >= 0 && row < gridClone.length && col < gridClone[row].length) && (!(gridClone[row][col].isLocked(direction))))
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
            newGrid[i][j] = this.grid[i][j];
         }
      }
      return newGrid;
   }
   
//    private int[][] generateMaze()
//    {
//       return;
//    }
}