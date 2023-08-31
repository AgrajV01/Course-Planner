package Model;

/**
 * This is the Subject interface
 * Author: Agraj Vuppula & Ashraful Islam
 */
public interface Subject {
     void addObserver(Observer observer);
     void removeObserver(Observer observer);
      void notify(CourseOffering courseOffering, CourseData courseData);
}
