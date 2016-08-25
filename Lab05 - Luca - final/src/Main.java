import commands.Commands;
import visualPart.FileTree;
import visualPart.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Main class creating the actual JFrame
 * and a Menu bar on top for addictional options.
 */
public class Main {

    /** Main: make a Frame, add a visualPart.FileTree */
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
                        fileMenu.add(searchMenuItem);

                        JFrame frame = new MainFrame("My MP3 Player");
                        frame.setJMenuBar(menuBar);
                        frame.setForeground(Color.black);
                        frame.setBackground(Color.lightGray);
                        frame.pack();

                        //frame.setContentPane(new visualPart.popup());

                        frame.setVisible(true);

                        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//JFrame.EXIT_ON_CLOSE
                        frame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent we)
                            {
                                String ObjButtons[] = {"Yes","No"};
                                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Tema5",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                                if(PromptResult==JOptionPane.YES_OPTION)
                                {
                                    System.exit(0);//sau: we.getWindow().dispose(); ?
                                }
                            }
                        });
                    }
                }
        );}

}
