/* Constants */
const taskListDivClassName = "taskListDiv";

var taskListCursor = null;
var working = false;

function addProfileDetailsToDOM() {
  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  fetch('/profile?' + queryParams).then((response) => {
      return response.json();
  }).then((response) => {
    var name = response.user.name;
    var email = response.user.email;
    var phone = response.user.phone;
    var rating = response.user.rating;

    if(response.canEditProfile){
      addEditProfileButtonToDom();
    }
  })
}

function constructTaskNode(task) {
  let deadline = new Date(task.deadline);
  let deadlineString = deadline.toLocaleString('en-US',
      { hour: 'numeric', minute: 'numeric', hour12: true });
  deadlineString += ', ' + deadline.toDateString();

  let f = '<div class="task-node">' +
        '<div class="task-first-line">' +
          '<a href="/task-view.html?taskId=' + task.id + '"><span class="task-title">' + task.title + '</span></a>' +
          '<span class="task-compensation">&#x20B9; ' + task.compensation + '</span>' +
        '</div>' +

        '<div class="task-second-line">' +
          '<span class="task-deadline">Deadline: ' + deadlineString + '</span>' +
        '</div>' +
      '</div>';

  return f;
}

/*
 * Function to load tasks.
 */
function loadTasks(refreshList = false) {
  if(working)
    return;
  working = true;
  let fetchURL = '/task/completed';
  let taskList = $('div.' + taskListDivClassName);

  // If the list is to be refreshed, then cursor should be null
  if (refreshList) {
    taskListCursor = null;
    taskList.empty();
  }

  let sortOption = $('#taskSortOption').val();
  let sortDirection = $('#taskSortDirection').val();
  let taskType = $('#taskType').val();

  if(taskType == 'Tasks Completed')
    fetchURL = '/task/completed';
  else if(taskType == 'Tasks Created')
    fetchURL = '/task/created';
  else
    fetchURL ='/task/assigned';

  $.get(fetchURL, { cursor: taskListCursor,
                    sortOption: sortOption,
                    sortDirection: sortDirection
      }).done(function(response) {

    // Append Tasks to list
    for (task of response.tasks) {
      taskNode = $(constructTaskNode(task));
      console.log(task);
      taskList.append(taskNode);
    }

    /*Todo : Infinite Scrolling, was having some errors with cursor*/
    // Update cursor
    taskListCursor = response.nextCursor;
    working = false;
  });
}

// For Infinite Scrolling
window.addEventListener('scroll', function() {
  if (document.documentElement.scrollTop + document.documentElement.clientHeight
      >= document.documentElement.scrollHeight) {
    if (working === false) {
      //working = true;
      loadTasks();
    }
  }
});

$(document).ready(function(){
  addProfileDetailsToDOM();
  loadTasks();

  // For Switching Sort Options
  $('#taskSortOption').on('change',function(){
    loadTasks(true);
  });

  // For Switching Sort Directions
  $('#taskSortDirection').on('change',function(){
    loadTasks(true);
  });

  $('#taskType').on('change',function(){
    loadTasks(true);
  });

});
