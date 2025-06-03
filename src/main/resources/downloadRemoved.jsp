<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Download file</title>
    <style><%@include file="css/common-styles.css"%></style>
    <style><%@include file="css/download-styles.css"%></style>
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
            <div class="download-form__text">
                Your file was removed. <br>
                You can share more files on the home page. <br>
                Good luck! <br>
            </div>
            <a href="${pageContext.request.contextPath}/" class="button" role="button">Go home</a>
        </div>
    </div>
</main>
</body>
</html>