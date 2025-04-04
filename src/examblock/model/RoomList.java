package examblock.model;

import java.util.ArrayList;
import java.util.List;

/** Javadoc  */

public class RoomList {
    /** Javadoc  */
    private List<Room> roomlist;

    /** Javadoc  */
    public RoomList() {
        this.roomlist = new ArrayList<>();
    }

    /** Javadoc
     * @param room r
     * */
    public void addRoom(Room room) {
        this.roomlist.add(room);
    }

    /** Javadoc
     * @return r
     * */
    public List<Room> all() {
        return new ArrayList<>(roomlist); // ✅ Returns a new list, but with the same Room references
    }



    /** Javadoc
     * @return r
     * */
    public String getFullDetail() {
        if (this.roomlist.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Room room : this.roomlist) {
            sb.append(room.toString());
        }
        return sb.toString();
    }

    /** Javadoc
     * @param room r
     * */
    public void removeRoom(Room room) {
        this.roomlist.remove(room);
    }

    /** Javadoc
     * @param id i
     * @return r
     * */
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
        return "RoomList{"
                + "roomlist=" + roomlist
                + '}';
    }
}
