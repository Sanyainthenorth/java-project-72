@param hexlet.code.dto.MainPage page

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* ваш существующий CSS */
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
        .hero-section {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            padding: 40px;
            margin-top: 30px;
            text-align: center;
        }
        .hero-title {
            font-weight: 600;
            margin-bottom: 20px;
            color: #333;
        }
        .hero-subtitle {
            color: #6c757d;
            margin-bottom: 30px;
            font-size: 1.1rem;
        }
        .url-input-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .form-control {
            padding: 12px 16px;
            border-radius: 8px;
            font-size: 1rem;
        }
        .btn-primary {
            padding: 12px 30px;
            border-radius: 8px;
            font-weight: 500;
            margin-top: 15px;
        }
        .example-text {
            color: #6c757d;
            font-size: 0.9rem;
            margin-top: 10px;
        }
        .footer {
            text-align: center;
            margin-top: 40px;
            color: #6c757d;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <span class="navbar-brand mb-0 h1">Анализатор страниц</span>
            <div class="d-flex">
                <a href="/" class="nav-link active">Главная</a>
                <a href="/urls" class="nav-link">Сайты</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <!-- ДОБАВЬТЕ ЭТОТ БЛОК ДЛЯ FLASH-СООБЩЕНИЙ -->
        @if (page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()} alert-dismissible fade show mt-3">
            ${page.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        @endif
        <!-- КОНЕЦ БЛОКА FLASH-СООБЩЕНИЙ -->

        <div class="hero-section">
            <h1 class="hero-title">Анализатор страниц</h1>
            <p class="hero-subtitle">Бесплатно проверяйте сайты на SEO пригодность</p>

            <div class="url-input-container">
                <form action="/urls" method="post">
                    <div class="mb-3">
                        <label for="urlInput" class="form-label">Ссылка</label>
                        <input type="text" class="form-control" id="urlInput" name="url"
                               placeholder="Введите URL сайта" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Проверить</button>
                    <div class="example-text">Пример: https://www.example.com</div>
                </form>
            </div>
        </div>
    </div>

    <div class="footer">
        created by Sasha
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>