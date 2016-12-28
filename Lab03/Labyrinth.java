/**
 * Created by Admin on 17.03.2016.
 */
public interface Labyrinth {

    public int getRowCount();
    public int getColumnCount();
    public boolean isFreeAt(int x, int y);
    public boolean isWallAt(int x, int y);
    public int[] searchForCellValue(int value);
    public int[] getStartCell();
    public int[] getFinishCell();
}
