
$(document).ready( function (){
    getChart();
    $("#btnDiv").css("display", "block")
});
function getChart() {
    $.ajax({
        url: '/getChartList.ajax',
        method: 'post',
        dataType: 'json',
        success: function(data) {
            listSet(data.data);
        }
    });
}

function listSet(data) {
    $("#melon").empty();
    $("#bugs").empty();
    $("#genie").empty();
    $("#flo").empty();
    $("#vibe").empty();
    $("#youtube").empty();

    for (var i=0; i<data.length; i++) {
        var html = '';
        var rank = data[i].ranking;
        var title = data[i].title;
        html += '<tr>' +
            '<td class="rank" style="text-align: center">' +  rank + '</td>' +
            '<td class="tdImg"><img class="rankImg"src="../../chart-image/' + _img(title) + '" /></td>' +
            '<td>'+title+'</td></tr>';
        $("#"+data[i].site).append(html);
    }
}
function _img(title){
    if( title.indexOf("(Rollin`)")     > -1 ) return "x74e-afep934nbksq.jpg";
    if( title.indexOf("(We Ride)")     > -1 ) return "x31j-fjf329bn20gnv.jpg";
    if( title.indexOf("하이힐")          > -1 ) return "x87m-ajfie319nmg9.jpg";
    if( title.indexOf("(New Version)") > -1 ) return "x67e-qnvj931opqwb.jpg";
}
