function loadLoginLogoutButton() {
    fetch('/login_status').then(response => response.json()).then(status => {
        if (status.logged_in) {
            loggedInView(status);
        } 
        else {
            loggedOutView(status);
        }
    });
}

function setUserDetails(user_details)
{
    fetch('/user')
    .then(response => response.json())
    .then(user_details =>
    {
        console.log(user_details);
    });
    console.log(user_details.email);
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
}