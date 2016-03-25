import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.Desktop;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.*;

/**
 * Clasa de definire a comenzilor specifice unui shell.
 */
public class Comenzi implements ComenziInterface {

    /**
     * @param caleaCurenta reprezinta path-ul curent al utilizatorului
     */
    private String pathInitial;
    private String caleaCurenta;

Comenzi() {
    //pathInitial = "D:\\a muzica";

        pathInitial = new File(".").getAbsolutePath();
        pathInitial = pathInitial.substring(0,pathInitial.length()-2);
        caleaCurenta = pathInitial;

}



    /**
     * getter pentru path-ul current
     * @return string cu path-ul curent
     */
    public String getCale() {
        return caleaCurenta;
    }

    /**
     * Va face play la fisier cu playerul nativ al OS-ului
     * @param mp3File String cu numele fisierului mp3
     */
    public void play(String mp3File) { //deschide fisierul din path cu playerul nativ
        File fisier = new File(caleaCurenta + "\\" + mp3File);
        try {

            if (!mp3File.substring(mp3File.length() - 4, mp3File.length()).equals(".mp3")
                    && !mp3File.substring(mp3File.length() - 4, mp3File.length()).equals(".wav")
                    && !mp3File.substring(mp3File.length() - 5, mp3File.length()).equals(".flac")
                    ) {
                throw new Eroare("Fisierul nu are formatul corect (suporta numai .mp3 .wav .flac)");
            } else {
                Desktop.getDesktop().open(fisier);
            }
        } catch (RuntimeException e) {
            System.err.println("Fisierul nu a fost gasit...");
        } catch (Eroare e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println("Eroare IO..");
        }
    }

    /**
     * Listeaza tot din folderul curent
     * mai intai va lista foldere din acel folder ele fiind reprezentate cu un [X] langa nume
     * apoi va lista fisierele care nu sunt directoare
     */
    public void ls() { //printeaza toata lista de subdirectoare directe
        File folderCurent = new File(caleaCurenta);
        String[] subdirectoare = folderCurent.list();
        List < String > fisiere = new ArrayList < > ();

        for (String subdirector: subdirectoare) {
            if (new File(caleaCurenta + "\\" + subdirector).isDirectory())
                System.out.println("[X] - " + subdirector);
            else {
                fisiere.add(subdirector);
            }
        }

        if (!fisiere.isEmpty()) {
            for (int i = 0; i < fisiere.size(); i++) {
                System.out.println(fisiere.get(i));
            }
        }
        System.out.print("\n");
    }

    /**
     * Listeaza numai fisiere de muzica acceptate
     */
    public void list() { //printeaza numa fisierele de muzica din directorul curent
        File folderCurent = new File(caleaCurenta);
        String[] subdirectoare = folderCurent.list();

        for (String nume: subdirectoare) {
            if (nume.contains(".mp3") || nume.contains(".wav") || nume.contains(".flac")) {
                System.out.println(nume);
            }
        }
        System.out.print("\n");
    }

    /**
     * Cauta recursiv in metadatele fiecarui fisier audio intalnit in folderul curent si in subfoldere
     * Daca a gasit ceva atunci calea fisierului candidat va fi afisata
     * @param cale reprezinta calea curenta a utilizatorului. Este dat in parametru pentru a putea apela recursiv functia
     * @param criteriu ce cuvant va cauta in metadatele Titlu/Artist/Album
     */



    public void find(String cale, String criteriu)/* throws InterruptedException */{
        //AddShutdownHookHandler hookHandler = new AddShutdownHookHandler();
       // Runtime.getRuntime().addShutdownHook(hookHandler);

        File folderCurent = new File(cale);


        Pattern filePattern = Pattern.compile(criteriu.toUpperCase());

        String[] subdirectoare = folderCurent.list();
        for (String nume: subdirectoare) {
            if (new File(cale + "\\" + nume).isDirectory()) {
                find(cale + "\\" + nume, criteriu);
            } else if (nume.contains(".mp3") || nume.contains(".wav") || nume.contains(".flac")) {

                try {

                    InputStream input = new FileInputStream(new File(cale + "\\" + nume));
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);
                    input.close();


                    Matcher title = filePattern.matcher(metadata.get("title").toUpperCase());
                    Matcher artist = filePattern.matcher(metadata.get("xmpDM:artist").toUpperCase());
                    Matcher album = filePattern.matcher(metadata.get("xmpDM:album").toUpperCase());


                    if (title.find() || artist.find() || album.find()) {

                        System.out.println(cale + "\\" + nume);
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Fisierul nu a fost gasit...");
                } catch (IOException e) {
                    System.err.println("Eroare IO");
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (TikaException e) {
                    e.printStackTrace();
                }

            } //end if
        } //end for
       // Runtime.getRuntime().removeShutdownHook(hookHandler);
    }

    /**
     * Metoda de iesire din folderul curent in parintele sau
     */
    public void cd_out()
    {
        try {
            if (caleaCurenta.equals(pathInitial))
                throw new Eroare ("Va aflati in root..");
            caleaCurenta = caleaCurenta.substring(0, caleaCurenta.lastIndexOf("\\"));
        } catch(Eroare e)
        {
           System.err.println(e);
        }
    }

    /**
     * 'change directory' simplist. Verifica numele introdus daca este folder in locatia curenta.
     * Daca este atunci path-ul curent va fi schimbat
     * @param muta numele folderului in care se muta path-ul curent
     */
    public void cd(String muta) {
        try {
            File folderCurent = new File(caleaCurenta);
            String[] subdirectoare = folderCurent.list();
            boolean amGasitDirector = false;
            for (String subdirector: subdirectoare) {
                if (new File(caleaCurenta + "\\" + subdirector).isDirectory() && subdirector.toUpperCase().equals(muta.toUpperCase())) {
                    caleaCurenta += "\\" + subdirector;
                    amGasitDirector = true;
                }
            }

            System.out.print("\n");

            if (muta.equals(""))
                throw new Eroare("Dati numele unui folder pe langa comanda.. (cd nume_folder)");
            else if (!amGasitDirector)
                throw new Eroare("Folder inexistent");

        } catch (Eroare e) {
            System.err.println(e);
        }

    }

    /**
     * Afiseaza metadatele unui fisier audio acceptat
     * @param mp3File numele fisierului audio
     */
    public void info(String mp3File) {
        String audioFileLoc = caleaCurenta + "\\" + mp3File;

        try {
            if (!mp3File.contains(".mp3") && !mp3File.contains(".wav") && !mp3File.contains(".flac"))
            {
                throw new Eroare ("Tip-ul fisierului nu este suportat\n(Tipurile acceptate sunt mp3/wav/flac)");
            }
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

            System.out.println("Duration: " + Float.valueOf(metadata.get("xmpDM:duration")) / 60000 + " minutes");

            System.out.print("\n");

        } catch (FileNotFoundException e) {
            System.err.println("Fisierul nu a fost gasit...");
        } catch (IOException e) {
            System.err.println("Eroare IO");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (Eroare e) {
            System.err.println(e);
        }
    }
}