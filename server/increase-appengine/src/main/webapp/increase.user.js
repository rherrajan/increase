// ==UserScript==
// @id             iitc-plugin-increase@rherrajan
// @name		   IITC Plugin: Increase
// @category       Layer
// @version    	   0.0.6
// @namespace      https://github.com/jonatkins/ingress-intel-total-conversion
// @updateURL      https://sylvan-dragon-772.appspot.com/increase.user.js
// @downloadURL    https://sylvan-dragon-772.appspot.com/increase.user.js
// @description    
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
window.plugin.increase = function() {};
window.plugin.increase.feeder = function() {};

window.plugin.increase.feeder.minZoom = 9;
    
window.plugin.increase.feeder.setup = function() {
  
  addHook('publicChatDataAvailable', window.plugin.increase.feeder.handleData);
  
  var refreshMinutes = 60;
  setInterval ( window.plugin.increase.feeder.wakeup, refreshMinutes*60*1000 );
  
};

window.plugin.increase.feeder.stored = {};


window.plugin.increase.feeder.handleData = function(data) {
    
  if(window.map.getZoom() < window.plugin.increase.feeder.minZoom) {
      return;
  }
  
    var url = "https://sylvan-dragon-772.appspot.com/feeder";
    var inputData = JSON.stringify(data.raw);
    var jqxhr = $.post(url, inputData);
    
    jqxhr.done(function(responseData) {
        console.log("done: ", responseData);
    });
    
    jqxhr.fail(function(responseData) {
        console.log("fail: ", responseData);
        alert( "fail: " + responseData );
    });
        
}

window.plugin.increase.feeder.wakeup = function() {
  console.log('periodicRefresh: timer fired - leaving idle mode');
  idleReset();
}

var setup = plugin.increase.feeder.setup;

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

