/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function onScanSuccess(qrCodeMessage) {
    window.location.href =
        "http://localhost:8080/product-detail/?productID="
        + qrCodeMessage;
}

var html5QrcodeScanner = new Html5QrcodeScanner(
    "reader",
    { formatsToSupport: [ Html5QrcodeSupportedFormats.EAN_13],
        fps: 10, qrbox: { width: 250, height: 200 } });
html5QrcodeScanner.render(onScanSuccess);

$(document).ready(function () { $('#loginModal').modal('show'); $(function () { $('[data-toggle="tooltip"]').tooltip() }) });