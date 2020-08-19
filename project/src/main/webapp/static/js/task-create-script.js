/* Constants */
const clientTzOffsetInMins = new Date().getTimezoneOffset();
const taskCreateFormId = "taskCreateForm";
const deadlineInputId = "deadlineInput";
const deadlineHelpId = "deadlineHelp";

/*
 * Function to insert a hidden input tag with browser timezone
 * offset to task creation form.
 */
function insertTimezoneOffsetToForm() {
  /* Make Offset input */
  let offsetInput = document.createElement('input');
  offsetInput.type = 'hidden';
  offsetInput.name = 'clientTzOffsetInMins';
  offsetInput.value = clientTzOffsetInMins;

  $('form#' + taskCreateFormId).append(offsetInput);
}

/*
 * Function to set Initial deadline to 1 hr past current time
 */
function setInitialDeadline() {
  let initialDeadline = new Date(Date.now() - clientTzOffsetInMins * 60000 + 3600 * 1000);
  $('#' + deadlineInputId).val(initialDeadline.toISOString().slice(0, 16));
  updateDeadlineHelp();
}

/*
 * Updates Deadline Help String to show how much time till deadline.
 */
function updateDeadlineHelp() {
  let timeDiff = new Date($('#' + deadlineInputId).val()).getTime() - new Date().getTime();
  timeDiff = Math.floor(timeDiff / 1000); // Convert to Seconds
  let deadlineHelpMessage = 'More than ';

  function numberEnding (number) {
      return (number > 1) ? 's' : '';
  }

  if (timeDiff >= 31536000) {
    let year = Math.floor(timeDiff / 31536000);
    deadlineHelpMessage += year + ' year' + numberEnding(year);
  } else if (timeDiff >= 604800) {
    let week = Math.floor(timeDiff / 604800);
    deadlineHelpMessage += week + ' week' + numberEnding(week);
  } else if (timeDiff >= 86400) {
    let day = Math.floor(timeDiff / 86400);
    deadlineHelpMessage += day + ' day' + numberEnding(day);
  } else if (timeDiff >= 3600) {
    let hour = Math.floor(timeDiff / 3600);
    deadlineHelpMessage += hour + ' hour' + numberEnding(hour);
  } else if (timeDiff >= 60) {
    let minute = Math.floor(timeDiff / 60);
    deadlineHelpMessage += minute + ' minute' + numberEnding(minute);
  } else if (timeDiff > 0) {
    let second = Math.floor(timeDiff);
    deadlineHelpMessage += second + ' second' + numberEnding(second);
  } else {
    deadlineHelpMessage = 'This deadline has passed';
  }
  if (timeDiff > 0) {
    deadlineHelpMessage += ' till deadline';
  }

  $('#' + deadlineHelpId).text(deadlineHelpMessage);
}

/*
 * Functions to be called after the window has loaded,
 * when the document is ready.
 */
$(document).ready(function() {
  insertTimezoneOffsetToForm();
  setInitialDeadline();

  $('#' + deadlineInputId).on('change', function() {
    updateDeadlineHelp();
  });
});
