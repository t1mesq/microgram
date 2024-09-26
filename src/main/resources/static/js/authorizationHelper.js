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


function createRequest (method, body){
    if(!method){
        method = 'get'
    }
    let request={
        method: method,
        headers: makeHeaders()
    }
    if(body){
        request.body = JSON.stringify(body)
    }
    return request;
}