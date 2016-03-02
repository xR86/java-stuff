/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab12;





/**
 *
 * @author shull
 */
public class Lab12 {

    
    public static void main(String[] args) {
        int n;

        if (args.length < 1) //n-avem parametrii --> facem un n random
            n=(int)(Math.random() * (6-3+1) + 3); //range=3..6
                                                  //deci max-min+1
                                                  //+ pt nr de start
        else
            n = Integer.parseInt(args[0]);  //luam n-ul din parametrii 
        
        
    char[][][] patrat = new char[n][n][2];
    char[] latin = new char[n]; //tabelul propriu zis de caractere latine
    char[] greek = new char[n]; //tabelul propriu zis de caractere grecesti
    
    
    
       if (args.length<1) //cazul in care n-avem parametrii--> populam cu latine/grecesti
       {
           for (int i=0;i<n;i++) //introducem primele n litere din alfabetele latin/grecesc
            {
                latin[i]=(char)('a'+i);
                greek[i]=(char)('\u03B1'+i);
            }
       }
    
       
        if (args.length>1){ //daca avem n din parametrii
                            //atunci populam cu argumentele din parametrii
            if (args.length != n*2+1)
            {
                System.out.println("Not the right nb of arguments!");
                System.exit(-1);
            }

            for (int i=0;i<n;i++) //introducem primele n litere din alfabetele latin/grecesc
            {
                latin[i]=args[1+i].charAt(0);
                greek[i]=args[n+1+i].charAt(0);
            }
        }
        else if (args.length==1)  //daca n-ul este dat dar nu si cu ce populam
        {                         //atunci
            for (int i=0;i<n;i++) //introducem primele n litere din alfabetele latin/grecesc
            {
                latin[i]=(char)('a'+i);
                greek[i]=(char)('\u03B1'+i);
            }
        }
       
       
    populare(patrat,n,latin,greek);   
       
       
       
     printare(patrat,n);  //printam matricea
       
       
    }
    
    public static void populare (char[][][] patrat, int n, char latin[],char greek[])
    {
        for (int first_line=0;first_line<n;first_line++)
        {
            patrat[0][first_line][0]=latin[first_line];
            patrat[0][first_line][1]=greek[first_line];
        }
        
        for (int col=1;col<n;col++)
           for (int row=0;row<n;row++)
           {
               if (row+1<n)
                   patrat[col][row][0]=patrat[col-1][row+1][0];
               else
                   patrat[col][row][0]=patrat[col-1][0][0];
               
               if (row-1>=0)
                   patrat[col][row][1]=patrat[col-1][row-1][1];
               else
                   patrat[col][row][1]=patrat[col-1][n-1][1];
           }
            
    }
    
    public static void printare (char[][][] patrat, int n)
    {
       for (int i=0;i<n;i++)
       {
            for (int j=0;j<n;j++)
            {
                System.out.print(patrat[i][j][0]);
                System.out.print(patrat[i][j][1]);
                System.out.print(" ");
            } //am printat o celula latin/grecesc
        System.out.print("\n"); 
       } //am printat un rand
    }
    
    
    public String[] generate(int n, char[] alphabet) {
 String[] words = new String[n];
 for (int i = 0; i < n; i++) {
 StringBuilder sb = new StringBuilder();
 while (true) {
 int pos = (int) (Math.random() * (alphabet.length + 1)) - 1;
 if (pos < 0) break;
 sb.append(alphabet[pos]);
 }
 words[i] = sb.toString();
 }
 return words;
 }

}
