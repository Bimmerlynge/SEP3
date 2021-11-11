function getProgress(e) {
    var progress = document.getElementById("progressBar");
    var value_clicked = e.offsetX * progress.max / progress.offsetWidth;
    if (value_clicked >= 100) {
        value_clicked = 100;
    }
    else if(value_clicked <= 0) {
        value_clicked = 0;
    }
    return value_clicked;
}

function getVolume(e) {
    var volume = document.getElementById("volumeControl");
    var value_clicked = e.offsetX * volume.max / volume.offsetWidth;
    if (value_clicked >= 100) {
        value_clicked = 100;
    }
    else if(value_clicked <= 0) {
        value_clicked = 0;
    }
    return Math.floor(value_clicked);
}

