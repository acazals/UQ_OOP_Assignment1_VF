package examblock.model;

/** Javadoc  */

public class Room {

    /** Javadoc  */
    protected final String id;

    /** Javadoc
     * @param id i
     * */
    public Room(String id) {
        this.id = id;
    }

    /** Javadoc
     * @return r
     * */
    public String roomId() {
        return  this.id;
    }

    @Override
    public String toString() {
        return id;
    }
}
