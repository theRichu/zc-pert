$("a").live("mousedown", function(event){
	if(event.button == "2") {
		chrome.extension.sendRequest({"request":event.srcElement.innerText});
	}
});