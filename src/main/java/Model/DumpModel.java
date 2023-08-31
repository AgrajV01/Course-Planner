package Model;

import java.io.*;
import java.util.*;
/**
 * This is the DumpModel class, here were read all the data from the csv file and print the output to the console
 * @Author: Agraj Vuppula & Ashraful Islam
 */
public class DumpModel {
    private List<CourseData> courseList = new ArrayList<>();
    private List<Department> departmentList = new ArrayList<>();

    public DumpModel(File DATA_FILE) {
        try(Scanner sc = new Scanner(DATA_FILE)) {
            // This is to jump to next line
            sc.nextLine();
            String line ="";
            while(sc.hasNext()) {
                line = sc.nextLine();
                String[] values = line.split(",");

                if(values[6].equals("(null)") || values[6].equals("<null>"))
                    values[6] = "";
                StringBuilder instructorsBuilder = new StringBuilder();
                for (int i = 6; i < values.length-1; i++) {
                    instructorsBuilder.append(values[i]);
                    if (i != values.length - 2) {
                        instructorsBuilder.append(",");
                    }
                }
                values[6] =   instructorsBuilder.toString() ;
                CourseData temp = new CourseData(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim(),
                        Integer.parseInt(values[4].trim()), Integer.parseInt(values[5].trim()), values[6].trim(),
                        values[values.length-1].trim());
                courseList.add(temp);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sortCourse();
        setTheDepartments();
        sortCourse();
    }
    public void sortCourse(){

        java.util.Collections.sort(courseList, new Comparator<CourseData>() {
            @Override
            public int compare(CourseData o1, CourseData o2) {
                String c1 = o1.getSubjectName() + o1.getCatalogNumber() + o1.getSemester() + o1.getLocation() + o1.getComponent();
                String c2 = o2.getSubjectName() + o1.getCatalogNumber() + o2.getSemester() + o2.getLocation() + o2.getComponent();

                return c1.compareTo(c2);
            }
        });
    }
    public void dumpModelToConsole(){
        String Subject;
        sortCourse();

        String location;
        String instructor;
        String componentCode;
        int enrolment1;
        int enrolment2;
        int j = 0;

        for(int i = 0; i < courseList.size(); i++){
            location = courseList.get(i).getLocation();
            instructor = courseList.get(i).getInstructor();
            componentCode = courseList.get(i).getComponent();
            enrolment1 = courseList.get(i).getEnrollmentTotal();
            enrolment2 = courseList.get(i).getEnrollmentCap();
            Subject = courseList.get(i).getSubjectName();
             int l = 0;

            j = i+1;

            if(j <= courseList.size() -1){
                while(location.equals(courseList.get(j).getLocation())
                        && componentCode.equals(courseList.get(j).getComponent()) ){
                    enrolment1 = enrolment1 + courseList.get(j).getEnrollmentTotal();
                    enrolment2 = enrolment2 + courseList.get(j).getEnrollmentCap();
                    if(!(courseList.get(j).getInstructor().equals(courseList.get(j-1).getInstructor())) ){
                        instructor = courseList.get(j-1).getInstructor();
                        l = j-1;

                    }
                    j++;
                    if(j >= courseList.size()-1){
                        break;
                    }

                }
            }

            int k = 0;
            if( i - 1 >= 0 ){

                 k = j - 1;

               if(courseList.get(i-1).getLocation().equals(courseList.get(k).getLocation()) &&
                       (courseList.get(i-1).getInstructor().equals(courseList.get(k).getInstructor())
                       )){

                   System.out.println("\t\t Type = " + courseList.get(i).getComponent() +
                           ", Enrolment = " + enrolment1 + "/" + enrolment2);
                   i = j - 1;
                  continue;
               }

            }
            i = j - 1;
            System.out.println(courseList.get(i).getSubjectName());
            System.out.println("\t" + courseList.get(i).getSemester() + " in " + courseList.get(i).getLocation() + " by "
                    + courseList.get(i).getInstructor());
            if((instructor.equals(courseList.get(l).getInstructor())) ){
                System.out.println( ", " + instructor);
            }

            System.out.println("\t\t Type = " + courseList.get(i).getComponent() +
                    ", Enrolment = " + enrolment1 + "/" + enrolment2);

        }
    }

    public List<CourseData> getCourseList() {
        return courseList;
    }
    public List<Department> getDepartment(){
        return departmentList;
    }
    public void setTheDepartments(){

        boolean departmentFound = false;
        int i =0;
        for(CourseData courseData : courseList) {
            for(Department department : departmentList) {
                if (department.getName().equals(courseData.getSubjectName())){
                    departmentFound = true;
                    department.addDepartment(courseData);
                    break;
                }
            }
            if(!departmentFound){
                long departmentId = departmentList.size();
                Department temp = new Department(departmentId, courseData.getSubjectName());
                temp.addDepartment(courseData);
                departmentList.add(temp);
            }
            departmentFound = false;
        }
    }
}

