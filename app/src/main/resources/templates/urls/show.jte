@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import java.util.List
@import java.time.format.DateTimeFormatter

@param Url url
@param List<UrlCheck> checks

<h1>Сайт: ${url.getName()}</h1>

<table class="table table-bordered">
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

<hr>

<h2>Проверки</h2>

<!-- Форма для проверки -->
<form action="/urls/${url.getId()}/checks" method="post" class="mb-3">
    <button type="submit" class="btn btn-primary">Запустить проверку</button>
</form>

@if(!checks.isEmpty())
<table class="table table-bordered table-hover">
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
@else
<p>Проверок еще не было</p>
@endif

<footer class="mt-4">
    <small>created by Hexlet</small>
</footer>