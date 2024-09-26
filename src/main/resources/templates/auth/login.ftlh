<#import "../layout.ftlh" as main>
<@main.layout>


    <link href="../css/login.css" rel="stylesheet">

    <div id="particles-js"></div>
    <body class="login">
    <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">

        <div class="login-container-wrapper clearfix">
            <div class="welcome"><strong>Добро пожаловать,</strong> пожалуйста пройдите авторизацию.</div>

            <form  id="login-form" action="/auth/login" method="post" class="form-horizontal login-form">
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="email" id="form3Example3" class="form-control input-lg" name="username" placeholder="Email"/>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group relative password">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="password" id="form3Example4" class="form-control input-lg" name="password" placeholder="Пароль"/>
                        <i class="fa fa-lock"></i>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" data-mdb-button-init data-mdb-ripple-init
                            class="btn btn-success btn-lg btn-block">
                        Войти
                    </button>
                </div>

            </form>
        </div>
    </div>

    </body>


</@main.layout>