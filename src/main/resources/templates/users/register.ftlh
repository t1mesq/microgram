<#import "../layout.ftlh" as main>
<@main.layout>
    <link href="../css/login.css" rel="stylesheet">

    <div id="particles-js"></div>
    <body class="login">
    <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
        <div class="login-container-wrapper clearfix">
            <div class="welcome"><strong>Добро пожаловать,</strong> пожалуйста зарегистрируйтесь.</div>
            <form action="/user/register" method="post" enctype="multipart/form-data"
                  class="form-horizontal login-form">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>
                <div class="form-group relative">
                    <div class="file-upload">
                        <span>Выберите файл</span>
                        <input type="file" name="file"/>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="email" id="form3Example3" class="form-control input-lg" name="email"
                               placeholder="Email"required/>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="text" id="nickName" class="form-control input-lg" name="nickName"
                               placeholder="Ник"required/>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="text" id="name" class="form-control input-lg" name="name"
                               placeholder="Имя"required/>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="text" id="surname" class="form-control input-lg" name="surname"
                               placeholder="Фамилия"required/>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="text" id="bio" class="form-control input-lg" name="bio"
                               placeholder="О себе"required/>
                        <i class="fa fa-info-circle"></i>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="text" id="age" class="form-control input-lg" name="age"
                               placeholder="Возраст"required/>
                        <i class="fa fa-birthday-cake"></i>
                    </div>
                </div>
                <div class="form-group relative">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="text" id="phoneNumber" class="form-control input-lg" name="phoneNumber"
                               placeholder="Телефон"required/>
                        <i class="fa fa-phone"></i>
                    </div>
                </div>
                <div class="form-group relative password">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="password" id="password" class="form-control input-lg" name="password"
                               placeholder="Пароль"required/>
                        <i class="fa fa-lock"></i>
                    </div>
                </div>
                <div class="form-group relative password">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="password" id="confirmationPassword" class="form-control input-lg"
                               name="confirmationPassword" placeholder="Подтверждение пароля"required/>
                        <i class="fa fa-lock"></i>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" data-mdb-button-init data-mdb-ripple-init
                            class="btn btn-success btn-lg btn-block">
                        Регистрация
                    </button>
                </div>
            </form>
        </div>
    </div>
    </body>
</@main.layout>
