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
                var div = document.createElement('tr');
                div.id = 'td' + k;
                div.innerHTML = `
                            <td>
                                <img src="${data[k]['imageLink']}" class="img-fluid rounded img-thumbnail col-md-9" 
                                     id="img"/>
                            </td>
                            <td>
                                <a href="/product-detail?productID=${data[k]['productID']}">
                                    <h5 class="title" id="title">
                                        ${data[k]['name']}
                                    </h5>
                                </a>
                            </td>
                            <td>
                                <h5 class="price">${data[k]['sellprice']
                        .toLocaleString('it-IT', {style: 'currency', currency: 'VND'})}</h5>
                            </td>
                            <td class="text-center">
                                <p>${data[k]['quantityInCart']}</p>
                               
                            </td>
                            
                        <!--Row ${k}-->`;
                list.appendChild(div);

            }

            
        });
    });

}