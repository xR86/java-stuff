package VisualPart; /**
 * Created by Admin on 31.03.2016.
 */

import Commands.Commands;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

/**
 * Display a file system in a JTree view
 */
public class FileTree extends JPanel {
    public static String path;

    /** Construct a FileTree of the current directory (same with the source) */
    FileTree(File dir) {

        setLayout(new BorderLayout());

        // Make a tree list with all the nodes, and make it a JTree
        JTree tree = new JTree(addNodes(null, dir));

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
                }
                else{
                    MainFrame.setRightPanel("Nothing to show here..");
                }
            }  //changing the value means selecting a file
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

    public Dimension getMinimumSize() {
        return new Dimension(200, 400);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }

}
