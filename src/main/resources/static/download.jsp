<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Download file</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/download-styles.css">
    <script src="${pageContext.request.contextPath}/static/js/download-scripts.js"></script>
</head>
<body>
<main class="main">
    <a href="${pageContext.request.contextPath}/" class="home-widget">
        <div class="home-widget__text">
            <-- Go home
        </div>
    </a>
    <div class="download-form">
        <div class="download-form__body">
            <div class="download-form__text">Your file is ready to be downloaded =)</div>
            <div class="download-form__success-text">Download has started...</div>
            <div class="button download-form__download-button" role="button" id="downloadButton">Download</div>
        </div>
        <div class="download-form__remove-suggestion">
            <div class="download-form__remove-text">If you don't need this file anymore, please remove it.</div>
            <div class="button download-form__remove-button" role="button" id="removeButton">Remove</div>
        </div>
    </div>
</main>
<div id="fileID" style="display: none; opacity: 0; pointer-events: none">
    <% out.print(request.getParameter("id")); %>
</div>
</body>
</html>