package examblock.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Session {

    private Venue venue;
    private int sessionNumber;
    private LocalDate day;
    private LocalTime start;
    private ExamList exams;
    private StudentList students;
    private StudentList cohort;
    //private ArrayList<Desk> allocatedDesks;
    private Desk[][] allocatedDesks;
    private int studentCount;
    private boolean allocated;

    public static Long myLui = -250L;
    public static Student Empty = new Student( myLui, "", "Empty", 15, 2, 2003, "Blue" );
    // creating an empty student that will be used in our StudentList with all students

    public Session (Venue venue, int sessionNumber, LocalDate day, LocalTime start) {
        this.venue = venue; // stores if the session will be AARA friendly or not
        // check that the id works : is unique
        if (venue.getSessionIDs().contains(sessionNumber)) {
            // make the id of the session is unique for that venue
            Boolean temp = true;
            int i = 1;
            while (temp) {
                if (venue.getSessionIDs().contains(i) ){
                    i++;
                } else {
                    this.sessionNumber = i;
                    // normally the ID of our session is added in the list with the previous method
                    this.venue.addSession(this);
                    temp = false;
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

    public ExamList getExams(){
        return this.exams;
    }

    public Venue getVenue() {
        return venue;
    }

    public LocalTime getTime() {
        return start;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int i) {
        this.sessionNumber = i;
    }


    public LocalDate getDay() {
        return this.day;
    }


    public void allocateStudents(ExamList exams, StudentList cohort) {
        if (exams.isEmpty() || cohort.isEmpty()) {
            return; // No exams or no  students = nothing to allocate
        }

        if (this.allocated) {
            throw new IllegalStateException(" that session has already been allocated");
        }

        this.cohort = cohort; // saving the cohort with all students infos
        this.exams = exams; // Store exams for this session
        this.allocated = true;

        // Step 1: Count total students & check desk availability
        int totalStudents = 0;
        ArrayList<StudentList> studentsByExam = new ArrayList<>();
        StudentList allStudents = new StudentList();

        for (Exam exam : exams.getExams()) {
            Subject subject = exam.getSubject();
            //  list of students taking this subject
            StudentList studentsTakingExam = cohort.bySubject(subject, this.getVenue().getAARA());
            StudentList studentsTakingExamSorted = studentsTakingExam.sortStudents();
            studentsByExam.add(studentsTakingExamSorted);
            totalStudents+= studentsTakingExamSorted.size();
        } ;
        this.studentCount = totalStudents;

        // now compute gaps between exams
        int freeDesks = this.FreeDesks(studentsByExam); // number of free desks
        int NbDesksBetweenExams = freeDesks;
        if (this.exams.size() >1) {
            // more than one exam => we divide by nb exam -1
            NbDesksBetweenExams = freeDesks / (this.exams.size()-1); // freeDesks divided by the number of different exams -1 eg : 3 exams , 6 free columns => 3 free columns between 1 and 2 and between 2 and 3
        }


        for (StudentList byExam : studentsByExam) {
            for (Student student : byExam.getStudents()) {
                // add all the sorted student for that exam
                if (!allStudents.contains(student)) {
                    allStudents.add(student);
                }
            }
            // once this is done add all the EMPTY students needed
            for (int i=0; i<NbDesksBetweenExams; i++) {
                allStudents.add(Empty);
            }
        }
        this.students = allStudents;

        // now i have my list ready
        int DeskCount = 0;
        for (int j =0; j<this.allocatedDesks[0].length; j++ ) {

            for (int i= 0; i<this.allocatedDesks.length; i++) {

                if (DeskCount> this.getVenue().deskCount()) {
                    throw new IllegalStateException( " error ");
                }
                if (DeskCount > allStudents.size()) {
                    this.allocatedDesks[i][j] = new Desk(DeskCount+1);
                    this.allocatedDesks[i][j].setFamilyName(Empty.familyName());
                    this.allocatedDesks[i][j].setGivenAndInit("");
                    DeskCount++;
                }
                 else {
                     Student temp  = allStudents.get(DeskCount);
                     this.allocatedDesks[i][j] = new Desk(DeskCount+1);
                     String str = "";

                        String[] parts = temp.givenNames().trim().split("\\s+");
                        String first = parts[0];

                        if (parts.length > 1) {
                            str = str+ first + " " + parts[1].charAt(0) + ".";
                        }
                    this.allocatedDesks[i][j].setGivenAndInit(str);
                    this.allocatedDesks[i][j].setFamilyName(temp.familyName());
                    DeskCount++;

                }

            }
        }

    }

    // cohort : all year 12 students
    // total nb of students and total nb of desk and calculates the desk gap ;
    // how to distribute gap ??
    // count number of different exams / count number of subjects (same)
    // sorting students by family name
    // allocate diffent desks to students ; front to back, left to right
    // what if more students in cohort then available desks in the venue : probably dosen t happen here
    // iterate through all exams and identify which student is taking which exam


    private int FreeDesks(ArrayList<StudentList> StudentByExam) {
        int totalStudent = 0;
        for (StudentList byExam : StudentByExam) {
            totalStudent+= byExam.size();
        }
        if (totalStudent > this.getVenue().deskCount()) {
            throw new IllegalStateException( " too many students in that venue");
        }
        return (this.getVenue().deskCount() - totalStudent); // returning the number of free desks

    }


    public void printDesks() {

        for (Exam exam : this.exams.getExams()) {
            System.out.println(exam);
        }
        System.out.println(this.getVenue().roomId());
        System.out.println(" Number of Students : " +this.studentCount);
//        System.out.println(this.students.getStudents().get(0));

        int rows = this.allocatedDesks.length;
        int cols = this.allocatedDesks[0].length;

        for (int i = 0; i < rows; i++) {  // Iterate over rows
            for (int line = 0; line < 3; line++) { // 3 lines per desk
                for (int j = 0; j < cols; j++) {  // Iterate throu columns
                    Desk desk = this.allocatedDesks[i][j];

//                    if (desk == null || desk.deskFamilyName().equals("Empty")) {
//                        switch (line) {
//                            case 0:
//                                System.out.printf("Desk %-10d", ((i+1) * cols + j+1)); //  desk number
//                                break;
//                            case 1:
//                                System.out.printf("%-15s", "Empty"); // empty
//                                break;
//                            case 2:
//                                System.out.printf("%-15s", " "); // no given name
//                                break;
//                        }
//                    } else {
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



    public int countStudents() {
        return this.studentCount;
    }


    public void scheduleExam( Exam exam, int nbStudents) {
        this.studentCount+= nbStudents;
        exam.addVenue(this.getVenue());
        this.exams.add(exam); // so that exam will take place in that venue
        //this.allocateStudents(this.exams, this.cohort);
    }

    public StudentList getCohort() {
        return this.cohort;
    }

    //

    // parameters : exam and nmber of students
    // add an exam to an existing sessions ( our given instance of )
    // check is the VENUE is AARA firendly
    // check the number of student
    @Override
    public String toString() {
        return "Session{" +
                "venue=" + venue +
                ", sessionNumber=" + sessionNumber +
                ", day=" + day +
                ", start=" + start +
                ", studentCount =" + studentCount+
                '}';
    }
}
