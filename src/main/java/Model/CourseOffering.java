package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
/**
 * This is the Course Offering class
 * @Author: Agraj Vuppula & Ashraful Islam
 */
public class CourseOffering {
    private long courseOfferingId;
    private String location;
    private String instructors;
    private int year;
    private long semesterCode;
    private String term;
    @JsonIgnore
    private List<Section> sections = new ArrayList<>();
    public CourseOffering(CourseData courseData){
        this.courseOfferingId = -1;
        this.semesterCode = Integer.parseInt(courseData.getSemester());
        this.location = courseData.getLocation();
        this.instructors = courseData.getInstructor();
        this.year = convertToYear(courseData.getSemester());
        this.term = convertToTerm(courseData.getSemester());
    }

    public CourseOffering(long courseId, int id, CourseData courseData){
        this.instructors = courseData.getInstructor();
        this.location = courseData.getLocation();
        this.semesterCode = Integer.parseInt(courseData.getSemester());
        this.courseOfferingId = id;
        this.year = convertToYear(courseData.getSemester());
        this.term = convertToTerm(courseData.getSemester());
    }
    public int convertToYear(String sem){
        int value = Integer.parseInt(sem);
        int X = value/1000;
        int Y = (value/100) % 10;
        int Z = (value/10) % 10;
        int theYear = 1900 + 100*X + 10*Y + 1*Z;
        return theYear;
    }
    public String convertToTerm(String sem){
        String season = "";
        int ans = Integer.parseInt(sem);
        ans = ans % 10;
        if(ans == 1)
            season = "Spring";
        else if(ans == 4)
            season = "Summer";
        else if(ans == 7)
            season = "Fall";
        return season;
    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }
    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getInstructors() {
        return instructors;
    }
    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public long getSemesterCode() {
        return semesterCode;
    }
    public void setSemesterCode(long semesterCode) {
        this.semesterCode = semesterCode;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public List<Section> getSections() {
        return sections;
    }

    public void addCourseOffering(CourseData cd) {
        boolean ans = false;
        for(Section sc : sections){
            if(sc.getType().equals(cd.getComponent()) && sc.getEnrollmentCap() == cd.getEnrollmentCap()
                    && sc.getEnrollmentTotal() == cd.getEnrollmentTotal()){
                ans = true;
                break;
            }
        }
        if(!ans){
            sections.add(new Section(cd.getComponent(), cd.getEnrollmentCap(), cd.getEnrollmentTotal()));
        }
    }
}