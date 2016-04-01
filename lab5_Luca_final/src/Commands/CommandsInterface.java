package Commands;

/**
 * Interfata facuta pentru clasa comenzi
 */
public interface CommandsInterface {
    String getCale();
    void play(String mp3File);
    void ls();
    void list(String currentLocation);
    void find(String cale, String criteriu) /*throws InterruptedException*/;
    void cd_out();
    void cd(String muta);
    String info(String mp3File);
}
