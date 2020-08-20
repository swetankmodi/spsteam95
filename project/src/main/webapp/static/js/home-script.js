/* Constants */
const taskListDivClassName = "taskListDiv";

var taskListCursor = null;
var working = false;

function constructTaskNode(task) {
  let deadline = new Date(task.deadline);
  let deadlineString = deadline.toLocaleString('en-US',
      { hour: 'numeric', minute: 'numeric', hour12: true });
  deadlineString += ', ' + deadline.toDateString();

  let f = '<div class="task-node">' +
        '<div class="task-first-line">' +
          '<a href="/task_view.html?taskId=' + task.id + '"><span class="task-title">' + task.title + '</span></a>' +
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
function loadTasks() {
  let fetchURL = '/task/all';

  $.get(fetchURL, { cursor: taskListCursor }).done(function(response) {
    // let response = JSON.parse(responseJSON);
    let taskList = $('div.' + taskListDivClassName);

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
    if (working === false) {
      working = true;
      loadTasks();
    }
  }
});

$(document).ready(function(){
  loadTasks();
});
