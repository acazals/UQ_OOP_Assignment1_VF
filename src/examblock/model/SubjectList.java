package examblock.model;

import java.util.ArrayList;
import java.util.List;

public class SubjectList {

    private ArrayList<Subject> subjects;

    public SubjectList() {
        this.subjects = new ArrayList<>();
    }

    public void addSubject( Subject subject) {
        this.subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        if (this.subjects.contains(subject)) {
            this.subjects.remove(subject);
        }
    }

    public List<Subject> all() {
        return new ArrayList<>(subjects); // reference in java
    }

    public Subject byTitle(String title) {
        // get the first Subject with a matching title
        for (Subject subject : this.subjects) {
            if (subject.getTitle().equals(title)) {
                return subject;
            }
        }
        throw new java.lang.IllegalStateException();
    }

    public String getFullDetail() {
        if (this.subjects.isEmpty()) {
            return null;
        } else {
            String total = "";
            for (int i=0; i<this.subjects.size(); i++) {
                total = total + subjects.get(i).getFullDetail() + "\n"; // skip a line
            }
            return total;
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i =1;
        for ( Subject subject : subjects) {
            String str = i + ". " + subject.getTitle() + "\n";
            sb.append(str);
            i++;
        }
        return sb.toString();
    }
}
