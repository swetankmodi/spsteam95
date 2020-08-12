function addProfileDetailsToDOM(){
  fetch('/profile').then(response => response.json()).then(user => {

    //Set the name, email, phone and rating to the previous filled data
    var name = user.name;
    var email = user.email;
    var phone = user.phone;
    var rating = user.rating;
    
    var nameDiv = document.createElement('p');
    nameDiv.innerHTML = 'Name: ';
    nameDiv.innerHTML += name;

    var emailDiv = document.createElement('p');
    emailDiv.innerHTML = 'Email: ';
    emailDiv.innerHTML += email;

    var phoneDiv = document.createElement('p');
    phoneDiv.innerHTML = 'Phone: ';
    phoneDiv.innerHTML += phone;

    var ratingDiv = document.createElement('p');
    ratingDiv.innerHTML = 'Rating: ';
    ratingDiv.innerHTML += rating;

    var profileDiv = document.createElement('div');
    profileDiv.appendChild(nameDiv);
    profileDiv.appendChild(emailDiv);
    profileDiv.appendChild(phoneDiv);
    profileDiv.appendChild(ratingDiv);
    document.querySelector('div.' + 'profileDiv')
          .appendChild(profileDiv);
  });
}

window.onload = () => {
  addProfileDetailsToDOM();
}
