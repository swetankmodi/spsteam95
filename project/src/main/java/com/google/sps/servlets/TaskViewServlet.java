package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.sps.data.Task;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

/** Servlet facilitating viewing task details. */
@WebServlet("/task")
public class TaskViewServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private PreparedQuery pq;
  private Query query;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    long taskId = getParameter(request, "taskId", -1L);
    
    if (taskId == -1)
      return;
    
    Entity entity;

    try {
      entity = datastore.get(KeyFactory.createKey("Task", taskId));
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    
    //get user id
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      return;
    }

    
    String userEmail = userService.getCurrentUser().getEmail();
    User user = User.getUserFromEmail(userEmail);
    long userId = user.getId();

    Task task = Task.getTaskFromDatastoreEntity(entity);
    List<Long> taskAssigneeList = new ArrayList<>();
    JsonObject taskData = new JsonObject();
    Gson gson = new Gson();
    
    /*
    If the current user is the creator of the task and the task is not already assigned, then send 
    list of assignees to client
    */
    boolean isCurrentUserAlreadyApplied = false;
    if(task.getCreatorId() == userId && !task.isAssigned()) {
      Filter taskIdFilter =
        new FilterPredicate("taskId", Query.FilterOperator.EQUAL, task.getId());
      Query taskQuery = new Query("TaskApplicants").setFilter(taskIdFilter);
      pq = datastore.prepare(taskQuery);
      for (Entity assigneeEntity : pq.asIterable()) {
        long applicantsId = Long.parseLong(assigneeEntity.getProperty("applicantId").toString());
        taskAssigneeList.add(applicantsId);
      }
    }

    /*
    To check if the current logged in User has already applied for the task
    */
    Filter taskIdFilter =
        new FilterPredicate("taskId", Query.FilterOperator.EQUAL, task.getId());
    Filter applicantFilter =
        new FilterPredicate("applicantId", Query.FilterOperator.EQUAL, userId);
    CompositeFilter taskApplicantFilter = CompositeFilterOperator.and(taskIdFilter, applicantFilter);
    Query taskQuery = new Query("TaskApplicants").setFilter(taskApplicantFilter);
    Entity applicantsEntity = datastore.prepare(taskQuery).asSingleEntity();
    isCurrentUserAlreadyApplied = applicantsEntity != null;

    taskData.add("task", gson.toJsonTree(task));
    taskData.add("taskAssigneeList", gson.toJsonTree(taskAssigneeList));
    taskData.addProperty("isCreator", task.getCreatorId() == userId);
    taskData.addProperty("isCurrentUserAlreadyApplied", isCurrentUserAlreadyApplied);
    taskData.addProperty("loggedInUserId", userId);
    taskData.addProperty("userLogoutUrl", userService.createLogoutURL("/"));
    response.setContentType("application/json;");
    response.getWriter().println(taskData);
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
  private Long getParameter(HttpServletRequest request, String name, Long defaultValue) {
    Long value = Long.parseLong(request.getParameter(name));
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
