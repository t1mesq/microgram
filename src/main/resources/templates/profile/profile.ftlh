<#import "../layout.ftlh" as main>
<@main.layout>
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-3">
                    <#if user.avatar?? && user.avatar != "">
                        <img src="/users/download/${user.id}" class="img-fluid rounded-circle" alt="Profile Picture">
                    <#else>
                        <img src="api/images/image/anon.jpeg" alt="Default user image"
                             class="img-fluid rounded-circle"
                             style="width: 200px; height: 200px; object-fit: cover;">
                    </#if>
                </div>

                <div class="col-md-9">
                    <div class="d-flex justify-content-between align-items-center">
                        <h2 class="mb-3">${user.nickName}
                            <#assign
                known = SPRING_SECURITY_CONTEXT??
                >




                            <#if known>
                            <button type="button" class="btn btn-primary ml-3" data-toggle="modal"
                                    data-target="#editProfileModal">Редактировать профиль
                            </button>
                            <button type="button" class="btn btn-primary ml-3" data-toggle="modal"
                                    data-target="#createPostModal">Создать публикацию
                            </button>
                            </#if>
                        </h2>
                    </div>
<#if known>
                    <button id="subscriptionsModalBtn" class="btn btn-primary">Подписки <span id="subscriptionCount" class="badge badge-light"></span></button>
                    <button id="subscribersModalBtn" class="btn btn-primary">Подписчики <span id="subscriberCount" class="badge badge-light"></span></button>
                    <p>Публикаций: ${publicationCount}</p>

</#if>


                    <p><i class="fas fa-envelope"></i>Адрес электронной почты: ${user.email}</p>
                    <p><i class="fas fa-calendar-alt"></i>Возраст: ${user.age}</p>
                    <h5>О себе:</h5>
                    <#if user.bio?has_content>
                        <p>${user.bio}</p>
                    <#else> <p> Не заполнено</p>
                    </#if>
                </div>
            </div>
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
        </div>
    </main>
<#if known>
    <!-- Модальное окно -->
    <div class="modal fade" id="subscriptionsModal" tabindex="-1" role="dialog" aria-labelledby="subscriptionsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="subscriptionsModalLabel">Подписки и подписчики</h5>
                    <button type="button" class="close btn btn-primary" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <h6>Подписки:</h6>
                    <ul id="subscriptionsList"></ul>
                    <h6>Подписчики:</h6>
                    <ul id="subscribersList"></ul>
                </div>
            </div>
        </div>
    </div>

    <!-- Модальное окно для редактирования профиля -->
    <div class="modal fade" id="editProfileModal" tabindex="-1" role="dialog" aria-labelledby="editProfileModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editProfileModalLabel">Редактирование профиля</h5>
                    <button type="button" class="close btn btn-primary" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editProfileForm" action="/profile/edit" method="post" enctype="multipart/form-data">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <div class="form-group">
                            <label for="nickName">Никнейм</label>
                            <input type="text" class="form-control" id="nickName" name="nickName" required>
                        </div>
                        <div class="form-group">
                            <label for="name">Имя</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="form-group">
                            <label for="surname">Фамилия</label>
                            <input type="text" class="form-control" id="surname" name="surname" required>
                        </div>
                        <div class="form-group">
                            <label for="age">Возраст</label>
                            <input type="number" class="form-control" id="age" name="age" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Номер телефона</label>
                            <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" required>
                        </div>
                        <div class="form-group">
                            <label for="bio">О себе:</label>
                            <input type="text" class="form-control" id="bio" name="bio" required>
                        </div>
                        <div class="form-group">
                            <label for="file">Аватарка</label>
                            <input type="file" class="form-control-file" id="file" name="file">
                        </div>
                        <button type="submit" class="btn btn-primary">Сохранить изменения</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Модальное окно для создания публикации -->
    <div class="modal fade" id="createPostModal" tabindex="-1" role="dialog" aria-labelledby="createPostModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-center" id="editProfileModalLabel">Создание публикации</h5>
                    <button type="button" class="close btn btn-primary" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <#if fileUploadError??>
                        <div class="alert alert-danger" role="alert">
                            ${fileUploadError}
                        </div>
                    </#if>
                    <form id="createPostForm" action="publication/create" method="post" enctype="multipart/form-data">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="form-group">
                            <label for="image">Фото публикации</label>
                            <div>
                                <input type="file" class="form-control-file" id="image" name="image" required>
                            </div>
                        </div>

                        <div class="form-group mt-4">
                            <label for="description">Текст публикации</label>
                            <textarea type="text" class="form-control" id="description" name="description" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary mt-3">Сохранить изменения</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</#if>
    <!-- Модальное окно публикации -->
    <div class="modal fade" id="postModal"   tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <div id="postDivModal"></div>

                    <button id="countLikeDivModal" type="button" class="btn btn-primary likes ml-2"> <i class="bi bi-heart"></i></button>

                    <button type="button" id="postCloseModal" class="close btn btn-primary" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <img id="imagePost" class="card-img opacity-100"
                             style="width: 40%;" alt="image">
                    </div>
                    <div id="textDescription"></div>

                    <div class="modal-footer">

                        <div id="comment" class="col-12"></div>

                    </div>
                    <input type="hidden" id="post_id">


                    <div class="form-group mt-4 d-flex justify-content-evenly">
                        <a id="commentBtn" type="" class="btn btn-primary mt-3 mr-2">Комментарии</a>
                        <#if known>
                            <#if user.isCurrentUser>
                        <button id="editPostBtn" type="button" class="btn btn-primary mt-3 mr-2">Редактировать</button>
                        <button id="deletePostBtn" type="button" class="btn btn-primary mt-3">Удалить</button>
                        </#if>
                        </#if>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <#if known>
    <!-- Модальное окно для изменения публикации -->
    <div class="modal fade" id="editPostModal" tabindex="-1" role="dialog" aria-labelledby="editPostModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-center" id="editProfileModalLabel">Редактирование публикации</h5>
                    <button type="button" onclick="closeModalEdit()" class="close btn btn-primary" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="createPostModal" action="publication/edit" method="post" enctype="multipart/form-data">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="form-group">
                            <label for="image">Фото публикации</label>
                            <div>
                                <img id="imagePostEdit" class="card-img opacity-100"
                                     style="width: 40%;" alt="image">
                            </div>
                        </div>

                        <input type="hidden" id="commentEdit_id" name="id">


                        <div class="form-group mt-4">
                            <label for="descriptionEdit">Текст публикации</label>
                            <textarea type="text" class="form-control" id="descriptionEdit"
                                      name="description"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary mt-3">Сохранить изменения</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </#if>


    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <script>

        let userCreateThisPost = false;




        $(document).ready(function() {
            <#if known>
            userCreateThisPost = true;
            $.get("/subscriptions", function(data) {
                $('#subscriptionCount').text(data.subscriptions.length);
                $('#subscriberCount').text(data.subscribers.length);
            });

            $('#subscriptionsModalBtn').click(function() {
                $.get("/subscriptions", function(data) {
                    $('#subscriptionsModal').modal('show');

                    $('#subscriptionsList').empty();
                    $('#subscribersList').empty();

                    data.subscriptions.forEach(function(subscription) {
                        $('#subscriptionsList').append('<li>' + subscription.userNickName + '</li>');
                    });
                });
            });

            $('#subscribersModalBtn').click(function() {
                $.get("/subscriptions", function(data) {
                    $('#subscriptionsModal').modal('show');

                    $('#subscriptionsList').empty();
                    $('#subscribersList').empty();

                    data.subscribers.forEach(function(subscriber) {
                        $('#subscribersList').append('<li>' + subscriber.subscriberNickName + '</li>');
                    });
                });
            });
            </#if>
        });

    </script>


    <script src="/js/profile.js"></script>
    <script src="/js/workWithPost.js"></script>
    <script src="/js/authorizationHelper.js"></script>

</@main.layout>
