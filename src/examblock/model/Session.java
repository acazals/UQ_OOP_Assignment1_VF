package examblock.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    private static Long myLui = -250L;
    private static Student Empty = new Student( myLui, "", "Empty", 15, 2, 2003, "Blue" );
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

    public List<Exam> getExams(){
        return this.exams.all();
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


    public LocalDate getDate() {
        return this.day;
    }

    private StudentList sortStudents(StudentList students) {
        // sorting students based on their frst name ( alphabetically)
        if (students.all().isEmpty()) {
            throw new IllegalStateException( " we can t sort an empty list");
        }
        ArrayList<Student>  mySortedList= new ArrayList<>(students.all());
        mySortedList.sort( (s1, s2) -> s1.familyName().compareTo(s2.familyName()));
        StudentList sorted = new StudentList();
        for (Student student : mySortedList) {
            sorted.add(student);
        }
        return sorted;
    }


    public void allocateStudents(ExamList exams, StudentList cohort) {
        if (exams.all().isEmpty()) {
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

        for (Exam exam : exams.all()) {
            // get all the students taking that one exam
            StudentList byExam = new StudentList();
            for (Student student : cohort.all()) {
                if (student.getSubjects().all().contains(exam.getSubject()) && student.isAara() == this.getVenue().getAARA()) {
                    // than that student is taking that exam
                    if (!allStudents.all().contains(student)) { // that one student might be taking two exams at the same time...
                        byExam.add(student);
                        allStudents.add(student);
                        totalStudents++;
                    }
                }
            }
            studentsByExam.add( this.sortStudents(byExam));
        } ;
        this.studentCount = totalStudents;

        int freeDesks = this.FreeDesks(studentsByExam); // number of free desks

        int NbDesksBetweenExams = freeDesks;
        if (this.exams.all().size() >1) {
            // more than one exam => we divide by nb exam -1
            NbDesksBetweenExams = freeDesks / (this.exams.all().size()-1); // freeDesks divided by the number of different exams -1 eg : 3 exams , 6 free columns => 3 free columns between 1 and 2 and between 2 and 3
        }

        // now we can save the different seats :
        StudentList Disposition = new StudentList();
        for (StudentList MyStudents : studentsByExam) {

            for (Student student : MyStudents.all()) {
                Disposition.add(student);
            }
            // now that this exam is seated we add the empty desks
            for (int i=0; i<NbDesksBetweenExams; i++) {
                Disposition.add(Empty);
            }
        }
        // finally the matrix is filled

        this.students = Disposition;

        // now i have my list ready
        int DeskCount = 0;
        for (int j =0; j<this.allocatedDesks[0].length; j++ ) {

            for (int i= 0; i<this.allocatedDesks.length; i++) {

                if (DeskCount> this.getVenue().deskCount()) {
                    throw new IllegalStateException( " error ");
                }
                if (DeskCount > Disposition.size()) {
                    this.allocatedDesks[i][j] = new Desk(DeskCount+1);
                    this.allocatedDesks[i][j].setFamilyName(Empty.familyName());
                    this.allocatedDesks[i][j].setGivenAndInit("");
                    DeskCount++;
                }
                 else {
                     Student temp  = Disposition.get(DeskCount);
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

        for (Exam exam : this.exams.all()) {
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
                this.exams.add(exam); // so that exam will take place in that venue
        //this.allocateStudents(this.exams, this.cohort);
    }


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
