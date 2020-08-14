package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;
import java.util.List;

/** A Task. */
public final class Task {

  /*
   * TODO:
   * - add functions:
   *   - addApplicant(id, appliedUsersList)
   *   - assignTaskToUser(id, assignedTo, active)
   *   - rateTaskAssignee(id, completionRating)
   */

  private final long id;
  private String title;
  private String details;
  private final long creationTime;
  private long compensation;
  private final long creatorId;
  private long deadline;
  private String address;     // TODO: change into suitable address class.
  private boolean assigned;
  private long assigneeId;
  private float completionRating;
  private boolean active;
  private List<String> taskAssigneeList;

  /**
   * Parametrized constructor for a Task that sets creationTime to current Server time.
   * Since this sets some parameters with set defaults, this is to be used for task creation.
   *
   * @param id The unique id of the task assigned by the DataStore.
   * @param title The title string of the task.
   * @param details The detail string of the task.
   * @param compensation The compensation for the task.
   * @param creatorId User Id if the task creator.
   * @param deadline The deadline expressed in Unix Epoch Time.
   * @param address The address of the task.
   */
  public Task(long id, String title, String details, long compensation, long creatorId,
      long deadline, String address) {
    this.id = id;
    this.title = title;
    this.details = details;
    this.creationTime = System.currentTimeMillis();
    this.compensation = compensation;
    this.creatorId = creatorId;
    this.deadline = deadline;
    this.address = address;
    this.assigned = false;
    this.assigneeId = -1;
    this.completionRating = 0;
    this.active = true;
  }

  /**
   * Parametrized constructor for a Task.
   *
   * @param id The unique id of the task assigned by the DataStore.
   * @param title The title string of the task.
   * @param details The detail string of the task.
   * @param creationTime The creation time of the task.
   * @param compensation The compensation for the task.
   * @param creatorId User Id if the task creator.
   * @param deadline The deadline expressed in Unix Epoch Time.
   * @param address The address of the task.
   * @param assigned Boolean: true if it is assigned, else false.
   * @param assigneeId User Id of assignee.
   * @param completionRating The rating of the task after completion.
   * @param active Boolean: true if it is active, else false.
   */
  public Task(long id, String title, String details, long creationTime, long compensation,
      long creatorId, long deadline, String address, boolean assigned, long assigneeId,
      float completionRating, boolean active) {
    this.id = id;
    this.title = title;
    this.details = details;
    this.creationTime = creationTime;
    this.compensation = compensation;
    this.creatorId = creatorId;
    this.deadline = deadline;
    this.address = address;
    this.assigned = assigned;
    this.assigneeId = assigneeId;
    this.completionRating = completionRating;
    this.active = active;
  }

  /**
   * Static function to extract a Task object from a Task Entity.
   *
   * @return If the Entity is valid, the task object. Else, null.
   * @param entity Task Entity.
   */
  public static Task getTaskFromDatastoreEntity(Entity entity) {
    if (!entity.getKind().equals("Task")) {
      return null;
    }

    long id = entity.getKey().getId();
    String title = (String) entity.getProperty("title");
    String details = (String) entity.getProperty("details");
    long creationTime = (long) entity.getProperty("creationTime");
    long compensation = (long) entity.getProperty("compensation");
    long creatorId = (long) entity.getProperty("creatorId");
    long deadline = (long) entity.getProperty("deadline");
    String address = (String) entity.getProperty("address");
    boolean assigned = (boolean) entity.getProperty("assigned");
    long assigneeId = (long) entity.getProperty("assigneeId");
    float completionRating = ((Double) entity.getProperty("completionRating")).floatValue();
    boolean active = (boolean) entity.getProperty("active");

    Task task = new Task(id, title, details, creationTime, compensation, creatorId, deadline,
        address, assigned, assigneeId, completionRating, active);

    return task;
  }

  /** Returns the ID. */
  public long getId() {
    return this.id;
  }

  /** Returns the title. */
  public String getTitle() {
    return this.title;
  }

  /** Returns the task details. */
  public String getDetails() {
    return this.details;
  }

  /**
   * Returns the creation time as the number of milliseconds from Unix Epoch.
   * Unix Epoch: 00:00:00 UTC, 1st January 1970
   */
  public long getCreationTime() {
    return this.creationTime;
  }

  /** Returns the compensation. */
  public long getCompensation() {
      return this.compensation;
  }

  /** Returns the creator's User ID. */
  public long getCreatorId() {
    return this.creatorId;
  }

  /**
   * Returns the deadline as the number of milliseconds from Unix Epoch.
   * Unix Epoch: 00:00:00 UTC, 1st January 1970
   */
  public long getDeadlineAsLong() {
    return this.deadline;
  }

  /** Returns the Address. */
  public String getAddress() {
    return this.address;
  }

  /** Returns the Task Assignee's User ID. */
  public long getAssigneeId() {
    return this.assigneeId;
  }

  /** Returns the Task Completion Rating. */
  public float getCompletionRating() {
    return this.completionRating;
  }

  /** Returns true if the task is active currently, else returns false. */
  public boolean isActive() {
    return this.active;
  }

  /** Returns true if the task is assigned to a user, else returns false. */
  public boolean isAssigned() {
    return this.assigned;
  }

  /** Sets the title. */
  public void setTitle(String title) {
    this.title = title;
  }

  /** Sets the task details. */
  public void setDetails(String details) {
    this.details = details;
  }

  /** Sets the compensation. */
  public void setCompensation(long compensation) {
    this.compensation = compensation;
  }

  /** Sets the deadline. */
  public void setDeadline(long deadline) {
    this.deadline = deadline;
  }

  /** Sets the Address. */
  public void setAddress(String address) {
    this.address = address;
  }

  /** Sets the Task Completion Rating. */
  public void setCompletionRating(float completionRating) {
    this.completionRating = completionRating;
  }

  public void setTaskAssigneeList(List<String> taskAssigneeList){
    this.taskAssigneeList = taskAssigneeList;
  }


  /** Activates the task. */
  public void activate() {
      this.active = true;
  }

  /** Deactivates the task. */
  public void deactivate() {
      this.active = false;
  }

  /** Check if user is the task creater. */
  public boolean isCreator() {
      return this.getId()==this.getCreatorId();
  }

}

