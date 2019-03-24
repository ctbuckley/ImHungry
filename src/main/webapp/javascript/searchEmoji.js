
/**
 * Javascript for the emoji button
 */


function changeEmoji(form){
		var image = document.getElementById('emoji');
		var userInput = document.getElementById('queryInput').value;
		var searchCount = document.getElementById('numResultsInput').value;
		console.log(userInput);
		if (userInput == null || userInput.length == 0 || searchCount != parseInt(searchCount) || searchCount < 1){
			return false;
		}
		else {
			image.src = "https://buildahead.com/wp-content/uploads/2017/02/happy-emoji-smaller.png";
			form.action = "/FeedMe/results";
			return true;
		}
} 
	