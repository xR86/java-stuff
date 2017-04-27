package fii.practic.spiders.youtube;
import fii.practic.commons.Spider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;

/**
 * Youtube scrapper implementation.
 */
public class Youtube  implements Spider {

    private static final Logger log = LoggerFactory.getLogger(Youtube.class);

    public static final String BASE_URL = "https://www.youtube.com/channels";

    //https://www.youtube.com/channels
    //first: ->  a.category-title-link
    //first: https://www.youtube.com/channels/music -> . yt-uix-sessionlink
    //first: https://www.youtube.com/user/KatyPerryMusic/videos

    @Override
    public String getName() {
        return "Youtube";
    }

    @Override
    public void start() throws Exception {
        log.debug("[STARTED] " + this.getName());

        File input = new File("input.html");
        //String input = null;
        Document doc = Jsoup.parse(input, "UTF-8", BASE_URL);

        //<a href="/channels/music" class="category-title-link"><span class="category-title">
        Elements categoryLinks = doc.select("a.category-title-link"); // duplicates
        String relHref = categoryLinks.get(0).attr("href").substring(10);
        log.debug("element found" + relHref);

        PrintWriter writer = new PrintWriter(input);
        writer.print("");
        writer.close();


        //first: https://www.youtube.com/channels/music -> . yt-uix-sessionlink
        //first: https://www.youtube.com/user/KatyPerryMusic/videos

        Document doc2 =  Jsoup.parse(input, "UTF-8", BASE_URL + relHref);
        Elements links = doc.select(".yt-uix-sessionlink").get(); // duplicates
        //String relHref = links.get(0).attr("href").substring(10);


    }
}
