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
    getRevenueOfMonth(1, 2022);

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
                    ['Week', 'Revenue'],
                    ['Week 1', data[0]],
                    ['Week 2', data[1]],
                    ['Week 3', data[2]],
                    ['Week 4', data[3]],
                ]);
                barChartByMonth.draw(barCreated, barOptions);

            });

        });
    }

    var barChartByYear = new google.visualization.ColumnChart(document.getElementById('bar-chart-by-year'));

    getRevenueOfYear(2022);

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
                    ['Month', 'Revenue'],
                    ['Jan', data[0]],
                    ['Feb', data[1]],
                    ['Mar', data[2]],
                    ['April', data[3]],
                    ['May', data[4]],
                    ['June', data[5]],
                    ['July', data[6]],
                    ['Aug', data[7]],
                    ['Sep', data[8]],
                    ['Oct', data[9]],
                    ['Nov', data[10]],
                    ['Dec', data[11]]
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