$(function() {
	var caption = getQueryStrValByName("caption");
	var content = getQueryStrValByName("content");
	var userType = getQueryStrValByName("user_type");
	$(".item-title").html(caption);
	$(".item-subtitle").html(content);
});