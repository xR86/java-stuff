package OracleConnect;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucai on 4/28/2016.
 */
public class OracleConnect extends JFrame {

    private String user;
    private String pass;
    private Connection conn;

    private JPanel topPanel;
    //private JTextArea textbox;
    private JTable table;
    private JScrollPane scrollPane;
    private static DatabaseMetaData metadata = null;

    public OracleConnect(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void connect() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1522/XE", user, pass); //change data here
    }


    public void select_query() throws SQLException {


        // Create columns names
        //String columnNames[] = { "Column 1", "Column 2", "Column 3" };
        // String columnNames[] = new String[10];
        // Create some data


        Statement stmt = conn.createStatement();
        Statement count_stmt = conn.createStatement();
        try {
            String select_q = "SELECT * FROM STUDENTI"; //the query
            ResultSet rs = stmt.executeQuery(select_q);
            ResultSetMetaData rsmd = rs.getMetaData();

            String count_q = "SELECT COUNT(*) FROM STUDENTI"; //counting rows
            ResultSet count = count_stmt.executeQuery(count_q);
            count.next();
            int nb_of_rows = count.getInt(1); //fetching row count


            // Set the frame characteristics
            setTitle("My Table");
            setSize(1000, 300);
            setBackground(Color.gray);


            // Create a panel to hold all other components
            topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            getContentPane().add(topPanel, "North");

            /*textbox = new JTextArea();
            getContentPane().add(textbox,"South");*/


            String[] columnNames = new String[rsmd.getColumnCount()];
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }


            String dataValues[][] = new String[nb_of_rows][rsmd.getColumnCount()];


            // Create a new table instance
            table = new JTable(dataValues, columnNames);


            // Add the table to a scrolling pane
            scrollPane = new JScrollPane(table);
            topPanel.add(scrollPane, BorderLayout.CENTER);


            while (rs.next()) {

                //System.out.println(nb_of_rows);

                for (int i = 0; i < rsmd.getColumnCount(); i++) /*{
                    rs.getString(i + 1);
                    if (rs.wasNull())
                        dataValues[rs.getRow() - 1][i] = " ";
                    else*/
                    dataValues[rs.getRow() - 1][i] = rs.getString(i + 1);
                /*}
                System.out.print("\n");*/


            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 904) //identifier not found
                System.err.println(e.getMessage());
            else
                e.printStackTrace();
        }
    }

    public void showBasicMetadata() throws SQLException {

        try {
            metadata = conn.getMetaData();
            System.out.println("Database Product Name: "
                    + metadata.getDatabaseProductName());
            System.out.println("Database Product Version: "
                    + metadata.getDatabaseProductVersion());
            System.out.println("Logged User: " + metadata.getUserName());
            System.out.println("JDBC Driver: " + metadata.getDriverName());
            System.out.println("Driver Version: " + metadata.getDriverVersion());
            System.out.println("\n");


        } catch (SQLException e) {
            if (e.getErrorCode() == 904) //identifier not found
                System.err.println(e.getMessage());
            else
                e.printStackTrace();
        }
    }


    public static void getTablesMetadata() throws SQLException {
        ResultSet rs = null;
        rs = metadata.getTables(null, metadata.getUserName(), "%", new String[]{"TABLE"});
        System.out.println("tables: ");
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_NAME"));
        }
        System.out.println("");

        rs = metadata.getTables(null, metadata.getUserName(), "%", new String[]{"VIEW"});
        System.out.println("views: ");
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_NAME"));
        }
        System.out.println("");

        rs = metadata.getTables(null, metadata.getUserName(), "%", new String[]{"PROCEDURE"});
        System.out.println("procedures: ");
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_NAME"));
        }
        System.out.println("");
    }
}