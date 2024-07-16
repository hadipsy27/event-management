function addCsrfToken() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend((evt, req) => {
        req.setRequestHeader(header, token);
    });
}