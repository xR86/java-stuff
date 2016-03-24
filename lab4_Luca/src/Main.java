/**
 * Created by shull on 3/22/2016.
 */
import java.awt.Desktop;
import java.io.*;
import java.lang.RuntimeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.nio.file.*;


//apache tika libraries
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//is key pressed
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;



public class Main {

    public static void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                // up arrow
                break;
            case KeyEvent.VK_DOWN:
                // down arrow
                break;
            case KeyEvent.VK_RIGHT:
                // right arrow
                break;
            case KeyEvent.VK_LEFT:
                // left arrow
                break;
        }
    }


    public static void main(String[] args){
/*try {*/

       // ls(); //listeaza foldere/fisiere
       // list(); //listeaza numai fisiere mp3
       // find("E:\\PROG\\CRIT\\20-PAjava\\lab4_Luca","opeth"); //cauta recursiv in folder si subfoldere in metadate
       // play(); //play mp3-ul target
       // info(); //afiseaza metadate
       // cd("src"); //schimba directorul daca exista si afiseaza path-ul curent
       // cd("srcc"); //in caz ca nu exista ramane la directorul curent


/*
}catch(IOException e)
{
    e.printStackTrace();
}
*/


    } //end main

public static void play(){ //deschide fisierul din path cu playerul nativ
    String path = "E:\\PROG\\CRIT\\20-PAjava\\lab4_Luca\\example.mp3";
    File fisier = new File(path);
    try {

        if (!path.substring(path.length()-4,path.length()).equals(".mp3")){
            throw new Eroare("Fisierul nu are formatul corect (suporta numai .mp3 .wav .flac)");}
        else{
            Desktop.getDesktop().open(fisier);
        }
    }
    catch (RuntimeException e) {
        System.err.println("Fisierul nu a fost gasit... \nIncearca alt nume");
    }
    catch (Eroare e)
    {
        System.err.println(e);
    }
    catch (IOException e) {
        e.printStackTrace();
    }
}


    public static void ls(){ //printeaza toata lista de subdirectoare directe
        String cale = new String("E:\\PROG\\CRIT\\20-PAjava\\lab4_Luca");
        File folderCurent = new File(cale);
        String[] subdirectoare = folderCurent.list();

        System.out.println("Fisiere: ");
        for (String nume:subdirectoare)
        {

                System.out.println(nume);

        }
    }

    public static void list(){ //printeaza numa fisierele de muzica din directorul curent
        String cale = new String("E:\\PROG\\CRIT\\20-PAjava\\lab4_Luca");
        File folderCurent = new File(cale);
        String[] subdirectoare = folderCurent.list();

        System.out.println("Fisiere de muzica: ");
        for (String nume:subdirectoare)
        {
            if (nume.contains(".mp3") || nume.contains(".wav") || nume.contains(".flac"))
            {
                System.out.println(nume);
            }
        }
    }

    public static void find(String cale,String criteriu)
    {
        //String cale = new String("D:\\a muzica");
        File folderCurent = new File(cale);


        Pattern filePattern = Pattern.compile(criteriu.toUpperCase());

        String[] subdirectoare = folderCurent.list();
        for (String nume:subdirectoare) {
            if (new File(cale + "\\" + nume).isDirectory()) {
                //System.out.println(cale + "\\" + nume);
                find(cale + "\\" + nume, criteriu);
            } else if (nume.contains(".mp3")) {

                try {

                    InputStream input = new FileInputStream(new File(cale+"\\"+nume));
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);
                    input.close();


                    Matcher title = filePattern.matcher(metadata.get("title").toUpperCase());
                    Matcher artist = filePattern.matcher(metadata.get("xmpDM:artist").toUpperCase());
                    Matcher album = filePattern.matcher(metadata.get("xmpDM:album").toUpperCase());


                    if(title.find() || artist.find() || album.find())
                    {

                        System.out.println(cale+"\\"+nume);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (TikaException e) {
                    e.printStackTrace();
                }

            } //end if
        } //end for

    }

    public static void cd(String muta)
    {
        String cale = new String("E:\\PROG\\CRIT\\20-PAjava\\lab4_Luca");
        File folderCurent = new File(cale);
        String[] subdirectoare = folderCurent.list();

        for (String nume:subdirectoare)
        {
            if (new File(cale + "\\" + nume).isDirectory() && nume.equals(muta))
            {
                cale+="\\" + muta;
            }
        }

        System.out.println(cale);
    }

    public static void info()
    {
        String audioFileLoc = "E:\\PROG\\CRIT\\20-PAjava\\lab4_Luca\\example.mp3";

        try {

            InputStream input = new FileInputStream(new File(audioFileLoc));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

// List all metadata
  /*          String[] metadataNames = metadata.names();


            for(String name : metadataNames){
                System.out.println(name + ": " + metadata.get(name));
            }*/

// Retrieve the necessary info from metadata
// Names - title, xmpDM:artist etc. - mentioned below may differ based
// on the standard used for processing and storing standardized and/or
// proprietary information relating to the contents of a file.

            System.out.println("Title: " + metadata.get("title"));
            System.out.println("Artist: " + metadata.get("xmpDM:artist"));
            System.out.println("Album: " + metadata.get("xmpDM:album"));
            System.out.println("Release Date: " + metadata.get("xmpDM:releaseDate"));
            System.out.println("Genre: " + metadata.get("xmpDM:genre"));
            System.out.println("Format: " + metadata.get("xmpDM:audioCompressor"));
            System.out.println("Sample Rate: " + metadata.get("xmpDM:audioSampleRate"));
            System.out.println("Channels: " + metadata.get("channels"));

            System.out.println("Duration: " + Float.valueOf(metadata.get("xmpDM:duration"))/60000 + " minutes"); //metadata.get("xmpDM:duration")

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

}
