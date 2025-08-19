@import hexlet.code.model.Url
@param Url url


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>URL: ${url.getName()}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1>URL: ${url.getName()}</h1>
    <p>ID: ${url.getId()}</p>
    <p>Дата создания: ${url.getCreatedAt().toString()}</p> <!-- Добавьте .toString() -->
    <a href="/urls" class="btn btn-primary">Назад к списку</a>
</div>
</body>
</html>