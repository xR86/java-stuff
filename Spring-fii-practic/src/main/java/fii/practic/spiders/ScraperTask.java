package fii.practic.spiders;

import fii.practic.spiders.vimeo.Vimeo;

import java.util.TimerTask;

/**
 * Created by xR86 on 11-Mar-17.
 */
public class ScraperTask extends TimerTask {

    public ScraperTask(){
        //Some stuff
    }

    @Override
    public void run() {
        System.out.println("\nTask is running - 30 seconds till next run");
        Vimeo vimeoObject = new Vimeo();
        vimeoObject.scrapeContent();

        System.out.println( "\tscraped content: " + vimeoObject.getVimeoScrapedContent() );
    }

}