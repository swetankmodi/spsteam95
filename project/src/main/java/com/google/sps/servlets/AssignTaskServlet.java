package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Task;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.Enumeration;
/** Servlet facilitating assign task. */
@WebServlet("/task/assign")
public class AssignTaskServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println(request.getMethod());
    Enumeration<String> params = request.getParameterNames(); 
    while(params.hasMoreElements()){
      String paramName = params.nextElement();
      System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
    }
    long taskId = getParameter(request, "taskId", -1);
    
    if (taskId == -1)
      return;

    long assigneeId = getParameter(request, "assigneeId", -1);
    System.out.println("Hola" +" "+taskId + " " +assigneeId);
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      return;
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity taskEntity;
    try{
      taskEntity = datastore.get(KeyFactory.createKey("Task", taskId));
    }catch (Exception e) {
      System.out.println(e);
      return;
    }
    
    Task task = Task.getTaskFromDatastoreEntity(taskEntity);
    task.setAssigneeId(assigneeId);
    taskEntity.setProperty("assigneeId", assigneeId);
    taskEntity.setProperty("assigned", true);

    datastore.put(taskEntity);
    /*TODO:
        response to be redirected to the task_view page*/
    
    response.sendRedirect("/task_view.html?taskId=" + String.valueOf(taskId));
  }

  private long getParameter(HttpServletRequest request, String name, long defaultValue) {
    Long value = Long.parseLong(request.getParameter(name));
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}