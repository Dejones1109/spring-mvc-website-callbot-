var conversationTable;

$(document).ready(function() {

	conversationTable = $("#conversationTable").DataTable({
		columnDefs:[{targets:0, render:function(data){
		      return moment(data).format('DD/MM/YYYY HH:mm:ss');
		}}],
		"order": [[ 0, "desc" ]],
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.21/i18n/Vietnamese.json"
        }, 
        "searching": false,
		"scrollX": false,
	});
		
});

$('#btnCall').on('click', function(e){
	
	var form = $("#createAndCallForm")[0];
    var isValid = form.checkValidity();
    if (!isValid) {
        e.preventDefault();
        e.stopPropagation();
    }else{
    	$('#callModal').modal('hide');
    	$.ajax({
    		type: $('#createAndCallForm').attr('method'),
    		url: $('#createAndCallForm').attr('action'),
    		data: $('#createAndCallForm').serialize(),
    		timeout : 100000,
    		success: function (data) {
    			var obj = JSON.parse(data);
    				
    			if (obj.status == 0) {
    					
    				conversationTable.row.add([
    					obj.data.createTime,
    					'<a href="detail?conversation_id='+obj.data.conversationId+'">'+obj.data.conversationId+'</a></td>',
    					obj.data.customer,
    					obj.data.status
    				]).draw();
    					
    				alert(obj.message)
    			}else {
    				alert(obj.message)
    			}
    				
    		},
    		complete: function () {
    		}
    	});
    }
    form.classList.add('was-validated');
	
	/*
	
	*/
	
		
});

$('#btnUpload').on('click', function(e){
	var form = $("#uploadFileForm")[0];
    var isValid = form.checkValidity();
    if (!isValid) {
        e.preventDefault();
        e.stopPropagation();
    }else{
    	$('#uploadFileModal').modal('hide');
    	var myform = document.getElementById("uploadFileForm");
		var fd = new FormData(myform);
    	$.ajax({
    		type: $('#uploadFileForm').attr('method'),
			url: $('#uploadFileForm').attr('action'),
			data: fd,
			cache: false,
		    processData: false,
		    contentType: false,
    		timeout : 100000,
    		success: function (data) {
    				
    			
    			var obj = JSON.parse(data);
    				
    			if (obj.status == 0) {
        			alert(obj.message)
        			location.reload();
    			}else {
    				alert(obj.message)
    			}
    				
    		},
    		complete: function () {
    		}
    	});
    }
    form.classList.add('was-validated');
	
})

$( "#btnSearch" ).click(function() {
	$.ajax({
		type: $('#searchConversationForm').attr('method'),
		url: $('#searchConversationForm').attr('action'),
		data: $('#searchConversationForm').serialize(),
		timeout : 100000,
		success: function (data) {
			var obj = JSON.parse(data);
							
			if (obj.status == 0) {
				
				
				$('#conversationTable tbody').empty();
				
				conversationTable.destroy();
				conversationTable = $("#conversationTable").DataTable({
					
					data: jQuery.parseJSON(JSON.stringify(obj.data)),
					      
					columns:[
						{data: 'createTime'},
					    {data: 'conversationId'},
					    {data: 'customer'},
					    {data: 'status'}
					],
					
					columnDefs:[
						{targets:0, render:function(data){
					      return moment(data).format('DD/MM/YYYY HH:mm:ss');
						}},
						
						{targets:1, render:function(data){
  					      return '<a href="detail?conversation_id='+data+'">'+data+'</a></td>';
  						}}
					],
					
					"order": [[ 0, "desc" ]],
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/1.10.21/i18n/Vietnamese.json"
			        }, 
			        
			        "searching": false,
					"scrollX": false,
				});
			
			}else {
				alert(obj.message);
			}
				
		},
		complete: function () {
		}
	});
			
});

$( "#export_file" ).click(function() {
	
	var form = $("#exportFileForm")[0];
	$("#exportFileMsisdn").val($("#msisdn").val());
	$("#exportFileStartDate").val($("#startDate").val());
	$("#exportFileEndDate").val($("#endDate").val());
	
	form.submit();	
});


	