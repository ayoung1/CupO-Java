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
   
   public boolean isLocked(int direction)
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
      if (lock == 0)
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
}