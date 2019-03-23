/**
 * 
 */
function manageList(form){
	var userInput = document.getElementById('listName').value;
	console.log(userInput);
	if (userInput == null || userInput.length == 0){
		
	}
	else{
		
		form.action = "/FeedMe/listManagement";
	}
	

} 