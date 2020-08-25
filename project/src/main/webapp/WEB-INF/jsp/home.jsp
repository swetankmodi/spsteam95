<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous" />
    <link rel="stylesheet" href="/static/css/master.css" />
    <link rel="stylesheet" href="/static/css/home.css">
    <link rel="stylesheet" href="/static/css/about.css">
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
          <c:if test="${isLoggedIn}">

            <li class="nav-item active">
              <a class="nav-link" href="/">Home</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="/userProfile.jsp?userId=${loggedInUser.id}">Profile</a>
            </li>

          </c:if>
        </ul>

        <c:if test="${isLoggedIn}">
          <a class="btn btn-sm btn-outline-danger" href="${userLogoutUrl}">Logout</a>
        </c:if>

        <c:if test="${not isLoggedIn}">
          <a class="btn btn-sm btn-outline-success" href="${userLoginUrl}">Login</a>
        </c:if>

      </div>
    </nav>
    <%-- Navbar ends --%>

    <c:if test="${not isLoggedIn}">
        <!-- About section starts -->
    <div class="container" >
      <div class="jumbotron text-center">
        <p class="display-4 greeting">Hello There!</p>
        <p class="lead user-message" style="align-self: center;">
          Are you crumbled with lot of work? You no longer want to do but want it to get done.<br>
          Or you have plenty of time and wanna fill your pockets with some extra money<br>
          Welcome to Door2Door! <br>
          Before we get started, we'd like to provide the best user experience possible!</p>
        <button class="btn btn-dark mt-3" href="#timeline" >Let's get Started'</button>
      </div>
    </div>
    <!-- About section ends -->

    <!-- Timeline section starts -->
    <div id="timeline" class="container">
      <div class="jumbotron">
        <!-- load timeline -->
        <h2>How to get your task done?</h2>
        <img src="/static/images/add-task-timeline.png"><br><br>
        <h3>Earn Money by Completing Tasks &#128525;</h3>
        <img src="/static/images/apply-for-task.png">
      </div>
    </div>
    <!-- Timeline section ends -->

    <!-- Footer starts -->
    <div class="container">
      <div class="container">
        <h1 style="color:antiquewhite;">Who we are?</h1>
      </div>
      <div class="row founder">
        <div class="circle small">
          <h1>Swetank<br>Modi</h1>
          <h3>
            <a href="#" class="share-btn icon-linkedin">in</a>
            <a href="#" class="share-btn icon-google">g</a>
          </h3>
        </div>
        <div class="circle small">
          <h1>Aman<br>Singh</h1>
          <h3>
            <a href="#" class="share-btn icon-linkedin">in</a>
            <a href="#" class="share-btn icon-google">g</a>
          </h3>
        </div>
        <div class="circle small">
          <h1>Arijit<br>Saha</h1>
          <h3>
            <a href="#" class="share-btn icon-linkedin">in</a>
            <a href="#" class="share-btn icon-google">g</a>
          </h3>
        </div>
        <div class="circle small">
          <h1>Palak<br>Mittal</h1>
          <h3>
            <a href="#" class="share-btn icon-linkedin">in</a>
            <a href="#" class="share-btn icon-google">g</a>
          </h3>
        </div>
        <div class="circle small">
          <h1>Tanmay<br>Bansal</h1>
          <h3>
            <a href="#" class="share-btn icon-linkedin">in</a>
            <a href="#" class="share-btn icon-google">g</a>
          </h3>
        </div>
      </div>
    </div>

    <!-- Footer ends -->
    </c:if>

    <c:if test="${isLoggedIn}">

      <div class="container">
        <div class="row sortFilterDiv">
          <div class="col-2">
            <a href="/task/create" class="btn btn-sm btn-outline-success createButton">Create Task</a>
          </div>

          <div class="col-5">
          </div>

          <div class="col form-inline">
            <span class="form-control-sm sort-filter-options" style="padding-top: 6px;">Sort by </span>

            <select id="taskSortOption" class="form-control-sm sort-filter-options">
              <option>Deadline</option>
              <option>Creation</option>
              <option>Compensation</option>
            </select>

            <select id="taskSortDirection" class="form-control-sm sort-filter-options">
              <option>Ascending</option>
              <option>Descending</option>
            </select>

            <div class="form-control-sm form-check form-check-inline" style="padding-top: 6px;">
              <input class="form-check-input sort-filter-options" type="checkbox" id="taskFilterActive" value="active" checked>
              <label class="form-check-label sort-filter-options" for="taskFilterActive">Active</label>
            </div>

          </div>

        </div>
      </div>

      <div class="container taskListDiv">
      </div>

    </c:if>

    <%-- Bootstrap Scripts (JQuery, popper, bootstrap) --%>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="/static/js/home-script.js"></script>
  </body>

</html>
