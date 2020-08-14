package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/** Servlet facilitating viewing task details. */
@WebServlet("/task")
public class TaskViewServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    long taskId = Long.parseLong(getParameter(request, "taskId", "-1"));
    
    if (taskId == -1)
        return;
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity entity;

    try {
        entity = datastore.get(KeyFactory.createKey("Task", taskId));
    } catch (Exception e) {
        System.out.println(e);
        return;
    }

    Task task = Task.getTaskFromDatastoreEntity(entity);
    List<String> taskAssigneeList = new ArrayList<String>();

    if(task.isCreator()) {
        Filter taskIdFilter = new FilterPredicate("taskId", Query.FilterOperator.EQUAL, task.getId());
        Query query=new Query("TaskApplicants");

        for (Entity assigneeEntity : datastore.prepare(query).asIterable()) {
            taskAssigneeList.add((String) assigneeEntity.getProperty("applicantId"));
            taskAssigneeList.add("Heya");
        }
    }
    task.setTaskAssigneeList(taskAssigneeList);
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(task));
  }

  /**
   * If present, get the request parameter identified by name, else return defaultValue.
   *
   * @param request The HTTP Servlet Request.
   * @param name The name of the rquest parameter.
   * @param defaultValue The default value to be returned if required parameter is unspecified.
   * @return The request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
