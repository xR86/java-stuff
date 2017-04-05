package fii.practic.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * REST API used for path
 */

//@RestController //will not resolve to index.html (will only return string)
@Controller
public class RootService {

    @RequestMapping(path="/", method = RequestMethod.GET )
    public String index(){
        return "index.html";
    }
    
}
