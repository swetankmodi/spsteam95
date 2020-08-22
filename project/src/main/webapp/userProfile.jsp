<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title> User | Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/master.css">
    <link rel="stylesheet" href="/static/css/user-profile.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  </head>

  <body>
    <!-- Navbar Begins -->
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
              <div id="profile-url"></div>
          </li>
        </ul>

        <div id="logout-button"></div>

      </div>
    </nav>
    <!-- Navbar ends -->

    <p></p>
    
    <div class="container userCard">
      <div class="container">
        <div class="row">

          <div class="col-lg-12">
            <strong><p class="userHeader" id="userTitle"></p></strong>
          </div>

        </div>
      </div>
      <hr>
      <div class="container">
        <!-- Details -->

        <!-- Name -->
        <div class="row">
          <div class="col-lg-2 userLabel">
            <strong>Name: </strong>
          </div>
          <div class="col-lg-10 userValue" id="userName">
          </div>
        </div>

        <!-- Email -->
        <div class="row">
          <div class="col-lg-2 userLabel">
            <strong>Email: </strong>
          </div>
          <div class="col-lg-10 userValue" id="userEmail">
          </div>
        </div>

        <!-- Phone -->
        <div class="row">
          <div class="col-lg-2 userLabel">
            <strong>Phone: </strong>
          </div>
          <div class="col-lg-10 userValue" id="userPhone">
          </div>
        </div>

        <!-- Rating -->
        <div class="row">
          <div class="col-lg-2 userLabel">
            <strong>Rating: </strong>
          </div>
          <div class="col-lg-10 userValue" id="userRating">
          </div>
        </div>

        <p></p>
        
        <div class="row">
          <div class="col-lg-12" id="edit-profile"></div>
        </div>
      
      </div>
    </div>

    <!-- Bootstrap Scripts (JQuery, popper, bootstrap) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="/static/js/userProfile.js" ></script>
  </body>
  
  
</html>
