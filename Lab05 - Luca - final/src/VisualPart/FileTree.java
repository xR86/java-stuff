package visualPart; /**
 * Created by Admin on 31.03.2016.
 */

import commands.Commands;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Vector;

/**
 * Display a file system in a JTree view
 */
public class FileTree extends JPanel {
    public static String path;
    public static String searchTitle;
    public static String searchArtist;
    private JTree tree;

    JPopupMenu menu = new JPopupMenu("Popup");

    /**
     * Popup Trigger for Contextual Menu
     */
    class PopupTriggerListener extends MouseAdapter {
        public void mousePressed(MouseEvent ev) {
            int selRow = tree.getRowForLocation(ev.getX(), ev.getY());
            TreePath selPath = tree.getPathForLocation(ev.getX(), ev.getY());
            if(selRow != -1) {
                if (SwingUtilities.isRightMouseButton(ev)) {
                    menu.show(ev.getComponent(), ev.getX(), ev.getY());
                }
            }
        }

        public void mouseReleased(MouseEvent ev) {
            /*if (ev.isPopupTrigger()) {
                menu.show(ev.getComponent(), ev.getX(), ev.getY());
            }*/
            int selRow = tree.getRowForLocation(ev.getX(), ev.getY());
            TreePath selPath = tree.getPathForLocation(ev.getX(), ev.getY());
            if(selRow != -1) {
                if (SwingUtilities.isRightMouseButton(ev)) {
                    menu.show(ev.getComponent(), ev.getX(), ev.getY());
                }
            }
        }

        public void mouseClicked(MouseEvent ev) {
        }
    }


    /** Construct a FileTree of the current directory (same with the source) */
    FileTree(File dir) {

        setLayout(new BorderLayout());

        // Make a tree list with all the nodes, and make it a JTree
        tree = new JTree(addNodes(null, dir));

        tree.addMouseListener(new PopupTriggerListener());



        Commands commands = new Commands();
        JMenuItem newMenuItem = new JMenuItem("Play", KeyEvent.VK_N);
        newMenuItem.addActionListener(new AbstractAction() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                commands.play(FileTree.path); //commands.play(path)
            }
        });
        menu.add(newMenuItem);

        JMenuItem searchMenuItem = new JMenuItem("Web Search", KeyEvent.VK_M);
        searchMenuItem.addActionListener(new AbstractAction() {
            //@Override
            public void actionPerformed(ActionEvent e) {

                try {
                    URI searchInfo = new URI("https://www.google.ro/search?q=" +
                            URLEncoder.encode(FileTree.searchArtist,"UTF-8") +
                            "%20" +
                            URLEncoder.encode(FileTree.searchTitle,"UTF-8"));
                    Desktop.getDesktop().browse(searchInfo);
                } catch(IOException er){
                    System.err.println("IO Error");
                }
                catch (URISyntaxException er){
                    System.err.println("Error with the URI");
                }


            }
        });
        menu.add(searchMenuItem);


        // Add a listener
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            /**
             * Changed value means a different node selected by the cursor
             * Once selected it shows metadata(if .mp3), shows a list of .mp3s with some metadatas(if folder)
             * or displays a relevant message that we can't do anything with it..
             * @param e
             */
            public void valueChanged(TreeSelectionEvent e) {
                Commands commands = new Commands();
                String jTreeVarSelectedPath = commands.getCale() + File.separator;
                Object[] paths = tree.getSelectionPath().getPath();

                for (int i=1; i<paths.length; i++) {
                    jTreeVarSelectedPath += paths[i];
                    if (i+1 <paths.length ) {
                        jTreeVarSelectedPath += File.separator;
                    }
                } //gets the path used in our defined functions from commands lab
                //System.out.println(commands.info(jTreeVarSelectedPath));
                path = jTreeVarSelectedPath;


                if (new File(jTreeVarSelectedPath).isDirectory()){
                    MainFrame.setRightTable(path);
                }
                else if (jTreeVarSelectedPath.contains(".mp3") || jTreeVarSelectedPath.contains(".wav")) {
                    MainFrame.setRightPanel(commands.info(jTreeVarSelectedPath));

                    /**
                     * getting the metadatas in case of web search
                     */
                    try {
                        InputStream input = new FileInputStream(new File(jTreeVarSelectedPath));
                        ContentHandler handler = new DefaultHandler();
                        Metadata metadata = new Metadata();
                        Parser parser = new Mp3Parser();
                        ParseContext parseCtx = new ParseContext();
                        parser.parse(input, handler, metadata, parseCtx);
                        input.close();

                        /**
                         * metadatas used for web search
                         */
                        searchTitle = metadata.get("title");
                        searchArtist = metadata.get("xmpDM:artist");
                    } catch (FileNotFoundException er) {
                        System.err.println("File not found...");
                    } catch (IOException er) {
                        System.err.println("IO Error ...");
                    } catch (SAXException er) {
                        er.printStackTrace();
                    } catch (TikaException er) {
                        er.printStackTrace();
                    }

                }
                else{
                    MainFrame.setRightPanel("Nothing to show here..");
                }
            }
        });

        // Lastly, put the JTree into a JScrollPane.
        JScrollPane scrollpane = new JScrollPane();
        scrollpane.getViewport().add(tree);
        add(BorderLayout.CENTER, scrollpane);
    }

    /** Add nodes from under "dir" into curTop. Highly recursive. */
    private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        Vector<String> ol = new Vector<>();
        String[] tmp = dir.list();
        for (String aTmp : tmp) ol.addElement(aTmp);
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector<String> files = new Vector<>();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = ol.elementAt(i);
            String newPath;
            if (curPath.equals("."))
                newPath = thisObject;
            else
                newPath = curPath + File.separator + thisObject;
            if ((f = new File(newPath)).isDirectory())
                addNodes(curDir, f);
            else
                files.addElement(thisObject);
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++)
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }

}
