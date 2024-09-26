async function post_info(id, canUseLike){

    let response = await fetch("/publications/"+id, createRequest())

    $("#comment").html("");

    let res = await response.json();
    let image=document.getElementById('imagePost')
    image.setAttribute("src", "/publications/download/"+id)

    let textDescription=document.getElementById('textDescription')
    textDescription.innerText=res.description;
    document.getElementById('postDivModal').innerText=formatDate(new Date(res.timeOfPublication))
        ;

    document.getElementById('countLikeDivModal').innerText="Likes: "+res.likeCount;


    let post = document.getElementById('post_id')
    post.value = id;

    if(userAunt) {
        let btnLike = document.getElementById("divLikeBtn");
        if(btnLike) {
            btnLike.innerHTML = "";
            if (canUseLike) {


                if (res.like) {
                    btnLike.innerHTML = '<button id="likeDeleteBtn" type="button" class="btn btn-primary mt-3">Like <i class="bi bi-heart-fill bg-washed-red"></i></button>\n';
                } else {
                    btnLike.innerHTML = '<button id="likeBtn" type="button" class="btn btn-primary mt-3">Like <i class="bi bi-heart"></i></button>\n';

                }
            }
        }
    }

    let myModal = new bootstrap.Modal(document.getElementById('postModal'));

    $("#likeBtn").off('click').click( async function () {
        let post = document.getElementById('post_id')
        let like = {publicationId: post.value}
        let response = await fetch("/likes/createLike", createRequest("post", like));
        let res = await response;
        getPublications();
        post_info(post.value, true);
    });

    $("#likeDeleteBtn").off('click').click( async function () {
        let post = document.getElementById('post_id')
        let like = {publicationId: post.value}
        let response = await fetch("/likes/deleteLike", createRequest("post", like));
        let res = await response;
        getPublications();
        post_info(post.value, true);

    });

    myModal.show();
}


$("#commentBtn").click( function() {
    loudComment();
});



async function loudComment(){
    let data = document.getElementById('comment')
    data.innerHTML="";
    $("#comment").html("");
    let post = document.getElementById('post_id')

    let response = await fetch("/comments/"+post.value, createRequest());
    let res = await response.json();
    let deleteCommentBtn ='';
    let messageDivSendComment = '';


    for (let i = 0; i<res.length; i++){


        if(res[0].isAuthorPost) {
            deleteCommentBtn = '<button id="editPostBtn" style="height: fit-content;" onclick="deleteComment(' + res[i].id + ')" type="button" class="btn btn-primary mt-3">x</button>\n';
        }
        let newElement = document.createElement('p');
        newElement.innerHTML ='<div class="d-flex justify-content-between">\n' +
            '    <span><span class="fw-bold">'+res[i].commentator+'</span> '+res[i].text+'  </span>' +
            deleteCommentBtn+
            '    </div>' ;
        data.appendChild(newElement);
    }

    if(res.length===0){
        let newElement = document.createElement('p');
        newElement.innerHTML ='<div class="d-flex justify-content-between">\n' +
            '    <span>Нету комментариев</span>' +

            '    </div>' ;
        data.appendChild(newElement);
    }

    if(userAunt) {


        messageDivSendComment = '<input type="text" id="messageCommentInput" class="form-control" placeholder="Добавить комментарий" aria-label="Добавить комментарий" aria-describedby="button-addon2">\n' +
            '  <button class="btn btn-outline-secondary" onclick="sendCommentMessage(' + post.value + ')" type="button" id="button-addon2">Отправить</button>';

    }

    let inputComment = document.createElement('div');
    inputComment.setAttribute("class", "input-group col-12");
    inputComment.innerHTML=messageDivSendComment;
    data.appendChild(inputComment);


}

async function sendCommentMessage(idPost){
    let messageInput=document.getElementById("messageCommentInput").value;
    let message={text: messageInput,publicationId:idPost}
    let response = await fetch("/comments/sendCommentMessage", createRequest("post", message));
    let res = await response;
    loudComment();
}

async function deleteComment(id){
    let post = document.getElementById('post_id')
    let response = await fetch("/comments/deleteComment/"+post.value+"/"+id, createRequest());
    let res = await response;

    loudComment();
}

function formatDate(date) {
    let datePart = [
        date.getDate(),
        date.getMonth() + 1,
        date.getFullYear()
    ].map((n, i) => n.toString().padStart(i === 2 ? 4 : 2, "0")).join(".");
    let timePart = [
        date.getHours(),
        date.getMinutes(),
        date.getSeconds()
    ].map((n, i) => n.toString().padStart(2, "0")).join(":");
    return datePart + " " + timePart;
}
