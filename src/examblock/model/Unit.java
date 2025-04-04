package examblock.model;

/** Javadoc  */
public class Unit {

    /** Javadoc  */
    private final Subject subject;

    /** Javadoc  */
    private final Character unitId;

    /** Javadoc  */
    private final String title;

    /** Javadoc  */
    private final String description;

    // private methods to check the constructor parameters

    /**
    private void validateParameters
     (Subject subject, Character unitId, String getTitle, String description) {
        if (subject == null) {
            throw new IllegalArgumentException(" cannot be null.");
        }
        if (unitId == null || !Character.toString(unitId).matches("[0-9A-Z]")) {
            throw new IllegalArgumentException(" ID must be a single character (0-9 or A-Z).");
        }
        if (getTitle == null || getTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null / empty.");
        }
        if (!getTitle.matches("([A-Z][a-z]*|[0-9]+|I{1,3}|IV|V?I{0,3})+
     ( [A-Z][a-z]*|[0-9]+|I{1,3}|IV|V?I{0,3})*")) {
            throw new IllegalArgumentException(" contains invalid characters ");
        }
        if (description == null || !description.matches("^[A-Z].*\\.$")) {
            throw new IllegalArgumentException("Description must start with
     a capital letter + end with a full stop.");
        }
    } */


    //Formats the getTitle : ensure single spaces + no trailing full stop.

    private String formatTitle(String title) {
        return title.trim().replaceAll("\\s+", " ").replaceAll("\\.$", "");
    }

    /** Javadoc
     * @param title t
     * @param subject s
     * @param unitId u
     * @param description d
     *
     *
     * */
    public Unit(Subject subject, Character unitId, String title, String description) {
        //validateParameters(subject, unitId, getTitle, description);
        // if one parameter wrong, error thrown
        this.subject = subject;
        this.unitId = unitId;
        this.title = formatTitle(title);
        this.description = description;
    }

    /** Javadoc
     * @return r
     * */
    public Subject getSubject() {
        return this.subject;
    }



    /** Javadoc
     * @return r
     * */
    public Character id() {
        return this.unitId;
    }

    /** Javadoc
     * @return r
     * */
    public String getDescription() {
        return this.description;
    }


    /** Javadoc
     * @return string
     * */
    public String getFullDetail() {
        return "Unit{"
                +
                "subject=" + subject
                +
                ", unitId=" + unitId
                +
                ", getTitle='" + title + '\''
                +
                ", description='" + description + '\''
                +
                '}';
    }

    @Override
    public String toString() {
        // unit ID + getTitle
        return "Unit{"
                +
                ", unitId=" + unitId
                +
                ", getTitle='" + title + '\''
                +
                '}';
    }
}

