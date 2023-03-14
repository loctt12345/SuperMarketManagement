/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function action() {
    var x = document.getElementById("orderId").value;
    fetch("api/order/updateStatus", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({"txtOrderId" : x, "txtStatus" : "5"})
    }).then((response) => {
        window.location.href = "/shipStaff";
    });
}