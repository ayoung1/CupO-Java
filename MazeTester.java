/*
* In printed mazes the "=" character represents an upper and lower wall in the room;
*/

public class MazeTester
{
   public static void main(String[] args)
   {
      Room[][] testGrid = {{new Room(2,2,1,2),new Room(2,2,2,2),new Room(2,1,1,2),new Room(2,2,2,1)},
                          {new Room(1,1,1,2),new Room(2,1,2,1),new Room(1,2,2,1),new Room(2,2,2,2)},
                          {new Room(1,2,1,2),new Room(2,2,2,2),new Room(2,2,2,2),new Room(2,2,2,2)},
                          {new Room(1,1,2,2),new Room(2,1,2,1),new Room(2,2,2,2),new Room(2,2,2,2)}};
      Maze testMaze = new Maze(testGrid,0,0,3,0);
      testMaze.printMaze();
      if (testMaze.mazeIsSolvable())
      {
         System.out.println("Solvable maze was tested as solvable!");
      }
      else
      {
         System.out.println("Solvable maze was tested as unsolvable...");
      }
      Room[][] testGridUnsolvable = {{new Room(2,2,1,2),new Room(2,2,2,2),new Room(2,1,1,2),new Room(2,2,2,2)},
                          {new Room(1,1,1,2),new Room(2,1,2,1),new Room(1,2,2,1),new Room(2,2,2,2)},
                          {new Room(1,2,1,2),new Room(2,2,2,2),new Room(2,2,2,2),new Room(2,2,2,2)},
                          {new Room(1,1,2,2),new Room(2,1,2,1),new Room(2,2,2,2),new Room(2,2,2,2)}};
      Maze testMaze2 = new Maze(testGridUnsolvable,0,0,3,0);
      testMaze2.printMaze();
      if (testMaze2.mazeIsSolvable())
      {
         System.out.println("Unsolvable maze was tested as solvable...");
      }
      else
      {
         System.out.println("Unsolvable maze was tested as unsolvable!");
      }
      
      Maze testMazeGenerator = new Maze("regular");
      testMazeGenerator.printMaze();
      if (testMazeGenerator.mazeIsSolvable())
      {
         System.out.println("Generated maze is solvable!");
      }
      else
      {
         System.out.println("Generated maze was unsolvable...");
      }
   }                                
}