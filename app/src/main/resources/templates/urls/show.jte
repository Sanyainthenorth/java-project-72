@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import java.util.List

@param Url url
@param List<UrlCheck> checks

<h1>Сайт: ${url.getName()}</h1>

<!-- Форма для проверки -->
<form action="/urls/${url.getId()}/checks" method="post">
    <button type="submit" class="btn btn-primary">Проверить</button>
</form>

<table class="table table-bordered table-hover mt-3">
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
            <td>${check.getCreatedAt()}</td>
        </tr>
        @else
        <tr>
            <td colspan="6" class="text-center">Проверок еще не было</td>
        </tr>
        @endfor
    </tbody>
</table>