var platform ;
$(document).ready(function () {

    var filter = "win16|win32|win64|mac|macintel";
    if(navigator.platform){
        if(0 > filter.indexOf(navigator.platform.toLowerCase())){
            platform = "M";
        }else{
            platform = "P";
        }
    }



    getChart();
    getCharForGraph('melon');
    $("#btnDiv").css("display", "block");
});

function getChart() {
    $.ajax({
        url: '/getChartList.ajax',
        method: 'post',
        dataType: 'json',
        success: function (data) {
            listSet(data.data);
        }
    });

}

function getCharForGraph(site) {

    var param = {"site": site};
    $.ajax({
        url: '/getRankForGraphList.ajax',
        method: 'post',
        dataType: 'json',
        data: param,
        success: function (data) {
            setData(data);
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


    for (var i = 0; i < data.length; i++) {
        var html = '';
        var rank = data[i].ranking;
        var cRanking = data[i].cRanking;
        var title = data[i].title;
        var site = data[i].site;
        var html2 = "";
        if(cRanking < 0 ){
            //음수면 순위 하락 (파랑이)
            html2 = "<p style='color:#00f;font-size:0.6em'>▼&nbsp;"+ (cRanking*-1) +"</p>";
        }else if(cRanking > 0 ){
            html2 = "<p style='color:#f00;font-size:0.6em'>▲&nbsp;"+ cRanking +"</p>";
        }else if(cRanking == 0){
            html2 = "<p style='color:#000;font-size:0.6em'>⁃ </p>";
        }
        html += '<tr onclick="openDayModal(\''+site+'\',\''+title+'\');">' +
            '<td class="rank" style="text-align: center">' + rank + '<br/>'+html2+'</td>' +
            '<td class="tdImg"><img class="rankImg"src="../../chart-image/' + _img(title) + '" /></td>' +
            '<td>' + title + '</td></tr>';
        $("#" + data[i].site).append(html);
    }
}
function openDayModal( site , title ) {
    var param = {"site": site , "title" : title};
    $.ajax({
        url: '/getDailyGraphList.ajax',
        method: 'post',
        dataType: 'json',
        data: param,
        success: function (data) {
            if(dailyChart != "" && dailyChart != undefined){
                dailyChart.destroy();
            }
            drawDailyChart(data.data, site, title);
        }
    });
}


var dailyChart ;
function drawDailyChart(data, site, title){


    var labels = new Array();
    var dataArray = new Array();

    for(var i = 0 ; i < data.length ; i++){
       labels.push(data[i].stime+'시');
        dataArray.push(data[i].ranking);
    }

    // 우선 컨텍스트를 가져옵니다.
    var ctx1 = document.getElementById("daily-area").getContext('2d');
    /*
    - Chart를 생성하면서,
    - ctx를 첫번째 argument로 넘겨주고,
    - 두번째 argument로 그림을 그릴때 필요한 요소들을 모두 넘겨줍니다.
    */
    dailyChart = new Chart(ctx1, {
        type: 'line',
        data: {
            labels : labels,
            datasets :[{
                data : dataArray,
                label : title,
                borderColor : "rgb(240, 101, 86)"
            }]
        },
        options: {
            spanGaps: true,
            responsive : true,
            pointBorderWidth : 7,
            plugins: {
                legend: {
                    display: true,
                    labels: {
                        usePointStyle : true,
                        boxWidth : 5,
                    },
                    padding : 30,
                },
                tooltip: {
                    enabled : true,
                    mode: 'index',
                    intersect: false,
                    backgroundColor : '#aeaeae',
                    displayColors : true,
                    rtl : false,
                    padding :15,
                    usePointStyle :  true,
                    titleFont :{
                        size : 20
                    },
                    bodyFont : {
                        size : 13
                    },
                    bodySpacing : 5,
                    borderWidth : 7,
                    boxWidth: 7,
                    bodyAlign : 'right',
                    callbacks: {
                        label: function(context) {
                            var label = context.dataset.label || '';

                            if (label) {
                                label += '    ';
                            }
                            if (context.parsed.y !== null) {
                                label += context.parsed.y +"위";
                            }
                            return label;
                        }
                    }
                },
            },
            pointHoverBorderWidth : 5,
            scales: {
                x: {
                    display: true,
                },
                y: {
                    reverse : true,
                    display: true,
                    ticks : {
                        autoSkip :true,
                        autoSkipPadding : 10,
                    }
                }
            },
        }
    });

    switch (site) {
        case 'melon' : site='멜론'; break;
        case 'youtube' : site='유튜브'; break;
        case 'genie' : site='지니'; break;
        case 'flo'   : site='플로'; break;
        case 'vibe' : site='바이브'; break;
        case 'bugs' : site='벅스'; break;
    }

    $("#daily-modal-info").text("❏ ["+site + "] " + title +"")
    $('#dailyModal').modal('show');

}


function _img(title) {
    if (title.indexOf("(Rollin`)") > -1) return "x74e-afep934nbksq.jpg";
    if (title.indexOf("(We Ride)") > -1) return "x31j-fjf329bn20gnv.jpg";
    if (title.indexOf("하이힐") > -1) return "x87m-ajfie319nmg9.jpg";
    if (title.indexOf("(New Version)") > -1) return "x67e-qnvj931opqwb.jpg";
}

var monthlyChart;

function setData(data) {

    // 우선 컨텍스트를 가져옵니다.
    var ctx = document.getElementById("chart-area").getContext('2d');
    /*
    - Chart를 생성하면서,
    - ctx를 첫번째 argument로 넘겨주고,
    - 두번째 argument로 그림을 그릴때 필요한 요소들을 모두 넘겨줍니다.
    */
    monthlyChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: {
            spanGaps: true,
            responsive : true,
            pointBorderWidth : 7,
            plugins: {
                legend: {
                    display: true,
                    labels: {
                        usePointStyle : true,
                        boxWidth : 5,
                    },
                    padding : 30,
                },
                tooltip: {
                    enabled : true,
                    mode: 'index',
                    intersect: false,
                    backgroundColor : '#aeaeae',
                    displayColors : true,
                    rtl : false,
                    padding :15,
                    usePointStyle :  true,
                    titleFont :{
                        size : 20
                    },
                    bodyFont : {
                        size : 13
                    },
                    bodySpacing : 5,
                    borderWidth : 7,
                    boxWidth: 7,
                    bodyAlign : 'right',
                    callbacks: {
                        label: function(context) {
                            var label = context.dataset.label || '';

                            if (label) {
                                label += '    ';
                            }
                            if (context.parsed.y !== null) {
                                label += context.parsed.y +"위";
                            }
                            return label;
                        }
                    }
                },
           },
            pointHoverBorderWidth : 5,
            scales: {
                x: {
                    display: true,
                },
                y: {
                    reverse : true,
                    display: true,
                    ticks : {
                        autoSkip :true,
                        autoSkipPadding : 10,
                    }
                }
            },
        }
    });
}
