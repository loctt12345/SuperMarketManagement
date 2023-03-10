function openWalletInfo() {
    let walletInfo = document.getElementById("wallet_info");
    walletInfo.style.display = "flex";
}

function closeWalletInfo() {
    let walletInfo = document.getElementById("wallet_info");
    walletInfo.style.display = "none";
}

function setTotalPrice() {
//    fetch('/api/cart/getTotalPriceInCart').then(function (response) {
//        response.json().then((data) => {
//            document.getElementById('totalPrice').innerHTML = data
//                    .toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
//        });
//    });
    
    fetch('/api/cart/getTotalPriceInCartToDollar').then((response) => {
                response.json().then((data) => {
                    document.getElementById('totalPrice').innerHTML = data.toLocaleString('it-IT', {
                        style : 'currency', currency : 'USD', minimumFractionDigits: 2,
                        maximumFractionDigits: 2});
                    var price = JSON.parse(data);
                    document.getElementById('total').value = price.toFixed(2);
                });
            });
}