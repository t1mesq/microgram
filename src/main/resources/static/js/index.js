let userCreateThisPost = false;

async function getPublications() {

    let response = await fetch("/publications/getPublications", createRequest())

    $("#comment").html("");

    let res = await response.json();
    let data = document.getElementById('form-publication');
    data.innerHTML = '';
    for (let i = 0; i < res.length; i++) {
        let newElement = document.createElement('div');
        let isLike = '';
        if (res[i].like) {
            isLike = '<div class="d-flex justify-content-end me-3">' +
                '<button type="button" class="btn btn-outline-danger col-2">' +
                '  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">' +
                '     <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"></path>' +
                '  </svg>' +
                '</button>' +
                '</div>';
        }
        newElement.innerHTML =
            '   <div onclick="post_info(\'' + res[i].id + '\', true)" class="card opacity-75 border-0 bg-white h-100 mt-3 py-5 ">' +
            '      <div class="card-body border-top border-3 mx-3 px-0 d-flex flex-wrap justify-content-center  align-content-around flex-column">' +
            '           <img src="/publications/download/' + res[i].id + '" class="card-img opacity-100"\n' +
            '                                 style="width: 45%;" alt="image">' +
            '           <p class="card-title text-center mt-2 fst-italic fs-5">' + res[i].description + '</p>' +
            '      </div>' +
            '      <input type="hidden" id="like_id">' +
                   isLike +
            '</div>';
        data.appendChild(newElement);
    }
}

getPublications();