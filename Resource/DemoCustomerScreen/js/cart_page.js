function subQuantity(id) {
    let input = document.getElementById("quantity-input" + id);
    if (input.value === "0") {

    }
    else {
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