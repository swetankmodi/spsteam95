<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous" />
    <link rel="stylesheet" href="/static/css/master.css" />
    <%-- <link rel="stylesheet" href="/static/css/task-create.css"> --%>
    <%-- <link rel="stylesheet" href="style.css"> --%>
    <%-- <script src="script.js"></script> --%>
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
          <% if (isLoggedIn) { %>

            <li class="nav-item">
              <a class="nav-link active" href="/">Home</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="/userProfile.html?userId=${loggedInUser.id}">Profile</a>
            </li>

          <% } %>
        </ul>

        <% if (isLoggedIn) { %>
          <a class="btn btn-sm btn-outline-danger" href="${userLogoutUrl}">Logout</a>
        <% } else { %>
          <a class="btn btn-sm btn-outline-success" href="${userLoginUrl}">Login</a>
        <% } %>

      </div>
    </nav>
    <%-- Navbar ends --%>

    <%-- <div class="LoginLogoutDiv">
    </div>

    <div class="taskListDiv">
      <ul class="taskListUl">
      </ul>
    </div> --%>

  </body>

</html>
