package trivia;

import java.io.*;

public class Room implements Serializable
{
   public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
   private final int UNLOCKED = 0, LOCKED = 1, TRUELOCK = 2;
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
   
   public boolean allLocked()
   {
      if (this.northDoor == TRUELOCK && this.eastDoor == TRUELOCK && this.westDoor == TRUELOCK && this.southDoor == TRUELOCK)
      {
         return true;
      }
      return false;
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
   private void writeObject(ObjectOutputStream out) throws IOException
   {
	   try
	   {
		   Room outObject = new Room(this.northDoor,this.eastDoor,this.southDoor,this.westDoor);
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
		   Room inObject = (Room)in.readObject();
		   this.northDoor = inObject.getNorthDoor();
		   this.eastDoor = inObject.getEastDoor();
		   this.southDoor = inObject.getSouthDoor();
		   this.westDoor = inObject.getWestDoor();
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