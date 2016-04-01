package VisualPart;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Basic contruction of the main frame
 * A JTree will always be shown in the left side
 * while on the right side we alternate metadata view of an .mp3 in a text area with
 * a table of metadatas from all .mp3 files present in the selected folder.
 * (The right side start with an empty text area by default)
 */
public class MainFrame extends JFrame {
    private static JTextArea textArea = new JTextArea();

    private static JSplitPane jsplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new FileTree(new File(".")), textArea);

    /**
     * Basic constructor for the Main Frame
     * title represents the title shown by the top bar of the app.
     * We set a default size for the text area of 500x500. (should be enough)
     * Next we create a container wich holds by default a Tree in the left side and
     * an empty text area in the right side.
     * @param title
     */
    public MainFrame(String title){
        super(title);
        setLayout(new BorderLayout());
        Dimension size = new Dimension(500,500);
        textArea.setPreferredSize(size);

        /* SPLIT PANE HERE */

/*
        cp.add(textArea,BorderLayout.CENTER);
        cp.add(button,BorderLayout.SOUTH);
        cp.add(new VisualPart.FileTree(new File(".")));*/
        Container cp = getContentPane();
        cp.add(jsplitPane);
    }


    /**
     * Method for changing the right pane to a text area
     * with metadatas from the selected .mp3 file.
     * @param text
     */
    static void setRightPanel(String text){
        textArea.setText(text);
        jsplitPane.setRightComponent(textArea);
    }


    /**
     * Method for changing the right pane to a table of
     * metadatas from the mp3 files present in the selected folder.
     * @param path
     */
    static void setRightTable(String path) {
        String[] columnNames = {"File Name",
                "Song",
                "Author",
                "Album",
                "Year"};
        Object[][] data = new Object[1000][5];

        File folderCurent = new File(path);
        String[] subdirectoare = folderCurent.list();
        int iterator_date=0;
        for (String nume: subdirectoare) {
            if (nume.contains(".mp3") || nume.contains(".wav") || nume.contains(".flac")) {
                try {
                    InputStream input = new FileInputStream(new File(path + File.separator + nume));
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);
                    input.close();
                    data[iterator_date][0] = nume;
                    data[iterator_date][1] = metadata.get("title");
                    data[iterator_date][2] = metadata.get("xmpDM:artist");
                    data[iterator_date][3] = metadata.get("xmpDM:album");
                    data[iterator_date][4] = metadata.get("xmpDM:releaseDate");
                    iterator_date++;
                } catch (FileNotFoundException e) {
                System.err.println("File not found...");
            } catch (IOException e) {
                System.err.println("IO Error ...");
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            }
            }
        }
        JTable table = new JTable(data, columnNames);
        JSplitPane table_full = new JSplitPane(JSplitPane.VERTICAL_SPLIT,table.getTableHeader(),table);
        jsplitPane.setRightComponent(table_full);
    }
}
