/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function subQuantity(id) {
    let input = document.getElementById("quantity-input" + id);
    if (input.value === "0") {

    } else {
        if (input.value === "1") {
            let btnUpdate = document.getElementById("update" + id);
            btnUpdate.innerHTML = "Remove";
        }
        input.value = (parseInt(input.value) - 1).toString();
    }
}

function addQuantity(id) {
    let input = document.getElementById("quantity-input" + id);
    if (input.value === "0") {
        let btnUpdate = document.getElementById("update" + id);
        btnUpdate.innerHTML = "Update";
    }
    input.value = (parseInt(input.value) + 1).toString();
}

function setTotalPrice() {
    var priceTags = document.getElementsByClassName('price');
    var sum = 0;
    for (var priceTag = 0; priceTag < priceTags.length; ++priceTag) {
        var quantityTag = document.getElementById("quantity-input" + (priceTag + 1));
        var priceText = priceTags[priceTag].textContent;
        sum = sum + parseInt(priceText.substring(0, priceText.length - 1)) * quantityTag.value;
    }
    document.getElementById('totalPrice').innerHTML = sum + '₫';
}

function loadCart() {
    fetch('/api/cart/show/2').then(function (response) {
        response.json().then(data => {
            console.log(data);
            let list = document.getElementById("list");
            for (var k in data) {
                var div = document.createElement('tr');
                div.id = 'td'+k;
                div.innerHTML = `
                            <td>
                                <img src="${data[k]['imageLink']}" class="img-fluid rounded img-thumbnail col-md-9" 
                                     id="img"/>
                            </td>
                            <td>
                                <a href="#">
                                    <h5 class="title" id="title">
                                        ${data[k]['name']}
                                    </h5>
                                </a>
                            </td>
                            <td>
                                <h5 class="price">${data[k]['sellprice']}₫</h5>
                            </td>
                            <td>
                                <div class="form-group d-flex">
                                    <button class="btn btn-light" onclick="subQuantity(${k})">
                                        <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/icons-remove.svg"
                                             alt="remove-icon" width="20" height="20" />
                                    </button>
                                    <input type="text" value="${data[k]['quantityInCart']}" id="quantity-input${k}" class="form-control col-3 col-md-3" />
                                    <button class="btn btn-light" onclick="addQuantity(${k})">
                                        <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/icons-add.svg"
                                             alt="add-icon" width="20" height="20" />
                                    </button>
                                </div>
                            </td>
                            <td>
                                <button id="update${k}" class="btn btn-danger btnUpdate" onclick="update(${k})">Update</button>
                            </td>
                            
                        <input type="hidden" id="product_id${k}" name="" value="${data[k]['productID']}" />

                        <!--Row ${k}-->`;
                list.appendChild(div);

            }
            setTotalPrice();
        });
    });

}

function update(index) {
    var productID = document.getElementById('product_id' + index).value;
    var quantity = document.getElementById('quantity-input' + index).value;
    var buttonName = document.getElementById('update' + index).innerHTML;

    if (buttonName === "Update") {
        fetch('/api/cart/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'productID': productID, 'quantityInCart': quantity})
        }).then(
                function (response) {
                    response.json().then(data => {
                        setTotalPrice();
                    });
                }
        );
    } else
    if (buttonName === "Remove") {
        fetch('/api/cart/remove', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'productID': productID})
        }).then(
                function (response) {
                    response.json().then(data => {
                       document.getElementById('td'+index).style.display = 'none';
                    });
                    setTotalPrice();
                }
        );
    }
}

