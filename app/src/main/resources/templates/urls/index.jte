@import hexlet.code.dto.UrlsPage
@import hexlet.code.model.Url
@import java.time.format.DateTimeFormatter

@param UrlsPage page

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц - Сайты</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .navbar {
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 15px 0;
        }
        .navbar-brand {
            font-weight: 600;
            color: #333;
            font-size: 1.5rem;
        }
        .nav-link {
            color: #6c757d;
            font-weight: 500;
            margin-right: 20px;
        }
        .nav-link.active {
            color: #0d6efd;
        }
        .content-section {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            padding: 30px;
            margin-top: 30px;
        }
        .section-title {
            font-weight: 600;
            margin-bottom: 25px;
            color: #333;
            font-size: 1.8rem;
        }
        .table th {
            border-top: none;
            font-weight: 600;
            color: #495057;
            background-color: #f8f9fa;
            padding: 15px;
        }
        .table td {
            padding: 15px;
            vertical-align: middle;
        }
        .footer {
            text-align: center;
            margin-top: 40px;
            color: #6c757d;
            font-size: 0.9rem;
        }
        .no-data {
            text-align: center;
            padding: 40px;
            color: #6c757d;
            font-style: italic;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <span class="navbar-brand mb-0 h1">Анализатор страниц</span>
            <div class="d-flex">
                <a href="/" class="nav-link">Главная</a>
                <a href="/urls" class="nav-link active">Сайты</a>
            </div>
        </div>
    </nav>

    <div class="container">
        @if (page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()} alert-dismissible fade show mt-3">
            ${page.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        @endif

        <div class="content-section">
            <h1 class="section-title">Сайты</h1>

            @if (page.getUrls() != null && !page.getUrls().isEmpty())
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Имя</th>
                            <th>Последняя проверка</th>
                            <th>Код ответа</th>
                        </tr>
                    </thead>
                    <tbody>
                        @for (var url : page.getUrls())
                        <tr>
                            <td>${url.getId()}</td>
                            <td><a href="/urls/${url.getId()}" class="text-decoration-none">${url.getName()}</a></td>
                            <td>
                                @if (page.getLatestChecks() != null && page.getLatestChecks().containsKey(url.getId()))
                                    ${page.getLatestChecks().get(url.getId()).getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}
                                @else
                                    <span class="text-muted">—</span>
                                @endif
                            </td>
                            <td>
                                @if (page.getLatestChecks() != null && page.getLatestChecks().containsKey(url.getId()))
                                    ${page.getLatestChecks().get(url.getId()).getStatusCode()}
                                @else
                                    <span class="text-muted">—</span>
                                @endif
                            </td>
                        </tr>
                        @endfor
                    </tbody>
                </table>
            </div>
            @else
            <div class="no-data">
                <p>Пока нет добавленных сайтов</p>
            </div>
            @endif
        </div>
    </div>

    <div class="footer">
        created by Sasha
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>