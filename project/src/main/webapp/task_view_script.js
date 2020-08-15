function viewTaskDetails() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/task?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
      const taskContainer = document.getElementById('task-container');
      taskContainer.innerHTML = '<h1>' + response.title + '</h1>';
      taskContainer.innerHTML += '<p>' + response.details + "</p>";
      taskContainer.innerHTML += '<p><strong>Location</strong>: ' + response.address + "</p>";
      var deadline = new Date(response.deadline).toUTCString();
      taskContainer.innerHTML += '<p><strong>Task Deadline</strong>: ' + deadline + "</p>";
      taskContainer.innerHTML += '<p><strong>Created By</strong>: ' + response.creatorId + "</p>";
      taskContainer.innerHTML += '<p><strong>Compensation</strong>: ' + response.compensation + "</p>";
      loadAssigneeList(response.taskAssigneeList)
  })
} 

/* 
 * Function to load task assignees list
 */
function loadAssigneeList(taskAssigneeList)
{
  console.log(taskAssigneeList);
  var taskAssigneeContainer = document.getElementById('task-assignee-list');
  for( i=0;i<taskAssigneeList.length;i++) {
    var assignButton = document.createElement('button');
    assignButton.innerHTML = "Assign";
    assignButton.onclick = function(){
      /*TODO:
        We need to make a POST request by passing
        assignee's id and task id
      */
      alert('TODO:You need to make a POST request by passing assignees id and task id');
    }
    var assignee = document.createElement('button');
    assignee.innerHTML = taskAssigneeList[i];
    assignee.onclick = function(){
      /*TODO:
        We need to redirect it to assignee's profile id
        rather than its own profile
      */
      location.href='userProfile.html';
    }
    var assigneeContainer = document.createElement('li');
    assigneeContainer.append(assignee);
    assigneeContainer.append(assignButton);
    taskAssigneeContainer.append(assigneeContainer);
  }
}
