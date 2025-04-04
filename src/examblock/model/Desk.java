package examblock.model;

/**
 *  Javadoc  */
public class Desk {
    /** Javadoc  */
    private String familyName;
    /** Javadoc  */
    private String givenName;
    /** Javadoc  */
    private final int number;
    /*
    * Javadoc  */


    /** Javadoc
     * @param deskNumber  d
     * */
    public Desk(int deskNumber) {
        this.givenName = null;
        this.familyName = null;
        this.number = deskNumber;
    }

    /**
     * test
     * @param familyName  f
     * Javadoc  */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     *
     * Javadoc
     * @param givenAndInit g*/
    public void setGivenAndInit(String givenAndInit) {
        this.givenName = givenAndInit;
    }

    /** Javadoc
     * @return str
     * */
    public String deskFamilyName() {
        return this.familyName;
    }

    /** Javadoc
     * @return str
     * */
    public String deskGivenAndInit() {
        return this.givenName;
    }

    /** Javadoc
     * @return i
     * */
    public int deskNumber() {
        return this.number;
    }

    /** Javadoc  */
    @Override
    public String toString() {
        return " Desk : " + this.number + "\n" + this.familyName + "\n" + this.givenName;
    }
}
