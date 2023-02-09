/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function subQuantity() {
    let input = document.getElementById("quantity-input");
    if (input.value === "0") {
        input.disabled = true;
    } else
        input.value = (parseInt(input.value) - 1).toString();
}

function addQuantity() {
    let input = document.getElementById("quantity-input");
    input.value = (parseInt(input.value) + 1).toString();
}

function loadProduct() {
    let productID = document.getElementById("product_id").value;
    fetch('/api/products?productID=' + productID, {
    }).then(
            function (response) {
                response.json().then(data => {
                    document.getElementById("imgProduct").src = data['imageLink'];
                    document.getElementById("title").innerHTML = data['name'];
                    document.getElementById("price").innerHTML = 
                            data['sellprice'].toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
                    document.getElementById("description").innerHTML = data['description'];
                }).catch((err) => {
                    document.getElementById('content').innerHTML = `
                        <h2 class="row justify-content-center"> The product is not in our super market </h2>
                        `
                });
            }
    );
}

