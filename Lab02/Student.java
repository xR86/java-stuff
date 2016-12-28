
import java.util.Vector;

public class Student extends Persons {

    static int IDcounter = 0;
    private String studentID;
    private Vector<Project> myProjectPreferences = new Vector<Project>();


    /**
     * Student(Vector<Project> myProjectPreferences) generates a new student with his preferences and ID (see the default constructor)
     * @param myProjectPreferences
     */
    Student(Vector<Project> myProjectPreferences){
        IDcounter = IDcounter+1;
        this.studentID = this.toString();

        this.myProjectPreferences = myProjectPreferences;
    }

    /**
     * Student() - this class constructor increments the static counter (unique to a student whithin a session) and assigns it to the student ID
     */
    Student(){
        IDcounter = IDcounter+1;
        this.studentID = this.toString();

    }

    @Override
    public String toString(){
        String temp = "S";
        temp += IDcounter;
        return  temp;
    }

    /**
     * getStudentID() gives a student's ID
     * @return the ID of the student
     */
    public String getStudentID() {
        return this.studentID;
    }


    /**
     * isFree() checks if the students has any projects allocated
     * @return 1 if the student has projects allocated
     * @return 0 if the student is free (has no projects allocated)
     */
    @Override
    protected int isFree() {
        if (myProjectPreferences.capacity() != 0) {
            return 1;
        }
        return 0;
    }

    /**
     * addProjectPreference() adds the preferences of each students. Example: S1 prefers P1, P7
     */
    public void addProjectPreference() {

    }

    public void printProjectPreferences(){
        System.out.print(this.getStudentID());
        for(int i=0; i<this.myProjectPreferences.size(); i++){
            System.out.print(" ");
            System.out.print(this.myProjectPreferences.get(i).getProjectID());
        }

    }

}