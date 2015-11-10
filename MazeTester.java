public class MazeTester
{
   public static void main(String[] args)
   {
      int[][] testGrid = {{0,0,0,1,1,2},
                          {0,1,0,0,0,0},
                          {0,0,1,1,1,1},
                          {1,0,1,1,1,1}};
      Maze testMaze = new Maze(testGrid,0,0);
      if (testMaze.mazeIsSolvable())
      {
         System.out.println("Solvable maze was tested as solvable!");
      }
      else
      {
         System.out.println("Solvable maze was tested as unsolvable...");
      }
      int[][] testGridUnsolvable = {{0,0,0,1,1,2},
                                    {0,1,0,0,0,1},
                                    {0,0,1,1,1,1},
                                    {1,0,1,1,1,1}};
      Maze testMaze2 = new Maze(testGridUnsolvable,0,0);
      if (testMaze2.mazeIsSolvable())
      {
         System.out.println("Unsolvable maze was tested as solvable...");
      }
      else
      {
         System.out.println("Unsolvable maze was tested as unsolvable!");
      }
   }                                
}