/**
 * Created by Admin on 31.03.2016.
 */
import java.awt.event.*;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Submenu  {
    static int counter = 0;

    // Exit app
    static class exitApp implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    public static void main(final String args[]) {
        JFrame frame = new JFrame("MenuSample Example");
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

        // File->New, N - Mnemonic
        JMenuItem quitMenuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
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

        fileMenu.add(quitMenuItem);


        frame.setJMenuBar(menuBar);
        frame.setSize(350, 250);
        frame.setVisible(true);
    }
}