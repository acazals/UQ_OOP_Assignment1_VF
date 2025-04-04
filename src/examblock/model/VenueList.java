package examblock.model;

import java.util.ArrayList;
import java.util.List;

/** Javadoc  */
public class VenueList {
    /** Javadoc  */
    private final ArrayList<Venue> venues;

    /** Javadoc  */
    public VenueList() {
        this.venues = new ArrayList<>();
    }

    /** Javadoc
     * @param venue v
     * Javadoc  */
    public void addVenue(Venue venue) {
        this.venues.add(venue);
    }

    /** Javadoc
     * @param sessions s
     * @param cohort c
     * @param exams e
     *
     *
     *
     * */
    public void allocateStudents(SessionList sessions, ExamList exams, StudentList cohort) {
        for (Venue venue : this.venues) {
            // for each evnue we consider the sessions taking place in it
            for (Session session : sessions.forVenue(venue)) {
                List<Exam> exams1 = session.getExams();
                ExamList myExams = new ExamList();
                for (Exam exam : exams1) {
                    myExams.add(exam);
                }
                session.allocateStudents(myExams, cohort);
            }
        }


    }


    /** Javadoc
     * @return List<Venue> </Venue>
     * */
    public List<Venue> all() {
        return new ArrayList<>(this.venues);
    }

    /** Javadoc
     * @param id id
     * @return Venue v
     * */
    public Venue getVenue(String id) {
        // get first venue with a matching id

        for (Venue venue : this.venues) {
            if (venue.venueId().equals(id)) {
                return venue;
            }
        }
        // same venue not found
        throw new java.lang.IllegalStateException();
    }

    /** Javadoc
     * @return String str
     * */
    public String getFullDetail() {
        StringBuilder bd = new StringBuilder();
        for (Venue venue : this.venues) {
            bd.append(venue.toString());
        }
        // go on
        return bd.toString();
    }

    /** Javadoc
     * @param venue v
     * */
    public void removeVenue(Venue venue) {
        this.venues.removeIf(myvenue -> myvenue.venueId().equals(venue.venueId()));
    }

    /** Javadoc
     * @param sessions sesh
     * */
    public void printAllocations(SessionList sessions) {
        // print students allocations for each session of each venue
        for (Venue venue : this.venues) {
            // for each venue
            for (Session session : sessions.forVenue(venue)) {
                // for each session taking place in that venue
                session.printDesks();

            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Venue venue : this.venues) {
            String str = i + ". " + venue.roomId() + "\n";
            sb.append(str);
            i++;
        }
        return sb.toString();
    }
}
