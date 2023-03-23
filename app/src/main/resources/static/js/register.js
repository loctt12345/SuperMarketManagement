/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var check = function () {
    if (document.getElementById('password').value ===
            document.getElementById('confirm_password').value) {
        document.getElementById('confirm_password').setCustomValidity('');
    } else {
        document.querySelector('#confirm_password') 
                .setCustomValidity('Mật khẩu không phù hợp');
    }
}
