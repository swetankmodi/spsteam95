function viewTaskDetails() {
  var queryParams = new URLSearchParams(window.location.search);
  fetch('/task?' + queryParams.toString()).then((response) => {
      return response.json();
  }).then((response) => {
      const taskContainer = document.getElementById('task-container');
      taskContainer.innerHTML = '<h1>' + response["title"] + '</h1>';
      taskContainer.innerHTML += '<input type="text" name="taskId" value="' + queryParams.get("taskId") + '" style="display: none">';
      taskContainer.innerHTML += '<p>' + response["details"] + "</p>";
      taskContainer.innerHTML += '<p><strong>Location</strong>: ' + response["address"] + "</p>";
      var deadline = new Date(response["deadline"]).toUTCString();
      taskContainer.innerHTML += '<p><strong>Task Deadline</strong>: ' + deadline + "</p>";
      taskContainer.innerHTML += '<p><strong>Created By</strong>: ' + response["creatorId"] + "</p>";
      taskContainer.innerHTML += '<p><strong>Compensation</strong>: ' + response["compensation"] + "</p>";
  })
}