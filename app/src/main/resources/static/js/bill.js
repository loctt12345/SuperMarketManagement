/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





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
    }

    let totals = document.getElementsByClassName('total');
    for (var i = 0; i < totals.length; ++i) {
        var total = parseFloat(totals[i].innerHTML);
        totals[i].innerHTML = total.toLocaleString('de-DE',
                {style: 'currency', currency: 'VND'});
    }

};