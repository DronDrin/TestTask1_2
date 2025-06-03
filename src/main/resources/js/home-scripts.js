let sendButton, userFile, progressBar;

window.addEventListener('load', () => {
    sendButton = document.getElementById("sendButton");
    userFile = document.getElementById("userFile");
    progressBar = document.getElementById("progressBar");

    sendButton.addEventListener('click', () => {
        if (userFile.files.length > 0) {
            const file = userFile.files[0];
            const request = new XMLHttpRequest();
            request.open("post", "/api/v1/file/");
            request.upload.addEventListener('progress', e => {
                if (e.lengthComputable) {
                    setProgressBarState(progressBar, e.loaded / e.total, e.loaded)
                } else {
                    setProgressBarState(progressBar, undefined, e.loaded);
                }
            });
            request.responseType = "blob";
            request.upload.addEventListener('load', () => {
                setProgressBarState(progressBar, 1)
            });
            const data = new FormData();
            data.set("userFile", file);
            request.send(data);
        }
    });
});


function setProgressBarState(progressBar, decimalProgress, bytes) {
    progressBar.innerText = decimalProgress === undefined ? bytes + " bytes" : Math.round(decimalProgress * 10000) / 100 + "%";
}

