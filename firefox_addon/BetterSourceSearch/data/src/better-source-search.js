$(document).ready(function(){
   SyntaxHighlighter.defaults['smart-tabs'] = false;
   SyntaxHighlighter.defaults['toolbar'] = false;
   SyntaxHighlighter.defaults['quick-code'] = false;

   if($("frame").length > 0) {

      $( "<body>" ).insertAfter( $("frameset") );
      var frames = $("frame");
      $("frameset").remove();

      for(var i=0; i < frames.length; i++){
         $.ajax({url:frames[i].src, async:false}).done(function(data) {
            var wrapper = $( "<div class='wrapper_div'>" );
            var n=data.indexOf("<body>");
            var m=data.indexOf("</body>");
            wrapper.html(data.substring(n+6,m));
            wrapper.appendTo( $("body") )
         });
      }

      $($('div.wrapper_div')[0]).addClass("ui-layout-north");
      $($('div.wrapper_div')[1]).addClass("ui-layout-center");

      var url = window.location.href;
      var matches = url.match(/\.[a-zA-Z]+$/gi);
      var ext = null;

      if(matches != null){
         var ext = matches[0].split("&")[0];
         var ext = ext.substring(1, ext.length);
      }

      if(ext != null) {
         $("pre").addClass("brush: " + ext);
         SyntaxHighlighter.highlight();
      }

   } else {
      var centerDiv = $('<div id="center_div" class="ui-layout-center">').prependTo($('form'));
      var topDiv = $('<div id="top_div" class="ui-layout-north">').prependTo($('form'));
      $($('table')[0]).appendTo(topDiv);
      $($('table')[1]).appendTo(centerDiv);

      $('a > img').attr('src','http://icons.iconarchive.com/icons/rade8/minium-2/16/Sidebar-Downloads-icon.png');
   }

   var selects = $("select");

   for(var i=0; i< selects.length; i++) {
      var select = $(selects[i]);
      select.combobox();
      $("#toggle").click(function() {
              select.toggle();
      });
   }

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

   var layouter = $('body').layout();  
});