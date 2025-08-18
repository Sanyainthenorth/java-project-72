@param List<Url> urls
@param String flash
@param String flashType

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    @if (flash != null)
        <div class="alert alert-${flashType}">${flash}</div>
    @endif

    <h1>Список URL</h1>
    <table class="table">
        <thead>
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Дата создания</th>
            </tr>
        </thead>
        <tbody>
        @for (Url url : urls)
            <tr>
                <td>${url.getId()}</td>
                <td><a href="/urls/${url.getId()}">${url.getName()}</a></td>
                <td>${url.getCreatedAt()}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>