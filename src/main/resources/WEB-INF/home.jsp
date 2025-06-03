<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>First JSP App</title>
    <script><%@include file="js/home-scripts.js"%></script>
</head>
<body>
<input type="file" id="userFile">
<button id="sendButton">send</button>
<span id="progressBar"></span>
<form action="api/v1/file/" method="get">
    <input type="tel" name="id" id="id">
    <button type="submit">download by id</button>
</form>
<form action="api/v1/file/remove/" method="post">
    <input type="tel" name="id" id="id">
    <button type="submit">delete by id</button>
</form>
</body>
</html>