package com.google.sps.servlets;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;    

@WebServlet("/login-status")
public class LoginStatusServlet extends HttpServlet {

  /**
   * @return JSON response with the status of the current logged-in user and if the user details are complete.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    JSONObject status = new JSONObject();

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      status.put("loggedIn", true);
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/index.html";
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
      status.put("logoutUrl", logoutUrl);
      status.put("email", userEmail);

      Datastore datastore = DatastoreOptions.newBuilder().setProjectId("summer20-sps-95").build().getService();
      KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
      Key key = keyFactory.newKey(userEmail);
      Entity getEntity = datastore.get(key);

      //User with given email does not exist in Datastore, add a User with dummy user details to datastore
      if(getEntity == null){ 

        /*TODO : Set the attributes phone and rating as 
                  long and double data-type respectively
                  which for now is set to string
        */
        Entity userEntity = Entity.newBuilder(key)
                .set("name", "")
                .set("email", userEmail)
                .set("phone", "")
                .set("rating", "0.0")
                .build();
        datastore.put(userEntity);
        status.put("editDetails", true);
      }

      //If the current user has non-dummy details
      else if(getEntity.getString("name").length() > 0){
        status.put("editDetails", false);
      }

      //If the current user already exist but has dummy details
      else{
        status.put("editDetails", true);
      }
    }

    else {
      status.put("loggedIn", false);
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      status.put("loginUrl", loginUrl);
    }

    response.setContentType("application/json;");
    response.getWriter().println(status);
  }
}
