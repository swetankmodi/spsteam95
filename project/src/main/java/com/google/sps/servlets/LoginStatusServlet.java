package com.google.sps.servlets;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.Address;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;    
import java.util.List;
import java.util.ArrayList;

@WebServlet("/login-status")
public class LoginStatusServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject status = new JSONObject();

        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn()) {
            status.put("logged_in", true);
            String userEmail = userService.getCurrentUser().getEmail();
            String urlToRedirectToAfterUserLogsOut = "/index.html";
            String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            status.put("logoutUrl", logoutUrl);
            status.put("email", userEmail);

            Datastore datastore = DatastoreOptions.newBuilder().setProjectId("summer20-sps-95").build().getService();
            KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
            Key key = keyFactory.newKey(userEmail);
            Entity getEntity = datastore.get(key);
            
            if(getEntity == null){ //User with given email does not exist in Datastore, add a User with empty details to datastore
                Address currAddress = new Address("", "", "", "", "", 0);
                Entity userEntity = Entity.newBuilder(key)
                            .set("name", "")
                            .set("email", userEmail)
                            .set("phone", 0L)
                            .set("rating", 0.0)
                                        .build();
                datastore.put(userEntity);
            }
        } 
        else {
            status.put("logged_in", false);
            String urlToRedirectToAfterUserLogsIn = "/index.html";
            String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
            status.put("loginUrl", loginUrl);
        }

        response.setContentType("application/json;");
        response.getWriter().println(status);
    }
}