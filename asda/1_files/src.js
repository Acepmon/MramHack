// make dojo.toJson() print dates correctly (this feels a bit dirty)
Date.prototype.json = function(){ return dojo.date.stamp.toISOString(this, {selector: 'date'});};

// Javascript for form etc.
	
var province_selected = false;
var group_selected = false;


function isset( strVariableName ) { 

    try { 
        eval( strVariableName );
    } catch( err ) { 
        if ( err instanceof ReferenceError ) 
           return false;
    }

    return true;

 } 


// Create SELECT box
function creat_select(FilteringSelect, sel_id, stateStore, require, p_holder) {

    var missingMessageTxt = dojo.query("#missingMessageTxt").text();
    var select = new FilteringSelect({
        name: sel_id,
        placeholder: p_holder,

	missingMessage: missingMessageTxt,
        required: require,
        store: stateStore

    }, sel_id);
    select.startup();
}
var filteringSelectWidget;
var newData;
var store;
// Update select box data from url
function soum_update(sel_id, soumStore_url) {
    filteringSelectWidget = dijit.byId(sel_id);

       // Clear current value since options are changing.
       filteringSelectWidget.set("value", ""); 
    
       store = filteringSelectWidget.get("store");  
       dojo.xhrGet({
            // The URL to request
            url: soumStore_url,
            handleAs: "json",
            // The success handler
            load: function(jsonData) {
               require(["dojo/Deferred", "dojo/dom", "dojo/on", "dojo/domReady!"],
                    function(Deferred, dom, on){
                        function asyncProcess(msg){
                            var deferred = new Deferred();
                            if(msg == 'allsoum') { 
                                newData = jsonData;
                                console.log(newData);
                                store.setData(newData);
                            } else if(msg == 'selsoum') {
                                if(isset(post_vars[sel_id])) {
                                 filteringSelectWidget.set("value", post_vars[sel_id]);
                                }   
                            }
                            setTimeout(function(){
                              deferred.resolve(msg);
                            }, 1000);
                            return deferred.promise;
                          }
                        

                            var process = asyncProcess("allsoum");
                            process.then(function(results){
                              return asyncProcess("selsoum");
                            });
                    });
                
               
            },
            error: function(error){
              soum_update(sel_id, soumStore_url);
            }
        });

           // Give the underlying store a new data array.
}


// Dojo startup functions
dojo.addOnLoad(function(){
    

    gototop();
    require(["dijit/Dialog", "dijit/form/TextBox", "dijit/form/Button"]);
    
    

     // Call first line on page loaded
    first_cordinates();


    dojo.connect(dijit.byId("attach_all"), 'onChange', function(event){
	 var checkedStatus = this.checked;
	 dojo.query(".checkbox").forEach(function(box){
	    dijit.byNode(box).attr("checked", checkedStatus);
	 });
    });

    require(['dojo/aspect', 'dijit/Dialog'], function (Aspect, Dialog) {
	Aspect.around(Dialog.prototype, '_onKey', function (original) {
	    return function () { }; // no-op
	});
    });
    
    dojo.connect(dijit.byId("crossed_soum"), 'onChange', function(event){
	 console.log(dijit.byId("crossed_soum").checked);
	if(dijit.byId("crossed_soum").checked == true) {
	     $('.crossed_soum').show();
	} else {
	     $('.crossed_soum').hide();
	}
	 return false;
    });

});     



var rechecked = false;
function recheck_attention() {
    
    if(!rechecked) {
	var check_result = check_required();
        if(check_result == 'error') {
            // 
        } else if(check_result != true) {
            var empty_fields_error = dojo.query("#empty_fields_error").text();
            alert(empty_fields_error /* + '\n Error fields: ' + check_result */ );
            
        } else {
            gototop();
            require([
                "dijit/TooltipDialog",
                "dijit/popup",
                "dojo/on",
                "dojo/dom",
                "dojo/domReady!"
            ], function(TooltipDialog, popup, on, dom){
                var myTooltipDialog = new TooltipDialog({
                    id: 'recheckTooltipDialog',
                    style: "width: 300px;",
                    content: dojo.query("#recheck_text").text()
                    
                });
                popup.open({
                    popup: myTooltipDialog,
                    around: dom.byId('main_title')
                });
                on(dom.byId('recheckTooltipDialog'), 'click', function(){
                    popup.close(myTooltipDialog);
                });
                on(dom.byId('submitButton'), 'click', function(){
                    popup.close(myTooltipDialog);
                });
            });
            
            rechecked = true;
        }
    } else {
        gototop();
    }
    
}

function gototop() {
    require(["dojo/window", "dojo/dom", "dojo/dom-geometry", "dojo/domReady!"],
    function(win, dom, domGeom){
        win.scrollIntoView("main_title");
    });
}


