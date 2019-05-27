$(document).ready(function(){
    $('.sidenav').sidenav();
    $('.tooltipped').tooltip();
    $('.modal').modal();
    $('select').formSelect();
    
    
    $(".addbill_form_container").submit(function(event){
    	event.preventDefault();
    	var form = $(this);
    	var url = form.attr('url');
        var method = form.attr('method');
        var product = form.attr('product');
        console.log(product);
        $.ajax({
        	type: method,
        	url: url,
        	data: form.serialize(),
        	processData: false,
        	beforeSend: function(){
        		$("#loader"+product).show();
            	$("#subformbill"+product).addClass('disabled');
            	$("#inputquantity"+product).attr('disabled','disabled');
        	},
        	success: function(response){
        		$("#modal-content"+product).html(response);
        	},
        	error: function(response){
        		console.log("ERROR OCCURRED!");
        	}
        });
    });   
});

function deleteRow(form_id, label){
	Swal({
		  title: 'Voulez-vous vraiment supprimer '+label+'?',
		  text: "Attention!",
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: '<i class="fas fa-ban"></i> Annuler',
		  confirmButtonText: '<i class="fas fa-radiation-alt"></i> Oui, supprimer '+label+'!'
		}).then((result) => {
		  if (result.value) {
		    $("#"+form_id).submit();
		  }
		})
}


