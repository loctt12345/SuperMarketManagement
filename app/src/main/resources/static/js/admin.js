/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function onScanSuccess(qrCodeMessage) {
    $('#scannerToAddProduct').modal('hide');
    $('#myModal').modal('show');
    document.getElementById('productID').value =qrCodeMessage;
    html5QrcodeScanner.clear();
    html5QrcodeScanner = new Html5QrcodeScanner(
        "reader",
        { formatsToSupport: [ Html5QrcodeSupportedFormats.EAN_13],
            fps: 10, qrbox: { width: 250, height: 200 } });
}

var html5QrcodeScanner = new Html5QrcodeScanner(
    "reader",
    { formatsToSupport: [ Html5QrcodeSupportedFormats.EAN_13],
        fps: 10, qrbox: { width: 250, height: 200 } });
html5QrcodeScanner.render(onScanSuccess);