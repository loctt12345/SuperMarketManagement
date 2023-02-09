/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadCart() {
    var page = document.getElementById('page').value;
    fetch('/api/cart/show/' + page).then(function (response) {
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
                            <td>
                                <div class="form-group d-flex">
                                   
                                    <input type="number" min="0" value="${data[k]['quantityInCart']}" 
                                    id="quantity-input${k}" class="form-control col-7 col-md-7" />
                                   
                                </div>
                            </td>
                            <td class="col-md-2 ">
                                <button id="update${k}" class="btn btn-success" 
                                            onclick="update(${k})"><i class="fa-solid fa-pen-to-square"></i></button>
                                <button id="remove{k}" class="btn btn-danger" 
                                            onclick="remove(${k})"><i class="fa-solid fa-trash-can"></i></button>
                             </td>
                            
                        <input type="hidden" id="product_id${k}" name="" value="${data[k]['productID']}" />

                        <!--Row ${k}-->`;
                list.appendChild(div);

            }

            fetch('/api/cart/getTotalPriceInCart').then((response) => {
                response.json().then((data) => {
                    document.getElementById('totalPrice').innerHTML =
                            data.toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
                });
            });
        });
    });

}

function update(index) {
    var productID = document.getElementById('product_id' + index).value;
    var quantity = document.getElementById('quantity-input' + index).value;

    if (quantity == 0) {
        remove(index);
    } else {
        fetch('/api/cart/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'productID': productID, 'quantityInCart': quantity})
        }).then(
                function (response) {
                    response.json().then(data => {
                    }).then(() => {
                        fetch('/api/cart/getTotalPriceInCart').then((response) => {
                            response.json().then((data) => {
                                document.getElementById('totalPrice').innerHTML =
                                        data.toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
                            });
                        });
                    });
                }
        );
    }
}

function remove(index) {
    var productID = document.getElementById('product_id' + index).value;
    fetch('/api/cart/remove', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({'productID': productID})
    }).then(
            function (response) {
                response.json().then(data => {
                    document.getElementById('td' + index).style.display = 'none';
                    document.getElementById('cartIcon').setAttribute('value', data['cartSize']);
                }).then(() => {
                    fetch('/api/cart/getTotalPriceInCart').then((response) => {
                        response.json().then((data) => {
                            document.getElementById('totalPrice').innerHTML =
                                    data.toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
                        });
                    });
                }

                );
            }
    );
}

