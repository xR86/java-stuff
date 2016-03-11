package com.company;

public class Problem {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
	// should I use hashcode() instead of ID's ?
        System.out.println(";;;;");

        Project proj1 = new Project();
        Lecturer lect1 = new Lecturer();
        Student stud1 = new Student();

        System.out.println(stud1.getStudentID());
        stud1.addProjectPreference();

        System.out.println(lect1.getLecturerID());

        System.out.println(proj1.getProjectID());

    }
}
