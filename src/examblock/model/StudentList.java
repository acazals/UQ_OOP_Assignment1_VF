package examblock.model;

import java.util.ArrayList;
import java.util.List;

public class StudentList {

    private ArrayList<Student> students;

    public StudentList() {
        this.students = new ArrayList<>();
    }

    public StudentList(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public void add(Student student) {
        this.students.add(student);
    }

    public int size() {
        return this.students.size();
    }



    public List<Student> all() {
        return new ArrayList<>(this.students);
    }

    public Student get(int i) {
        if (i > this.students.size()) {
            throw new IllegalArgumentException("");
        } else {
            return this.students.get(i);
        }
    }

    public boolean contains( Student student) {
        if (this.students.contains(student)) {
            return true;
        } else {
            return false;
        }
    }

    public Student byLui( Long lui) {
        for ( Student student : this.students) {
            if (student.getLui() == lui)  {
                return student;
            }
        }
        throw new IllegalStateException();
    }


    public int countStudents( Subject subject, boolean aara) {
        if (this.students.isEmpty()) {
            throw new IllegalArgumentException( " this is an empty list");

        } else {
            int total =0;
            for (Student student : this.students) {

                // loop through his exams and check if it matches
                if (student.getSubjects().all().isEmpty()) {
                    throw new IllegalArgumentException( " student not taking anye xams here");
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


    public String getFullDetail() {
        if (this.students.isEmpty()) {
            return "";
        } else {
            String total = "";
            for (int i =0; i<this.students.size(); i++) {
                total = total + students.get(i).toString() + "\n";
            }
            return total;
        }
    }



    @Override
    public String toString() {
          StringBuilder sb = new StringBuilder();
          for (Student student : this.students) {
              String str = student.getLui() + " " + student.givenNames() + " " + student.familyName() + "\n";
              sb.append(str);
              int i=1;
              for (Subject subject : student.getSubjects().all()) {
                  String str2 = i + ". "+subject.getTitle() + "\n";
                  sb.append(str2);
                  i++;
              }
              sb.append("================================================================================ \n");
          }
          return sb.toString();
    }
}
