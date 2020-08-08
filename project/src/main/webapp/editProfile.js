function loadEditProfilePage(){
  fetch('/user-details').then(response => response.json()).then(user => {

    /*
      Set the name, email, phone and rating to the previous filled data
    */

    var name = user.name;
    var email = user.email;
    var phone = user.phone;
    var rating = user.rating;

    document.getElementById("name").value = name;
    document.getElementById("email").value = email;
    document.getElementById("phone").value = phone;
    document.getElementById("rating").value = rating;
    
  });
}

window.onload = () => {
  loadEditProfilePage();
}
