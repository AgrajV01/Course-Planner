package Controller;

import Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Rest Api Controller class
 * @Author: Agraj Vuppula & Ashraful Islam
 */
@RestController
public class CoursePlannerController {

    private DumpModel model = new DumpModel(new File("data/course_data_2018.csv"));
    private List<Watcher> watchers = new ArrayList<>();
    private About about= new About("Course Planner", "Agraj Vuppula & Ashraful Islam");
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/about")
    public About getAPI(){
        return about;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/dump-model")
    public void getDumpModel(){
        model.dumpModelToConsole();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments")
    public List<Department> getDepartments(){
        return model.getDepartment();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{deptId}/courses")
    public List<Course> getCoursesofDepartment(@PathVariable("deptId") int departmentID ){
    if(departmentID < 0 || departmentID >= model.getDepartment().size())
        throw new notFoundException("This departmentID is not found");
    return model.getDepartment().get(departmentID).getCourses();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    public List<CourseOffering> getCourseOffering(@PathVariable("deptId") int departmentID,
                                                  @PathVariable("courseId") int courseID){
        if(departmentID < 0 || departmentID >= model.getDepartment().size() ||
                courseID < 0 || courseID >= model.getDepartment().get(departmentID).getCourses().size())
            throw new notFoundException("The courseID you are looking at this department is not found");
        return model.getDepartment().get(departmentID).getCourses().get(courseID).getCourseOfferingList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    public List<Section> getSectionOffering(@PathVariable("deptId") int departmentID,
                                            @PathVariable("courseId") int courseID,
                                            @PathVariable("courseOfferingId") int offeringID ){
        if(departmentID < 0 || departmentID >= model.getDepartment().size() || courseID < 0 || courseID >=
                model.getDepartment().get(departmentID).getCourses().size() || offeringID < 0 || offeringID >=
                model.getDepartment().get(departmentID).getCourses().get(courseID).getCourseOfferingList().size())
            throw new notFoundException("The CourseOfferingId you are looking for is not found in the department ID: "
                    + departmentID + " with the course ID:" + courseID);
        return model.getDepartment().get(departmentID).getCourses().get(courseID).
                getCourseOfferingList().get(offeringID).getSections();
    }

    //Part 3 optional
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/stats/students-per-semester?deptId={deptId}")
    public List<GraphDataPoint> graphData(){
        List<GraphDataPoint> temp = new ArrayList<>();
        return temp;
    }
    //part 4
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/addoffering")
    public CourseData addOffering(@RequestBody CourseData courseData){
        try {
            model.getCourseList().add(courseData);
            model.setTheDepartments();
            return courseData;
        }
        catch(Exception e){
            throw new BadRequest("Couldn't add this new Data");
        }
    }
    // Part 5
    @GetMapping("/api/watchers")
    public List<Watcher> getWatchers(){
        return watchers;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/watchers/{watcherID}")
    public Watcher getOneWatcher(@PathVariable("watcherID") int watcherId) {
        if(watcherId < 0 || watcherId >= watchers.size())
            throw new notFoundException("Not found the id you asked for");
        return watchers.get(watcherId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/watchers")
    public void createWatcher(@RequestBody WatcherData watcherdata){
        try {
            watchers.add(new Watcher(watchers.size(), model.getDepartment().get(watcherdata.getDeptId()),
                    model.getDepartment().get(watcherdata.getDeptId()).getCourses().get(watcherdata.getCourseId())));
        }
        catch( Exception e) {
            throw new BadRequest("sorry, could not add the watcher");
        }
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/watchers/{watcherID}")
    public void delete(@PathVariable ("watcherID") int watcherId){
        if(watcherId < 0 || watcherId >= watchers.size())
            throw new notFoundException("There is no watcher with the id " + watcherId);
        try {
            watchers.remove(watcherId);
        }
        catch (Exception e){
            throw new BadRequest("sorry, could not delete that watcher");
        }
    }

    /**
     * deals with bad request
     */
    // Support returning errors to client
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public  static class BadRequest extends RuntimeException {
        public BadRequest(String str) {
            super(str);
        }
    }
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public  static class notFoundException extends RuntimeException {
        public notFoundException(String str) {
            super(str);
        }
    }

}