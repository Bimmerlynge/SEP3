var value_clicked = 20;
window.GetProgress = () => {
    document.getElementById('progressBar').addEventListener('click', function (e) {
        value_clicked = e.offsetX * this.max / this.offsetWidth;
    });
    return value_clicked;
};