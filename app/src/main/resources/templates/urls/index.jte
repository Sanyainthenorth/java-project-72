@import java.util.List
@import hexlet.code.model.Url

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>URL List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>URL List</h1>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>URL</th>
                    <th>Created At</th>
                </tr>
            </thead>
            <tbody>
            @for (Url url : urls)
                <tr>
                    <td>${url.getId()}</td>
                    <td>${url.getName()}</td>
                    <td>${url.getCreatedAt()}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
</body>
</html>