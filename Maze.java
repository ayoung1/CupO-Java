/*
In order to test the path-finding algorithm values for the maze are used as follows:
   [0 = Traversable], [1 = Wall], [2 = End], [3 = Traversed]
*/

public class Maze
{
   private int[][] grid;
   private int curCol, curRow;
   
   public Maze(int[][] grid, int curCol, int curRow)
   {
      if (curCol < 0 || curRow < 0 || curRow >= grid.length || curCol >= grid[curCol].length)
      {
         throw new IndexOutOfBoundsException();
      }
      this.grid = grid;
      this.curCol = curCol;
      this.curRow = curRow;
   }
   
   public boolean mazeIsSolvable()
   {
      int[][] gridClone = copyGrid();
      return traverseMaze(gridClone, this.curCol, this.curRow);
   }
   
   private boolean traverseMaze(int[][] gridClone, int col, int row)
   {
      boolean pathFound = false;
      if (validIndex(gridClone,col,row))
      {
         if (gridClone[row][col] == 2)
         {
            return true;
         }
         gridClone[row][col] = 3;
         pathFound = traverseMaze(gridClone,col+1, row);
         if (!pathFound)
         {
            pathFound = traverseMaze(gridClone,col, row+1);
         }
         if (!pathFound)
         {
            pathFound = traverseMaze(gridClone,col-1,row);
         }
         if (!pathFound)
         {
            pathFound = traverseMaze(gridClone,col,row-1);
         }
      }
      return pathFound;
   }
   
   private boolean validIndex(int[][] gridClone, int col, int row)
   {
      if ((col >= 0 && row >= 0 && row < gridClone.length && col < gridClone[row].length) && (gridClone[row][col] != 1 && gridClone[row][col] != 3))
      {
         return true;
      }
      return false;
   }
   
   private int[][] copyGrid()
   {
      int[][] newGrid = new int[this.grid.length][];
      for (int i = 0; i < newGrid.length; i++)
      {
         newGrid[i] = new int[this.grid[i].length];
         for (int j = 0; j < newGrid[i].length; j++)
         {
            newGrid[i][j] = this.grid[i][j];
         }
      }
      return newGrid;
   }
}