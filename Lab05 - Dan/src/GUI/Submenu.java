package GUI;
/**
 * Created by Admin on 31.03.2016.
 */
import java.awt.event.*;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Submenu extends JPanel{
    static int counter = 0;

    // Exit app
    static class exitApp implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    public void submenu(final JFrame frame){
        JMenuBar menuBar = new JMenuBar();

        // File Menu, F - Mnemonic
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);//open menu is by default: alt+f
        menuBar.add(fileMenu);

        // File->New, N - Mnemonic
        JMenuItem newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
        newMenuItem.addActionListener(new AbstractAction() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("new menu item changed" + counter);
                counter++;
            }
        });
        fileMenu.add(newMenuItem);
/*
        newMenuItem.addChangeListener(new AbstractAction("My Menu Item"){
            public void actionPerformed(ActionEvent e) {
                System.out.println("new menu item changed" + counter);
                counter++;
            }
        });*/

        // Edit->Options Submenu, O - Mnemonic, at.gif - Icon Image File
        JMenu findOptionsMenu = new JMenu("Options");
        Icon atIcon = new ImageIcon("at.gif");
        findOptionsMenu.setIcon(atIcon);
        findOptionsMenu.setMnemonic(KeyEvent.VK_O);
        fileMenu.add(findOptionsMenu);

        // ButtonGroup for radio buttons
        ButtonGroup directionGroup = new ButtonGroup();

        // Edit->Options->Forward, F - Mnemonic, in group
        JRadioButtonMenuItem forwardMenuItem = new JRadioButtonMenuItem("Forward", true);
        forwardMenuItem.setMnemonic(KeyEvent.VK_F);
        findOptionsMenu.add(forwardMenuItem);
        directionGroup.add(forwardMenuItem);

        // Edit->Options->Backward, B - Mnemonic, in group
        JRadioButtonMenuItem backwardMenuItem = new JRadioButtonMenuItem("Backward");
        backwardMenuItem.setMnemonic(KeyEvent.VK_B);
        findOptionsMenu.add(backwardMenuItem);
        directionGroup.add(backwardMenuItem);

        // File->New, Q - Mnemonic
        //JMenuItem quitMenuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        //quitMenuItem.addActionListener(new exitApp());
        /*quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitMenuItem.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });*/
        /*quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.
            }
        });*/

        //fileMenu.add(quitMenuItem);

        // File Menu, F - Mnemonic
        JMenu actionMenu = new JMenu("Action");
        actionMenu.setMnemonic(KeyEvent.VK_A);//open menu is by default: alt+a
        menuBar.add(actionMenu);

        // File->New, N - Mnemonic
        JMenuItem playMenuItem = new JMenuItem("Play", KeyEvent.VK_P);
        playMenuItem.addActionListener(new AbstractAction() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("play pressed" + counter);
                counter++;
            }
        });
        actionMenu.add(playMenuItem);


        frame.setJMenuBar(menuBar);
        frame.setSize(350, 250);
        frame.setVisible(true);

    }
    public static void main(final String args[]) {


    }
}