/**
 * Created by shully on 3/22/2016.
 *
 */


import java.io.*;

public class Main {




    public static void main(String[] args) {

        Comenzi comenzi = new Comenzi();
        String[] listaComenzi = {
                "quit",
                "ls",
                "list",
                "cd",
                "play",
                "find",
                "info",
                "cd.."
        };
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "~~Shell for MP3 player v.1.0~~\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        while (true) {

            System.out.print("~~ " + comenzi.getCale() + " ---> ");
            String comanda = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                comanda = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                if (comanda.equals(listaComenzi[0])) //quit
                    System.exit(0);
                else if (comanda.equals(listaComenzi[1])) { //ls
                    comenzi.ls();
                } else if (comanda.equals(listaComenzi[2])) { //list
                    comenzi.list();
                }
                else if (comanda.equals(listaComenzi[7])) { //cd..
                    comenzi.cd_out();
                }
                else {
                    //comanda+=" ";
                    if (!comanda.contains(" ")) throw new Eroare("Comanda invalida..");
                    String comandaCuFisier[] = comanda.split(" ", 2);

                    comanda = comandaCuFisier[0];
                    String fisier = comandaCuFisier[1];


                    if (comanda.equals(listaComenzi[3])) { //cd
                        comenzi.cd(fisier);
                    } else if (comanda.equals(listaComenzi[4])) { //play
                        comenzi.play(fisier);
                    } else if (comanda.equals(listaComenzi[5])) { //find
                        comenzi.find(comenzi.getCale(), fisier);
                        System.out.println("Am terminat cautarea..\n");
                    } else if (comanda.equals(listaComenzi[6])) { //info
                        comenzi.info(fisier);
                    } else {
                        throw new Eroare("Comanda nu exista..");
                    }
                }



            } catch (Eroare e) {
                System.err.println(e);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } //end while


    } //end main

}