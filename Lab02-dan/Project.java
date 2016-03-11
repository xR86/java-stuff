package com.company;

import java.util.Vector;

/**
 *
 */
public class Project {

    static int IDcounter = 0;
    private int projectID;

    private Integer capacity;
    private Lecturer myLecturer;
    private Vector<Student> myStudents;

  Project(Integer capacity, Lecturer myLecturer) {
      IDcounter = IDcounter+1;
      this.projectID = IDcounter;

      this.capacity = capacity;
      this.myStudents = new Vector<Student>();
      this.myLecturer = myLecturer;

  }
  Project(){
      IDcounter = IDcounter+1;
      this.projectID = IDcounter;

  }

  public Integer getProjectID() {
        return this.projectID;
    }
  public Integer getCapacity() {
    return capacity;
  }
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }


}