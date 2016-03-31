package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Admin on 31.03.2016.
 */


public class MainGUI{
    static JFrame frame;

    public MainGUI() {
        //Submenu sub = new Submenu();


        frame = new JFrame("MenuSample Example");
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

        new Submenu().submenu(frame);
        //new FileTree().main("");

        //frame.add(sub);

    }

    public static void main(final String args[]){
        MainGUI smth = new MainGUI();

        Label yellowLabel = new Label("ops");
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);

    }
}
