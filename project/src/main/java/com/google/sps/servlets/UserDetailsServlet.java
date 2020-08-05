package com.google.sps.servlets;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Datastore;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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
import com.google.cloud.datastore.Entity.Builder;

@WebServlet("/user")
public class UserDetailsServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();
        String name= getParameter(request,"name","");
        String phone= getParameter(request,"phone","");

        //yet to resolve
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = datastore.getKey(email);
        Entity user = new Entity();
        user = key.get();
        user.name = name;
        user.phone = phone;

        response.sendRedirect("/index.html");
    }
    public String getParameter(HttpServletRequest request, String text, String defaultValue)
    {
        String value= request.getParameter(text);
        if(value=="")
        {
            return defaultValue;
        }
        return value;
    }
}