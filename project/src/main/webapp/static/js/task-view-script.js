/* Transforms integer deadline to Human Readable String */
function formReadableDeadlineString() {
  let deadline = new Date(parseInt($('#taskDeadline').text()));
  let deadlineString = deadline.toLocaleString('en-US',
      { hour: 'numeric', minute: 'numeric', hour12: true });
  deadlineString += ', ' + deadline.toDateString();
  $('#taskDeadline').text(deadlineString);
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

$(document).ready(function() {
  formReadableDeadlineString();
});
