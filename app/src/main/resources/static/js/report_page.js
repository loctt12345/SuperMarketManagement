function chart() {
    // var bienx = ['thu2','thu3','thu4','thu5','thu6','thu7']
    //     var bieny = [2,5,6,8,1,3,10]
    //     var CHART = document.getElementById('linechart').getContext('2d')
    //     var line_chart = new Chart(CHART,{
    //         type: 'line',
    //         data: {
    //             labels: bienx,
    //             datasets: [{
    //                 label: 'name_data',
    //                 data: bieny
    //             }]
    //         }
    //     });
    var xValues = ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"];
    var yValues = [66, 84, 62, 78, 62, 72, 91, 92, 55, 53, 70, 90, 0];
    // var barColors = ["red", "green","blue","orange"];
    var barColors = "lightgreen";

    new Chart("barChart", {
        type: "bar",
        data: {
        labels: xValues,
        datasets: [{
            backgroundColor: barColors,
            data: yValues
            }]
        },
        options: {
            legend: {display: false},
            title: {
              display: true,
              text: "Doanh thu của năm 2022"
            }
          }
    });
}