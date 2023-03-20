///* 
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
function loadCart() {
    var page = document.getElementById('page').value;
    fetch('/api/cart/show/' + page).then(function (response) {
        response.json().then(data => {
            let list = document.getElementById("list");
            list.innerHTML = '';
            var div = document.createElement('div');
            div.className = "column-labels";
            div.innerHTML = `
                    <label class="product-image">Image</label>
                    <label class="product-details">Product</label>
                    <label class="product-price">Price</label>
                    <label class="product-quantity">Quantity</label>
                    <label class="product-removal">Remove</label>
                    <label class="product-line-price">Total</label>
            `;
            list.appendChild(div);
            for (var k in data) {
                var div = document.createElement('div');
                div.className = "product";
                div.id = 'div' + k;
                div.innerHTML = `
                        <div class="product-image b">
                            <img src="${data[k]['imageLink']}">
                        </div>
                        <div class="product-details">
                            <a href="/product-detail?productID=${data[k]['productID']}">
                                <div class="product-title">${data[k]['name']}</div>
                            </a>
                        </div>
                        <div class="product-price">${data[k]['sellprice']
                        .toLocaleString('de-DE',
                                {style: 'currency', currency: 'VND'})}
                        </div>
                            <div class="product-quantity">
                            <input type="number" 
                                    id="quantity-input${k}" 
                                    onblur="update(${k})"
                                    value="${data[k]['quantityInCart']}" min="1">
                        </div>
                        <div class="product-removal">
                            <button class="remove-product" onclick="remove(${k})">
                                Remove
                            </button>
                        </div>
                        <div class="product-line-price">
                            ${(data[k]['sellprice'] * data[k]['quantityInCart']).toLocaleString('de-DE',
                        {style: 'currency', currency: 'VND'})}
                        </div>
                 <input type="hidden" id="product_id${k}" value="${data[k]['productID']}" />
                `;
                list.appendChild(div);

            }

            fetch('/api/cart/getTotalPriceInCart').then((response) => {
                response.json().then((data) => {
                    if (data != 0) {
                        let list = document.getElementById("list");
                        let div = document.createElement('div');
                        div.className = "totals";
                        div.innerHTML = `
                    <div class="totals-item">
                        <label>Total:</label>
                        <div class="totals-value" id="cart-subtotal">
                            ${data.toLocaleString('de-DE', {style: 'currency', currency: 'VND'})}
                        </div>
                    </div>
                    `;
                        list.appendChild(div);
                        let button = document.createElement('button');
                        button.innerHTML = "Checkout";
                        button.className = "checkout";
                        button.onclick = function () {
                            window.location.href = "./showPaying"
                        };
                        list.appendChild(button);
                    }
                });
            });
        }).catch((err) => {
            let list = document.getElementById("list");
            list.innerHTML = '';
            var div = document.createElement('div');
            div.className = "column-labels";
            div.innerHTML = `
                    <label class="product-image">Image</label>
                    <label class="product-details">Product</label>
                    <label class="product-price">Price</label>
                    <label class="product-quantity">Quantity</label>
                    <label class="product-removal">Remove</label>
                    <label class="product-line-price">Total</label>
            `;
            list.appendChild(div);
        });
    });

}

function update(index) {
    var productID = document.getElementById('product_id' + index).value;
    var quantity = document.getElementById('quantity-input' + index).value;

    if (quantity <= 0) {
        quantity = '1';
        document.getElementById('quantity-input' + index).value = quantity;
    }
    fetch('/api/cart/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({'productID': productID, 'quantityInCart': quantity})
    }).then(
            function (response) {
                loadCart();
            }
    );
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
                    document.getElementById('cartIcon').setAttribute('value', data['cartSize']);
                }).then(() => {
                    loadCart();
                }
                );
            }
    );
}

