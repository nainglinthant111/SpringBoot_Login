 

function validateEmail(email) {
var emailRegex = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,3})$/;
 return emailRegex.test(email);
}