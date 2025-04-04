package examblock.model;

import java.util.ArrayList;
import java.util.List;

/** Javadoc  */

public class ExamList {

    // constructs an empty list of Exams
    /** Javadoc  */
    private final List<Exam> examList;

    /** Javadoc  */
    public ExamList() {
        this.examList = new ArrayList<>();
    }

    /** Javadoc
     * @return r
     * */
    public List<Exam> all() {
        return new ArrayList<>(examList);
    }

    /** Javadoc
     * @param exam e
     * */
    public void add(Exam exam) {
        this.examList.add(exam);
    }

    /** Javadoc
     * @param title t
     * @return r
     * */
    public Exam bySubjectTitle(String title) {
        for (Exam exam : this.examList) {
            if (exam.getSubject().title().equals(title)) {
                return  exam;
            }

        }
        throw new java.lang.IllegalStateException();
    }

    /** Javadoc
     * @param exam e
     * */
    public void removeExam(Exam exam) {
        this.examList.remove(exam);
    }


    /** Javadoc
     * @return r
     * */
    public String getFullDetail() {
        StringBuilder sb = new StringBuilder();
        for (Exam exam : this.examList) {
            sb.append(exam.getFullDetail()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Exam exam : examList) {
            sb.append(index).append(". ").append(exam.getFullDetail()).append("\n");
            index++;
        }

        return sb.toString();
    }
}

