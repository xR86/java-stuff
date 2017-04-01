package fii.practic.spiders.vimeo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fii.practic.commons.Spider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import fii.practic.commons.OutputHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import static fii.practic.commons.OutputHelpers.getUpToNCharacters;

import java.io.IOException;

/**
 * Created by xR86 on 11-Mar-17.
 */
public class Vimeo implements Spider{
    //Private instance variables
    private JsonNode vimeoScrapedContent;
    private int timeout;

    //const
    public static final Logger log = LoggerFactory.getLogger(Vimeo.class);
    public static final String BASE_URL = "http://vimeo.com";

    public Vimeo() {
        vimeoScrapedContent = null;
        timeout = 6000;
    }

    @Override
    public String getName() {
        return "Vimeo scraper";
    }

    @Override
    public void start() throws IOException {

    }

    //Set & Get
    public JsonNode getVimeoScrapedContent(){
        return this.vimeoScrapedContent;
    }

    //Utility functions
    private Document getSite(String URL){
        Document vimeoDocument = null;
        OutputHelpers ot = new OutputHelpers();
        try {
            vimeoDocument = Jsoup.connect(URL).timeout(this.timeout).get();
            //System.out.println( ot.getUpToNCharacters(vimeoDocument.toString(), 100) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vimeoDocument;
    }

    public void scrapeContent(){
        //OutputHelpers ot = new OutputHelpers();
        //Elements vimeoElements = vimeoDocument.select("vimeo.explore_data");
        //System.out.println(vimeoElements);

        String rawPage = getSite("https://vimeo.com/").body().toString();

        String queryStart = "vimeo.explore_data = "; //start of js variable
        String queryEnd   = "\"explore\"}";          //end of js variable
        int startIndex = rawPage.indexOf(queryStart) + queryStart.length();
        int endIndex   = rawPage.indexOf(queryEnd) + queryEnd.length();

        String vimeoJsonString = rawPage.substring(startIndex, endIndex);
        //System.out.println(vimeoJsonString);

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.vimeoScrapedContent = mapper.readTree(vimeoJsonString);
            //System.out.println( ot.getUpToNCharacters(vimeoJson.toString(), 100) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
