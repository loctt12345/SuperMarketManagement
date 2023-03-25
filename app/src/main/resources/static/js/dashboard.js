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

    var barChartByMonth = new google.visualization.ColumnChart(document.getElementById('bar-chart-by-month'));
    //Get the first Revenue Month
    getRevenueOfMonth(new Date().getMonth() + 1, new Date().getFullYear());
    document.getElementById('ddlViewByMonthOfYear').value = new Date().getMonth() + 1;
    document.getElementById('ddlViewByYearOfMonth').value = new Date().getFullYear();

    function getRevenueOfMonth(month, yearOfMonth) {
        fetch("api/dashboard/getMonthRevenue", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({"txtMonth": month, "txtYearOfMonth": yearOfMonth})

        }).then(function (response) {
            response.json().then(data => {
                var barCreated = google.visualization.arrayToDataTable([
                    ['Tuần', 'Doanh thu'],
                    ['Tuần 1', data[0]],
                    ['Tuần 2', data[1]],
                    ['Tuần 3', data[2]],
                    ['Tuần 4', data[3]],
                ]);
                barChartByMonth.draw(barCreated, barOptions);

            });

        });
    }

    var barChartByYear = new google.visualization.ColumnChart(document.getElementById('bar-chart-by-year'));

    getRevenueOfYear(new Date().getFullYear());
    document.getElementById('ddlViewByYear').value = new Date().getFullYear();

    function getRevenueOfYear(year) {
        fetch("api/dashboard/getYearRevenue", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({"txtYear": year})

        }).then(function (response) {
            response.json().then(data => {
                var barCreated = google.visualization.arrayToDataTable([
                    ['Tháng', 'Doanh thu'],
                    ['Tháng 1', data[0]],
                    ['Tháng 2', data[1]],
                    ['Tháng 3', data[2]],
                    ['Tháng 4', data[3]],
                    ['Tháng 5', data[4]],
                    ['Tháng 6', data[5]],
                    ['Tháng 7', data[6]],
                    ['Tháng 8', data[7]],
                    ['Tháng 9', data[8]],
                    ['Tháng 10', data[9]],
                    ['Tháng 11', data[10]],
                    ['Tháng 12', data[11]]
                ]);
                barChartByYear.draw(barCreated, barOptions);

            });

        });
    }

    // draw bar chart twice so it animates



    //Get được onchange -> Đầu tiên sẽ cần cài cho nó 1 cái mặc định
    document.getElementById("ddlViewByMonthOfYear").onchange = function () {
        var e = document.getElementById("ddlViewByMonthOfYear");
        var f = document.getElementById("ddlViewByYearOfMonth");
        var value = e.options[e.selectedIndex].value;
        var values = f.options[f.selectedIndex].value;
        getRevenueOfMonth(value, values);
    }
    document.getElementById("ddlViewByYearOfMonth").onchange = function () {
        var e = document.getElementById("ddlViewByMonthOfYear");
        var f = document.getElementById("ddlViewByYearOfMonth");
        var value = e.options[e.selectedIndex].value;
        var values = f.options[f.selectedIndex].value;
        getRevenueOfMonth(value, value);

    }

    document.getElementById("ddlViewByYear").onchange = function () {
        var e = document.getElementById("ddlViewByYear");
        var value = e.options[e.selectedIndex].value;
        getRevenueOfYear(value);
    }

}