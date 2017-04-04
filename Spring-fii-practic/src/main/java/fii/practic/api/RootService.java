package fii.practic.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API used for path
 */

//@RestController //will not resolve index.html
@Controller
public class RootService {

    @RequestMapping(path="/", method = RequestMethod.GET )
    public String index(){
        return "index.html"; //index.html
    }
    
}
