/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var totalPage = parseInt(document.getElementById('numOrders').value);

var currentPage = parseInt(document.getElementById('pageNum').value);
$(document).ready(function () {
    console.log("ready!");

    window.onload = function ()
    {
        var size = parseInt(document.getElementById('size').value);

        for (var i = 0; i < size; ++i) {
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
            document.getElementById('select' + i).value
                    = document.getElementById('status' + i).value;
        }

        let totals = document.getElementsByClassName('total');
        for (var i = 0; i < totals.length; ++i) {
            var total = parseFloat(totals[i].innerHTML);
            totals[i].innerHTML = total.toLocaleString('de-DE',
                    {style: 'currency', currency: 'VND'});
        }
        document.getElementsByClassName('page-item')[0].className = 'page-item';
        document.getElementsByClassName('page-item')[currentPage - 1].className = 'page-item active';
    }
});
var pagingCount = 0;
$(function () {
    window.pagObj = $('#pagination').twbsPagination({
        totalPages: totalPage,
        visiblePages: 10,
        onPageClick: function (event, page) {
            console.log(event);
            if (pagingCount !== 0) {
                location.href =
                        "/admin/order_management?page="
                        + page;

            } else {
            }
            pagingCount++;
        }
    });
});