<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>Task | ${task.title}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/master.css">
    <link rel="stylesheet" href="/static/css/task-view.css">
    <link rel="stylesheet" href="/static/css/5-star-rating/star-rating.css">
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
            <a class="nav-link" href="/userProfile.jsp?userId=${loggedInUser.id}">Profile</a>
          </li>
        </ul>

        <a class="btn btn-sm btn-outline-danger" href="${userLogoutUrl}">Logout</a>

      </div>
    </nav>
    <%-- Navbar ends --%>

    <p></p>

    <div class="container taskCard">
      <div class="container">
        <%-- Task Title --%>
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
              <a href="/userProfile.jsp?userId=${task.creatorId}">${creator.name}</a>
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

        <div class="container assigneeInfo">

          <c:if test="${task.isActive()}">

            <%-- State: active, unassigned --%>
            <c:if test="${!task.isAssigned()}">
              <p class="assignmentMessage">

                <c:if test="${isCreator}">
                  <em>The task is open to applicants.</em>
                </c:if>

                <c:if test="${not isCreator}">
                  <c:if test="${hasApplied}">
                    <%-- User has applied already --%>
                    <em>You have applied for this task.</em>
                  </c:if>

                  <c:if test="${not hasApplied}">
                    <%-- Apply button, if not applied --%>
                    <div class="flexCenterRow">
                      <button class="btn btn-outline-success" id="applyButton"><a href="/task/apply?taskId=${task.id}">Apply</a></button>
                    </div>
                  </c:if>
                </c:if>

              </p>

            </c:if>

            <%-- State: active, assigned --%>
            <c:if test="${task.isAssigned()}">
              <p class="assignmentMessage">
                <em>The task has been assigned to
                  <strong><a href="/userProfile.jsp?userId=${task.assigneeId}">${assignee.name}</a></strong>.
                </em>

                <c:if test="${isCreator}">
                  <p></p>
                  <form class="" action="/task/rate" method="post">
                    <input type="number" name="taskId" value="${task.id}" hidden>
                    <input type="number" name="assigneeId" value="${assignee.id}" hidden>

                    <div class="row task-rater">
                      <div class="col-lg-2">
                        <span class="starRating">
                          <input id="taskRating5" type="radio" name="rating" value="5" />
                          <label for="taskRating5">5</label>
                          <input id="taskRating4" type="radio" name="rating" value="4" />
                          <label for="taskRating4">4</label>
                          <input id="taskRating3" type="radio" name="rating" value="3" />
                          <label for="taskRating3">3</label>
                          <input id="taskRating2" type="radio" name="rating" value="2" />
                          <label for="taskRating2">2</label>
                          <input id="taskRating1" type="radio" name="rating" value="1" />
                          <label for="taskRating1">1</label>
                        </span>
                      </div>

                      <div class="col-lg-2 task-rate-button">
                        <input type="submit" class="btn btn-sm btn-outline-primary" value="Rate" />
                      </div>
                    </div>
                  </form>
                </c:if>
              </p>
            </c:if>

          </c:if>

          <c:if test="${!task.isActive()}">

            <%-- State: inactive, unassigned --%>
            <c:if test="${!task.isAssigned()}">
              <p class="assignmentMessage">
                <em>The task has been closed.</em>
              </p>
            </c:if>

            <%-- State: inactive, assigned --%>
            <c:if test="${task.isAssigned()}">
              <p class="assignmentMessage">
                <em>The task was completed by <a href="/userProfile.jsp?userId=${task.assigneeId}"><strong>${assignee.name}</strong></a> with a rating of <strong>${task.completionRating}</strong>.</em>
              </p>
            </c:if>

          </c:if>

        </div>

        <c:if test="${isCreator && task.isActive()}">
          <%-- Content visible only to the creator --%>

          <p class="col-lg-12 flexCenterRow applicantListHeader">Applicants</p>
          <hr>

          <div class="container applicantListDiv" id="task-applicant-list">

            <c:forEach items="${taskApplicantList}" var="applicant">
              <div class="col-lg-5 card applicantDiv">

                <div class="card-body">
                  <div class="row">
                    <span class="col"><a href="/userProfile.jsp?userId=${applicant.id}">${applicant.name}</a></span>
                    <span class="score">
                  		<div class="score-wrap">
                        <span class="stars-active" style="width: ${(applicant.rating * 100) / 5}%">
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
                    <c:if test="${!task.isAssigned()}">
                      <button class="btn btn-sm btn-secondary" id="assignButton${applicant.id}"><a href="/task/assign?taskId=${task.id}&assigneeId=${applicant.id}">Assign</a></button>
                    </c:if>
                  </div>
                </div>

              </div>
            </c:forEach>
          </div>

        </c:if>

      </div>
    </div>

    <!-- Bootstrap Scripts (JQuery, popper, bootstrap) -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="/static/js/task-view-script.js" ></script>
  </body>

</html>
