console.log('============');

function uploadAndStartMatch(){
	console.log('>>>>>>>>>>>.');
	
	var fl = $('#file')[0];
    
    var formData = new FormData();
    formData.append('file', fl.files[0]);

    $.ajax({
        url: '/uploadAndStartMatch', //window.location.pathname,
        type: 'POST',
        data: formData,
		success : function(data) {
			// alert(data)
			toast(data);
			var arr = [ 
				{nome:'Foto', qtd:3, status: 'pendente', foto:['ciro', 'lula', 'Bolsonaro']}
			];
			var temp, item, a, i;
			temp = document.getElementsByTagName("template")[0];
			item = temp.content.getElementById("nome");
			var table = document.getElementById("tabela");
			for (i = 0; i < arr.length; i++) {
				var t = document.getElementsByTagName("template")[0];
				td = t.content.querySelectorAll("td");
				td[0].textContent = arr[i].nome;
				td[1].textContent = arr[i].qtd;
				td[2].textContent = arr[i].status;

				var tb = document.getElementsByTagName("tbody");
				var clone = document.importNode(t.content, true);
				tb[0].appendChild(clone);
			}
		},
        cache: false,
        contentType: false,
        processData: false,
        xhr: function() { // Custom XMLHttpRequest
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) { // Avalia se tem suporte a propriedade upload
                myXhr.upload.addEventListener('progress', function() {
                	console.log('enviando....');
                }, false);
            }
            return myXhr;
        }
    });
}

function carrega(){
	console.log('>>>carregando<<<');
	
	
	var arr = [ 
		{nome:'Foto', qtd:3, status: 'concuido', foto:['ciro', 'lula', 'Bolsonaro']}, 
		{nome:'Video', qtd:1,status: 'concuido', foto:['ciro', 'lula', 'Bolsonaro']} 
	];
	var temp, item, a, i;
	temp = document.getElementsByTagName("template")[0];
	item = temp.content.getElementById("nome");
	var table = document.getElementById("tabela");
	for (i = 0; i < arr.length; i++) {
		var t = document.getElementsByTagName("template")[0];
		td = t.content.querySelectorAll("td");
		td[0].textContent = arr[i].nome;
		td[1].textContent = arr[i].qtd;
		td[2].textContent = arr[i].status;

		var tb = document.getElementsByTagName("tbody");
		var clone = document.importNode(t.content, true);
		tb[0].appendChild(clone);
	}
	
	return;
	
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
function toast(msg) {
	
	console.log( msg );
	  // Get the snackbar DIV
	  var x = document.getElementById("snackbar");

	  // Add the "show" class to DIV
	  x.className = "show";
	  
	  x.innerHTML = msg;

	  // After 3 seconds, remove the show class from DIV
	  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
	}
