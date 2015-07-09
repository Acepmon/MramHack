// Create text box
function creat_textbox(NumberTextBox, input_name, consts_str, require, fieldmaxlength) {
    var missingMessageTxt = dojo.query("#missingMessageTxt").text();
    var TextBox = new NumberTextBox({
        name: input_name,
        id: input_name,
        constraints: consts_str,
        required: require,
	missingMessage: missingMessageTxt,
        maxlength: fieldmaxlength,
        autocomplete: "off",
        style: "width: 70px;"
    }, input_name);
    TextBox.startup();
}


function set_error(elemid, state /*"Error", "Incomplete", ""*/, message) {
    var elem = dijit.byId(elemid);
    elem.focus();
    elem.set("state", state);
    elem.set("message", message);
}
	
function check_required1() {
    var s = "";
    require(["dojo/dom"], function(dom){
        var f = dojo.byId("mainform");
        for(var i = f.elements.length-1; i >= 0; i=i-1){
            var elem = f.elements[i];
            if(elem.name == "button"){ continue; }
            if(elem.type == "radio" && !elem.checked){ continue; }
            var require_attr = dojo.attr(elem, "aria-required");
            //console.log(require_attr);
	    
            if(elem.value == "" && require_attr == 'true') {
                s += elem.name + ": " + "\n";
		set_error(elem.id, "Error", "Заавал бөглөнө үү!");
                //console.log(elem);
            }
	    
        }
    });

    if(s != "") {
        //alert(s); //Буцааж false болгохоо мартаваа!! 
        return (s);
    } else {
        f.submit();
    }
}
	
	
function check_required() {
    var s = "";
    require(["dojo/dom"], function(dom){
        var f = dojo.byId("mainform");
        for(var i = f.elements.length-1; i >= 0; i=i-1){
            var elem = f.elements[i];
            if(elem.name == "button"){ continue; }
            if(elem.type == "radio" && !elem.checked){ continue; }
            var require_attr = dojo.attr(elem, "aria-required");
            //console.log(require_attr);
	    
            if(elem.value == "" && require_attr == 'true') {
                s += elem.name + ": " + "\n";
		set_error(elem.id, "Error", "Заавал бөглөнө үү!");
                //console.log(elem);
            }
	    
        }
    });
    
    //var province1 = dijit.byId("province1").attr("value");
    //var province2 = dijit.byId("province2").attr("value");
    //var province3 = dijit.byId("province3").attr("value");
    //var soum1 = dijit.byId("soum1").attr("value");
    //var soum2 = dijit.byId("soum2").attr("value");
    //var soum3 = dijit.byId("soum3").attr("value");

    
    //var plansheet_code1 = dijit.byId("plansheet_code1").attr("value");
    //var plansheet_code2 = dijit.byId("plansheet_code2").attr("value");
    //var plansheet_code3 = dijit.byId("plansheet_code3").attr("value");

    
    if(s != "") {
        //alert(s); //Буцааж false болгохоо мартаваа!! 
        return (s);
    } else {
        return true;
    }
}

function edit_set_coord() {
    var data_text = $('#coordinates_json').text();
    if(data_text.length > 0 ) {
	var data_json = JSON.parse(data_text);
	set_coordinate_values(data_json);
	$('#file_import_success').hide();
    }
}

var files;
$('input[type=file]#coordinate_file').on('change', uploadFiles);
function uploadFiles(event) {   
    // START A LOADING SPINNER HERE
    $('#fileupload_loading').show();
    files = event.target.files;
  
    event.stopPropagation(); // Stop stuff happening
    event.preventDefault(); // Totally stop stuff happening
    // Create a formdata object and add the files
    var data = new FormData();
    $.each(files, function(key, value) {
	data.append(key, value);
    });
    
    if($('#recheck_text').text().length > 0 ) {
	var myloadurl = 'index.php?task=fileupload&ajax=1';
    } else {
	var myloadurl = '../index.php?task=fileupload&ajax=1';
    }
    
    $.ajax({
	url: myloadurl,
	type: 'POST',
	data: data,
	cache: false,
	dataType: 'json',
	processData: false, // Don't process the files
	contentType: false, // Set content type to false as jQuery will tell the server its a query string request
	success: function(data, textStatus, jqXHR) {
	    $('#fileupload_loading').hide();
	    if(typeof data.error === 'undefined') {
		// Success so call function to process the form
		var callbacks = $.Callbacks();
		    callbacks.add( remove_old_lines );
		    callbacks.add( set_coordinate_values );
		    callbacks.fire(data);
		    callbacks.remove( remove_old_lines );
		    callbacks.remove( set_coordinate_values );
	    } else {
		// Handle errors here
		alert($('#file_import_error').text());
	    }
	},
	error: function(jqXHR, textStatus, errorThrown) {
	    // Handle errors here
	    alert($('#file_import_error').text());
	    $('#fileupload_loading').hide();
	    // STOP LOADING SPINNER
	}
    });
}

function isNumber(o) {
  return ! isNaN (o-0) && o !== null && o !== "" && o !== false;
}

