<#import "layout.ftlh" as main>
<@main.layout>

    <#assign
    known = SPRING_SECURITY_CONTEXT??
    >

    <div class="jumbotron jumbotron-fluid position-relative overlay-bottom" style="margin-bottom: 90px;">
        <div class="container text-center my-5 py-5">
            <h3 class="text-white display-4 mt-4 mb-4">Добро пожаловать в Прототип Инстаграмма — Microgram!</h3>
            <h3 class="text-white display-5 mb-5">Ищите друзей, подписывайтесь на интересных пользователей и ставьте лайки друг другу! Делитесь позитивом и создавайте свою уникальную ленту вместе!</h3>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <form action="/search" method="get" class="search-form mt-5">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <div class="input-group">
                            <input type="text" name="query" placeholder="Искать по имени, никнейму и email"
                                   class="form-control">
                            <button type="submit" class="btn btn-primary">Найти</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row row-cols-3 mt-5 mb-4 pt-5" id="form-publication">


        </div>

        <div class="modal fade" id="postModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <div id="postDivModal"></div>
                        <button id="countLikeDivModal" type="button" class="btn btn-primary likes ml-2">
                            <i class="bi bi-heart"></i>
                        </button>
                        <button type="button" id="postCloseModal" class="close btn btn-primary" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="likeForm">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <div class="form-group">
                                <img id="imagePost" class="card-img opacity-100" style="width: 40%;" alt="image">
                            </div>
                            <div id="textDescription"></div>
                            <div class="modal-footer">
                                <div id="comment" class="col-12"></div>
                            </div>
                            <input type="hidden" id="post_id">
                            <div class="form-group mt-4 d-flex justify-content-evenly">
                                <a id="commentBtn" class="btn btn-primary mt-3 mr-2">Комментарии</a>
                                <div id="divLikeBtn"></div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <link href="/css/flexbox.css" rel="stylesheet"> <!-- Подключение flexbox.css -->
    <link rel="stylesheet" href="/css/publication-style.css">
    <script src="/js/authorizationHelper.js"></script>
    <script src="/js/index.js"></script>
    <script src="/js/workWithPost.js"></script>





</@main.layout>