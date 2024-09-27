function restoreUser() {
        return JSON.parse(localStorage.getItem('user'))
}


function makeHeaders() {
    let user = restoreUser();
    let headers = new Headers();
    headers.append('Content-Type', 'application/json')
    if (user) {
        let ls='Basic ' + btoa(user.email + ':' + user.password);
        headers.append('Authorization', ls);
    }
    return headers
}


function createRequest(method = "GET", body = null) {
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    const headers = {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken
    };

    return {
        method: method,
        headers: headers,
        body: body ? JSON.stringify(body) : null
    };
}
