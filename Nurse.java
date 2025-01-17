// Nurse.java
public class Nurse implements Observer {
    private RoomInvoker invoker;

    @Override
    public void update(String message) {
        System.out.println("Nurse received appointment update: " + message);
    }

    public Nurse(RoomInvoker invoker) {
        this.invoker = invoker;
    }



    public void assignRoom(Room room) {
        Command assignCommand = new AssignRoomCommand(room);
        invoker.setCommand(assignCommand);
        invoker.executeCommand();
    }

    public void checkoutRoom(Room room) {
        Command checkoutCommand = new CheckoutRoomCommand(room);
        invoker.setCommand(checkoutCommand);
        invoker.executeCommand();
    }
}
