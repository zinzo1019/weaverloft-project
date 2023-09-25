<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    /* 버튼 스타일 수정 */
    .admin-button {
        padding: 7px 15px;
        background-color: #007BFF;
        color: #fff;
        font-size: 16px;
        text-align: center;
        text-decoration: none;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        margin-bottom: 10px;
    }

    /* 호버 효과 추가 */
    .admin-button:hover {
        background-color: #0056b3;
    }
</style>
<div class="container-fluid px-4">
    <div class="px-3 py-2 border-bottom mb-3">
        <div class="container d-flex flex-wrap justify-content-center">
            <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto" role="search"
                  onsubmit="submitForm(event)">
                <input type="search" class="form-control" placeholder="Search..." aria-label="Search"
                       id="searchInput">
            </form>
            <div class="text-end">
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <p><a href="/${user.role}/myPage">
<%--                        <span id="userNameDisplay"></span></a>님, 환영합니다! </p>--%>
                        <span>${user.name}</span></a>님, 환영합니다! </p>
                    <div class="admin-page"></div>
                    <a href="/logout" class="btn btn-outline-dark btn-sm ms-2">로그아웃</a>
                </c:if>

                <c:if test="${empty pageContext.request.userPrincipal}">
                    <button type="button" class="btn btn-light text-dark me-2"
                            onclick="location.href='/loginForm'">로그인
                    </button>
                    <button type="button" class="btn btn-primary" onclick="location.href='/guest/register'">
                        회원가입
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>
</html>

<script>
    $.ajax({
        url: '/ROLE_ADMIN/session',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var userDto = data;
            var userName = userDto.name; // 사용자 이름 추출
            var role = userDto.role;
            $('#userNameDisplay').text(userName); // 사용자 이름 화면에 출력

            if (role === 'ROLE_ADMIN') {
                // role이 ROLE_ADMIN인 경우에만 버튼 생성 및 표시
                var adminButton = $('<a>', {
                    href: '/ROLE_ADMIN/chart',
                    text: '관리자 페이지',
                    class: 'btn btn-primary admin-button'
                });
                $('.admin-page').append(adminButton); // 버튼을 컨테이너에 추가
            }
        },
        error: function () {
        }
    });

    /** 최근 게시글 중 검색 */
    function submitForm(event) {
        event.preventDefault();
        var searchTerm = document.getElementById('searchInput').value;
        $.ajax({
            type: 'POST',
            url: '/search?id='+${id},
            data: {"keyword": searchTerm}, // 서버에 전달할 데이터
            success: function (response) {
                $("#searchResults").html(response);
            },
            error: function (error) {

            }
        });
    }

</script>
<!-- 부트스트랩 스크립트 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
