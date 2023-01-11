function subQuantity() {
    let input = document.getElementById("quantity-input");
    if (input.value === "0") {
        input.disabled = true;
    }
    else 
        input.value = (parseInt(input.value) - 1).toString();
}

function addQuantity() {
    let input = document.getElementById("quantity-input");
    input.value = (parseInt(input.value) + 1).toString();
}