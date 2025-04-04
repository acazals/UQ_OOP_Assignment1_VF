package examblock.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Javadoc  */
public class StudentList {

    /** Javadoc  */
    private final ArrayList<Student> students;

    /** Javadoc  */
    public StudentList() {
        this.students = new ArrayList<>();
    }

    /** Javadoc
     * @param student s
     * */
    public void add(Student student) {
        this.students.add(student);
    }

    /** Javadoc
     * @return r
     * */
    public List<Student> all() {
        return new ArrayList<>(this.students);
    }


    /** Javadoc
     * @param lui l
     * @return r
     * */
    public Student byLui(Long lui) {
        for (Student student : this.students) {
            if (Objects.equals(student.getLui(), lui))  {
                return student;
            }
        }
        throw new java.lang.IllegalStateException();
    }


    /** Javadoc
     * @param subject a
     * @param aara a
     * @return r
     *
     * */
    public int countStudents(Subject subject, boolean aara) {
        if (this.students.isEmpty()) {
            throw new IllegalArgumentException(" this is an empty list");

        } else {
            int total = 0;
            for (Student student : this.students) {

                // loop through his exams and check if it matches
                if (student.getSubjects().all().isEmpty()) {
                    throw new IllegalArgumentException(" student not taking anye xams here");
                }
                for (Subject mysubject  : student.getSubjects().all()) {

                    if (mysubject.equals(subject) && student.isAara() == aara) {
                        // then that student is indeed taking that subject and is that AARA type
                        total++;
                    }
                }
            }
            return total;
        }
    }


    /** Javadoc
     * @return r
     * */
    public String getFullDetail() {
        if (this.students.isEmpty()) {
            return "";
        } else {
            StringBuilder total = new StringBuilder();
            for (Student student : this.students) {
                total.append(student.toString()).append("\n");
            }
            return total.toString();
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Student student : this.students) {
            String str = student.getLui() + " "
                    + student.givenNames() + " " + student.familyName() + "\n";
            sb.append(str);
            int i = 1;
            for (Subject subject : student.getSubjects().all()) {
                String str2 = i + ". " + subject.title() + "\n";
                sb.append(str2);
                i++;
            }
            sb.append("====================================================================== \n");
        }
        return sb.toString();
    }
}
