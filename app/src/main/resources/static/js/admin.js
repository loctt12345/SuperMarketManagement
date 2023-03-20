/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function onScanSuccess(qrCodeMessage) {
    //    $('#scannerToAddProduct').modal('hide');
    let myModal = new bootstrap.Modal(document.getElementById('myModal'));
    // $('#myModal').modal('show');
    myModal.show();
    document.getElementById('scannerCloseBtn').click();
    document.getElementById('productID').value = qrCodeMessage;
    newScanner();
}
document.getElementById('addNewProductBtn').addEventListener('click',newScanner());
// let closeAddModalBtn = document.getElementById('closeAddModalBtn');
// closeAddModalBtn.addEventListener('click', function () {
//     scannerToAddProduct.show();
//     myModal.hide();
// });
// document.addEventListener('click', function (e) {
//     if (!document.getElementById('scannerToAddProduct').contains(e.target)) {
//         scannerToAddProduct.show();
//         myModal.hide();
//     }
// })
function newScanner() {
    var html5QrcodeScanner = new Html5QrcodeScanner(
    "reader",
    {
        formatsToSupport: [Html5QrcodeSupportedFormats.EAN_13],
        fps: 10, qrbox: { width: 250, height: 200 }
    });
html5QrcodeScanner.render(onScanSuccess);
}

let currentPage = 1;

function loadProduct() {
    var items = document.getElementsByClassName('product-row');
    for (var i = 0; i < items.length; i++) {
        if (i >= 0 && i < 6) {
            items[i].style.display = 'table-row';
        } else {
            items[i].style.display = 'none';
        }
    }
    // document.getElementById('itemBack').style.visibility = 'hidden';
    // document.getElementById('page1').style.backgroundColor = '#EEEEEE';
    // var pages = document.getElementsByClassName('page-item');
    // var numPage = Math.floor(items.length/6) + 1;
}
let items = document.getElementsByClassName('product-row');
let numPage = Math.floor(items.length / 6) + 1;

// function changePage(event) {
//     let pageLinkId = event.target.id;
//     let pageIndex = parseInt(pageLinkId.substring(4));
//     let page = 'page';
//     let currentPageString1 = currentPage.toString();
//     document.getElementById(page.concat("",currentPageString1)).style.backgroundColor = 'transparent';
//     if (pageIndex === 0) {
//         currentPage++;
//     }
//     else if (pageIndex === -1) {
//         currentPage--;
//     } else {
//         currentPage = parseInt(pageIndex);
//     }
//     let itemBack = document.getElementById('itemBack');
//     if (currentPage === 1) itemBack.style.visibility = 'hidden';
//     else itemBack.style.visibility = 'visible';

//     let itemNext = document.getElementById('itemNext');
//     if (currentPage === numPage) itemNext.style.visibility = 'hidden';
//     else itemNext.style.visibility = 'visible';

//     for (let i = 0; i < items.length; i++) {
//         if (i < (6 * (currentPage - 1)) || i >= (6 * currentPage)) {
//             items[i].style.display = 'none';
//         } else {
//             items[i].style.display = 'table-row';
//         }
//     }
//     let currentPageString2 = currentPage.toString();
//     document.getElementById(page.concat("",currentPageString2)).style.backgroundColor = '#EEEEEE';
// }
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
        }
    });
});