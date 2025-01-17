public class AssignRoomCommand implements Command{

    public Room room;

    public AssignRoomCommand(Room room){

        this.room= room;
    }

    @Override
    public void execute() {

        room.assign();

    }
}
