
var accountTable;

$(document).ready(function() {
	accountTable = $("#accountTable").DataTable({

		"order": [[ 1, "asc" ]],
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.21/i18n/Vietnamese.json"
        }
	});
	
	
		
});

$('#btnCreate').on('click', function(e){
		
	var form = $("#createAccountForm")[0];
    var isValid = form.checkValidity();
    if (!isValid) {
        e.preventDefault();
        e.stopPropagation();
    }else{
    	$('#createAccountModal').modal('hide');
    	$.ajax({
    		type: $('#createAccountForm').attr('method'),
    		url: $('#createAccountForm').attr('action'),
    		data: $('#createAccountForm').serialize(),
    		timeout : 100000,
    		success: function (data) {
    			var obj = JSON.parse(data);
    				
    			if (obj.status == 0) {
    				var rowNode = accountTable.row.add([
    					obj.data.userName,
    					obj.data.roleName,
    					obj.data.status,
    					'<a type="button" class="edit" data-toggle="modal" data-target="#editAccountModal"><i class="fa fa-edit"></i> Sửa</a>&nbsp&nbsp&nbsp <a type="button" class="reset" data-toggle="modal" data-target="#resetPasswordModal"><i class="fa fa-refresh"></i> Đặt lại mật khẩu</a>',
    					obj.data.id
    				]).draw().node();
    				
    				$(rowNode).find('td').eq(4).addClass('HiddentAccountId').css({"display": "none"});
    				
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

});


//edit

var row;
$('#accountTable tbody').on('click','a.edit',function() {
	
	 row = accountTable.row($(this).parents('tr'));
	 
	 var data = row.data();
	 var username = data[0];
	 var role = data[1];
	 var status = data[2];
	 var id = data[4];
	 	 	 
	 // set value
	 $('#edit_user_name').val(username);
	 $('#edit_id').val(id);
	 
	 $('#edit_role option:selected').removeAttr('selected');
	 $("#edit_role option").filter(function() {
	 	return this.text == role; 
     }).attr('selected', true);
	 
	 $('#edit_status option:selected').removeAttr('selected');
	 $("#edit_status option").filter(function() {
		 return this.text == status; 
	 }).attr('selected', true);

})

$('#btnEdit').on('click', function(e){
		
	var form = $("#editAccountForm")[0];
    var isValid = form.checkValidity();
    if (!isValid) {
        e.preventDefault();
        e.stopPropagation();
    }else{
    	$('#editAccountModal').modal('hide');
    	$.ajax({
    		type: $('#editAccountForm').attr('method'),
    		url: $('#editAccountForm').attr('action'),
    		data: $('#editAccountForm').serialize(),
    		timeout : 100000,
    		success: function (data) {
    			var obj = JSON.parse(data);
    				
    			if (obj.status == 0) {
    				var rowNode = row.data([
    					obj.data.userName,
    					obj.data.roleName,
    					obj.data.status,
    					'<a type="button" class="edit" data-toggle="modal" data-target="#editAccountModal"><i class="fa fa-edit"></i> Sửa</a>&nbsp&nbsp&nbsp <a type="button" class="reset" data-toggle="modal" data-target="#resetPasswordModal"><i class="fa fa-repeat"></i> Đặt lại mật khẩu</a>',
    					obj.data.id
    				]).draw().node();
    				
    				$(rowNode).find('td').eq(4).addClass('HiddentAccountId').css({"display": "none"});
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

});

//reset pass

$('#accountTable tbody').on('click','a.reset',function() {		 
	 var data = accountTable.row($(this).parents('tr')).data();
	 var id = data[4];
	 	 	 
	 // set value
	 $('#reset_id').val(id);

})

$('#btnResetPassword').on('click', function(e){
	
	$('#resetPasswordModal').modal('hide');
	$.ajax({
		type: $('#resetPasswordForm').attr('method'),
		url: $('#resetPasswordForm').attr('action'),
		data: $('#resetPasswordForm').serialize(),
		timeout : 100000,
		success: function (data) {
			var obj = JSON.parse(data);
				
			if (obj.status == 0) {
				alert(obj.message)
			}else {
				alert(obj.message)
			}
				
		},
		complete: function () {
		}
	});

});

	