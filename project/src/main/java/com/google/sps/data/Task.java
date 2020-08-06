package com.google.sps.data;

/** A Task. */
public final class Task {

/*id, title, details, creationTime, endDate, cost, createdBy, address, assignedTo, appliedUsersList, completionRating, active
Functions:
createTask(title, details, creationTime, endDate, cost)
addApplicant(id, appliedUsersList)
assignTaskToUser(id, assignedTo, active)
rateTaskAssignee(id, completionRating)
deactivateTask(id)
*/

  private final long id;
  private String title;
  private String details;

  /**
   * Parametrized constructor for a Task.
   *
   * @param id The unique id of the task assigned by the DataStore.
   * @param title The title string of the task.
   * @param id The detail string of the task.
   */
  public Task(long id, String title, String details) {
    this.id = id;
    this.title = title;
    this.details = details;
  }

  /** Returns the title. */
  public String getTitle() {
      return this.title;
  }

  /** Returns the details. */
  public String getDetails() {
      return this.details;
  }

}

