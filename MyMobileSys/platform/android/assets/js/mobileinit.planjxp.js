var pagesInit = [];

<%-- En el evento mobileinit de JQueryMobile dejamos la lógica necesaria para activar los eventos Click de los botones SelectMenu
	 Esto se realiza debido a que los botones en los SelectMenu no se crean hasta la primera vez que se despliega la pagina que los contiene. 
--%>
<%
function void initControls(Object controls) {
	for(Object control: controls ) {
		if(control.isEnhancejq()) {
		    if( control.getType().equals("Text") )
				println("$('#" + control.getName() + "').textinput();");
			else if( control.getType().equals("Button") ) 
				println("$('#" + control.getName() + "').button();");
			else if( control.getType().equals("Check") ) 
				println("$('#" + control.getName() + "').checkboxradio();");
			else if( control.getType().equals("RadioGroup") ) 
				println("$('#" + control.getName() + "').controlradio();");
			else if( control.getType().equals("Combo") )
				println("$('#" + control.getName() + "').selectmenu();");
			else if( control.getType().equals("List") )
				println("$('#" + control.getName() + "').listview();");
			else if( control.getType().equals("Image") )
				println("");
			else if( control.getType().equals("Form") )
				println("$('#" + control.getName() + "').listview();");
			else if( control.getType().equals("Grid") )
				println("");
			else if( control.getType().equals("Html") )
				println("");
			else if( control.getType().equals("Container") )
				initControls(control.getControls());
		}
	}
}
%>
$(document).on( "mobileinit", function() {
	
	$(document).on( "pagecontainerbeforetransition", function( event, ui ) {
		if (ui.prevPage) {
			//Informamos a la parte nativa que el combobox(Si se abre como página) se ha cerrado
			var id = ui.prevPage.attr('id');
			
			if(id.indexOf("-dialog") > 0) {
				//alert("CERRADO: " + id.substr(0, id.length - 7));
				Android.setIsComboOpen(false);
				Android.setComboOpen("");
			}
		}
 	});
	
	$(document).on( "pagecontainercreate", function( event, ui ) {
		<% for(Object panel: screen.getPanels() ) { %>
			$('#<%=panel.getName()%>').page();
		<% } %>
		<% for(Object panel: screen.getPanelsMenu() ) { %>
			$('#<%=panel.getName()%>').panel();
			<% initControls(panel.getControls()); %>
		<% } %>
	} );
	
	$.fn.hasAttr = function(name) {  
	   return this.attr(name) !== undefined;
	};
	
	$.mobile.defaultPageTransition = 'none';
});
