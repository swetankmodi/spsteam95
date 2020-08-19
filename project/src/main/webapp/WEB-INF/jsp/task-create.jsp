<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>Task | Create</title>
    <link rel="stylesheet" href="/style.css">
    <script src="/task_create_script.js"></script>
  </head>

  <body>
    <h1>Create Task</h1>

    <form action="/task/create" method="POST" id="taskCreateForm">
      <label for="taskTitle">Title:</label>
      <input type="text" name="title" id="taskTitle" placeholder="Enter title" required>
      <p></p>
      <label for="taskDetails">Details:</label>
      <input type="text" name="details" id="taskDetails" placeholder="Enter Details">
      <p></p>
      <label for="taskCompensation">Compensation:</label>
      <input type="number" name="compensation" id="taskCompensation"
          placeholder="Enter Compensation" required>
      <p></p>
      <label for="taskAddress">Address:</label>
      <input type="text" name="address" id="taskAddress" placeholder="Enter Address">
      <p></p>
      <label for="taskDeadline">Deadline:</label>
      <input type="datetime-local" name="deadline" id="taskDeadline" required>
      <p></p>
      <input type="submit" name="submit" value="Create">
    </form>

  </body>

</html>
