<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>First JSP App</title>
</head>
<body>
<h1>Hello world.</h1>
<form action="api/v1/file/" method="post" enctype="multipart/form-data">
    <input type="file" name="userFile" id="userFile">
    <button type="submit">send</button>
</form>
</body>
</html>