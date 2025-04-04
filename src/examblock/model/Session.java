package examblock.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/** Javadoc  */
public class Session {

    /** Javadoc  */
    private final Venue venue;

    /** Javadoc  */
    private int sessionNumber;

    /** Javadoc  */
    private final LocalDate day;

    /** Javadoc  */
    private final LocalTime start;

    /** Javadoc  */
    private ExamList exams;



    /** Javadoc  */
    //private ArrayList<Desk> allocatedDesks;
    private Desk[][] allocatedDesks;

    /** Javadoc  */
    private int studentCount;

    /** Javadoc  */
    private boolean allocated;

    /** Javadoc  */
    private static final Long myLui = -250L;
    /** Javadoc  */
    private static final Student Empty = new Student(myLui, "", "Empty", 15, 2, 2003, "Blue");
    // creating an empty student that will be used in our StudentList with all students


    /** Javadoc
     * @param day d
     * @param venue v
     * @param sessionNumber s
     * @param start s
     *
     * */
    public Session(Venue venue, int sessionNumber, LocalDate day, LocalTime start) {
        this.venue = venue; // stores if the session will be AARA friendly or not
        // check that the id works : is unique
        if (venue.getsessionids().contains(sessionNumber)) {
            // make the id of the session is unique for that venue

            int i = 1;
            while (true) {
                if (venue.getsessionids().contains(i)) {
                    i++;
                } else {
                    this.sessionNumber = i;
                    // normally the ID of our session is added in the list with the previous method
                    this.venue.addSession(this);

                    break;
                }
            }
        } else {
            this.sessionNumber = sessionNumber;
        }
        this.day = day;
        this.start = start;
        this.exams = new ExamList();

        this.allocatedDesks = new Desk[venue.getRows()][venue.getColumns()];
        this.allocated = false;
    }

    /** Javadoc
     * @return r
     * */
    public List<Exam> getExams() {
        return this.exams.all();
    }

    /** Javadoc
     * @return r
     * */
    public Venue getVenue() {
        return venue;
    }

    /** Javadoc
     * @return r
     * */
    public LocalTime getTime() {
        return start;
    }

    /** Javadoc
     * @return r
     * */
    public int getSessionNumber() {
        return sessionNumber;
    }

    /** Javadoc
     * @param i i
     * */
    public void setSessionNumber(int i) {
        this.sessionNumber = i;
    }


    /** Javadoc
     * @return r
     * */
    public LocalDate getDate() {
        return this.day;
    }

    @SuppressWarnings("checkstyle:ParenPad")
    private StudentList sortStudents(StudentList students) {
        // sorting students based on their frst name ( alphabetically)
        if (students.all().isEmpty()) {
            throw new IllegalStateException(" we can t sort an empty list");
        }
        ArrayList<Student>  mySortedList = new ArrayList<>(students.all());
        mySortedList.sort((s1, s2) -> s1.familyName().compareTo(s2.familyName()));
        StudentList sorted = new StudentList();
        for (Student student : mySortedList) {
            sorted.add(student);
        }
        return sorted;
    }

    /** Javadoc
     * @param exams e
     * @param cohort c
     *
     * */
    public void allocateStudents(ExamList exams, StudentList cohort) {
        if (exams.all().isEmpty()) {
            return; // No exams or no  students = nothing to allocate
        }

        if (this.allocated) {
            throw new IllegalStateException(" that session has already been allocated");
        }


        this.exams = exams; // Store exams for this session
        this.allocated = true;

        // Step 1: Count total students & check desk availability
        int totalStudents = 0;
        ArrayList<StudentList> studentsByExam = new ArrayList<>();
        StudentList allStudents = new StudentList();

        for (Exam exam : exams.all()) {
            // get all the students taking that one exam
            StudentList byExam = new StudentList();
            for (Student student : cohort.all()) {
                if (student.getSubjects().all().contains(exam.getSubject())
                        && student.isAara() == this.getVenue().isAara()) {
                    // than that student is taking that exam
                    if (!allStudents.all().contains(student)) {
                        // that one student might be taking two exams at the same time...
                        byExam.add(student);
                        allStudents.add(student);
                        totalStudents++;
                    }
                }
            }
            studentsByExam.add(this.sortStudents(byExam));
        }
        this.studentCount = totalStudents;

        int freeDesks = this.freeDesks(studentsByExam); // number of free desks

        int nbDesksBetweenExams = freeDesks;
        if (this.exams.all().size() > 1) {
            // more than one exam => we divide by nb exam -1
            nbDesksBetweenExams = freeDesks / (this.exams.all().size() - 1);
            // freeDesks divided by the number of different exams -1 eg : 3 exams ,
            // 6 free columns => 3 free columns between 1 and 2 and between 2 and 3
        }

        // now we can save the different seats :

        StudentList disposition = new StudentList();
        for (StudentList myStudents : studentsByExam) {

            for (Student student : myStudents.all()) {
                disposition.add(student);
            }
            // now that this exam is seated we add the empty desks
            for (int i = 0; i < nbDesksBetweenExams; i++) {
                disposition.add(Empty);
            }
        }
        // finally the matrix is filled

        // now i have my list ready
        int deskCount = 0;
        for (int j = 0; j < this.allocatedDesks[0].length; j++) {
            for (int i = 0; i < this.allocatedDesks.length; i++) {

                if (deskCount > this.getVenue().deskCount()) {
                    throw new IllegalStateException(" error ");
                }
                if (deskCount > disposition.all().size()) {
                    this.allocatedDesks[i][j] = new Desk(deskCount + 1);
                    this.allocatedDesks[i][j].setFamilyName(Empty.familyName());
                    this.allocatedDesks[i][j].setGivenAndInit("");
                } else {
                    Student temp  = disposition.all().get(deskCount);
                    this.allocatedDesks[i][j] = new Desk(deskCount + 1);
                    String str = "";

                    String[] parts = temp.givenNames().trim().split("\\s+");
                    String first = parts[0];

                    if (parts.length > 1) {
                        str = str + first + " " + parts[1].charAt(0) + ".";
                    }
                    this.allocatedDesks[i][j].setGivenAndInit(str);
                    this.allocatedDesks[i][j].setFamilyName(temp.familyName());

                }
                deskCount++;

            }
        }

    }

    // cohort : all year 12 students
    // total nb of students and total nb of desk and calculates the desk gap ;
    // how to distribute gap ??
    // count number of different exams / count number of subjects (same)
    // sorting students by family name
    // allocate diffent desks to students ; front to back, left to right
    // what if more students in cohort then available desks in the venue :
    // probably dosen t happen here
    // iterate through all exams and identify which student is taking which exam

    /** Javadoc
     * @param studentbyexam s
     * @return r
     * */
    private int freeDesks(ArrayList<StudentList> studentbyexam) {
        int totalStudent = 0;
        for (StudentList byExam : studentbyexam) {
            totalStudent += byExam.all().size();
        }
        if (totalStudent > this.getVenue().deskCount()) {
            throw new IllegalStateException(" too many students in that venue");
        }

        return (this.getVenue().deskCount() - totalStudent); // returning the number of free desks

    }

    /** Javadoc  */
    public void printDesks() {

        System.out.println(this.getVenue().roomId());
        System.out.println(this.getVenue().roomId() + ":" + this.getDate() + " " + this.getTime());



        int cols = this.allocatedDesks[0].length;

        for (Desk[] allocatedDesk : this.allocatedDesks) {  // Iterate over rows
            for (int line = 0; line < 3; line++) { // 3 lines per desk
                for (int j = 0; j < cols; j++) {  // Iterate throu columns
                    Desk desk = allocatedDesk[j];


                    //                    if (desk == null
                    //                    || desk.deskFamilyName().equals("Empty")) {
                    //                        switch (line) {
                    //                            case 0:
                    //                                System.out.printf
                    //                                ("Desk %-10d", ((i+1) * cols + j+1));
                    //                                break;
                    //                            case 1:
                    //                                System.out.printf("%-15s", "Empty"); // empty
                    //                                break;
                    //                            case 2:
                    //                                System.out.printf("%-15s", " ");
                    //                                break;
                    //                        }
                    //} else {

                    switch (line) {
                        case 0: // Desk Number
                            System.out.printf("Desk %-10d", desk.deskNumber());
                            break;
                        case 1: //  Family Name
                            System.out.printf("%-15s", desk.deskFamilyName());
                            break;
                        case 2: // given Name
                            System.out.printf("%-15s", desk.deskGivenAndInit());
                            break;
                    }
                }
                // }
                System.out.println(); //  line after each row
            }
            System.out.println(); // separating matrix rows
        }

    }


    /** Javadoc
     * @return r
     * */
    public int countStudents() {
        return this.studentCount;
    }

    /** Javadoc
     * @param exam e
     * @param nbStudents n
     * */
    public void scheduleExam(Exam exam, int nbStudents) {
        this.studentCount += nbStudents;
        this.exams.add(exam); // so that exam will take place in that venue

    }


    // parameters : exam and nmber of students
    // add an exam to an existing sessions ( our given instance of )
    // check is the VENUE is AARA firendly
    // check the number of student
    @Override
    public String toString() {
        return "Session{"
                + "venue=" + venue
                + ", sessionNumber=" + sessionNumber
                + ", day=" + day
                + ", start=" + start
                + ", studentCount =" + studentCount
                + '}';
    }
}
