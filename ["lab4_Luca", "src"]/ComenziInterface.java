/**
 * Interfata facuta pentru clasa comenzi
 */
public interface ComenziInterface {
    String getCale();
    void play(String mp3File);
    void ls();
    void list();
    void find(String cale, String criteriu) /*throws InterruptedException*/;
    void cd_out();
    void cd(String muta);
    void info(String mp3File);
}
