window.onload = function() {
    for (let i = 1; i <= orderStatus; i++) {
        document.getElementsByClassName('step' + i)[0].classList.add("active");
    }
};