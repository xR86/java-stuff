import Commands.Commands;
import VisualPart.FileTree;
import VisualPart.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Main class creating the actual JFrame
 * and a Menu bar on top for addictional options.
 */
public class Main {

    /** Main: make a Frame, add a VisualPart.FileTree */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {

                        Commands commands = new Commands();
                        JMenuBar menuBar = new JMenuBar();
                        JMenu fileMenu = new JMenu("File");
                        fileMenu.setMnemonic(KeyEvent.VK_F);//open menu is by default: alt+f
                        menuBar.add(fileMenu);

                        JMenuItem newMenuItem = new JMenuItem("Play", KeyEvent.VK_N);
                        newMenuItem.addActionListener(new AbstractAction() {
                            //@Override
                            public void actionPerformed(ActionEvent e) {
                                commands.play(FileTree.path); //commands.play(path)
                            }
                        });
                        fileMenu.add(newMenuItem);

                        JFrame frame = new MainFrame("My MP3 Player");
                        frame.setJMenuBar(menuBar);
                        frame.setForeground(Color.black);
                        frame.setBackground(Color.lightGray);
                        frame.pack();
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                }
        );}

}
