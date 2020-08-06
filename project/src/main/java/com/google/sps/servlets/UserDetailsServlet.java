package com.google.sps.servlets;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.Filter;
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
import com.google.gson.Gson;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        JSONObject user_details = new JSONObject();

        UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();
        Query query = new Query("User");
        Key wantedKey = com.google.appengine.api.datastore.KeyFactory.createKey("User", email);
        Filter filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY,Query.FilterOperator.EQUAL,wantedKey );
        query.setFilter(filter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(query);
        Entity entity = new Entity("User");
        user_details.put("name", entity.getProperty("name"));
        user_details.put("email", entity.getProperty("email"));
        user_details.put("phone", entity.getProperty("phone"));

        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(user_details));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();
        String name= getParameter(request,"name","");
        String phone= getParameter(request,"phone","");

        Query query = new Query("User");
        Key wantedKey = com.google.appengine.api.datastore.KeyFactory.createKey("User", email);
        Filter filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY,Query.FilterOperator.EQUAL,wantedKey );
        query.setFilter(filter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(query);
        Entity entity = new Entity("User");
        entity.setProperty("name",name);
        entity.setProperty("phone",phone);
        entity.setProperty("email",email);
        datastore.put(entity);

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