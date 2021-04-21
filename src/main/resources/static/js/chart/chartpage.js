$(document).ready(function () {
    getChart();
    getCharForGraph('melon');
    $("#btnDiv").css("display", "block")
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
            setData(data.data);
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


function setData(data) {


    // console.log(data.sdate);
    var tmpDateLabels = getUniqueObjectArray(data, 'sdate');
    var tmpTitleLabels = getUniqueObjectArray(data, 'title');
    var dateLabels = new Array();
    var titleLabels = new Array();
    for (var i = 0; i < tmpDateLabels.length; i++) {
        dateLabels.push(tmpDateLabels[i].sdate);
    }

var dataset = {};

    for (var i = 0; i < tmpTitleLabels.length; i++) {
        var tmpArray= {};
        titleLabels.push(tmpTitleLabels[i].title);
        var titleData = getListFilter(data, 'title', tmpTitleLabels[i].title);
        console.log("titleData : " + tmpTitleLabels[i].site);
        console.log(titleData);

        tmpArray['label'] = tmpTitleLabels[i].title;
        
        var ranking = new Array();
console.log(titleData.length);
        for(var j = 0 ; titleData.length ; j++){
            // console.log(titleData[j]);
            // console.log(titleData[j].ranking);
            console.log(titleData[j]['ranking']);
            // ranking.push(titleData[j]['ranking']);
            // ranking.push(titleData[j].ranking);
        }

        tmpArray['data'] = ranking;

        dataset.push(tmpArray);
    }


    console.log('dataset');
    console.log(dataset);


    // dataset

    //  label = title
    //  data  = ranking
    //  borderColor = ?





// 우선 컨텍스트를 가져옵니다.
    var ctx = document.getElementById("chart-area").getContext('2d');
    /*
    - Chart를 생성하면서,
    - ctx를 첫번째 argument로 넘겨주고,
    - 두번째 argument로 그림을 그릴때 필요한 요소들을 모두 넘겨줍니다.
    */
    var chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dateLabels,
            datasets: [{
                label: '곡명1',
                data: [12, 19, 3, 5, 2, 3],
                borderColor: 'rgb(255,99,234)'
            },
                {
                    label: '곡명2',
                    data: [1, 2, 3, 5, 2, 3],
                    borderColor: 'rgba(54, 162, 235, 1)'
                }],
        },
        options: {
            maintainAspectRatio: true, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
            scales: {
                y: {
                    type: 'linear',
                    display: true,
                    position: 'left',
                },
                y1: {
                    type: 'linear',
                    display: true,
                    position: 'right',

                    // grid line settings
                    grid: {
                        drawOnChartArea: false, // only want the grid lines for one axis to show up
                    },
                }
            }
        }
    });


    // options: {
    //     responsive: true,
    //         interaction: {
    //         mode: 'index',
    //             intersect: false,
    //     },
    //     stacked: false,
    //         plugins: {
    //         title: {
    //             display: true,
    //                 text: 'Chart.js Line Chart - Multi Axis'
    //         }
    //     },
    //     scales: {
    //         y: {
    //             type: 'linear',
    //                 display: true,
    //                 position: 'left',
    //         },
    //         y1: {
    //             type: 'linear',
    //                 display: true,
    //                 position: 'right',
    //
    //                 // grid line settings
    //                 grid: {
    //                 drawOnChartArea: false, // only want the grid lines for one axis to show up
    //             },
    //         },
    //     }
    // },

    // datasets: [
    //     {
    //         label: 'Dataset 1',
    //         data: Utils.numbers(NUMBER_CFG),
    //         borderColor: Utils.CHART_COLORS.red,
    //         backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
    //         yAxisID: 'y',
    //     },
    //     {
    //         label: 'Dataset 2',
    //         data: Utils.numbers(NUMBER_CFG),
    //         borderColor: Utils.CHART_COLORS.blue,
    //         backgroundColor: Utils.transparentize(Utils.CHART_COLORS.blue, 0.5),
    //         yAxisID: 'y1',
    //     }
    // ]

}


function getUniqueObjectArray(array, key) {
    var tempArray = [];
    var resultArray = [];
    for (var i = 0; i < array.length; i++) {
        var item = array[i];
        if (tempArray.includes(item[key])) {
            continue;
        } else {
            resultArray.push(item);
            tempArray.push(item[key]);
        }
    }
    return resultArray;
}

function drawGraph(site) {

}

function getListFilter(data, key, value) {

    console.log(data);

    var returnObject = new Array();
    $.each(data, function () {
        if (this[key] == value) {
            console.log(value);
            returnObject.push(this);
        }
    });

    return returnObject;
}


