package examblock.model;
import java.util.ArrayList;
import java.util.List;


public class ExamList {

    // constructs an empty list of Exams

    private List<Exam> examList;

    public ExamList() {
        this.examList = new ArrayList<>();
    }

    public List<Exam> all() {
        return new ArrayList<>(examList);
    }

    public void add(Exam exam) {
        this.examList.add(exam);
    }

    public Exam bySubjectTitle(String title) {
        for (Exam exam : this.examList) {
            if (exam.getSubject().getTitle().equals(title)) {
                return  exam;
            }

        }
        throw new IllegalStateException();
    }

//    public void removeExam(Exam exam) {
//        for (int i =0; i<this.examList.size(); i++) {
//            if (this.examList.get(i).equals(exam)) {
//                this.examList.remove(i);
//                // same exam founded
//                break;
//            }
//        }
//    }

    public void removeExam(Exam exam) {
        if (this.examList.contains(exam)) {
            this.examList.remove(exam);
        }
    }



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


        for (Exam exam : examList) {
            sb.append(exam.getFullDetail());
        }

        return sb.toString();
    }
}

