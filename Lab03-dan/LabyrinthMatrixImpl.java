package com.facultate;

import java.io.File;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Admin on 17.03.2016.
 */
public class LabyrinthMatrixImpl implements Labyrinth{
    private int[][] Labyrinth;
    private static final int size = 5;

    public int[][] getLabyrinth(){
        return Labyrinth;
    }
    public int getRowCount(){
        return Labyrinth.length;
    }
    public int getColumnCount(){
        return Labyrinth[0].length;
    }
    public boolean isFreeAt(int x, int y){
        if(Labyrinth[x][y] == 0) {
            return true;
        }
        else if(Labyrinth[x][y] == -1) {
            return true;
        }
        else if(Labyrinth[x][y] == 2) {
            return true;
        }
        return false;
    }
    public boolean isWallAt(int x, int y){
        return !isFreeAt(x,y);
    }

    public int[] searchForCellValue(int value){
        int rowCount = getRowCount();
        int colCount = getColumnCount();

        int returnCellLocationX;
        int returnCellLocationY;

        for(int i=0; i<rowCount; i++){
            for(int j=0; j<colCount; j++){
                if(Labyrinth[i][j] == value){
                    returnCellLocationX = i;
                    returnCellLocationY = j;
                    return new int[] {returnCellLocationX, returnCellLocationY};
                }
            }
        }
        return new int[0];//ermahgerd...
    }
    public int[] getStartCell(){
        return searchForCellValue(-1);
    }
    public int[] getFinishCell(){
        return searchForCellValue(2);
    }

    public void printRawArray(){
        for (int i=0; i<this.size; i++){
            for (int j=0; j<this.size; j++){
                System.out.print(Labyrinth[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    LabyrinthMatrixImpl(File file){
        Labyrinth = new int[size][size];

        Scanner input = null;
        try {

            input = new Scanner(file);
            while(input.hasNext())
            {
                for (int i=0; i<this.size; i++){
                    for (int j=0; j<this.size; j++){
                        Labyrinth[i][j]= input.nextInt();
                    }
                }
            }
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
