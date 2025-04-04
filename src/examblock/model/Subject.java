package examblock.model;

/**
 * Javadoc
 *
 * @param title       Javadoc
 * @param description Javadoc
 */
public record Subject(String title, String description) {

    /**
     * Javadoc
     */

    private String cleanAndValidateTitle(String title) {

        title = title.trim().replaceAll("\\s+", " ");

        // if title ends with a full stop
        if (title.endsWith(".")) {
            throw new IllegalArgumentException("Title must not end with a full stop.");
        }


        if (!title.matches("([A-Z][a-z]*|[0-9]+|[IVXLCDM]+)( [A-Z][a-z]*|[0-9]+|[IVXLCDM]+)*")) {
            throw new IllegalArgumentException("Invalid title format.");
        }

        return title;
    }

    private String validateDescription(String description) {
        if (!description.matches("[A-Z].*\\.")) {
            // .* means followed by any characters
            throw new IllegalArgumentException("must start with a capital letter "
                    +
                    "+ end with a full stop.");
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
        return this.title.toUpperCase() + "\n" + "\"" + this.description + "\"";
    }

    /**
     * Javadoc
     */
    @Override
    public String title() {
        return this.title;
    }

    /**
     * Javadoc
     */
    private static String  capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }


    @Override
    public String toString() {
        return this.title.toUpperCase(); // title as a String in all uppercase and a newline.
    }
}
