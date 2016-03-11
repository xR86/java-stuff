package com.company;

import java.util.Vector;

public class Student extends Persons {

    static int IDcounter = 0;
    private int studentID;
    private Vector<Project> myProjectPreferences;

    Student(Vector<Project> myProjectPreferences){
        IDcounter = IDcounter+1;
        this.studentID = IDcounter;

        this.myProjectPreferences = myProjectPreferences;
    }

    Student(){
        IDcounter = IDcounter+1;
        this.studentID = IDcounter;

    }

    public Integer getStudentID() {
        return this.studentID;
    }


    @Override
    protected int isFree() {
        if (myProjectPreferences.capacity() != 0) {
            return 1;
        }
        return 0;
    }

    public void addProjectPreference() {
    }
}