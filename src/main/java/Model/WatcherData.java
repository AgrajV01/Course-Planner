package Model;

/**
 * This is the WatcherData class
 * Author: Agraj Vuppula & Ashraful Islam
 */
public class WatcherData {
    private int deptId;
    private int courseId;

    public int getDeptId() {
        return deptId;
    }
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public WatcherData(int deptId, int courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }
}
