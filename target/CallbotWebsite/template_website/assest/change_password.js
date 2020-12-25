$('#btnChangePassword').on('click', function(e){
	
	var form = $("#changePasswordForm")[0];
    var isValid = form.checkValidity();
    if (!isValid) {
        e.preventDefault();
        e.stopPropagation();
    }else{
    	$('#settings_modal').modal('hide');
    	$.ajax({
    		type: $('#changePasswordForm').attr('method'),
    		url: $('#changePasswordForm').attr('action'),
    		data: $('#changePasswordForm').serialize(),
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
    }
    form.classList.add('was-validated');
		
});


	