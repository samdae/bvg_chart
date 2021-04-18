
$(document).ready( function (){
    getChart();
    $("#btnDiv").css("display", "block")
});
function getChart() {
        $.ajax({
        url: '/getChartList.ajax',
        method: 'get',
        dataType: 'json',
        success: function(data) {
            console.log(data);
        }
    });
    $.ajax({
        url: '/melon',
        dataType: 'json',
        success: function(data) {
            listSet('melon', data.melon);
        }
    });
    $.ajax({
        url: '/youtube',
        dataType: 'json',
        success: function(data) {
            listSet('youtube', data.youtube);
        }
    });
    $.ajax({
        url: '/bugs',
        dataType: 'json',
        success: function(data) {
            listSet('bugs', data.bugs);
        }
    });
    $.ajax({
        url: '/genie',
        dataType: 'json',
        success: function(data) {
            listSet('genie', data.genie);
        }
    });
    $.ajax({
        url: '/flo',
        dataType: 'json',
        success: function(data) {
            listSet('flo', data.flo);
        }
    });
    $.ajax({
        url: '/vibe',
        dataType: 'json',
        success: function(data) {
            listSet('vibe', data.vibe);
        }
    });
}

function listSet(id, data) {
    var tbody = $("#"+id);
    tbody.empty();
    var html = '';
    for (var i=0; i<data.length; i++) {
        var rank = data[i].ranking;
        var title = data[i].title;
        // var name = data[i].name;
        // var img = data[i].img;
        html += '<tr>' +
            '<td class="rank" style="text-align: center">' +  rank + '</td>' +
            '<td class="tdImg">' +
            '<img class="rankImg"src="../../chart-image/' + _img(title) + '" />' +
            '</td><td>'+title+'</td></tr>';
    }
    tbody.append(html);
}
function _img(title){
    if( title.indexOf("(Rollin')")     > -1 ) return "x74e-afep934nbksq.jpg";
    if( title.indexOf("(We Ride)")     > -1 ) return "x31j-fjf329bn20gnv.jpg";
    if( title.indexOf("하이힐")          > -1 ) return "x87m-ajfie319nmg9.jpg";
    if( title.indexOf("(New Version)") > -1 ) return "x67e-qnvj931opqwb.jpg";
}
