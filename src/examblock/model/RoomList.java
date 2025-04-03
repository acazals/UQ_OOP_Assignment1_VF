package examblock.model;
import java.util.ArrayList;
import java.util.List;

public class RoomList {
    private List<Room> roomlist;

    public RoomList() {
        this.roomlist = new ArrayList<>();
    }

    public void addRoom (Room room) {
        this.roomlist.add(room);
    }

    public List<Room> all() {
        return new ArrayList<>(roomlist); // ✅ Returns a new list, but with the same Room references
    }


    public String getFullDetail() {
        if (this.roomlist.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<this.roomlist.size(); i++){
            sb.append(this.roomlist.get(i).toString());
        }
        return sb.toString();
    }

    public void removeRoom(Room room) {
        if (this.roomlist.contains(room)) {
            this.roomlist.remove(room);
        }
    }

    public Room getRoom(String id) {
        for (Room room : this.roomlist) {
            if (room.roomId().equals(id)) {
                return room;
            }
        }
        throw new java.lang.IllegalStateException();
    }



    @Override
    public String toString() {
        return "RoomList{" +
                "roomlist=" + roomlist +
                '}';
    }
}
