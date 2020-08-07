function loadLoginLogoutButton() {
<<<<<<< HEAD
    fetch('/login-status').then(response => response.json()).then(status => {
        if (status.logged_in) {
            loggedInView(status);
        } 
        else {
            loggedOutView(status);
        }
    });
}

function setUserDetails()
{
    fetch('/user')
    .then(response => response.json())
    .then(user_details =>
    {
        console.log(user_details);
    });
}


function loggedInView(status){
    let loggedinSay = document.createElement('p');
    loggedinSay.innerHTML = 'You are logged in as <strong>';
    loggedinSay.innerHTML += status.email;
    loggedinSay.innerHTML += '<strong>. <a href =\"' + status.logoutUrl + '\">Log out</a>.';

    document.querySelector('div.' + 'LoginLogoutDiv')
            .appendChild(loggedinSay);
}

function loggedOutView(status){
    let loginAsk = document.createElement('p');
    loginAsk.innerText = 'Sign in !';

    let loginButton = document.createElement('button');
    loginButton.innerHTML = '<a href=\"' + status.loginUrl + '\">Login</a>';

    let loginDiv = document.createElement('div');
    loginDiv.appendChild(loginAsk);
    loginDiv.appendChild(loginButton);

    document.querySelector('div.' + 'LoginLogoutDiv')
            .appendChild(loginDiv);
}

window.onload = () => {
    loadLoginLogoutButton();
=======
  fetch('/login-status').then(response => response.json()).then(status => {
      if (status.loggedIn) {
          loggedInView(status);
      } 
      else {
          loggedOutView(status);
      }
  });
}

function loggedInView(status){
  //If the user has incomplete profile, redirect to editProfile.html
  if(status.editDetails){
    window.location.href = 'editProfile.html';
  }

  let loggedinSay = document.createElement('p');
  loggedinSay.innerHTML = 'You are logged in as <strong>';
  loggedinSay.innerHTML += status.email;
  loggedinSay.innerHTML += '</strong>. <a href =\"' + status.logoutUrl + '\">Log out</a>.';

  document.querySelector('div.' + 'LoginLogoutDiv')
          .appendChild(loggedinSay);
}

function loggedOutView(status){
  let loginAsk = document.createElement('p');
  loginAsk.innerText = 'Sign in !';

  let loginButton = document.createElement('button');
  loginButton.innerHTML = '<a href=\"' + status.loginUrl + '\">Login</a>';

  let loginDiv = document.createElement('div');
  loginDiv.appendChild(loginAsk);
  loginDiv.appendChild(loginButton);

  document.querySelector('div.' + 'LoginLogoutDiv')
          .appendChild(loginDiv);
}

window.onload = () => {
  loadLoginLogoutButton();
>>>>>>> 36495c695996360ea72899de6dcff2e299af612a
}
