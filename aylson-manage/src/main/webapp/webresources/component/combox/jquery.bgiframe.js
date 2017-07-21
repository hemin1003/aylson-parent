(function($){
		$.fn.bgIframe = $.fn.bgiframe = function(s) {
			// This is only for IE6
			if ( $.browser.msie && /6.0/.test(navigator.userAgent) ) {
				s = $.extend({
					top     : 'auto', // auto == .currentStyle.borderTopWidth
					left    : 'auto', // auto == .currentStyle.borderLeftWidth
					width   : 'auto', // auto == offsetWidth
					height  : 'auto', // auto == offsetHeight
					opacity : true,
					src     : 'javascript:false;'
				}, s || {});
				var prop = function(n){return n&&n.constructor==Number?n+'px':n;},
				    html = '<iframe class="bgiframe"frameborder="0"tabindex="-1"src="'+s.src+'"'+
				               'style="display:block;position:absolute;z-index:-1;'+
					               (s.opacity !== false?'filter:Alpha(Opacity=\'0\');':'')+
							       'top:'+(s.top=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\')':prop(s.top))+';'+
							       'left:'+(s.left=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\')':prop(s.left))+';'+
							       'width:'+(s.width=='auto'?'expression(this.parentNode.offsetWidth+\'px\')':prop(s.width))+';'+
							       'height:'+(s.height=='auto'?'expression(this.parentNode.offsetHeight+\'px\')':prop(s.height))+';'+
							'"/>';
				return this.each(function() {
					if ( $('> iframe.bgiframe', this).length == 0 )
						this.insertBefore( document.createElement(html), this.firstChild );
				});
			}
			return this;
		};

})(jQuery);