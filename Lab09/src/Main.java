import OracleConnect.OracleConnect;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

/**
 * Reads a list of contestants
 * each having his own nb of tasks
 * (also contained in a list for each person)
 */
public class Main {

    public static void main(String[] args) {
        //Date time;
        try {



            OracleConnect connection = new OracleConnect("STUDENT","student");
            connection.connect();

            connection.showBasicMetadata();
            connection.getTablesMetadata();
            connection.select_query();
            connection.setVisible(true);



        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}