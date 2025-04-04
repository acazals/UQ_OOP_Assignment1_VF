package examblock.model;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Javadoc  */
public class Student {


    /** Javadoc  */
    private Long lui; // large integer

    /** Javadoc  */
    private String givenNames;

    /** Javadoc  */
    private String familyName;

    /** Javadoc  */
    private LocalDate dob;

    /** Javadoc  */
    private String house;

    /** Javadoc  */
    private Boolean aara;  //AARA adjustments

    /** Javadoc  */
    private SubjectList subjects;

    /** Javadoc  */
    private ExamList exams;

    /** Javadoc  */
    private static Set<Long> usedLui = new HashSet<>();

    /** Javadoc  */
    private static final ArrayList<String> validHouses =
            new ArrayList<>(Arrays.asList("Blue", "Green", "Red", "White", "Yellow"));


    private boolean check(long lui, int year, int month, int day, String house) {
        try {
            LocalDate date = LocalDate.of(year, month, day);

        } catch (DateTimeException e) {
            System.out.println("Invalid Date.");
            return false;
        }
        if (usedLui.contains(lui)) {
            throw new IllegalArgumentException(" LUI must be Unique");


        }
        if (!validHouses.contains(house)) {
            throw new IllegalArgumentException(" House must be part the given HouseList");
        } else {
            return true;
        }

    }

    /** Javadoc
     * @param day d
     * @param month m
     * @param year y
     * @param familyName f
     * @param aara a
     * @param house h
     * @param lui l
     * @param givenNames g
     *
     *
     *
     *
     *
     * */
    public Student(Long lui, String givenNames, String familyName, int day,
                   int month, int year, String house, Boolean aara) {

        if (this.check(lui, year, month, day, house)) {
            usedLui.add(lui);
            this.lui = lui;
            this.givenNames = givenNames;
            this.familyName = familyName;
            this.dob = LocalDate.of(year, month, day);
            this.house = house;
            this.aara = aara;

            // initializing the lists
            this.exams = new ExamList();
            this.subjects = new SubjectList();
        }

    }

    /** Javadoc
     * @param day d
     * @param month m
     * @param year y
     * @param familyName f
     * @param house h
     * @param lui l
     * @param givenNames g
     *
     *
     *
     *
     *
     * */
    public Student(Long lui, String givenNames, String familyName,
                   int day, int month, int year, String house) {

        if (this.check(lui, year, month, day, house)) {
            usedLui.add(lui);
            this.lui = lui;
            this.givenNames = givenNames;
            this.familyName = familyName;
            this.dob = LocalDate.of(year, month, day);
            this.house = house;
            this.aara = false;

            // initializing the lists
            this.exams = new ExamList();
            this.subjects = new SubjectList();
        }

    }

    /** Javadoc
     * @param subject s
     * */
    public void addSubject(Subject subject) {
        // adds a subject to the student
        this.subjects.addSubject(subject);

    }

    /** Javadoc
     * @param lui l
     * */
    public void changeLui(Long lui) {
        if (usedLui.contains(lui)) {
            throw new IllegalArgumentException("Lui already used");
        }
        this.lui = lui;
    }

    /** Javadoc
     * @return r
     * */
    public String firstName() {
        return this.givenNames.split(" ")[0];
    }


    /** Javadoc
     * @return r
     * */
    public String fullName() {
        return this.givenNames + " " + this.familyName;
    }

    /** Javadoc
     * @return r
     * */
    public String givenNames() {
        return this.givenNames;
    }

    /** Javadoc
     * @return r
     * */
    public Boolean isAara() {
        return this.aara;
    }

    /** Javadoc
     * @param familyName f
     * */
    public void setFamily(String familyName) {
        this.familyName = familyName;
    }

    /** Javadoc
     * @param givenNames g
     * */
    public void setGiven(String givenNames) {
        this.givenNames = givenNames;
    }

    /** Javadoc
     * @return r
     * */
    public String getHouse() {
        return this.house;
    }

    /** Javadoc
     * @return r
     * */
    public String getFullDetail() {
        StringBuilder sb = new StringBuilder();
        for (Subject subject : this.subjects.all()) {
            sb.append(subject.title()).append("\n");
        }

        return "Student{"
                +
                "lui=" + lui
                +
                ", givenNames='" + givenNames + '\''
                +
                ", familyName='" + familyName + '\''
                +
                ", Dob=" + dob
                +
                ", house='" + house + '\''
                +
                ", aara=" + aara
                +
                " taken subjects = " + sb + "\n"
                + '}';

    }

    /** Javadoc
     * @return r
     * */
    public String shortName() {
        String first = this.givenNames.split(" ")[0];
        return first + " " + this.familyName;
    }

    /** Javadoc
     * @return r
     * */
    public String familyName() {
        return this.familyName;
    }

    /** Javadoc
     * @return r
     * */
    public LocalDate getDob() {
        return this.dob;
    }

    /** Javadoc
     * @return r
     * */
    public Long getLui() {
        return this.lui;
    }

    /** Javadoc
     * @return r
     * */
    public ExamList getExams() {
        return this.exams;
    }

    /** Javadoc
     * @param subject s
     * */
    public void removeSubject(Subject subject) {
        // all gives a reference so removing the subject from the reference removes it
        // from the actual subject list : subjects
        this.subjects.all().remove(subject);
    }

    /** Javadoc
     * @return r
     * */
    public SubjectList getSubjects() {
        return this.subjects;
    }
    // having a set of subjetc sinstead of a list would make operations faster
    // eg : for studentlist count subjetcs, AARA



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Subject subject : this.subjects.all()) {
            sb.append(subject.title()).append("\n");
        }

        return "Student{"
                + "lui=" + lui
                + ", givenNames='" + givenNames + '\''
                +
                ", familyName='" + familyName + '\''
                +
                ", Dob=" + dob
                +
                ", house='" + house + '\''
                +
                ", aara=" + aara
                +
                " taken subjects = " + sb
                + " \n \n \n \n"
                 + '}';
    }
}

