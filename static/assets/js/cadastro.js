console.log('============');

function incluirProcurado(){
	console.log('>>>>>>>>>>>.');
	
	var fl = $('#file')[0];
	var identificador = $('#identificador').val();
	var nome = $('#nome').val();
    
    var formData = new FormData();
    formData.append('nome', nome);
    formData.append('identificador', identificador); 
    formData.append('file', fl.files[0]);

    $.ajax({
        url: '/upload', //window.location.pathname,
        type: 'POST',
        data: formData,
        success: function(data) {
            alert(data)
        },
        cache: false,
        contentType: false,
        processData: false,
        xhr: function() { // Custom XMLHttpRequest
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) { // Avalia se tem suporte a propriedade upload
                myXhr.upload.addEventListener('progress', function() {
                }, false);
            }
            return myXhr;
        }
    });
}

function carrega(){
	console.log('>>>carregando<<<');
	
	$.get( "/bustaTodosProcurados", function( dado ) {
		console.log( dado );
		
		var table = document.getElementById("tabela");

		for(var i=0; i< dado.length; i++ ){
			var row = table.insertRow(1+i);
	
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			var cell4 = row.insertCell(3);
	
			cell1.innerHTML = dado[i].nome;
			cell2.innerHTML = dado[i].identificador;
			cell3.innerHTML = " ???? ";
			cell4.innerHTML = "<div class='avatar-group'>\n" + 
			"    <a href='#' class='avatar avatar-sm' data-toggle='tooltip'\n" + " onclick='return showImagem(\""+dado[i].identificador+"\");' \n"+
			"        data-original-title='Ryan Tompsonnnnnnn1'> <img\n" + 
			"        alt='Image placeholder'\n" + 
			"        src='/procurado/foto?id="+dado[i].identificador+"'\n" +
			"        class='rounded-circle'>\n" + 
			"    </a>\n" + 
			"</div>";
			
		}
		
	}, "json");
}
function showImagem(id){
	console.log('xxx: '+ id);
	console.log( $('#fotoId').src );
	$('#fotoId').attr('src', "/procurado/foto?id="+id);
	$('#myModal').modal('toggle');
}
