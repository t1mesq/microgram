function showFollowers() {
    $.get("/followers", function (data) {
        $("#followersModal .modal-body").html(data);
        $("#followersModal").modal("show");
    });
}

function showFollowing() {
    $.get("/following", function (data) {
        $("#followingModal .modal-body").html(data);
        $("#followingModal").modal("show");
    });
}

$("#followersButton").click(function () {
    showFollowers();
});

$("#followingButton").click(function () {
    showFollowing();
});


$("#editPostBtn").click(async function () {

    $("#postCloseModal").click();


    let post = document.getElementById('post_id');

    let commentId = document.getElementById('commentEdit_id');
    commentId.value = post.value


    let response = await fetch("/publications/" + post.value, createRequest())
    let res = await response.json();

    let image = document.getElementById('imagePostEdit')
    image.setAttribute("src", "/publications/download/" + post.value)

    let textDescription = document.getElementById('descriptionEdit')
    textDescription.innerText = res.description;

    $("#comment").html("");


    $("#editPostModal").modal("show");


});

function closeModalEdit() {
    $('#editPostModal').modal('hide');
}


async function deleteComment(id) {
    let post = document.getElementById('post_id')
    let response = await fetch("/comments/deleteComment/" + post.value + "/" + id, createRequest());
    let res = await response;

    loudComment();
}

$("#deletePostBtn").click(async function () {

    let post = document.getElementById('post_id')

    let response = await fetch("/publications/deletePublication/" + post.value, createRequest());
    let res = await response;
    window.location.reload();
});

$(document).ready(function() {
    $('#createPostForm').off('submit').on('submit', function(e) {
        e.preventDefault();
        var formData = new FormData(this);
        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                $('#createPostModal').modal('hide');
            },
            error: function(xhr) {
                $('#createPostModal .alert').remove();

                var errorMessage = xhr.responseText;
                $('#createPostModal .modal-body').prepend('<div class="alert alert-danger" role="alert">' + errorMessage + '</div>');
            }
        });
    });
});

