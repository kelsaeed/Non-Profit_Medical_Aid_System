public class CheckoutRoomCommand implements Command{

    public Room room;

    public CheckoutRoomCommand(Room room)
    {
        this.room= room;

    }

    @Override
    public void execute() {

        room.Checkout();
    }
}
