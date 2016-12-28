
import java.util.Vector;

public class Lecturer extends Persons {

    static int IDcounter = 0;
    private String lecturerID;
    private Vector<Student> myStudentPreferences = new Vector<Student>();
    private Vector<Project> myProjects = new Vector<Project>();

    /**
     * Lecturer() - this class constructor increments the static counter (unique to a lecturer whithin a session) and assigns it (along with the letter) to the lecturer ID
     */
    Lecturer(){
        IDcounter = IDcounter+1;
        this.lecturerID = this.toString();

    }

    /**
     * getStudentID() gives a lecturer's ID
     * @return the ID of the lecturer
     */
    public String getLecturerID() {
        return this.lecturerID;
    }

    /**
     * isFree() checks if the lecturer has any projects allocated
     * @return 1 if the lecturer has projects allocated
     * @return 0 if the lecturer is free (has no projects allocated)
     */
    @Override
    protected int isFree() {
        if (myStudentPreferences.capacity() != 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString(){
        String temp = "L";
        temp += IDcounter;
        return  temp;
    }


    /**
     * addStudentPreference() adds a lecturers preferences of students
     * @param student - is added to the private myStudentPreferences vector
     */
    public void addStudentPreference(Student student) {
        myStudentPreferences.add(student);
    }
}