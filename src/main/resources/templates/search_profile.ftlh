<#import "layout.ftlh" as main>
<@main.layout>

    <link rel="stylesheet" href="../css/search_profile.css">

    <div class="container">
        <h2>Profile</h2>
        <div class="col-md-3">
            <img src="/users/download/${user.id}" class="img-fluid rounded-circle" alt="Profile Picture">
        </div>
        <p>Name: ${user.name}</p>
        <p>Nickname: ${user.nickName}</p>
        <p>Email: ${user.email}</p>
        <p>User Info: ${user.bio}</p>
        <p>Публикаций: ${publicationCount}</p>


        <div class="row mt-5">
            <#list publications as post>
                <div class="col-md-4 mb-4">
                    <div onclick="post_info('${post.id}')" class="card opacity-75 border-0 bg-white h-100 ">
                        <div class="card-body border-top border-3 mx-3 px-0 d-flex flex-wrap justify-content-between flex-column">
                            <img src="/publications/download/${post.id}" class="card-img opacity-100"
                                 style="width: 45%;" alt="image">
                            <p class="card-title text-center mt-2 fst-italic fs-5">${post.description}</p>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        <#if isSubscribed?? && isSubscribed>
            <form action="/unsubscribe" method="post" class="subscribe-form">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit" class="btn btn-primary">Отписаться</button>
            </form>
        <#elseif isSubscribed??>
            <form action="/subscribe" method="post" class="subscribe-form">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit" class="btn btn-primary">Подписаться</button>
            </form>
        <#else>
            <p>Для подписки на пользователя, пожалуйста, <a href="/auth/login">войдите</a> в свой аккаунт.</p>
        </#if>
    </div>

</@main.layout>
