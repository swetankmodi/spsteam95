function viewTaskDetails() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/task?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
      const profileUrl = document.getElementById('profile-url');
      profileUrl.innerHTML = '<a class="nav-link" href="/userProfile.html?userId=' + response.loggedInUserId + '">Profile</a>';
      const logoutButton = document.getElementById('logout-button');
      logoutButton.innerHTML = '<a class="btn btn-sm btn-outline-danger" href="' + response.userLogoutUrl + '">Logout</a>';

      document.getElementById('taskTitle').innerText = response.task.title;
      document.getElementById('taskDetails').innerHTML = response.task.details;
      document.getElementById('taskLocation').innerHTML = response.task.address;
      var deadline = new Date(response.task.deadline).toUTCString();
      document.getElementById('taskDeadline').innerHTML = deadline;
      document.getElementById('taskCreatedBy').innerHTML = response.task.creatorId;
      document.getElementById('taskCompensation').innerHTML = response.task.compensation;
      
      console.log(response.isCurrentUserAlreadyApplied);
      if(!response.isCreator && !response.task.assigned && !response.isCurrentUserAlreadyApplied)
        loadApplyButton(response.task.id);
      if(response.isCreator && response.task.assigned && response.task.active)
        loadRateUserInputBox(response.task.id, response.task.assigneeId);
      loadAssigneeList(response.taskAssigneeList, response.task.id)
  })
} 

function loadApplyButton(taskId){
  var taskApplyContainer = document.getElementById('task-apply');
  var applyButton = document.createElement('button');
  applyButton.className = "btn btn-success";
  
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

function loadRateUserInputBox(taskId, assigneeId){
  let rate = document.createElement('input');
  rate.className = 'form-control col-lg-2';
  rate.type = "text";
  rate.name = 'rating';
  rate.style = "margin-right:16px";
  rate.placeholder = "Give Assignee Rating"
  rate.required = true;

  let id1 = document.createElement('textarea');
  id1.className = 'text';
  id1.name = 'taskId';
  id1.value = taskId;
  id1.hidden = true;

  let id2 = document.createElement('textarea');
  id2.className = 'text';
  id2.name = 'assigneeId';
  id2.value = assigneeId;
  id2.hidden = true;

  let ratingSubmit = document.createElement('input');
  ratingSubmit.className = 'btn btn-success col-lg-1'
  ratingSubmit.type = 'submit';
  ratingSubmit.value = 'Rate';

  let rateForm = document.createElement('form');
  rateForm.method = 'POST';
  rateForm.action = '/task/rate';
  let rateRow = document.createElement('div');
  rateRow.className = "row";
  rateRow.appendChild(rate);
  rateRow.appendChild(id1);
  rateRow.appendChild(id2);
  rateRow.appendChild(ratingSubmit);
  rateForm.appendChild(rateRow);
  document.querySelector('div.' + 'task-rate')
          .appendChild(rateForm);
}

/* 
 * Function to load task assignees list
 */
function loadAssigneeList(taskAssigneeList, taskId) {
  console.log(taskAssigneeList);
  var taskAssigneeContainer = document.getElementById('task-assignee-list');
  for( i=0;i<taskAssigneeList.length;i++) {
    var assignButton = document.createElement('button');
    assignButton.className = "btn btn-success";
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
    assignee.style = 'margin-right:16px'
    assigneeContainer.append(assignee);
    assigneeContainer.append(assignButton);
    taskAssigneeContainer.append(assigneeContainer);
    taskAssigneeContainer.append(document.createElement("p"));
  }
}

window.onload = () => {
  viewTaskDetails();
}
