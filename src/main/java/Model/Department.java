package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
/**
 * This is the Department class
 * @Author: Agraj Vuppula & Ashraful Islam
 */

public class Department {
    private long deptId;
    private String name;

    @JsonIgnore
    private List<Course> courseList = new ArrayList<>();

    public Department(long departmentID, String subjectName) {
        this.deptId = departmentID;
        this.name = subjectName;
    }

    public long getDeptId() {
        return deptId;
    }
    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @JsonIgnore
    public List<Course> getCourses() {
        return courseList;
    }

    public void addDepartment(CourseData courseData) {
        boolean courseFound = false;

        for(Course course : courseList){
            if(course.getCatalogNumber().equals(courseData.getCatalogNumber())) {
                courseFound = true;
                course.addCourse(courseData);
                break;
            }
        }
        if(!courseFound){
            int id = courseList.size();
            Course temp = new Course(id, courseData.getCatalogNumber());
            temp.addCourse(courseData);
            courseList.add(temp);
        }
    }
}
