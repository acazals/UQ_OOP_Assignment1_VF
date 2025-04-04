package examblock.model;

import java.util.ArrayList;

/** Javadoc  */
public class Venue extends Room {

    /** Javadoc  */
    private final int roomCount; // 1 or 2 or 3

    /** Javadoc  */
    private final RoomList rooms; // at least one room

    /** Javadoc  */
    private final int rows;

    /** Javadoc  */
    private final int columns;

    /** Javadoc  */
    private final int totalDesks; // not necesseraly rows x columns


    /** Javadoc  */
    private final boolean aara;

    /** Javadoc  */
    private ArrayList<Session> sessions;

    /** Javadoc  */
    private ArrayList<Integer> sessionids;


    /** Javadoc
     * @param aara a
     * @param id i
     * @param columns c
     * @param rooms r
     * @param rows r
     * @param roomCount r
     * @param totalDesks t
     *
     * */
    public Venue(String id, int roomCount, RoomList rooms, int rows, int columns,
                 int totalDesks, boolean aara) {
        super(id); // constructor of room
        this.roomCount = roomCount;
        this.rooms = rooms;
        this.rows = rows;
        this.columns = columns;
        this.totalDesks = totalDesks;
        this.aara = aara;
        this.sessions = new ArrayList<>();
        this.sessionids = new ArrayList<>();
    }

    /** Javadoc
     * @return r
     * */
    public int getColumns() {
        return this.columns;
    }

    /** Javadoc
     * @return r
     * */
    public int getRows() {
        return this.rows;
    }



    /** Javadoc
     * @return r
     * */
    public ArrayList<Integer> getsessionids() {
        return this.sessionids;
    }


    /** Javadoc
     * @param newSession n
     * */
    // add session : adds a session to where we store the past sessions and IDs
    public void addSession(Session newSession) {
        if (this.sessionids.contains(newSession.getSessionNumber())) {

            int i = 1;
            while (true) {
                if (this.sessionids.contains(i)) {
                    i++;
                } else {
                    newSession.setSessionNumber(i);

                    this.sessions.add(newSession);
                    this.sessionids.add(i);


                    break;
                }
            }
        } else {
            sessions.add(newSession);
            sessionids.add(newSession.getSessionNumber());
        }
    }


    /** Javadoc
     *  a
     * @return r
     * */
    public RoomList getRooms() {

        RoomList copy = new RoomList();
        for (Room room : this.rooms.all()) {
            copy.addRoom(room);
        }
        return copy;
    }


    /** Javadoc
     * @param aara a
     * @return r
     * */
    public boolean checkVenueType(boolean aara) {
        return this.aara == aara;
    }

    /** Javadoc
     * @return r
     * */
    public boolean isAara() {
        return this.aara;
    }

    /** Javadoc
     * @return r
     * */
    public int deskCount() {
        return this.totalDesks;
    }

    /** Javadoc
     * @return r
     * */
    public String venueId() {
        return this.id;
    }

    /** Javadoc
     *
     * @param numberStudents n
     * @return r
     * */
    public boolean willFit(int numberStudents) {
        // we cant fit all those students
        return this.deskCount() >= numberStudents;
    }


    @Override
    public String toString() {
        return id;
    }
}
