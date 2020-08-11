/* Constants */
const taskCreateFormId = "taskCreateForm";

/* 
 * Function to insert a hidden input tag with browser timezone
 * offset to task creation form.
 */
function insertTimezoneOffsetToForm() {
  let offset = new Date();

  /* Make Offset input */
  let offsetInput = document.createElement('input');
  offsetInput.type = 'hidden';
  offsetInput.name = 'clientTzOffsetInMins';
  offsetInput.value = offset.getTimezoneOffset();
  
  document.querySelector('form#' + taskCreateFormId).appendChild(offsetInput);
}

/*
 * Functions to be called after
 * the window has loaded
 */
window.onload = () => {
  insertTimezoneOffsetToForm();
}
