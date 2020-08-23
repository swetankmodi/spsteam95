<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>Task | ${task.title}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/master.css">
    <link rel="stylesheet" href="/static/css/task-view.css">
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

    <div class="container taskCard">
      <%-- Task Title --%>
      <div class="container">
        <div class="row">

          <div class="col-lg-12">
            <p class="taskHeader"><strong>${task.title}</strong></p>
          </div>

        </div>
      </div>

      <hr>

      <div class="container">
        <%-- Location --%>
        <div class="row">
          <label class="col-lg-2 flexRightRow taskLabel"><strong>Location</strong></label>

          <div class="col-lg">
            <p class="taskValue" id="taskAddress">${task.address}</p>
          </div>
        </div>

        <%-- Deadline --%>
        <div class="row">
            <label class="col-lg-2 flexRightRow taskLabel"><strong>Deadline</strong></label>

          <div class="col-lg">
            <p class="taskValue" id="taskDeadline">${task.getDeadlineAsLong()}</p>
          </div>
        </div>

        <%-- Details --%>
        <div class="row">
          <label class="col-lg-2 flexRightRow taskLabel"><strong>Details</strong></label>

          <div class="col-lg">
            <p class="taskValue" id="taskDetails">${task.details}</p>
          </div>
        </div>

        <%-- Created By --%>
        <div class="row">
          <label class="col-lg-2 flexRightRow taskLabel"><strong>Created By</strong></label>

          <div class="col-lg">
            <p class="taskValue" id="taskCreatedBy">
              <a href="/userProfile.html?userId=${task.creatorId}">${creator.name}</a>
            </p>
          </div>
        </div>

        <%-- Compensation --%>
        <div class="row">
          <label class="col-lg-2 flexRightRow taskLabel"><strong>Compensation</strong></label>

          <div class="col-lg">
            <p class="taskValue" id="taskCompensation">${task.compensation}</p>
          </div>
        </div>

        <div class="assigneeInfo">
          <c:if test="${task.isAssigned()} and ${task.isActive()}">
            <%-- task rate if assignment is done --%>
            <p>
              <em>The task has been assigned to <a href="/userProfile.html?userId=${task.assigneeId}">${assignee.name}</a>. Rate Them.</em>
            </p>
          </c:if>

          <c:if test="${!task.isAssigned()} and ${task.isActive()}">
            <%-- task rate if assignment is done --%>
            <p>
              <em>The task is open to applicants.</em>
            </p>
          </c:if>

          <c:if test="${!task.isAssigned()} and ${!task.isActive()}">
            <%-- task rate if assignment is done --%>
            <p>
              <em>The task is now inactive.</em>
            </p>
          </c:if>

        </div>

        <c:if test="${isCreator}">
          <%-- Content visible only to the creator --%>

          <p class="col-lg-12 flexCenterRow applicantListHeader">Applicants</p>
          <hr>

          <div class="container applicantListDiv" id="task-applicant-list">

            <c:forEach items="${taskApplicantList}" var="applicant">
              <div class="col-lg-5 card applicantDiv">

                <div class="card-body">
                  <div class="row">
                    <span class="col"><a href="/userProfile.html?userId=${applicant.id}">${applicant.name}</a></span>
                    <span class="col">${applicant.rating}</span>
                    <c:if test="${!task.isAssigned()}">
                      <button class="btn btn-sm btn-secondary" id="assignButton${applicant.id}"><a href="/task/assign?taskId=${task.id}&assigneeId=${applicant.id}">Assign</a></button>
                    </c:if>
                  </div>
                </div>

              </div>
            </c:forEach>
          </div>

        </c:if>

        <c:if test="${not isCreator}">
          <%-- Content visible to non-creator users --%>

          <c:if test="${(!task.isAssigned()) && (task.isActive())}">

            <c:if test="${hasApplied}">
              <%-- User has applied already --%>
              <p>You are an applicant</p>
            </c:if>

            <c:if test="${not hasApplied}">
              <%-- Apply button, if not applied --%>
              <button class="btn btn-sm btn-success" id="applyButton"><a href="/task/apply?taskId=${task.id}">Apply</a></button>
            </c:if>

          </c:if>

        </c:if>

        <c:if test="${task.isAssigned() && task.isActive()}">
          <%-- Task has been assigned to --%>
          <p>Task has been assigned to ${assigneeId}</p>
        </c:if>

        <%-- Add one for post completion too --%>

      </div>
    </div>

    <!-- Bootstrap Scripts (JQuery, popper, bootstrap) -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="/static/js/task-view-script.js" ></script>
    <%-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> --%>
  </body>

</html>
