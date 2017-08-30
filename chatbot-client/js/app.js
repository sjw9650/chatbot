(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	$(document).ready(function(){
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
		todoData["content"] = "reply_msg"; 
		todoData["user_key"] = 'fGsm2mpvKZCP';
		todoData["contents"] = todo;
		
		if(todo == ""){
			alert("공백 입니다!!");
		} else {
			
			$.ajax({
		        type: "POST"
		        ,url: "/message"
		        ,data: JSON.stringify(todoData)
		        ,contentType : "application/json"
		        ,success: function(data){
		        	
		        	console.log(data);
		        	
		        }
		        ,error : function(data, status, err) {
		        	console.log(err);
		        }
			});
			
		}
	}
	
	
})(window);
