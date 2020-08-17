function viewTaskDetails() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/task?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
      const taskContainer = document.getElementById('task-container');
      taskContainer.innerHTML = '<h1>' + response.task.title + '</h1>';
      taskContainer.innerHTML += '<p>' + response.task.details + "</p>";
      taskContainer.innerHTML += '<p><strong>Location</strong>: ' + response.task.address + "</p>";
      var deadline = new Date(response.task.deadline).toUTCString();
      taskContainer.innerHTML += '<p><strong>Task Deadline</strong>: ' + deadline + "</p>";
      taskContainer.innerHTML += '<p><strong>Created By</strong>: ' + response.task.creatorId + "</p>";
      taskContainer.innerHTML += '<p><strong>Compensation</strong>: ' + response.task.compensation + "</p>";
      
      console.log(response.isCurrentUserAlreadyApplied);
      if(!response.isCreator && !response.task.assigned && !response.isCurrentUserAlreadyApplied)
        loadApplyButton(response.task.id);
      loadAssigneeList(response.taskAssigneeList, response.task.id)
  })
} 

function loadApplyButton(taskId){
  var taskApplyContainer = document.getElementById('task-apply');
  var applyButton = document.createElement('button');
  applyButton.innerHTML = "Apply for this task !";
  applyButton.onclick = function() {
    var ajaxPostRequest = new XMLHttpRequest();
    ajaxPostRequest.open("POST", "/task/apply");
    ajaxPostRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    ajaxPostRequest.send("taskId=" + taskId);
    location.reload();
  }
  taskApplyContainer.append(applyButton);
}

/* 
 * Function to load task assignees list
 */
function loadAssigneeList(taskAssigneeList, taskId) {
  console.log(taskAssigneeList);
  var taskAssigneeContainer = document.getElementById('task-assignee-list');
  for( i=0;i<taskAssigneeList.length;i++) {
    var assignButton = document.createElement('button');
    let assign = taskAssigneeList[i];
    assignButton.innerHTML = "Assign";
    assignButton.onclick = function() {
      var ajaxPostRequest = new XMLHttpRequest();
      ajaxPostRequest.open("POST", "/task/assign");
      ajaxPostRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      ajaxPostRequest.send("taskId=" + taskId + "&assigneeId=" + assign);
      location.reload();
    }
    var assignee = document.createElement('button');
    assignee.innerHTML = taskAssigneeList[i];
    assignee.onclick = function(){
      location.href = '/userProfile.html?userId=' + assign;
    }
    var assigneeContainer = document.createElement('li');
    assigneeContainer.append(assignee);
    assigneeContainer.append(assignButton);
    taskAssigneeContainer.append(assigneeContainer);
  }
}

window.onload = () => {
  viewTaskDetails();
}
