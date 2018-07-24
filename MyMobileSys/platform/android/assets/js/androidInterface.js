var uihtmlclick = function( event ) {
							Android.controllerEvent($(this).attr('id'), TE_CLICK, null);
						};
				
function android_init() {
}

function scrollToControl(id, idheader) {
	var el = $('#' + id);
	var pos = el.position().top;
	var h = 0;
	
	if(idheader != '') {
		h = $('#' + idheader).outerHeight(true);
	}
	
	$.mobile.silentScroll(pos - h);
}

function setVisible(id, visible) {
	var el = $('#' + id);
	
	if(el.is('select')) {
		el.closest('.ui-select').hide();
	}
}

//setText para botones
function setTextButton(id, text) {
	$('#' + id).val(text).button('refresh');
}

//funci�n para asignar texto a un control
function setTextControl(idcontrol, text) {
	var t = $('#' + idcontrol);
	
	if(t.hasAttr('data-role')) {
		if(t.attr('data-role') == 'datebox') {
			if(text == '')
				t.val('');
			else 
				t.datebox('setTheDate', text);
		}
	} else {
		t.val(text);
	}
}
 
//funcion para cambial label
function setLabelControl(idcontrol, text) {
	var lbl = $('#' + idcontrol);
	
	lbl.html(text);
}

//funci�n para asignar html a un control HTML
function setHtmlControl(idcontrol, shtml) {
	$('#' + idcontrol).html(shtml);
}

//funci�n para activar un check
function setCheckControl(idcontrol, state) {
	$('#' + idcontrol).prop( "checked", state ).checkboxradio( "refresh" );
}

//funci�n para chequear/deschequear un radio
function setCheckRadioControl(idcontrol, state) {
	var nameRG =  $('#' + idcontrol).prop('name');
	
	$('#' + idcontrol).prop( "checked", state ).checkboxradio( "refresh" );
	$("input:radio[name=" + nameRG + "]").checkboxradio("refresh");
}

//funcion para seleccionar un elemento en un selectmenu
function setComboSelectedIndex(idcontrol, index) {
	var sm = $('#' + idcontrol);
	
	sm.prop("selectedIndex", index);
	try {
		sm.selectmenu("refresh");
	}catch(e) {}
	
}

//funci�n para hacer transici�n entre p�ginas
function changePage(pageTarget) {
	var id = pageTarget;
	var pcont = $( ":mobile-pagecontainer" );
	
	if(id.substr(0,1) == "#") id = id.substr(1);
	
	if(pcont.pagecontainer("getActivePage").attr('id') != id)
  		pcont.pagecontainer( "change", pageTarget );  															    
}

//funci�n para agregar items a un selectmenu
function addItemSelectMenu(idcontrol, items) {
	var sm = $('#' + idcontrol);
	sm.append(items);
}

//funci�n para configurar estilos css en los widgets jquery
//debe recibir un string con el CSS sin los {}
function setCSS(idcontrol, cssStyle) {
	var objcss = jQuery.parseJSON('{' + cssStyle + '}');
	
	
	$('#' + idcontrol).parent().css(objcss);
}

//funci�n para agregar items a un listview
function addItemListView(idcontrol, items) {
	var sm = $('#' + idcontrol);
	sm.append(items);
	try {
		sm.listview('refresh');
	}catch(e) {
	}
}

//para eliminar items en un listview
function removeAllListview(id) {
	var lv = $('#' + id);
	lv.empty();
	try {
		lv.listview('refresh');
	}catch(e) {}
}

//para desactivar boton
function disableButton(id) {
	var lv = $('#' + id);
	lv.button("disable");
}

//para activar boton
function enableButton(id) {
	var lv = $('#' + id);
	lv.button("enable");
}

//para desactivar check
function disableCheck(id) {
	var lv = $('#' + id);
	lv.checkboxradio("disable");
}

//para activar check
function enableCheck(id) {
	var lv = $('#' + id);
	lv.checkboxradio("enable");
}


//para desactivar input text
function disableText(id) {
	var lv = $('#' + id);

	if(lv.hasAttr('data-role')) {
		if(lv.attr('data-role') == 'datebox')
			lv.datebox('disable');
			lv.datebox('refresh');
	} else {
		lv.textinput( 'disable' );
		lv.textinput('refresh');
	}
}

//para activar input text
function enableText(id) {
	var lv = $('#' + id);
	
	if(lv.hasAttr('data-role')) {
		if(lv.attr('data-role') == 'datebox')
			lv.datebox('enable');
			lv.datebox('refresh');
	} else {
		lv.textinput('enable');
		lv.textinput('refresh');
	}
}

//para desactivar input select
function disableSelect(id) {
	var lv = $('#' + id);
	lv.selectmenu('disable');
}

//para activar input select
function enableSelect(id) {
	var lv = $('#' + id);
	lv.selectmenu('enable');
}

//Para agregar un dialogo a la p�gina
function createDialog(dialogcode, id) {
	$('#' + id).remove();
    $( ":mobile-pagecontainer" ).pagecontainer("getActivePage").append(dialogcode);
    $('#' + id).popup();
    $('#' + id + '_headerdialog').toolbar();
    //$( '#' + id ).on( "popupafterclose", function( event, ui ) { Android.closeDialog(id) } );
}

//Para mostrar un popup
function showDialog(id) {
    $('#' + id).popup("open");
}

//Para cerrar un popup
function closeDialog(id) {
	var d = $('#' + id); 
	
    d.popup('close');
    d.popup('destroy');
    d.remove();
	
	Android.closeDialog(id);
}

//Para agregar un bot�n a la p�gina
function createButton(buttoncode, id, idparent) {
	//$('#' + id).remove();
    $('#' + idparent).append(buttoncode);
	$('#' + id).button().button('refresh');
	
	//Agregamos las llamadas a los eventos click
	$( '#' + id ).click(function( event ) {
							event.preventDefault();
							Android.controllerEvent(event.target.id, TE_CLICK, null);
						});
}

//Para agregar un contenedor Html en la p�gina
function createHtml(htmlcode, id, idparent) {

	$('#' + id).remove();
	$('#' + idparent).append(htmlcode);
	
	//Agregamos las llamadas a los eventos click
	$( '#' + id ).click(function( event ) {
							Android.controllerEvent($(this).attr('id'), TE_CLICK, null);
						});
						
}

//Prueba para agregar un Html de manera m�s r�pida
function createHtml2(id, idparent) {
	$('<div/>', {
    	'id':id,
    	'click':uihtmlclick }
    ).appendTo('#' + idparent);				
}

//Para remover cualquier control
function removeControl(id) {
	$('#' + id).remove();
}

//Para remover cualquier check
function removeCheck(id) {
	$('#' + id).checkboxradio('destroy').parent().remove();
}

//Para remover contenedor Html
function removeHtml(id) {
	$('#' + id).remove();
}

//Para agregar un bot�n a la p�gina
function createCheck(checkcode, id, idparent) {
	$('#' + id).remove();
    $('#' + idparent).append(checkcode);
	$('#' + id).checkboxradio()
			   .checkboxradio('refresh')
			   .change(function( event ) {
						Android.updateCheckControl( event.target.id, $(event.target).prop("checked") );
				});
}

//Para agregar un radio group a la p�gina
function createRadioGroup(rgcode, id, idparent) {
	$('#' + id).remove();
    $('#' + idparent).append(rgcode);
	$('#' + id).controlgroup().controlgroup('refresh');
}

//Para agregar un radio al radio group correspondiente
function createRadio(rgcode, id, idparent) {
	var rg = $('#' + idparent);
	var rdname = '#' + id;
	
	$(rdname).remove();
    rg.controlgroup("container").append(rgcode);
	rg.enhanceWithin().controlgroup("refresh");
	$(rdname).change(function( event ) {
		Android.updateRadioControl( event.target.id, $(event.target).prop("checked") );
	});
}

//Para agregar un radio group a la p�gina
function createGrid(gridcode, id, idparent) {
	$('#' + id).remove();
    $('#' + idparent).append(gridcode);
}

//Para agregar un radio group a la p�gina
function createGridCol(gridcolcode, id, idparent) {
	$('#' + id).remove();
    $('#' + idparent).append(gridcolcode);
}

//Para abrir un Panel Menu
function openPanelMenu(id) {
	$('#' + id).panel("open");
}

//Para cerrar un Panel Menu
function closePanelMenu(id) {
	$('#' + id).panel("close");
}

//Para intercambiar cierres y aperturas del panel menu
function togglePanelMenu(id) {
	$('#' + id).panel("toggle");
}
