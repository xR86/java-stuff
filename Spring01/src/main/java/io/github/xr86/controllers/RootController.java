package io.github.xr86.controllers;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

//https://projects.spring.io/spring-boot/

@Controller
@EnableAutoConfiguration
public class RootController {

    /**
     * This is a method to display a html home for the root URL.
     * @return String Returns a string that is mapped automatically to the /public/index.html file.
     */
    @RequestMapping(value="/", method = RequestMethod.GET )
    public String startHtml(){
        return "index.html";
    }

    /**
     * This is a method to showcase returning a String to the user.
     * @return String Returns a hardcoded String.
     */
    @RequestMapping("/hello")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RootController.class, args);
    }
}