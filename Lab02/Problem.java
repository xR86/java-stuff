
import java.util.Vector;

public class Problem {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
	// should I use hashcode() instead of ID's ?

        System.out.println("....");

        /**
         * Lecturer, Project options
         *
         */
        //create new lecturers
        Lecturer lect1 = new Lecturer();
        System.out.println(lect1.getLecturerID());

        Lecturer lect2 = new Lecturer();
        Lecturer lect3 = new Lecturer();

        //create projects with their respective capacities and lecturers
        Project proj1 = new Project(2, lect1);
        System.out.println(proj1.getProjectID());

        Project proj2 = new Project(1, lect1);
        Project proj3 = new Project(1, lect1);
        Project proj4 = new Project(1, lect2);
        Project proj5 = new Project(1, lect2);
        Project proj6 = new Project(1, lect2);
        Project proj7 = new Project(1, lect3);
        Project proj8 = new Project(1, lect3);



        /**
         * Student PREFS
         *
         */
        //create a temporary vector to pass to the Student constructor
        Vector<Project> tempPref = new Vector<Project>();

        tempPref.add(proj1);
        tempPref.add(proj7);
        Student stud1 = new Student(tempPref);
        System.out.println(stud1.getStudentID());

        tempPref.clear();
        tempPref.add(proj1);
        tempPref.add(proj2);
        tempPref.add(proj3);
        tempPref.add(proj4);
        tempPref.add(proj5);
        tempPref.add(proj6);
        Student stud2 = new Student(tempPref);

        tempPref.clear();
        tempPref.add(proj2);
        tempPref.add(proj1);
        tempPref.add(proj4);
        Student stud3 = new Student(tempPref);

        tempPref.clear();
        tempPref.add(proj2);
        Student stud4 = new Student(tempPref);

        tempPref.clear();
        tempPref.add(proj1);
        tempPref.add(proj2);
        tempPref.add(proj3);
        tempPref.add(proj4);
        Student stud5 = new Student(tempPref);

        tempPref.clear();
        tempPref.add(proj2);
        tempPref.add(proj3);
        tempPref.add(proj4);
        tempPref.add(proj5);
        tempPref.add(proj6);
        Student stud6 = new Student(tempPref);

        tempPref.clear();
        tempPref.add(proj5);
        tempPref.add(proj3);
        tempPref.add(proj8);
        Student stud7 = new Student(tempPref);


        /**
         * Lecturer PREFS
         *
         */
        lect1.addStudentPreference(stud7);
        lect1.addStudentPreference(stud4);
        lect1.addStudentPreference(stud1);
        lect1.addStudentPreference(stud3);
        lect1.addStudentPreference(stud2);
        lect1.addStudentPreference(stud5);
        lect1.addStudentPreference(stud6);

        lect2.addStudentPreference(stud3);
        lect2.addStudentPreference(stud2);
        lect2.addStudentPreference(stud6);
        lect2.addStudentPreference(stud7);
        lect2.addStudentPreference(stud5);

        lect3.addStudentPreference(stud1);
        lect3.addStudentPreference(stud7);


        /**
         * Print Instance
         * */
        System.out.println("Student preferences");
        stud1.printProjectPreferences();


    }
}
