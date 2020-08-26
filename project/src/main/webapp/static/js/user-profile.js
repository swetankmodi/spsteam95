/* Constants */
const taskListDivClassName = "taskListDiv";

var taskListCursor = null;
var working = false;
var userId = null;

function setUserIdFromQueryParams(queryParams){
  var len = queryParams.length;
  if(queryParams.charAt(len - 1) == '/')
    userId = queryParams.substring(7, len - 1);
  else
    userId = queryParams.substring(7, len);
}

function constructTaskNode(task) {
  let deadline = new Date(task.deadline);
  let deadlineString = deadline.toLocaleString('en-US',
      { hour: 'numeric', minute: 'numeric', hour12: true });
  deadlineString += ', ' + deadline.toDateString();

  let f = '<div class="task-node">' +
        '<div class="task-first-line">' +
          '<a href="/task/view/' + task.id + '"><span class="task-title">' + task.title + '</span></a>' +
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
  if (working) return;
  working = true;

  var queryParams = new URLSearchParams(window.location.search);
  queryParams = queryParams.toString();
  //set userId from queryParams
  if(userId == null)
    setUserIdFromQueryParams(queryParams);

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

  if(taskType == 'Completed Tasks')
    fetchURL = '/task/completed';
  else if(taskType == 'Created Tasks')
    fetchURL = '/task/created';
  else
    fetchURL ='/task/assigned';

  $.get(fetchURL, { userId : userId,
                    cursor: taskListCursor,
                    sortOption: sortOption,
                    sortDirection: sortDirection
      }).done(function(response) {

    // Append Tasks to list
    for (task of response.tasks) {
      taskNode = $(constructTaskNode(task));
      taskList.append(taskNode);
    }

    // Update cursor
    taskListCursor = response.nextCursor;
    working = false;
  });
}

// For Infinite Scrolling
window.addEventListener('scroll', function() {
  if (document.documentElement.scrollTop + document.documentElement.clientHeight
      >= document.documentElement.scrollHeight) {
    loadTasks();
  }
});

$(document).ready(function(){
  loadTasks();

  // For Switching Sort Options
  $('#taskSortOption').on('change',function(){
    loadTasks(true);
  });

  // For Switching Sort Directions
  $('#taskSortDirection').on('change',function(){
    loadTasks(true);
  });

  // For Switching Task Types
  $('#taskType').on('change',function(){
    loadTasks(true);
  });

});
