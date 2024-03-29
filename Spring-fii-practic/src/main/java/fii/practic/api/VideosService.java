package fii.practic.api;

import fii.practic.domain.control.VideoControl;
import fii.practic.domain.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * REST API used for retrieving videos.
 */
@RestController
public class VideosService {
    private final VideoControl videoControl;

    @Autowired
    public VideosService(VideoControl videoControl) {
        this.videoControl = videoControl;
    }

    @RequestMapping(path = "/videos",method = RequestMethod.GET)
    public List<Video> list(@RequestParam(value = "name", defaultValue = "") String name) {
        System.out.println(escapeJava(name));
        System.out.println(escapeHtml(name));

        //TODO: code still vulnerable to backslash injection (other cases such as " are covered by escapeJava)
        return this.videoControl.findByName(escapeJava(name));
    }
}
