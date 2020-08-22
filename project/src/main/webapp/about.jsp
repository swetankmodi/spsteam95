<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>About</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous" />
    <link rel="stylesheet" href="/static/css/master.css" />
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
          
          <li class="nav-item active">
            <a class="nav-link" href="/about.jsp">About</a>
          </li>

          <c:if test="${isLoggedIn}">

            <li class="nav-item active">
              <a class="nav-link" href="/">Home</a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="/userProfile.html?userId=${loggedInUser.id}">Profile</a>
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

    <!-- About section starts -->
    <div class="container" >
      <div class="jumbotron text-center">
        <p class="display-4 greeting">Hello There!</p>
        <p class="lead user-message" style="align-self: center;">
          Are you crumbled with lot of work? You no longer want to do but want it to get done.<br>
          Or you have plenty of time and wanna fill your pockets with some extra money<br>
          Welcome to DOOR2DOOR! <br>
          Before we get started, we'd like to provide the best user experience possible!</p>
        <button class="btn btn-dark mt-3" href="#timeline" >Let's get Started'</button>
      </div>
    </div>
    <!-- About section ends -->

    <!-- Timeline section starts -->
    <div id="timeline" class="container">
      <div class="jumbotron">
        <!-- load timeline -->
      </div>
    </div>
    <!-- Timeline section ends -->

    <!-- Footer starts -->
    <!-- Footer ends -->

  </body>

</html>