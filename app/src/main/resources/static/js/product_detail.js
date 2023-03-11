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
                            data['sellprice'].toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
                    document.getElementById("description").innerHTML = data['description'];
                    $("input").keydown(function (event) {
                        if (event.keyCode === 13) {
                            event.preventDefault();
                        }
                    });
                }).catch((err) => {
                    document.getElementById('content').innerHTML = `
                        <h2 class="row justify-content-center col-md-12 col-12"> The product is not in our super market </h2>
                        <a class="row justify-content-center col-md-12 col-12" onclick="showForm()">You can suggest your product for us here!!!</a>
                        `;
                });
            }
    );
}

function showForm() {
    var form = document.getElementById("form");
    if (form === null) {
        var div = document.createElement("div");
        var productId = document.getElementById("product_id").value;
        div.innerHTML = `
        <form id="form" 
              action="/showRecommendation"
              class="row justify-content-center mt-3 form-group-inline"> 
            <input type="hidden" value="${productId}" name="txtProductId" />
            <input class="form-control col-5 ml-2 mr-2" 
                type="text" id="name" name="txtComment"
                placeholder="Leave us your comment..."
            />
            <input class="btn btn-danger" type="submit" value="Submit" />
        </form>
    `;
        document.getElementById('content').appendChild(div);
    }
}

function addToCart() {
    var txtProductID = document.getElementById('product_id').value;
    var txtNumber = document.getElementById('quantity-input').value;
    fetch('/api/cart/addToCart?txtProductID=' + txtProductID + '&txtNumber=' + txtNumber).then((response) => {
        window.location.href = "/";
    });
}
