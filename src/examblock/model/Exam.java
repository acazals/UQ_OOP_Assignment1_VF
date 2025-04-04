package examblock.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

/** Javadoc  */

public class Exam {

    // i added an override equals methode AND a check conditions boolean method
    /** Javadoc  */
    public static enum ExamType {

        /** Javadoc  */
        INTERNAL,  // Internal assessment, conducted by the school

        /** Javadoc  */
        EXTERNAL;  // External assessment, conducted by the QCAA

        // Optional: Custom method inside the enum for additional functionality
        /** Javadoc
         * @return str
         * */
        public String getDescription() {
            return switch (this) {
                case INTERNAL -> "Internal assessment, conducted by the school";
                case EXTERNAL -> "External assessment, conducted by the QCAA";
                //default -> "Unknown";
            };
        }
    }

    /** Javadoc  */
    private final Subject subject;
    /** Javadoc  */
    private final Exam.ExamType examtype;

    /** Javadoc  */
    private LocalDate date;
    /** Javadoc  */
    private final LocalTime time;

    /** Javadoc  */
    private Character unit;
    /** Javadoc  */
    private String subtitle;
    /** Javadoc  */
    private Character paper;


    /** Javadoc
     * @param day d
     * @param hour h
     * @param minute m
     * @param month m
     * @param year y
     * */
    private Boolean checkConditions(int day, int month, int year, int hour, int minute) {
        if (year < 2025) {
            System.out.println("Year must be 2025 or later.");
            return false;
        }
        if (month < 1 || month > 12) {
            System.out.println("Month must be between 1 and 12.");
            return false;
        }
        if (hour < 7 || hour > 17) {
            System.out.println("Hour must be between 7 and 17 (24-hour format).");
            return false;
        }
        if (minute < 0 || minute > 59) {
            System.out.println("Minute must be between 0 and 59.");
            return false;
        }

        // check the date :
        try {
            this.date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            System.out.println("Invalid day for given month and year.");
            return false;
        }
        return true;
    }

    /** Javadoc
     * @param year y
     * @param month m
     * @param minute m
     * @param hour h
     * @param day d
     * @param examType e
     * @param subject s
     * */
    public Exam(Subject subject, Exam.ExamType examType,
                int day, int month, int year, int hour, int minute) {

        // check conditions
        if (!this.checkConditions(day, month, year, hour, minute)) {
            throw new IllegalArgumentException("Something is wrong with the argument, "
                    +
                    "pls check the string printed right before this.");
        } else {
            this.subject = subject;

            this.examtype = examType;
            this.date = LocalDate.of(year, month, day);
            this.time = LocalTime.of(hour, minute);

        }
    }

    /** Javadoc
     * @param year y
     * @param month m
     * @param minute m
     * @param hour h
     * @param day d
     * @param examType e
     * @param subject s
     * @param unit u
     *
     * */
    public Exam(Subject subject, Exam.ExamType examType, Character unit,
                int day, int month, int year, int hour, int minute) {
        if (!this.checkConditions(day, month, year, hour, minute)) {
            throw new IllegalArgumentException("Something is wrong with the argument, "
                    +
                    "pls check the string printed right before this.");
        }
        this.subject = subject;
        this.examtype = examType;

        this.date = LocalDate.of(year, month, day);
        this.time = LocalTime.of(hour, minute);

        this.unit = unit;
    }


    /** Javadoc
     * @param year y
     * @param month m
     * @param minute m
     * @param hour h
     * @param day d
     * @param examType e
     * @param subject s
     * @param paper u
     * @param subtitle s
     *
     *
     * */
    public Exam(Subject subject, Exam.ExamType examType, Character paper, String subtitle,
                int day, int month, int year, int hour, int minute) {
        if (!this.checkConditions(day, month, year, hour, minute)) {
            throw new IllegalArgumentException("Something is wrong with the argument,"
                    +
                    " pls check the string printed right before this.");
        } else {
            this.subject = subject;
            this.examtype = examType;


            this.date = LocalDate.of(year, month, day);
            this.time = LocalTime.of(hour, minute);

            this.paper = paper;
            this.subtitle = subtitle;

        }
        // paper number and subtitle but no unit specified
    }

    /** Javadoc
     * @param year y
     * @param month m
     * @param minute m
     * @param hour h
     * @param day d
     * @param examType e
     * @param subject s
     * @param paper u
     * @param subtitle s
     * @param unit u
     *
     * */
    public Exam(Subject subject, Exam.ExamType examType, Character paper, String subtitle,
                Character unit, int day, int month, int year, int hour, int minute) {
        if (!this.checkConditions(day, month, year, hour, minute)) {
            throw new IllegalArgumentException("Something is wrong with the argument, "
                    +
                    "pls check the string printed right before this.");
        } else {
            this.subject = subject;
            this.examtype = examType;

            this.date = LocalDate.of(year, month, day);
            this.time = LocalTime.of(hour, minute);

            this.paper = paper;
            this.subtitle = subtitle;
            this.unit = unit;

        }
        // paper number and subtitle but no unit specified
    }

    /** Javadoc
     * @return r
     * */
    public Subject getSubject() {
        return subject;
    }

    // Getter for date
    /** Javadoc
     * @return r
     * */
    public LocalDate getDate() {
        return date;
    }

    // Getter for time
    /** Javadoc
     * @return r
     * */
    public LocalTime getTime() {
        return time;
    }

    //  title
    /** Javadoc
     * @return r
     * */
    public String getTitle() {
        // returns type subject paper subtitle
        if (this.paper != null) {
            return this.examtype + " - " + subject.title()
                    + " - " + paper.toString() + " - " + subtitle;
        } else {
            return this.examtype + " - " + subject.title() + " - " + subtitle;
        }

    }

    /** Javadoc
     * @return r
     * */
    public String getShortTitle() {
        // same as get title ??
        if (this.paper != null) {
            return this.examtype + " - " + subject.title()
                    + " - " + Character.toString(paper) + this.subtitle;
        } else {
            return this.examtype + " - " + subject.title() + " - " + this.subtitle;
        }

    }



    // full
    /** Javadoc
     * @return r
     * */
    public String getFullDetail() {
        if (this.examtype == ExamType.INTERNAL) {
            String title = "Year 12 Internal Assessment " + this.subject.title();

            if (this.paper != null) {
                title += " " + "Paper " + this.paper;
            }
            return title;
        } else {
            String title = "Year 12 External Assessment " + this.subject.title();

            if (this.paper != null) {
                title += " " + "Paper " + this.paper;
            }
            return title;
        }


    }


    // toString
    @Override
    public String toString() {
        return getShortTitle() + " on " + date + " at " + time;
    }


}


