@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import java.util.List
@import java.time.format.DateTimeFormatter

@param Url url
@param List<UrlCheck> checks

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц - ${url.getName()}</title>
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
            margin-bottom: 20px;
            color: #333;
        }
        .info-table {
            width: 100%;
            border-collapse: collapse;
        }
        .info-table th {
            text-align: left;
            padding: 12px;
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
            width: 200px;
        }
        .info-table td {
            padding: 12px;
            border-bottom: 1px solid #dee2e6;
        }
        .checks-table {
            width: 100%;
            border-collapse: collapse;
        }
        .checks-table th {
            background-color: #f8f9fa;
            padding: 12px;
            text-align: left;
            border-bottom: 2px solid #dee2e6;
            font-weight: 600;
        }
        .checks-table td {
            padding: 12px;
            border-bottom: 1px solid #dee2e6;
        }
        .btn-primary {
            padding: 10px 25px;
            border-radius: 8px;
            font-weight: 500;
        }
        .footer {
            text-align: center;
            margin-top: 40px;
            color: #6c757d;
            font-size: 0.9rem;
        }
        .no-checks {
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
        <div class="content-section">
            <h1 class="section-title">Сайт: ${url.getName()}</h1>

            <!-- Информация о сайте -->
            <div class="mb-4">
                <h3 class="mb-3">Информация о сайте</h3>
                <table class="info-table">
                    <tr>
                        <th>ID</th>
                        <td>${url.getId()}</td>
                    </tr>
                    <tr>
                        <th>Имя</th>
                        <td>${url.getName()}</td>
                    </tr>
                    <tr>
                        <th>Дата создания</th>
                        <td>${url.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}</td>
                    </tr>
                </table>
            </div>

            <hr class="my-4">

            <!-- Проверки -->
            <div>
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3 class="mb-0">Проверки</h3>
                    <form action="/urls/${url.getId()}/checks" method="post">
                        <button type="submit" class="btn btn-primary">Запустить проверку</button>
                    </form>
                </div>

                @if(!checks.isEmpty())
                <div class="table-responsive">
                    <table class="checks-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Код ответа</th>
                                <th>Заголовок</th>
                                <th>H1</th>
                                <th>Описание</th>
                                <th>Дата проверки</th>
                            </tr>
                        </thead>
                        <tbody>
                            @for(var check : checks)
                            <tr>
                                <td>${check.getId()}</td>
                                <td>${check.getStatusCode()}</td>
                                <td>${check.getTitle()}</td>
                                <td>${check.getH1()}</td>
                                <td>${check.getDescription()}</td>
                                <td>${check.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}</td>
                            </tr>
                            @endfor
                        </tbody>
                    </table>
                </div>
                @else
                <div class="no-checks">
                    <p>Проверок еще не было</p>
                </div>
                @endif
            </div>
        </div>
    </div>

    <div class="footer">
        created by Sasha
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
