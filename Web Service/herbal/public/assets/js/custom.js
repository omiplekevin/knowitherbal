//seemore
	$(document).ready(function () {

		// Grab all the excerpt class
		$('.seemore').each(function () {
		
			// Run formatWord function and specify the length of words display to viewer
			$(this).html(formatWords($(this).html(), 20));
			
			// Hide the extra words
			$(this).children('span').hide();
		
		// Apply click event to read more link
		}).click(function () {

			// Grab the hidden span and anchor
			var more_text = $(this).children('span.more_text');
			var more_link = $(this).children('a.more_link');
				
			// Toggle visibility using hasClass
			// I know you can use is(':visible') but it doesn't work in IE8 somehow...
			if (more_text.hasClass('hide')) {
				more_text.show();
				more_link.html(' &raquo; hide');		
				more_text.removeClass('hide');
			} else {
				more_text.hide();
				more_link.html(' &laquo; more');			
				more_text.addClass('hide');
			}

			return false;
		
		});
	});

	

		$(document).ready(function()
		{
			 $("tr:even").css("background-color","#FFFFFF");
			        // Paginate table rows
				$('table tbody').paginate({
							status: $('#status'),
							controls: $('#paginate'),
							itemsPerPage: 10
						});
		});

		$(function(){ $('.topbar').dropdown(); });
	

