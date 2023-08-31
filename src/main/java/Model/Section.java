package Model;

/**
 * This is the section class
 * Author: Agraj Vuppula & Ashraful Islam
 */
public class Section {
    public String type;
    public int enrollmentTotal;
    public int enrollmentCap;


    public Section(String type, int enrollmentCap, int enrollmentTotal) {
        this.type = type;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getType() {
        return type;
    }
    public int getEnrollmentCap() {
        return enrollmentCap;
    }
    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

}
