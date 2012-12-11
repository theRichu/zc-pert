(function( $ ) {
		$.widget( "ui.combobox", {
			_create: function() {
				var input,
					self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					value = selected.val() ? selected.text() : "",
					wrapper = this.wrapper = $( "<div>" )
						.addClass( "ui-combobox" )
						.insertAfter( select ),
      //ht = select.height(),
      wt = select.width();

				input = $( "<input>" )
					.appendTo( wrapper )
					.val( value )
					.addClass( "ui-combobox-input" )
     //.height(ht)
     .width(wt)
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
        var dropDownOpts = select.children( "option" );
        var filteredOpts = new Array();
        for(var i = 0; i < dropDownOpts.length; i++) {
          var opt = $(dropDownOpts[i]);
          var text = opt.text();
          if ( opt.val() && ( !request.term || matcher.test(text) ) ) {
            filteredOpts.push({
              label: text,
              value: text,
              option: opt
            });
          }
        }
        response(filteredOpts);
						},
						select: function( event, ui ) {
       select.val(ui.item.option.val());           
							self._trigger( "selected", event, {
								item: ui.item.option
							});
       //select.trigger("change"); //this only works in chrome
       document.forms[0].submit(); //this for firefox
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
            valid = false,
            dropDownOpts = select.children( "option" );
            
         for(var i = 0; i < dropDownOpts.length; i++) {
          if ( $(dropDownOpts[i]).text().match( matcher ) ) {
            this.selected = valid = true;
            return false;
          }
        }
        
								if ( !valid ) {
									// remove invalid value, as it didn't match anything
									$( this ).val( "" );
									select.val( "" );
									input.data( "autocomplete" ).term = "";
									return false;
								}
							}
						}
					})
					.addClass( "ui-widget ui-widget-content ui-corner-left" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a style='border: 0px'>" + item.label + "</a>" )
						.appendTo( ul );
				};
                                                      
				$( "<a>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.appendTo( wrapper )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-combobox-toggle" )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// work around a bug (likely same cause as #5265)
						$( this ).blur();

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
			},

			destroy: function() {
				this.wrapper.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
		});
	})( jQuery );