<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload file</title>
    <script src="${pageContext.request.contextPath}/static/js/home-scripts.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home-styles.css">
</head>
<body>
<main class="main">
    <div class="upload-form">
        <h3 class="upload-form__header">Upload your file:</h3>
        <div class="upload-form__file-area" role="button" id="uploadArea">
            <div class="upload-form__file-area__text">Click to choose file, or drop it here</div>
        </div>
        <div class="progress-bar upload-form__progress-bar" id="uploadProgressBar">
            <div class="progress-bar__wrapper">
                <div class="progress-bar__body">
                    <div class="progress-bar__indicator" id="progressBarIndicator"></div>
                </div>
                <div class="progress-bar__caption">
                    <div class="progress-bar__text" id="progressBarText">Upload: 40%</div>
                </div>
            </div>
        </div>
        <div class="button upload-form__upload-button" id="uploadButton">Upload</div>
        <div class="upload-form__finish">
            <div class="upload-form__link-description">
                Thanks! Your file was uploaded. Here's your link:
            </div>
            <div class="upload-form__link" id="fileLink">

            </div>
            <div class="button upload-form__another-button" id="uploadAnotherButton">
                Upload another file
            </div>
        </div>
    </div>
</main>
<input type="file" style="display: none" id="userFile">
</body>
</html>