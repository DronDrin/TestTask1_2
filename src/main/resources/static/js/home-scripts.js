let uploadButton, uploadArea, progressBar, progressBarIndicator, progressBarText, userFileEl, uploadForm,
    uploadAnotherButton, fileLinkEl;
let _stage = 0;
let file, fileName;

function setFile(_file) {
    const textEl = uploadArea.querySelector('.upload-form__file-area__text');
    file = _file;
    fileName = _file.name;
    if (fileName.length > 60)
        fileName = fileName.substring(0, 60);
    textEl.innerText = fileName;
    setStage(1);
}

window.addEventListener('load', () => {
    uploadButton = document.getElementById("uploadButton");
    uploadArea = document.getElementById("uploadArea");
    progressBar = document.getElementById("uploadProgressBar");
    progressBarIndicator = document.getElementById("progressBarIndicator");
    progressBarText = document.getElementById("progressBarText");
    userFileEl = document.getElementById('userFile');
    uploadAnotherButton = document.getElementById('uploadAnotherButton');
    fileLinkEl = document.getElementById('fileLink');
    uploadForm = document.querySelector('.upload-form');

    uploadArea.addEventListener('click', () => {
        if (getStage() <= 1)
            userFileEl.click();
    });

    uploadArea.addEventListener('drop', e => {
        if (e.dataTransfer.files.length > 0)
            setFile(e.dataTransfer.files[0]);
        e.preventDefault();
    });

    uploadArea.addEventListener('dragover', e => e.preventDefault());

    userFileEl.addEventListener('change', () => {
        if (getStage() > 1)
            return;
        const textEl = uploadArea.querySelector('.upload-form__file-area__text');
        if (userFileEl.files.length > 0) {
            setFile(userFileEl.files[0]);
        } else {
            textEl.innerText = 'Click to choose file, or drop it here';
            setStage(0);
        }
    });

    uploadButton.addEventListener('click', () => {
        if (getStage() === 1) {
            setStage(2);
            const request = new XMLHttpRequest();
            request.open("post", "/api/v1/file/");
            request.upload.addEventListener('progress', e => {
                if (e.lengthComputable) {
                    setProgressBarState(progressBar, e.loaded / e.total, e.loaded)
                } else {
                    setProgressBarState(progressBar, undefined, e.loaded);
                }
            });
            request.responseType = "text";
            request.upload.addEventListener('load', () => {
                setProgressBarState(progressBar, 1);
            });
            request.addEventListener('load', () => {
                fileLinkEl.innerText = window.location.origin + '/download/?id=' + request.responseText;
                setStage(3);
            });
            const data = new FormData();
            data.set("userFile", file);
            request.send(data);
        }
    });

    uploadAnotherButton.addEventListener('click', () => {
        if (getStage() === 3)
            setStage(0);
    });
});

function setStage(newStage) {
    _stage = newStage;
    removeClass(uploadForm, 'upload-form_file-chosen');
    removeClass(uploadForm, 'upload-form_uploading');
    removeClass(uploadForm, 'upload-form_finish');
    switch (_stage) {
        case 1:
            addClass(uploadForm, 'upload-form_file-chosen');
            break;
        case 2:
            addClass(uploadForm, 'upload-form_uploading');
            break;
        case 3:
            addClass(uploadForm, 'upload-form_finish');
            break;
    }
}

function getStage() {
    return _stage;
}

function setProgressBarState(progressBar, decimalProgress, bytes) {
    if (decimalProgress === undefined) {
        addClass(progressBar, 'progress-bar_unset')
        if (bytes < 10000) {
            progressBarText.innerText = bytes + ' bytes';
        } else if (bytes < 1000000) {
            progressBarText.innerText = round(bytes / 1024, 2) + ' KiB';
        } else if (bytes < 1000000000) {
            progressBarText.innerText = round(bytes / 1048576, 2) + ' MiB';
        } else {
            progressBarText.innerText = round(bytes / 1073741824, 2) + ' GiB';
        }

    } else {
        removeClass(progressBar, 'progress-bar_unset');
        const percentage = round(decimalProgress * 100, 2) + '%';
        progressBarIndicator.style.width = percentage;
        progressBarText.innerText = percentage;
    }
}

function addClass(element, className) {
    if (!element.classList.contains(className))
        element.classList.add(className);
}

function removeClass(element, className) {
    if (element.classList.contains(className))
        element.classList.remove(className);
}

function round(number, fractionalPartSize) {
    const multiplier = Math.pow(10, fractionalPartSize);
    return Math.round(number * multiplier) / multiplier;
}