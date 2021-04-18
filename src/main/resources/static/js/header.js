// session timer
var tid;
var cnt = parseInt(3600);

$(document).ready(function() {
	$(".menu > li > ul > li > a").each(function() {
		var url_pathname = window.location.pathname;
		var url_search = window.location.search;
		var url = (url_pathname +  url_search).replace("#", "");
		if(url == '/' || url == '/loginProcPage.do') {
			$(".menu > li").eq(1).addClass("on");
			$(".menu").children('li').eq(1).children('ul').children('li').eq(0).addClass("on");
		}
		if ( $(this).attr("href") == url ) {
			$(this).parent('li').parent('ul').parent('li').addClass("on");
			$(this).parent('li').addClass("on");
		} 
	});
	
	counter_init();
});

function counter_init() {
	tid = setInterval("counter_run()", 1000);
}

function counter_reset() {
	clearInterval(tid);
	cnt = parseInt(3600);
	counter_init();
}

function counter_run() {
	document.all.counter.innerText = time_format(cnt);
	cnt--;
	if(cnt < 0) {
		clearInterval(tid);
		self.location = "/logout.do";
	}
}
function time_format(s) {
	var nHour=0;
	var nMin=0;
	var nSec=0;
	if(s>0) {
		nMin = parseInt(s/60);
		nSec = s%60;

		if(nMin>60) {
			nHour = parseInt(nMin/60);
			nMin = nMin%60;
		}
	} 
	if(nSec<10) nSec = "0"+nSec;
	if(nMin<10) nMin = "0"+nMin;

	return nMin+"분 "+nSec + "초";
}
