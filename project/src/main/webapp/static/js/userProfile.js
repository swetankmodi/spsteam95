function addProfileDetailsToDOM() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/profile?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
    var name = response.user.name;
    var email = response.user.email;
    var phone = response.user.phone;
    var rating = response.user.rating;

    const profileUrl = document.getElementById('profile-url');
    profileUrl.innerHTML = '<a class="nav-link" href="/userProfile.jsp?userId=' + response.loggedInUserId + '">Profile</a>';
    const logoutButton = document.getElementById('logout-button');
    logoutButton.innerHTML = '<a class="btn btn-sm btn-outline-danger" href="' + response.userLogoutUrl + '">Logout</a>';

    document.getElementById('userName').innerHTML = name;
    document.getElementById('userEmail').innerHTML = email;
    document.getElementById('userPhone').innerHTML = phone;
    document.getElementById('userRating').innerHTML = rating;

    if(response.canEditProfile){
      addEditProfileButtonToDom();
    }
  })
} 

function addEditProfileButtonToDom(){
  var editProfileContainer = document.getElementById('edit-profile');
  var editButton = document.createElement('button');
  editButton.className = "btn btn-success";
  
  editButton.innerHTML = "Edit profile";
  editButton.onclick = function() {
    window.location = '/profile/edit';
  }
  editProfileContainer.append(editButton);
}

window.onload = () => {
  addProfileDetailsToDOM();
}
