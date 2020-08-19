<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>Task | Create</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/master.css">
    <link rel="stylesheet" href="/static/css/task-create.css">
  </head>

  <body>

    <%-- Navbar Begins --%>
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
      <a class="navbar-brand" href="/">Door2Door</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="/">Home</a>
          </li>

          <li class="nav-item">
            <a class="nav-link" href="/userProfile.html?userId=${loggedInUser.id}">Profile</a>
          </li>
        </ul>

        <a class="btn btn-sm btn-outline-danger" href="${userLogoutUrl}">Logout</a>

      </div>
    </nav>
    <%-- Navbar ends --%>

    <p></p>

    <div class="container formCard">
      <div class="container">
        <div class="row">

          <div class="col-lg-5">
            <p class="formHeader">Create a task</p>
          </div>

        </div>
      </div>

      <form action="/task/create" method="POST" id="taskCreateForm">

        <%-- Title --%>
        <div class="form-group row">
          <label class='col-form-label col-lg-2 flexRightRow formLabel' for="titleInput"><strong>Title</strong></label>
          <div class="col">
            <input class="form-control" type="text" name="title" id="titleInput" placeholder="Get Groceries" autocomplete="off" required>
          </div>
        </div>

        <%-- Details --%>
        <div class="form-group row">
          <label class='col-form-label col-lg-2 flexRightRow formLabel' for="detailsInput"><strong>Details</strong></label>
          <div class="col">
            <textarea class="form-control" rows="6" name="details" id="detailsInput" placeholder="1 Kg Potatoes, 1L Vegetable Oil" autocomplete="off" style="resize:none;" required></textarea>
          </div>
        </div>

        <%-- compensation --%>
        <div class="form-group row">
          <label class='col-form-label col-lg-2 flexRightRow formLabel' for="compensationInput"><strong>Compensation</strong></label>
          <div class="col">
            <input class='form-control' type="number" name="compensation" id="compensationInput" placeholder="500" autocomplete="off" required>
          </div>
        </div>

        <%-- Address --%>
        <div class="form-group row">
          <label class='col-form-label col-lg-2 flexRightRow formLabel' for="addressInput"><strong>Address</strong></label>
          <div class="col">
            <input class='form-control' type="text" name="address" id="addressInput" placeholder="221B Baker Street" autocomplete="off" required>
          </div>
        </div>

        <%-- Deadline --%>
        <div class="form-group row">
          <label class='col-form-label col-lg-2 flexRightRow formLabel' for="deadlineInput"><strong>Deadline</strong></label>
          <div class="col">
            <input class='form-control' type="datetime-local" name="deadline" id="deadlineInput" placeholder="500" autocomplete="off" required>
          </div>
        </div>

        <%-- Create button --%>
        <div class="form-group row flexCenterRow">
          <input class="btn btn-success" type="submit" name="submit" value="Create">
        </div>

      </form>

      <%-- Bootstrap Scripts (JQuery, popper, bootstrap) --%>
      <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
      <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
      <script src="/static/js/task-create-script.js"></script>
    </div>

  </body>

</html>
