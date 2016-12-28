import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        File file = new File("matrix.txt");
        LabyrinthMatrixImpl cevaRaw = new LabyrinthMatrixImpl(file);

        cevaRaw.printRawArray();

        LabyrinthViewImplementer cevaPrelucrat = new LabyrinthViewImplementer();
        cevaPrelucrat.setLabyrinth(cevaRaw);

        System.out.println("");
        System.out.println("");
        cevaPrelucrat.getLabyrinth();

        LabyrinthSolverInteractive rezolva = new LabyrinthSolverInteractive();

    }
}
