package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
/**
 * This is the Course class
 * @Author: Agraj Vuppula & Ashraful Islam
 */

public class Course implements Subject{
    private long courseId;
    private String catalogNumber;

    @JsonIgnore
    private List<CourseOffering> courseOfferingList;

    @JsonIgnore
    private List<Observer> observers;


    public Course(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
        courseOfferingList = new ArrayList<>();
        observers = new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public void addObserver(Observer observer){
        observers.add(observer);

    }

    @JsonIgnore
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @JsonIgnore
    @Override
    public void notify(CourseOffering courseOffering, CourseData courseData) {
        for(Observer obs : observers){
            obs.update(courseOffering, courseData);
        }
    }

    public long getCourseId() {
        return courseId;
    }
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
    public String getCatalogNumber() {
        return catalogNumber;
    }
    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
    public List<CourseOffering> getCourseOfferingList() {
        return courseOfferingList;
    }

    public void setCourseOfferingList(List<CourseOffering> courseOfferingList) {
        this.courseOfferingList = courseOfferingList;
    }

    public void addCourse(CourseData cd) {
        boolean ans = false;
        for(CourseOffering courseOffering : courseOfferingList){
            if(Integer.parseInt(cd.getSemester()) == courseOffering.getSemesterCode()){
                ans = true;
                courseOffering.addCourseOffering(cd);
                break;
            }
        }
        if(!ans){
            int id = courseOfferingList.size();
            CourseOffering temp = new CourseOffering(courseId,id,cd);
            temp.addCourseOffering(cd);
            courseOfferingList.add(temp);
        }
        // To notify the observers
        notify(new CourseOffering(cd),cd);
    }
}
