function addProfileDetailsToDOM() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/profile?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
    var name = response.name;
    var email = response.email;
    var phone = response.phone;
    var rating = response.rating;
    
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
  })
} 

window.onload = () => {
  addProfileDetailsToDOM();
}
