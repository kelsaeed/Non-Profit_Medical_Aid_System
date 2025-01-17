// Room.java
public class Room {
    private int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public void assign() {
        if (isAvailable) {
            System.out.println("Room " + roomNumber + " has been assigned.");
            isAvailable = false;
        } else {
            System.out.println("Room " + roomNumber + " is already occupied.");
        }
    }

    public void Checkout() {
        if (!isAvailable) {
            System.out.println("Room " + roomNumber + " has been checked out.");
            isAvailable = true;
        } else {
            System.out.println("Room " + roomNumber + " is already available.");
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
