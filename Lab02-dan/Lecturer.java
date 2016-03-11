package com.company;

import java.util.Vector;

public class Lecturer extends Persons {

    static int IDcounter = 0;
    private int lecturerID;
    private Vector<Student> myStudentPreferences;
    private Vector<Project> myProjects;

    Lecturer(){
        IDcounter = IDcounter+1;
        this.lecturerID = IDcounter;

    }

    public Integer getLecturerID() {
        return this.lecturerID;
    }

    @Override
    protected int isFree() {
        if (myStudentPreferences.capacity() != 0) {
            return 1;
        }
        return 0;
    }
}