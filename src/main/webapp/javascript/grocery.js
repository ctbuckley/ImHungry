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
	
	var ingredientUnmerged = document.getElementById(element.id).getAttribute("data-original");
	
	console.log(element.id)
	console.log(ingredientUnmerged)
	
	xhttp.open("POST", "CheckGroceryItem?item=" + ingredientUnmerged, true );
	xhttp.send();
};