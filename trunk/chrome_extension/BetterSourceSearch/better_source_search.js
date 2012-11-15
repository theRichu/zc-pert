$(document).ready(function(){
   SyntaxHighlighter.defaults['gutter'] = false;
   SyntaxHighlighter.defaults['smart-tabs'] = false;toolbar
   SyntaxHighlighter.defaults['toolbar'] = false;
   
   var url = window.location.href;
   var matches = url.match(/\.[a-zA-Z]{1,4}&content=1$/gi);
   var ext = null;

   if(matches != null){
      var ext = matches[0].split("&")[0];
      var ext = ext.substring(1, ext.length);
   }

   if(ext != null) {
      $("pre").addClass("brush: " + ext);
      SyntaxHighlighter.all();
   }
   
   var selects = $("select");
   
   
   
   
   for(var i=0; i< selects.length; i++) {
      var select = $(selects[i]);
      ExpandSelect(select.innerHTML);
      /*
      select.combobox();
      $( "#toggle" ).click(function() {
              select.toggle();
      });
      */
   }
   
   
   /*
   var cmdArg = $("input[name='cmdArg']");
   
   if(cmdArg) {
      cmdArg.addClass("ui-widget ui-widget-content ui-corner-all");
   }
   
   var filespec = $("input[name='filespec']");
   
   if(filespec) {
      filespec.addClass("ui-widget ui-widget-content ui-corner-all");
   }
   
   var contents = $("input[name='contents']");
   
   if(contents) {
      contents.addClass("ui-widget ui-widget-content ui-corner-all");
   }
   
   var Results = $("input[name='Results']");
   
   if(Results) {
      Results.addClass("ui-widget ui-widget-content ui-corner-all");
   }
   
   var Search = $("input[name='Search']");
   
   if(Search) {
      Search.addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
   }
   */
});