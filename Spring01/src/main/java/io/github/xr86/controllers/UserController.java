package io.github.xr86.controllers;

import org.json.JSONException;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller for the /users endpoint
 * Exposes two methods: retrieveUsers (GET), addUser (POST)
 */
@Controller
@EnableAutoConfiguration
public class UserController {
    private static final String RESP_SUCCESS = "{\"result\" : \"success\", \"id\" : \"id\"}";
    private static final String RESP_ERROR = "{\"result\" : \"failed\", \"error\" : {\"code\": 500, \"message\": \"Failed to update users.\"}}";


    /**
     * This is the method used for retrieving the users through GET.
     * Opens the file with the users, parses it and returns it.
     * @return String This returns the JSON array of users.
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces="application/json" )
    @ResponseBody
    public String retrieveUsers(){
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(new FileReader("users.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONArray users = null;
        try {
            users = new JSONArray(tokener);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users.toString();
    }

    /**
     * This is the method used for adding a new user to the file (POST).
     * Opens the file,  parses it, converts the request body to json, adds it to the parsed array and saves the array to file.
     * @param userData The json with the data for the new user (no constraints / special exceptions for invalid input yet)
     * @return RESP_SUCCESS Hardcoded http json response for the user.
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces="application/json" )
    @ResponseBody
    public String addUser(@RequestBody String userData) {
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(new FileReader("users.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONArray users = null;
        try {
            users = new JSONArray(tokener);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject newUser = null;
        try {
            newUser = new JSONObject(userData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //newUser.put("id", "1");

        users.put(newUser);

        FileWriter output = null;
        try {
            output = new FileWriter("users.json");
            output.write(users.toString());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RESP_SUCCESS;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RootController.class, args);
    }
}
