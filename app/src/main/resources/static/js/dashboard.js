google.load("visualization", "1", {packages: ["corechart"]});
google.setOnLoadCallback(drawCharts);

function drawCharts(barCreated) {

    // set bar chart options
    var barOptions = {
        focusTarget: 'category',
        backgroundColor: 'transparent',
        //colors: ['cornflowerblue', 'tomato'],    
        colors: ['cornflowerblue'],

        fontName: 'Open Sans',
        chartArea: {
            left: 50,
            top: 10,
            width: '80%',
            height: '70%'
        },
        bar: {
            groupWidth: '80%'
        },
        hAxis: {
            textStyle: {
                fontSize: 11
            }
        },
        vAxis: {
            minValue: 0,
            maxValue: 1500,
            baselineColor: '#DDD',
            gridlines: {
                color: '#DDD',
                count: 4
            },
            textStyle: {
                fontSize: 11
            }
        },
        legend: {
            position: 'bottom',
            textStyle: {
                fontSize: 12
            }
        },
        animation: {
            duration: 1200,
            easing: 'out',
            startup: true
        }
    };

    var barChart = new google.visualization.ColumnChart(document.getElementById('bar-chart'));
    //Get the first Revenue Month
    getRevenueOfMonth(1);

    function getRevenueOfMonth(month) {
        fetch("api/dashboard/getMonthRevenue", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({"txtMonth": month})
            
        }).then(function (response) {
            response.json().then(data => {
                var barCreated = google.visualization.arrayToDataTable([
                    ['Week', 'Revenue'],
                    ['Week 1', data[0]],
                    ['Week 2', data[1]],
                    ['Week 3', data[2]],
                    ['Week 4', data[3]],
                ]);
                barChart.draw(barCreated, barOptions);

            });

        });
    }


    // draw bar chart twice so it animates

    //Get được onchange -> Đầu tiên sẽ cần cài cho nó 1 cái mặc định
    document.getElementById("ddlViewBy").onchange = function () {
        var e = document.getElementById("ddlViewBy");
        var value = e.options[e.selectedIndex].value;
        getRevenueOfMonth(value);

    }



}