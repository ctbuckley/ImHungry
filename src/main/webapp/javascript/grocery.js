function sendCheckBack(element) {
	if (element.checked) {
    	console.log("Checked " + element.id);
    	console.log("Ingredient: " + element.parentNode.innerText.trim());
    	
    } else {
    	console.log("Unchecked " + element.id);
    	console.log("Ingredient: " + element.parentNode.innerText.trim());

    }
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		console.log("success");
	}
	xhttp.open("POST", "CheckGroceryItem?item=" + element.parentNode.innerText.trim(), true );
	xhttp.send();
};