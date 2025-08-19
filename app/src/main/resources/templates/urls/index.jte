@param List<Url> urls
@param Object flash
@param Object flashType

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
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Дата создания</th>
            </tr>
        </thead>
        <tbody>
            @for (var url : urls)
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