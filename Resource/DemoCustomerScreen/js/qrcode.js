function onScanSuccess(qrCodeMessage) {
    window.location.href =
        "http://127.0.0.1:5500/Resource/DemoCustomerScreen/"
        + qrCodeMessage + ".html";
}

var html5QrcodeScanner = new Html5QrcodeScanner(
    "reader",
    { fps: 10, qrbox: { width: 150, height: 150 } });
html5QrcodeScanner.render(onScanSuccess);

$(document).ready(function () { $('#loginModal').modal('show'); $(function () { $('[data-toggle="tooltip"]').tooltip() }) });
