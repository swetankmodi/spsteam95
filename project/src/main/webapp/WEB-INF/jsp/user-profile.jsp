<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">

  <head>
    <meta charset="UTF-8">
    <title> User | Details</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/master.css">
    <link rel="stylesheet" href="/static/css/user-profile.css">
  </head>

  <body class="user-profile-bg">

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

            <c:if test="${isMyProfile}">
              <li class="nav-item active">
                <a class="nav-link" href="/profile/me">Profile</a>
              </li>
            </c:if>

            <c:if test="${not isMyProfile}">
              <li class="nav-item">
                <a class="nav-link" href="/profile/me">Profile</a>
              </li>
            </c:if>
        </ul>

        <a class="btn btn-sm btn-outline-danger" href="${userLogoutUrl}">Logout</a>

      </div>
    </nav>
    <%-- Navbar ends --%>

    <p></p>

    <div class="container userCard">

      <div class="container">
        <div class="row">

          <%-- User Name --%>
          <div class="col-sm-11">
            <p class="userHeader"><strong>${profileUser.name}</strong></p>
          </div>

          <%-- Edit profile button --%>
          <c:if test="${isMyProfile}">
            <div class="col-sm">
              <button type="button" name="editProfileButton" class="btn btn-sm btn-primary">
                <a href="/profile/edit">Edit</a>
              </button>
            </div>
          </c:if>

        </div>
      </div>

      <hr>

      <div class="container">
        <%-- Details --%>

        <%-- Hidden user Id for JS --%>
        <input id="profileUserId" type="number" value="${profileUser.id}" hidden>

        <%-- Email --%>
        <div class="row">
          <label class="col-lg-1 flexRightRow userLabel">
            <strong>Email</strong>
          </label>

          <div class="col-lg">
            <p class="userValue" id="userEmail">${profileUser.email}</p>
          </div>
        </div>

        <%-- Phone --%>
        <div class="row">
          <label class="col-lg-1 flexRightRow userLabel">
            <strong>Phone</strong>
          </label>

          <div class="col-lg">
            <p class="userValue" id="userPhone">${profileUser.phone}</p>
          </div>
        </div>

        <%-- Rating --%>
        <div class="row">
          <label class="col-lg-1 flexRightRow userLabel">
            <strong>Rating</strong>
          </label>

          <div class="col-lg">
            <span class="score">
              <div class="score-wrap">
                <span class="stars-active" style="width: ${(profileUser.rating * 100) / 5}%">
                  <i class="fa fa-star" aria-hidden="true"></i>
                  <i class="fa fa-star" aria-hidden="true"></i>
                  <i class="fa fa-star" aria-hidden="true"></i>
                  <i class="fa fa-star" aria-hidden="true"></i>
                  <i class="fa fa-star" aria-hidden="true"></i>
                </span>
                <span class="stars-inactive">
                  <i class="fa fa-star-o" aria-hidden="true"></i>
                  <i class="fa fa-star-o" aria-hidden="true"></i>
                  <i class="fa fa-star-o" aria-hidden="true"></i>
                  <i class="fa fa-star-o" aria-hidden="true"></i>
                  <i class="fa fa-star-o" aria-hidden="true"></i>
                </span>
              </div>
            </span>
          </div>

        </div>

      </div>

      <div class="container">

        <div class="row">

          <div class="col-8">
            <select id="taskType" class="form-control-sm sort-filter-options">
              <option>Tasks Created</option>
              <option>Tasks Completed</option>
              <option>Tasks Pending</option>
            </select>
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

          </div>

        </div>

        <div class="container taskListDiv">
        </div>

      </div>
    </div>

    <%-- Bootstrap Scripts (JQuery, popper, bootstrap) --%>

    <link rel="icon" type="image/x-icon" href="/static/images/background/logo.png">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="/static/js/user-profile.js"></script>
  </body>


</html>
