(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	$(document).ready(function(){
		// 2-1) 페이지 로드시 등록된 할 일 리스트를 보여주는 메소드 호출.
		// 6-6) 새로 고침을 할 때는 항상 ALL 상태로 보여야 한다.
		findAllTask();
		
		// 1-2) 할 일 등록하기 -  커서를 두고 입력한 후 enter키를 누를 경우의 이벤트 처리.
		$(".new-todo").keypress(function (e) {
		    if (e.which == 13){
		    	insertTask();
		    }
		});
	});
	
	// 1-3) 할 일을 등록하는 함수 (갱신 없이 글이 등록하기 위해 AJAX 사용.)
	function insertTask() {
		var todo = $(".new-todo").val();
		// 1-4) 빈 문자이면 등록되지 않는 것을 처리하는 부분.
		if(todo == ""){
			alert("공백 입니다!!");
		} else {
			var todoData = {};
			todoData["todo"] = todo;
			$.ajax({
		        type: "POST"
		        ,url: "/api/todos"
		        ,data: JSON.stringify(todoData)
		        ,contentType : "application/json"
		        ,success: function(data){
		        	// 입력 후 input box 내부를 공백 처리 해줍니다.
		            $(".new-todo").val("");
		            // 추가한 내용을 list에 그려주는 함수 호출합니다.
		            addTodoHTML(data);
		            // 전체 ITEM LEFT 를 1 추가 시켜 줍니다.
		            var todoListSize = $(".todo-count strong").text();
		            $(".todo-count strong").text(parseInt(todoListSize) + parseInt(1));
		        }
		        ,error : function(data, status, err) {
		        	console.log(err);
		        }
			});
			
		}
	}
	
	
})(window);

// 2-1) 페이지 로드시, 등록된 할 일 리스트를 보여준다. 
// 6-1) ALL 버튼 클릭시 호출되는 함수.
function findAllTask(){
	$.ajax({
        type: "GET"
        ,url: "/api/todos"
        ,success: function(data){
        	var todoListSize = data.length;
        	$(".todo-list").empty();
        	data.forEach(function(item){
        		addTodoHTML(item);
        		if(item.completed == 1){
        			todoListSize -= 1;
        		}
        	});
        	// 5-1) 아직 완료하지 못한 일의 갯수를 보여준다.
        	$(".todo-count strong").text(todoListSize);
        	selectedOne();
        	$(".filters").children().first().children().first().attr("class", "selected");
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}

// todoList HTML 추가해주는 함수.
function addTodoHTML(item){
	if(item.completed == 1){
		// 2-2) prepend를 사용하여 최근에 사용한 일이 위에 오도록 배치하였습니다.
		$(".todo-list").prepend("<li class='completed'>" + 
		    						"<div class='view'>" +
										"<input class='toggle' type='checkbox' onclick=\"checkTodoCompletion(this,\'" + item.id + "\',\'" + item.completed +"\');\" checked/>" +
										"<label>" + item.todo + "</label>" +
										"<button class='destroy' onclick=\"deleteTask(this,\'" + item.id +"\');\"></button>" +
									"</div>" + 
									"<input class='edit' value='Create a TodoMVC template'>" + 
								"</li>");
	} else {
    	$(".todo-list").prepend("<li>" + 
		    						"<div class='view'>" +
										"<input class='toggle' type='checkbox' onclick=\"checkTodoCompletion(this,\'" + item.id + "\',\'" + item.completed + "\');\"/>" +
										"<label>" + item.todo + "</label>" +
										"<button class='destroy' onclick=\"deleteTask(this,\'" + item.id +"\');\"></button>" +
									"</div>" + 
									"<input class='edit' value='Rule the web'>" + 
								"</li>");
	}
}

// 3-1) 버튼을 클릭 시 이 일은 완료한 일로 상태가 변경.
function checkTodoCompletion(obj, id, completed) {

	if(completed == 0){
		completed = 1;
	} else {
		completed = 0;
	}
	
	var todoData = {};
	todoData["id"] = id;
	todoData["completed"] = completed;
	
	$.ajax({
        type: "PUT"
        ,url: "/api/todos"
        ,data: JSON.stringify(todoData)
        ,contentType : "application/json"
        ,success: function(data){
        	var todoListSize = $(".todo-count strong").text();
        	if(completed == 1){
        		// 3-3) 'completed' class를 추가.
        		$(obj).closest('li').attr("class", "completed");	
	            // 5-2) 할 일 갯수 동기화.
        		$(".todo-count strong").text(parseInt(todoListSize) - parseInt(1));
        	} else {
        		// 3-2) 리스트에 취소선을 그어주기 위해 해당 class를 삭제.
        		$(obj).closest('li').removeClass("completed");
        		$(".todo-count strong").text(parseInt(todoListSize) + parseInt(1));
        	}
        	// 해당 함수의 param을 바꿔준다.
        	var js_func = "checkTodoCompletion(this,\'" + id + "\',\'" + completed + "\');";
    	    $(obj).attr('onclick', js_func);
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}

// 4-2) 해당 글을 삭제하기 위한 함수.
function deleteTask(obj, id){
	
	var todoData = {};
	todoData["id"] = id;
	
	$.ajax({
        type: "DELETE"
        ,url: "/api/todos"
        ,data: JSON.stringify(todoData)
        ,contentType : "application/json"
        ,success: function(data){
        	// 5-1) 미완료  할 일 갯수 호출 함수.
        	countTask();
        	// 해당 할 일을 HTML상에서 삭제.
        	$(obj).closest('li').remove();
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}

// 5-1) 할 일 전체 갯수 표시 함수.
function countTask(){

	$.ajax({
        type: "GET"
        ,url: "/api/todos/count"
        ,contentType : "application/json"
        ,success: function(data){
        	$(".todo-count strong").text(data);
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}
// All, Acitve, Completed 버튼에서 하나의 버튼만 class="selected" 하나만 가지게 하기 위해 class 속성 삭제하는 함수.
function selectedOne(){
	var nodes =  $(".filters").children();
	nodes.each(function() {
		$(this).find("a").removeAttr("class");
	});
}
// 6-2) Active 클릭시 호출되는 함수.
function findActiveTask(obj){
	$.ajax({
        type: "GET"
        ,url: "/api/todos/active"
        ,success: function(data){
        	var todoListSize = data.length;
        	$(".todo-list").empty();
        	data.forEach(function(item){
        		addTodoHTML(item);
        	});
        	$(".todo-count strong").text(todoListSize);
        	selectedOne();
        	$(obj).attr("class", "selected");	
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}
// 6-3) Completed 클릭시 호출되는 함수.
function findCompletedTask(obj){
	$.ajax({
        type: "GET"
        ,url: "/api/todos/completed"
        ,success: function(data){
        	var todoListSize = data.length;
        	$(".todo-list").empty();
        	data.forEach(function(item){
        		addTodoHTML(item);
        	});
        	$(".todo-count strong").text(0);
        	selectedOne();
        	$(obj).attr("class", "selected");	
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}
// 7-1) 클릭시 이미 완료한 일을 리스트에서 삭제하는 함수.
function clearCompletedTask(){

	$.ajax({
        type: "DELETE"
        ,url: "/api/todos/clear"
        ,success: function(data){
        	findAllTask();
        	//$(".todo-count strong").text(0);
        }
        ,error : function(data, status, err) {
        	console.log(err);
        }
	});
}
