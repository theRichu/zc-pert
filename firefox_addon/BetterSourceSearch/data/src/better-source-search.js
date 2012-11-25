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
            //alert(data);
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

      var nav = $($('table')[0]);
      var navHomeY = nav.offset().top;
      var isFixed = false;
      var $w = $(window);
      $w.scroll(function() {
         var scrollTop = $w.scrollTop();
         var shouldBeFixed = scrollTop > navHomeY;
         if (shouldBeFixed && !isFixed) {
            nav.css({
               position: 'fixed',
               top: 0,
               left: nav.offset().left,
               width: nav.width(),
               'z-index': 1000,
               background: '#FFFFFF',
               'padding-top': navHomeY,
               'padding-bottom': navHomeY
            });
            isFixed = true;
         } else if (!shouldBeFixed && isFixed) {
            nav.css({
               position: 'static',
               'padding-top': 0,
               'padding-bottom': 0
            });
            isFixed = false;
         }
      });
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

   $('body').layout(); 
});