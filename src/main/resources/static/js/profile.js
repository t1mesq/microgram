// Function to fetch and display followers in the modal
function showFollowers() {
    $.get("/followers", function(data) {
        $("#followersModal .modal-body").html(data);
        $("#followersModal").modal("show");
    });
}

// Function to fetch and display following in the modal
function showFollowing() {
    $.get("/following", function(data) {
        $("#followingModal .modal-body").html(data);
        $("#followingModal").modal("show");
    });
}

// Event listeners for the follower and following buttons
$("#followersButton").click(function() {
    showFollowers();
});

$("#followingButton").click(function() {
    showFollowing();
});



$("#editPostBtn").click(async function() {

    $("#postCloseModal").click();



    let post = document.getElementById('post_id');

    let commentId = document.getElementById('commentEdit_id');
    commentId.value = post.value




    let response = await fetch("/publications/"+post.value, createRequest())
    let res = await response.json();

    let image=document.getElementById('imagePostEdit')
    image.setAttribute("src", "/publications/download/"+post.value)

    let textDescription=document.getElementById('descriptionEdit')
    textDescription.innerText=res.description;

    $("#comment").html("");


    $("#editPostModal").modal("show");



});

function closeModalEdit(){
    $('#editPostModal').modal('hide');
}



async function deleteComment(id){
    let post = document.getElementById('post_id')
    let response = await fetch("/comments/deleteComment/"+post.value+"/"+id, createRequest());
    let res = await response;

    loudComment();
}

$("#deletePostBtn").click(async function() {

    let post = document.getElementById('post_id')

    let response = await fetch("/publications/deletePublication/"+post.value, createRequest());
    let res = await response;
    window.location.reload();
});




