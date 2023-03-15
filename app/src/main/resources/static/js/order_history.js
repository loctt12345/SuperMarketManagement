/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



let money = document.getElementsByClassName('money');
            for (let i = 0; i < money.length; ++i) {
                const x = parseFloat(money[i].innerHTML);
                money[i].innerHTML = x.toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
            }

function redirect (link) {
    window.location.href = link;
} 