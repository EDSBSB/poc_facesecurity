

function carregaMenu( start){
	console.log('>>>>carregaMenu<<<<');
	
	$.get( "/assets/template/mainMenu.html", function( data ) {
	  $( "#sidenav-main" ).html( data );
	});

	if( start){
		start();
	}
}