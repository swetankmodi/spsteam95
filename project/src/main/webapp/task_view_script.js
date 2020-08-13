function viewTaskDetails() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/task?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
      const taskContainer = document.getElementById('task-container');
      taskContainer.innerHTML = '<h1>' + response["title"] + '</h1>';
      taskContainer.innerHTML += '<p>' + response["details"] + "</p>";
      taskContainer.innerHTML += '<p><strong>Location</strong>: ' + response["address"] + "</p>";
      var deadline = new Date(response["deadline"]).toUTCString();
      taskContainer.innerHTML += '<p><strong>Task Deadline</strong>: ' + deadline + "</p>";
      taskContainer.innerHTML += '<p><strong>Created By</strong>: ' + response["creatorId"] + "</p>";
      taskContainer.innerHTML += '<p><strong>Compensation</strong>: ' + response["compensation"] + "</p>";
      loadAssigneeList(response["taskAssigneeList"])
  })
} 

/* 
 * Function to load task assignees list
 */
function loadAssigneeList(taskAssigneeList)
{
  const taskContainer = document.getElementById('task-assignee-list');
  for(assignee in taskAssigneeList){
      console.log(assignee);
  }
}
