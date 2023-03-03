/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//function downloadURI(uri, name) {
//  var link = document.createElement("a");
//  link.download = name;
//  link.href = uri;
//  document.body.appendChild(link);
//  link.click();
//  document.body.removeChild(link);
//  delete link;
//};

window.onload = function ()
{
    var x = document.getElementById('orderId').value;
    let qrcode = new QRCode(document.getElementById("qrcode"),
            {
                text: x,
                width: 300,
                height: 300,
                colorDark: "#000000",
                colorLight: "#ffffff",
                correctLevel: QRCode.CorrectLevel.H
            });
};
