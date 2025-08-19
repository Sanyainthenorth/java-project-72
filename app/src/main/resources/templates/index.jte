@param Object flash
@param Object flashType

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>URL Checker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    @if (flash != null)
        <div class="alert alert-${flashType}">${flash}</div>
    @endif

    <h1>Добавить новый URL</h1>
    <form action="/urls" method="post">
        <div class="mb-3">
            <input type="text" name="url" class="form-control" placeholder="https://example.com" required>
        </div>
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
</div>
</body>
</html>