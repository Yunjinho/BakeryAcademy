$(document).ready(function(){
    $("#logout-link").click(function(e){
        e.preventDefault(); // prevent the default action
        // 여기에 로그아웃 처리 로직 추가
        window.location.href = '/member/logout'; 
    });
});
