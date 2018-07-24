<%-- Plantilla JXP mms.planjxp.js
Descripci�n: Se generan las rutinas de puente entre los eventos de los controles jquery y los
controladores nativos de la aplicaci�n
--%>
<%-- Cuerpo de la plantilla --%> 
<%
function void genCallController(Object control) {
	if( control.getOnclick() != null && !control.getType().equals("Custom")) { 
		if( control.getType().equals("List")) { 
			println("$(\"#" + control.getName() + "\").on('click', 'li a', function(event) {");
	 		println("event.preventDefault();");
			println("Android.controllerEvent(\"" + control.getName() + "\", TE_CLICK, $(this).closest('li').index());");
		} else if( control.getType().equals("Html") ) {
			println("$( \"#" + control.getName()+ "\" ).click(function( event ) {");
	 		println("event.preventDefault();");
			println("Android.controllerEvent(\"" + control.getName() + "\", TE_CLICK, null);");
		} else if( control.getType().equals("Combo") ) {
			println("$( \"#" + control.getName()+ "\" ).change(function( event ) {");
	 		println("event.preventDefault();");
			println("Android.controllerEvent(\"" + control.getName() + "\", TE_CLICK, null);");
		} else if( control.getType().equals("Check") ) {
			println("$( \"#" + control.getName()+ "\" ).click(function( event ) {");
	 		println("event.preventDefault();");
	 		println("Android.updateCheckControl( event.target.id, $(event.target).prop('checked') );");
			println("Android.controllerEvent(event.target.id, TE_CLICK, null);");
		} else if( !control.getType().equals("Custom") ) {
			println("$( \"#" + control.getName()+ "\" ).click(function( event ) {");
	 		println("event.preventDefault();");
			println("Android.controllerEvent(event.target.id, TE_CLICK, null);");
		}
		println("});");
	}
	
	
	if( control.getOnfocus() != null) {
		println("$( \"#" + control.getName()+ "\" ).focus(function( event ) {");
 		println("event.preventDefault();");
		println("Android.controllerEvent(event.target.id, TE_FOCUS, null);");
		println("});");
	} 
	
	if( control.getOnfocusout() != null) {
		println("$( \"#" + control.getName()+ "\" ).focusout(function( event ) {");
 		println("event.preventDefault();");
		println("Android.controllerEvent(event.target.id, TE_FOCUSOUT, null);");
		println("});");
	}
	
	/*
	if( control.getType().equals("Grid") ) {
		for(Object b: control.getBlocks() ) { 
			for(Object controlg: b.getControls()) {
				genCallController(controlg);
			}
		}
	} else if( control.getType().equals("Container") ) {
		for(Object c: control.getControls()) {
			genCallController(c);
		}
	}
	*/
}

%>
$( document ).ready(function() {

	numeral.language('common', {
	    delimiters: {
	        thousands: '.',
	        decimal: ','
	    },
	    abbreviations: {
	        thousand: 'k',
	        million: 'm',
	        billion: 'b',
	        trillion: 't'
	    },
	    ordinal : function (number) {
	        return number === 1 ? 'er' : '�me';
	    },
	    currency: {
	        symbol: '$'
	    }
	});

	numeral.language('common'); 
    initEvents();
    Android.initScreen();
    
<% if(screen.getOnLoad() != null) { %>
   	Android.controllerEvent("<%=screen.getName()%>", TE_LOAD, null);
<% } %>
});

function initEvents() {
	//Informamos a la interfaz nativa todos los eventos beforeshow en los page
	$( ":mobile-pagecontainer" ).pagecontainer({ beforeshow: function( event, ui ) {
																Android.controllerEvent(ui.toPage.attr('id'), TE_BEFORESHOW, null);
												}});
												
	//Se define el evento change solo para los datebox
	$("input:text[data-role='datebox']").change(function(event) {
		Android.updateTextControl( event.target.id, $(event.target).val() );
	});

/*
	$('input, textarea').on('focus', function (e) {
		$('[data-role=header],[data-role=footer]').css('position', 'fixed');
	}).on('blur', function (e) {
		$('[data-role=header],[data-role=footer]').css('position', 'fixed');
		//force page redraw to fix incorrectly positioned fixed elements
		setTimeout( function() {
			window.scrollTo( $.mobile.window.scrollLeft(), $.mobile.window.scrollTop() );
		}, 20 );
	});
	*/
	
	//Evento focusout por defecto para todos los input text que no sean datebox
	$("input:text,input[type='number'],input[type='password'],input[type='email'],input[type='tel']").focusout(function(event) {
		var txt = $(this);
		
		if(txt.data('role') != 'datebox') {
			var valunformat = '';
			var mmsFormat = txt.data('format'); 
			
			if(txt.attr("type") == 'number') { // En caso que el input text sea de tipo number se formnatea de acuerdo al atributo data-format
				if(mmsFormat) {		
					txt.val(numeral(txt.val()).format(mmsFormat));
					valunformat = numeral().unformat(txt.val());
				} else {
					valunformat = txt.val();
				}
			} else {
				valunformat = txt.val();
			}
			
			Android.updateTextControl(event.target.id, valunformat);
		}
	});
	
	$("input:text,input[type='number'],input[type='password'],input[type='tel']").focusin(function(event) {
		var txt = $(this);
		var mmsFormat = txt.data('format'); 
		
		if(txt.attr("type") == 'number') {
			if(mmsFormat && txt.val().trim().length > 0) {
				txt.val(numeral().unformat(txt.val()));
			}
		}
	});    	
    	
    	
    $("input:checkbox").change(function( event ) {
		Android.updateCheckControl( event.target.id, $(event.target).prop("checked") );
	});
	
    $("input:radio").change(function( event ) {
		Android.updateRadioControl(event.target.id, $(event.target).prop("checked") );
	});
	
	var sels = $("select");
	
	sels.change(function( event ) {
		Android.updateComboControl(event.target.id, $(event.target).prop("selectedIndex")-1 );
	});
	
	sels.on("selectmenucreate", function(event, ui) {
		var id = event.target.id;
		
		//Cada vez que se abre un selectmenu informamos a la parte nativa si hay un combo abierto
		//y cual combo es el que est� abierto
		//Esto sirve por ejemplo para que la parte nativa pueda cerrar un selectmenu
		//en caso que se presiones el bot�n BACK o ESCAPE
		$("#" + id + "-button").on("click", function(event) {
			//alert("ABIERTO: " + this.id.substr(0, this.id.length - 7));
			Android.setIsComboOpen(true);
			Android.setComboOpen(this.id.substr(0, this.id.length - 7));
		});
			
		//Cada vez que se cierra un popup de un selectmenu
		//informamos a la parte nativa que se ha cerrado el combo		
		$("#" + id + "-listbox").on("popupafterclose", function(event) {
			Android.setIsComboOpen(false);
			Android.setComboOpen("");
		}); 
		
	});
	
	<%for(Object control: xmlControls ) {
	genCallController(control);
  } %>   
} 