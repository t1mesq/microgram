<#macro layout>

    <!DOCTYPE html>
    <html lang="ru">

    <head>
        <meta charset="UTF-8">
        <title>Microgram</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">


        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="../css/style.css" rel="stylesheet">
    </head>

    <body>
    <#assign
    known = SPRING_SECURITY_CONTEXT??
    >

    <div class="container-fluid p-0">
        <nav class="navbar navbar-expand-lg bg-white navbar-light py-3 py-lg-0 px-lg-5">
            <a href="/" class="navbar-brand ml-lg-3">
                <h1 class="m-0 text-uppercase text-primary">Microgram</h1>
            </a>
            <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-between px-lg-3" id="navbarCollapse">
                <div class="navbar-nav mx-auto py-0">
                    <a href="/" class="nav-item nav-link">Главная</a>
                    <#if known>
                    <a href="/profile" class="nav-item nav-link">Профиль</a>
                    </#if>
                </div>

                <#if known>
                    <#assign
                    user = SPRING_SECURITY_CONTEXT.authentication.principal
                    name = user.getUsername()
                    >
                    <a href="/profile" class="text-dark link-underline-dark order-md-1 mr-2 ">Добро
                        пожаловать ${name}</a>
                    <a href="/logout" class="btn btn-primary rounded-1 order-md-1 " id="logout">Выход</a>
                <#else >
                    <a href="/auth/login" class="btn btn-primary rounded-1 btn-user order-md-1 mr-2"
                       type="button">Вход</a>
                    <a class="btn btn-primary rounded-1 btn-user order-md-1 " href="/user/register">Регистрация</a>
                </#if>

            </div>
        </nav>
        <hr class="my-0 border-top">
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="../js/profile.js"></script>
    <script src="../js/workWithPost.js"></script>
    <script src="../js/authorizationHelper.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCh39n5U-4IoWpsVGUHWdqB6puEkhRLdmI&callback=myMap"></script>

    <script>

        let userAunt= false;
        <#if known>
        userAunt= true;
        </#if>

    </script>









    <#nested >

    </body>

</html>
</#macro>