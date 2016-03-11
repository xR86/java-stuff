package com.company;

import java.util.Vector;

/**
 *
 */
public class Project {

    static int IDcounter = 0;
    private String projectID;

    private Integer capacity;
    private Lecturer myLecturer;
    private Vector<Student> myStudents = new Vector<Student>();

  Project(Integer capacity, Lecturer myLecturer) {
      IDcounter = IDcounter+1;
      this.projectID = this.toString();

      this.capacity = capacity;
      this.myStudents = new Vector<Student>();
      this.myLecturer = myLecturer;

  }
  Project(){
      IDcounter = IDcounter+1;
      this.projectID = this.toString();

  }

    @Override
   public String toString(){
        String temp = "P";
        temp += IDcounter;
        return  temp;
   }


    /**
     * getProjectID()
     * @return projectID
     */
  public String getProjectID() {
        return this.projectID;
    }
  public Integer getCapacity() {
    return capacity;
  }
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }


}