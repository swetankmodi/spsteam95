function addProfileDetailsToDOM(){
    fetch('/profile/my').then(response => response.json()).then(response => {
      //Set the name, email, phone and rating to the previous filled data
      var name = response.user.name;
      var email = response.user.email;
      var phone = response.user.phone;
  
      document.getElementById("name").value = name;
      document.getElementById("email").textContent = email;
      document.getElementById("phone").value = phone;
      
      const profileUrl = document.getElementById('profile-url');
      profileUrl.innerHTML = '<a class="nav-link" href="/userProfile.jsp?userId=' + response.loggedInUserId + '">Profile</a>';
      const logoutButton = document.getElementById('logout-button');
      logoutButton.innerHTML = '<a class="btn btn-sm btn-outline-danger" href="' + response.userLogoutUrl + '">Logout</a>';
    });
  }
  
  window.onload = () => {
    addProfileDetailsToDOM();
  }
  