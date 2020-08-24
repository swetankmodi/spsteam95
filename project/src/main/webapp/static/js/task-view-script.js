/* Transforms integer deadline to Human Readable String */
function formReadableDeadlineString() {
  let deadline = new Date(parseInt($('#taskDeadline').text()));
  let deadlineString = deadline.toLocaleString('en-US',
      { hour: 'numeric', minute: 'numeric', hour12: true });
  deadlineString += ', ' + deadline.toDateString();
  $('#taskDeadline').text(deadlineString);
}

$(document).ready(function() {
  formReadableDeadlineString();
});