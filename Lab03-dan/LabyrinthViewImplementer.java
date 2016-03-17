package com.facultate;

import com.facultate.LabyrinthMatrixImpl;

/**
 * Created by Admin on 17.03.2016.
 */
public class LabyrinthViewImplementer implements LabyrinthView{
    private StringBuilder[] LabyrinthViewMatrix;
    private static final int size = 5;

    public void getLabyrinth() {
        for(int i=0; i<this.size; i++){
            for(int j=0; j<LabyrinthViewMatrix.length; j++){
                System.out.print(LabyrinthViewMatrix[i].charAt(j));
            }
            System.out.println("");
        }

    }

    public void setLabyrinth(LabyrinthMatrixImpl model) {
        int[][] copy = model.getLabyrinth();
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if(LabyrinthViewMatrix[i] == null){
                    LabyrinthViewMatrix[i].append(' ');
                }
                LabyrinthViewMatrix[i].append('|');
                System.out.print(LabyrinthViewMatrix[i]);
                char temp = 'x'; //x - caz invalid

                if(copy[i][j] == -1){
                    temp = 'S';
                } else if (copy[i][j] == 1){
                    temp = '*';
                } else if (copy[i][j] == 0){
                    temp = ' ';
                } else if (copy[i][j] == 2){
                    temp = 'F';
                }
                System.out.print(temp);
                System.out.print(" ");

                LabyrinthViewMatrix[i].append(temp);
            }
            LabyrinthViewMatrix[i].append('|');
        }
    }


    @Override
    public String toString() {
        return super.toString();
    }

    LabyrinthViewImplementer(){
        LabyrinthViewMatrix = new StringBuilder[size];


    }

}
