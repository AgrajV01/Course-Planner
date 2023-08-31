package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the watcher class, it implements the Observer class
 * Author: Agraj Vuppula & Ashraful Islam
 */
public class Watcher implements Observer{
    public long id;
    public Department department;
    public Course course;

    public List<String> events = new ArrayList<>();

    public Watcher(long id, Department dept, Course course1){
        this.id = id;
        this.department = dept;
        this.course = course1;
        course.addObserver(this);
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public List<String> getEvents() {
        return events;
    }
    public void setEvents(List<String> events) {
        this.events = events;
    }
    @Override
    public void update(CourseOffering courseOffering, CourseData courseData) {
        Date now = new Date();
        String update = "" + now + ": Added section " + courseData.getComponent() + " with enrollment (" +
                courseData.getEnrollmentTotal() + "/" + courseData.getEnrollmentCap() + ") to offering " +
                courseOffering.getTerm() + " " + courseOffering.getYear();
        events.add(update);
    }
}
