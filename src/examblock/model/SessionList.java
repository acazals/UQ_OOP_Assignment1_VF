package examblock.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/** Javadoc  */
public class SessionList {

    /** Javadoc  */
    private ArrayList<Session> list;

    /** Javadoc  */
    public SessionList() {
        this.list = new ArrayList<>();
    }

    /** Javadoc
     * @return r
     * */
    public List<Session> all() {
        return new ArrayList<>(this.list);
    }

    /** Javadoc
     * @param session s
     * */
    public void add(Session session) {
        this.list.add(session);
    }

    /** Javadoc
     * @param start s
     * @param venue v
     * @param day d
     * @return r
     * */
    public int getSessionNumber(Venue venue, LocalDate day, LocalTime start) {
        for (Session session : this.list) {
            if (session.getTime().equals(start)
                    && session.getDate().equals(day) && session.getVenue().equals(venue)) {
                return session.getSessionNumber();
            }
        }
        return 0;
    }

    /** Javadoc
     * @param session s
     * */
    public void remove(Session session) {
        this.list.remove(session);
    }

    /** Javadoc
     * @param venue v
     * @param exam e
     * @param numberStudents n
     *
     *
     * */
    public void scheduleExam(Venue venue, Exam exam, int numberStudents) {
        // alocates an exam to an existing session

        // here wefind the other sessions using the same venue
        // and choose one of them session to schedule our exam

        for (Session session : this.forVenue(venue)) {
            // for each session using that venue
            if (session.getTime().equals(exam.getTime())) {
                // schedule exam to an already existing session

                session.scheduleExam(exam, numberStudents);
                System.out.println(exam.getSubject() + " exam added to " + venue.roomId());
                break;
            }

        }
    }

    /** Javadoc
     * @param venue v
     * @param sessionNumber s
     * @return r
     * */
    public Session getSession(Venue venue, int sessionNumber) {
        for (Session session : this.list) {
            if (session.getVenue().equals(venue) && session.getSessionNumber() == sessionNumber) {
                return session;
            }
        }
        throw new java.lang.IllegalStateException();
    }


    /** Javadoc
     * @param venue v
     * @param exam e
     * @return r
     * */
    public Session getSession(Venue venue, Exam exam) {
        for (Session session : this.list) {
            if (session.getVenue().equals(venue) && session.getExams().contains(exam)) {
                // matching venue && matching exam
                return session;
            }
        }
        throw new java.lang.IllegalStateException();
    }


    /** Javadoc
     * @param exam e
     * @param venue v
     * @param numberStudents n
     * @return r
     * */
    public int getSessionNewTotal(Venue venue, Exam exam, int numberStudents) {
        // searches for a session in agviven venue at a given time
        for (Session session : this.list) {
            if (session.getVenue().roomId().equals(venue.roomId())
                    && session.getTime().equals(exam.getTime())) {
                // here we found a session in same venue and same time as the exam

                System.out.println("There are already"
                        + session.countStudents()
                        + "students who will be taking an exam in that venue; along with the "
                        + numberStudents + "students for this exam.");
                int total = session.countStudents() + numberStudents;
                System.out.println(" That's a total of " + total + " students");
                return total;

            }
        }
        // if we arrive here then we didn't find a session same time same venue
        System.out.println("There is currently no exam session in that venue at that time.");
        System.out.println("Creating a session...");
        // we have to create this session
        Session newSesssion = new Session(venue, 1, exam.getDate(), exam.getTime());
        // if ever 1 is a session id already used in that venue our constructor handles it
        // newSesssion.setStudentCount(numberStudents);
        // potential error : that line would already define the student count to 32
        System.out.println(" That's a total of " + numberStudents + " students");
        // the session constructor will find a unique ID if 1 already used
        this.list.add(newSesssion);
        return numberStudents;

    }

    /** Javadoc
     * @param venue v
     * @return r
     *
     * */
    public List<Session> forVenue(Venue venue) {
        ArrayList<Session> mylist = new ArrayList<>();
        for (Session session : this.list) {
            if (session.getVenue().venueId().equals(venue.venueId())) {
                mylist.add(session);
            }
        }
        return mylist;
    }



}
