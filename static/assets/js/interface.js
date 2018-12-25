

function carregaMenu( start){
	console.log('>>>>carregaMenu<<<<');
	
	$.get( "/assets/template/mainMenu.html", function( data ) {
	  $( "#sidenav-main" ).html( data );
	});

	if( start){
		start();
	}
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
