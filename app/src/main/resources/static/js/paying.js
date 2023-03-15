function openWalletInfo() {
    let walletInfo = document.getElementById("wallet_info");
    walletInfo.style.display = "flex";
}

function closeWalletInfo() {
    let walletInfo = document.getElementById("wallet_info");
    walletInfo.style.display = "none";
}

function setTotalPrice() {
    fetch('/api/cart/getTotalPriceInCart').then(function (response) {
        response.json().then((data) => {
            document.getElementById('totalPrice').innerHTML = data
                    .toLocaleString('de-DE', {style : 'currency', currency : 'VND'});
        });
    });

    fetch('/api/cart/getTotalPriceInCartToDollar').then((response) => {
        response.json().then((data) => {
            var price = JSON.parse(data);
            document.getElementById('total').value = price.toFixed(2);
        });
    });
}