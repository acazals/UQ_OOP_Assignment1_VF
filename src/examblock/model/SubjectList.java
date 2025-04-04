package examblock.model;

import java.util.ArrayList;
import java.util.List;

/** Javadoc  */
public class SubjectList {

    /** Javadoc  */
    private final ArrayList<Subject> subjects;

    /** Javadoc  */
    public SubjectList() {
        this.subjects = new ArrayList<>();
    }

    /** Javadoc
     * @param subject s
     * */
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    /** Javadoc
     * @param subject s
     * */
    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
    }

    /** Javadoc
     * @return r
     * */
    public List<Subject> all() {
        return new ArrayList<>(subjects); // reference in java
    }

    /** Javadoc
     *
     * @param title t
     * @return r
     * */
    public Subject byTitle(String title) {
        // get the first Subject with a matching title
        for (Subject subject : this.subjects) {
            if (subject.title().equals(title)) {
                return subject;
            }
        }
        throw new java.lang.IllegalStateException();
    }

    /** Javadoc
     * @return r
     * */
    public String getFullDetail() {
        if (this.subjects.isEmpty()) {
            return null;
        } else {
            StringBuilder total = new StringBuilder();
            for (Subject subject : this.subjects) {
                total.append(subject.getFullDetail()).append("\n"); // skip a line
            }
            return total.toString();
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Subject subject : subjects) {
            String str = i + ". " + subject.title() + "\n";
            sb.append(str);
            i++;
        }
        return sb.toString();
    }
}
