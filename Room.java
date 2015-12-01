public class Room
{
   private static final int UNLOCKED = 0, NORTH = 0, LOCKED = 1, EAST = 1, TRUELOCK = 2, SOUTH = 2, WEST = 3;
   private int northDoor;
   private int southDoor;
   private int westDoor;
   private int eastDoor; 
   
   public Room()
   {
      this.northDoor = LOCKED;
      this.southDoor = LOCKED;
      this.westDoor = LOCKED;
      this.eastDoor = LOCKED;
   }
   
   public Room(int northDoor, int eastDoor, int southDoor, int westDoor)
   {
      this.northDoor = northDoor;
      this.eastDoor = eastDoor;
      this.southDoor = southDoor;
      this.westDoor = westDoor;
   }
   
   public int getNorthDoor()
   {
      return this.northDoor;
   }
   public int getEastDoor()
   {
      return this.eastDoor;
   }
   public int getSouthDoor()
   {
      return this.southDoor;
   }
   public int getWestDoor()
   {
      return this.westDoor;
   }
   public int checkDirection(int direction)
   {
      if (direction == NORTH)
      {
         return this.northDoor;
      }
      else if (direction == EAST)
      {
         return this.eastDoor;
      }
      else if (direction == SOUTH)
      {
         return this.southDoor;
      }
      else if (direction == WEST)
      {
         return this.westDoor;
      }
      return -1;    
   }
   public boolean isPassable(int direction)
   {
      if (direction == NORTH)
      {
         return checkLock(this.northDoor);
      }
      else if (direction == EAST)
      {
         return checkLock(this.eastDoor);
      }
      else if (direction == SOUTH)
      {
         return checkLock(this.southDoor);
      }
      else if (direction == WEST)
      {
         return checkLock(this.westDoor);
      }
      return false;
   }
   
   private boolean checkLock(int lock)
   {
      if (lock == TRUELOCK)
      {
         return false;
      }
      return true;
   }
   
   public void adjustLock(int direction, int key)
   {
      if (direction == NORTH)
      {
         this.northDoor = key;
      }
      else if (direction == EAST)
      {
         this.eastDoor = key;
      }
      else if (direction == SOUTH)
      {
         this.southDoor = key;
      }
      else if (direction == WEST)
      {
         this.westDoor = key;
      }      
   }
   
   public void printRoom()
   {
      String output = "";
      if (this.westDoor == 2)
      {
         output += "|";
      }
      else
      {
         output += " ";
      }
      if (this.northDoor == 2 && this.southDoor == 2)
      {
         output += "=";
         if (this.eastDoor == 2)
         {
            output += "|";
         }
         else
         {
            output += " ";
         }
      }
      else
      {
         if (this.northDoor == 2)
         {
            output += "¯";
         }
         else 
         {
            output += " ";
         }
         if (this.southDoor == 2)
         {
            output += "_";
         }
         else
         {
            output += " ";
         }
         if (this.eastDoor == 2)
         {
            output += "|";
         }
         else
         {
            output += " ";
         }
      }
      System.out.print(output);
   }
}