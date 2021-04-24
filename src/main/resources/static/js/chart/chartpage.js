$(document).ready(function () {
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
        var title = data[i].title;
        html += '<tr>' +
            '<td class="rank" style="text-align: center">' + rank + '</td>' +
            '<td class="tdImg"><img class="rankImg"src="../../chart-image/' + _img(title) + '" /></td>' +
            '<td>' + title + '</td></tr>';
        $("#" + data[i].site).append(html);
    }
}

function _img(title) {
    if (title.indexOf("(Rollin`)") > -1) return "x74e-afep934nbksq.jpg";
    if (title.indexOf("(We Ride)") > -1) return "x31j-fjf329bn20gnv.jpg";
    if (title.indexOf("하이힐") > -1) return "x87m-ajfie319nmg9.jpg";
    if (title.indexOf("(New Version)") > -1) return "x67e-qnvj931opqwb.jpg";
}

var chart;

function setData(data) {

    // 우선 컨텍스트를 가져옵니다.
    var ctx = document.getElementById("chart-area").getContext('2d');
    /*
    - Chart를 생성하면서,
    - ctx를 첫번째 argument로 넘겨주고,
    - 두번째 argument로 그림을 그릴때 필요한 요소들을 모두 넘겨줍니다.
    */
    chart = new Chart(ctx, {
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
