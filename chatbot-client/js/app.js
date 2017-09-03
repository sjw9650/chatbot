(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	$(document).ready(function(){
		
		findAllTask();
		
		$(".new-todo").keypress(function (e) {
		    if (e.which == 13){
		    	insertTask();
		    }
		});
	});
	
	// 1-3) 할 일을 등록하는 함수 (갱신 없이 글이 등록하기 위해 AJAX 사용.)
	function insertTask() {
		var todo = $(".new-todo").val();
		var todoData = {};
		todoData["content"] = todo; 

		
		if(todo == ""){
			alert("공백 입니다!!");
		} else {
			
			$.ajax({
		        type: "POST"
		        ,url: "/user/schedules"
		        ,data: JSON.stringify(todoData)
		        ,contentType : "application/json"
		        ,success: function(data){
		        	console.log(data);
		        	$(".new-todo").val("");
		            addTodoHTML(data);
		        }
		        ,error : function(data, status, err) {
		        	console.log(err);
		        }
			});
			
		}
	}
	
	
})(window);

function findAllTask(){
	$.ajax({
        type: "GET"
        ,url: "user/schedules"
        ,success: function(data){
        	var todoListSize = data.length;
        	$(".todo-list").empty();
        	data.forEach(function(item){
        		addTodoHTML(item);
        	});
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}
//todoList HTML 추가해주는 함수.
function addTodoHTML(item){
	$(".todo-list").prepend("<li class='view'>" + 
			"<div class='view'>" +
				"<input class='toggle' type='checkbox' onclick=\"checkTodoCompletion(this,\'" + item.userKey + "\',\'" + item.content +"\');\" checked/>" +
				"<label>" + item.content + "</label>" +
				"<button class='destroy' onclick=\"deleteTask(this,\'" + item.scheduleId +"\');\"></button>" +
			"</div>" + 
			"<input class='edit' value='Create a TodoMVC template'>" + 
		"</li>");
}
function deleteTask(obj, id){
	
	var todoData = {};
	todoData["scheduleId"] = id;
	
	$.ajax({
        type: "DELETE"
        ,url: "/user/schedules"
        ,data: JSON.stringify(todoData)
        ,contentType : "application/json"
        ,success: function(data){
        	$(obj).closest('li').remove();
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}