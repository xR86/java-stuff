
public class Main {

    //constants
    private static final int NRALFABGR = 25; //constanta asa ? (inlocuieste #define sau const din C++)
    private static final int NRALFABLAT = 26;

    //alphabets
    private static String[] greek = new String[NRALFABGR];//cand folosesc String, cand folosesc char ...
    private static String[] latin = new String[NRALFABLAT];


    public static void main(String[] args) {

        int n;

        if (args.length >= 7){
            n = Integer.parseInt(args[0]);

            for(int i=0;i<n;i++) {
                latin[i] = args[i+1];
            }
           
            for(int i=0;i<n;i++) {
                greek[i] = args[i+n+1].toString();//StringEscapeUtils.unescapeJava(args[4]);
            }


        } else {
            //if not, generate random n [0-25 ?]
            n = (int) (Math.random() * (NRALFABGR + 1)) - 1;//NRALFABGR for size

            //generate all greek letters
            for (int i = 0; i < greek.length; i++) {
                //este inclus de doua ori tau - are doua forme diferite ?
                greek[i] = Character.toString((char) (i + '\u03B1'));/*"\u2202";*/
                System.out.println(i + " , " + greek[i]);
            }
            //generate all latin letters
            for (int i = 0; i < latin.length; i++) {
                //este inclus de doua ori tau - are doua forme diferite ?
                latin[i] = Character.toString((char) (i + '\u0041'));/*"\u2202";*/
                System.out.println(i + " , " + latin[i]);
            }
        }




        /*
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int latinNr = (int) (Math.random() * (NRALFABLAT + 1)) - 1;
                int greekNr = (int) (Math.random() * (NRALFABGR + 1)) - 1;

                System.out.print(latin[latinNr] + greek[greekNr] + " | ");
            }
            System.out.println(" ");
        }
        */
	/*********************/
	System.out.println(" ");
	
	System.out.println("n=" + n);
	for (int i = 0; i < n*4; i++) {
            System.out.print("_");
        }
	
	String matrix[][];	

	for(int i=0;i<n;i++){
		System.out.println(" ");
		for(int j=0;j<n;j++){
			//matrix[i][j] = latin[j] + greek[j];
			//if(matrix[]
			int wildCard = (int) (Math.random() * (n + 1)) - 1;
			int temp = j+wildCard;

			if(temp >= n){
				temp = temp - n;			
			}
			System.out.print("| " + latin[temp] + greek[temp]);
			//sau cu j
		}
		System.out.print(" | ");
	}

        System.out.println(" ");
        for (int i = 0; i < 3*4; i++) {
            System.out.print("_");
        }

	/********************/
        /*hardcoded square*********************************************/
        System.out.println(" ");

        for (int i = 0; i < 3*4; i++) {
            System.out.print("_");
        }
        /*line 1*/
        /*
        StringBuilder line1 = new StringBuilder();
        line1.append("| ").append(latin[0]).append(greek[0]);
        */
        System.out.println(" ");
        System.out.print("| " + latin[0] + greek[0]);
        System.out.print(" | " + latin[1] + greek[2]);
        System.out.print(" | " + latin[2] + greek[1]);
        System.out.print(" | ");
        /*line 2*/
        System.out.println(" ");
        System.out.print("| " + latin[1] + greek[1]);
        System.out.print(" | " + latin[2] + greek[0]);
        System.out.print(" | " + latin[0] + greek[2]);
        System.out.print(" | ");
        /*line 3*/
        System.out.println(" ");
        System.out.print("| " + latin[2] + greek[2]);
        System.out.print(" | " + latin[0] + greek[1]);
        System.out.print(" | " + latin[1] + greek[0]);
        System.out.print(" | ");

        System.out.println(" ");
        for (int i = 0; i < 3*4; i++) {
            System.out.print("_");
        }

	/*end-hardcoded square*********************************************/


        System.out.println(" ");System.out.println(" ");

    }

}
