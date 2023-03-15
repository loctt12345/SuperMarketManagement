/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function loadCart() {
    fetch('/api/cart/show/' + '1').then(function (response) {
        response.json().then(data => {
            let list = document.getElementById("list");
            list.innerHTML = '';
            for (var k in data) {
                var div = document.createElement('div');
                div.innerHTML = `
                <div class="card-body">
                            <div class="row">
                                <div class="col-6">
                                    <img src="${data[k]['imageLink']}"
                                         class="img-responsive mt-2" alt="">
                                </div>
                                <div class="col-6">
                                    <a href="/product-detail?productID=${data[k]['productID']}" class="btn-link">${data[k]['name']}</a>
                                    <h6 class="font-weight-bold text-success">${data[k]['sellprice']
                        .toLocaleString('de-DE', {style: 'currency', currency: 'VND'})} 
x ${data[k]['quantityInCart']}</h6>
                                </div>
                            </div>
                        </div>
                            `;
                list.appendChild(div);

            }


        });
    });

}

$(document).ready(function () {
    $("#fab").click(function () {
        $("#fab").toggleClass("fabOpen");
        $(".fabContent").toggleClass("d-block");
    });
});
