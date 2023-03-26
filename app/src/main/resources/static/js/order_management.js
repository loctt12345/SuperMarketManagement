/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var totalPage = parseInt(document.getElementById('numOrders').value);

var currentPage = parseInt(document.getElementById('pageNum').value);


window.onload = function ()
{
    var items = document.getElementsByClassName('product-row');
    for (var i = 0; i < items.length; i++) {
        if (i >= 0 && i < 6) {
            items[i].style.display = 'table-row';
        } else {
            items[i].style.display = 'none';
        }
    }



//    let totals = document.getElementsByClassName('total');
//    for (var i = 0; i < 6; ++i) {
//        var total = parseFloat(totals[i].innerHTML);
//        totals[i].innerHTML = total.toLocaleString('de-DE',
//                {style: 'currency', currency: 'VND'});
//    }
//
//    for (var i = 0; i < 6; ++i)
//        document.getElementById('select' + i).value
//                = document.getElementById('status' + i).value;
//
//    for (var i = 0; i < 6; ++i) {
//        var x = document.getElementById('id' + i).value;
//        let qrcode = new QRCode(document.getElementById("orderid" + i),
//                {
//                    text: x,
//                    width: 100,
//                    height: 100,
//                    colorDark: "#000000",
//                    colorLight: "#ffffff",
//                    correctLevel: QRCode.CorrectLevel.H
//                });
//    }
};

let items = document.getElementsByClassName('product-row');
let numPage = Math.floor(items.length / 6) + 1;
var arrPage = [];

$(function () {
    window.pagObj = $('#pagination').twbsPagination({
        totalPages: numPage,
        visiblePages: 10,
        onPageClick: function (event, page) {
            
            for (let i = 0; i < items.length; i++) {
                if (i < (6 * (page - 1)) || i >= (6 * page)) {
                    items[i].style.display = 'none';
                } else {
                    items[i].style.display = 'table-row';
                }
            }
            
            if (arrPage.includes(page)) return;
            arrPage.push(page);
            let totals = document.getElementsByClassName('total');
            for (var i = 6 * (page - 1); i < Math.min(6 * (page - 1) + 6, totals.length); ++i) {
                var total = parseFloat(totals[i].innerHTML);
                totals[i].innerHTML = total.toLocaleString('de-DE',
                        {style: 'currency', currency: 'VND'});
            }

            for (var i = 6 * (page - 1); i < Math.min(6 * (page - 1) + 6, totals.length); ++i)
                document.getElementById('select' + i).value
                        = document.getElementById('status' + i).value;

            for (var i = 6 * (page - 1); i < Math.min(6 * (page - 1) + 6, totals.length); ++i) {
                var x = document.getElementById('id' + i).value;
                let qrcode = new QRCode(document.getElementById("orderid" + i),
                        {
                            text: x,
                            width: 100,
                            height: 100,
                            colorDark: "#000000",
                            colorLight: "#ffffff",
                            correctLevel: QRCode.CorrectLevel.H
                        });
            }
        }
    });
});