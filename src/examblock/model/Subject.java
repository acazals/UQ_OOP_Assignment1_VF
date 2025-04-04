package examblock.model;

/**
 * Javadoc
 *
 * @param getTitle       Javadoc
 * @param description Javadoc
 */
public record Subject(String getTitle, String description) {

    /**
     * Javadoc
     */

    private String cleanAndValidateTitle(String title) {

        title = title.trim().replaceAll("\\s+", " ");

        // if getTitle ends with a full stop
        if (title.endsWith(".")) {
            throw new IllegalArgumentException("Title must not end with a full stop.");
        }


        if (!title.matches("([A-Z][a-z]*|[0-9]+|[IVXLCDM]+)( [A-Z][a-z]*|[0-9]+|[IVXLCDM]+)*")) {
            throw new IllegalArgumentException("Invalid getTitle format.");
        }

        return title;
    }

    private String validateDescription(String description) {
        if (!description.matches("[A-Z].*\\.")) {
            // .* means followed by any characters
            throw new
                    IllegalArgumentException("must start with a capital + end with a full stop.");
        }

        return description;
    }

    /**
     * Javadoc
     */
    public Subject {
    }

    /**
     * Javadoc
     */
    @Override
    public String description() {
        return this.description;
    }

    /**
     * Javadoc
     */
    public String getFullDetail() {
        return this.getTitle.toUpperCase() + "\n" + "\"" + this.description + "\"";
    }

    /**
     * Javadoc
     */
    public String getTitle() {
        return this.getTitle;
    }


    @Override
    public String toString() {
        return this.getTitle.toUpperCase();
        // subject getTitle as a String in all uppercase and a newline.
    }
}
