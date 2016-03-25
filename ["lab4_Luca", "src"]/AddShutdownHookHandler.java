/**
 * Created by shull on 3/25/2016.
 */
public class AddShutdownHookHandler extends Thread{
    @Override
    public void run()
    {
        System.out.println("Shutdown hook ran!");
    }
}
