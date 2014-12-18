// ==UserScript==
// @name       IITC Plugin: Increase-Feeder
// @version    0.1
// @include        https://www.ingress.com/intel*
// @include        http://www.ingress.com/intel*
// @match          https://www.ingress.com/intel*
// @match          http://www.ingress.com/intel*
// @grant          none
// ==/UserScript==


function wrapper(plugin_info) {
// ensure plugin framework is there, even if iitc is not yet loaded
if(typeof window.plugin !== 'function') window.plugin = function() {};

// PLUGIN START ////////////////////////////////////////////////////////


// use own namespace for plugin
window.plugin.increaseFeeder = function() {};

window.plugin.increaseFeeder.minZoom = 9;
    
window.plugin.increaseFeeder.setup = function() {
  addHook('publicChatDataAvailable', window.plugin.increaseFeeder.handleData);
};

window.plugin.increaseFeeder.stored = {};


window.plugin.increaseFeeder.handleData = function(data) {
    
  if(window.map.getZoom() < window.plugin.increaseFeeder.minZoom) {
      return;
  }
  
    var url = "https://sylvan-dragon-772.appspot.com/main";
    var jqxhr = $.post(url);
    
    jqxhr.done(function() {
    	alert( " --- done" );
    });
    
    jqxhr.fail(function() {
    	alert( " --- fail" );
    });
    
    jqxhr.always(function() {
    	alert( " --- always" );
    });
        
    alert(" --- sending request... --- ");

// $.post( "test.php", { func: "getNameAndTime" }, function( data ) {
// console.log( data.name ); // John
// console.log( data.time ); // 2pm
// }, "json");
    
//  $.each(data.raw.success, function(ind, json) {	});

}

var setup = plugin.increaseFeeder.setup;

// PLUGIN END //////////////////////////////////////////////////////////


setup.info = plugin_info; //add the script info data to the function as a property
if(!window.bootPlugins) window.bootPlugins = [];
window.bootPlugins.push(setup);
// if IITC has already booted, immediately run the 'setup' function
if(window.iitcLoaded && typeof setup === 'function') setup();
} // wrapper end
// inject code into site context
var script = document.createElement('script');
var info = {};
if (typeof GM_info !== 'undefined' && GM_info && GM_info.script) info.script = { version: GM_info.script.version, name: GM_info.script.name, description: GM_info.script.description };
script.appendChild(document.createTextNode('('+ wrapper +')('+JSON.stringify(info)+');'));
(document.body || document.head || document.documentElement).appendChild(script);



