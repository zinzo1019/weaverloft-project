<%@ page import="com.example.choyoujin.DTO.BoardDto" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport"
          content="width=device-width, initial-scale=1">
    <title>메인 페이지</title>
    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<style>
    .bd-navbar {
        position: sticky;
        top: 0;
        z-index: 1071;
        min-height: 4rem;
        box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, .05), inset 0 -1px 0 rgba(0, 0, 0, .1);
    }

    .bd-sidebar {
        position: sticky;
        top: 4rem;
        z-index: 1000;
        height: calc(100vh - 4rem);
        background: #eee;
        border-right: 1px solid rgba(0, 0, 0, .1);
        overflow-y: auto;
        min-width: 160px;
        max-width: 220px;
    }

    .bd-sidebar .nav {
        display: block;
    }

    .bd-sidebar .nav > li > a {
        display: block;
        padding: .25rem 1.5rem;
        font-size: 90%;
    }
</style>

<script>

    var alertMessage = "${alertMessage}"; // alert 메시지
    var redirectUrl = "${redirectUrl}"; // 리디렉션할 URL
    if (alertMessage) { // 권한이 없다면
        alert(alertMessage); // alert 창 표시
        window.location.href = redirectUrl; // 리디렉션
    }

</script>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-3 bd-sidebar" style="width: 200px;">
            <%--                게시판 리스트--%>
            <ul class="nav">
                <%--                    로그인 안 했을 땐 일반 게시판만 보임--%>
                <li>일반 게시판</li>
                <c:forEach items="${guestbBoards}" var="board">
                    <li><a href="/${board.role}?id=${board.id}">${board.name} </a></li>
                </c:forEach>

                <%--                    로그인 상태--%>
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <li>사용자 게시판</li>
                    <c:forEach items="${memberBoards}" var="board">
                        <li><a href="/${board.role}?id=${board.id}">${board.name} </a></li>
                    </c:forEach>

                    <%--                        관리자 권한--%>
                    <c:set var="isAdmin" value="false"/>
                    <c:forEach items="${pageContext.request.userPrincipal.authorities}" var="authority">
                        <c:if test="${authority.authority eq 'ROLE_ADMIN'}">
                            <c:set var="isAdmin" value="true"/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${isAdmin}">
                        <li>관리자 게시판</li>
                        <c:forEach items="${adminBoards}" var="board">
                            <li><a href="/${board.role}?id=${board.id}">${board.name} </a></li>
                        </c:forEach>
                    </c:if>
                </c:if>
            </ul>
            <br>
        </div>
        <main class="col-9 py-md-3 pl-md-5 bd-content" role="main">
            <div class="container-fluid px-4">
                <div class="px-3 py-2 border-bottom mb-3">
                    <div class="container d-flex flex-wrap justify-content-center">
                        <%--                        검색--%>
                        <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto" role="search"
                              onsubmit="submitForm(event)">
                            <input type="search" class="form-control" placeholder="Search..." aria-label="Search"
                                   id="searchInput">
                        </form>

                        <div class="text-end">
                            <%--                    로그인 상태--%>
                            <c:if test="${not empty pageContext.request.userPrincipal}">
                                <p><a href="/user/myPage"> ${user.name} </a>님, 환영합니다! </p>
                                <a href="/logout">로그아웃</a><br/>
                                <%--                    로그아웃 상태    --%>
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

            <div id="searchResults">
                <%
                    BoardDto board = (BoardDto) request.getAttribute("board");
                    if (board.getName() == null || board.getName().isEmpty()) { // 게시판 선택 전
                        board.setName("최근 게시글");
                        board.setRole("ROLE_GUEST"); // 게스트 권한
                    }
                %>

                <h1 class="mt-4"><%= board.getName() %></h1>
                <div class="card mb-4">
                    <div class="card-header">
                    </div>
                    <div class="card-body">
                        <table class="table table-hover table-striped">
                            <thead>
                            <tr>
                                <th>글번호</th>
                                <th>작성자</th>
                                <th>제목</th>
                                <th>날짜</th>
                                <th>수정</th>
                                <th>삭제</th>
                            </tr>
                            <c:forEach items="${list.content}" var="dto">
                            <tr>
                                <td>${dto.id}</td>
                                <td>${dto.userName}</td>
                                <td><a href="/${board.role}/view?id=${dto.id}"> ${dto.title} </a></td>
                                <td> ${dto.createDate}</td>
<%--                                작성자와 로그인 한 사람의 id가 같다면 수정 & 삭제 버튼 활성화--%>
                                <c:if test="${dto.email eq user.email}">
                                    <td><a href="/${board.role}/update/view?id=${dto.id}">수정</a></td>
                                </c:if>
                                <c:if test="${dto.email eq user.email}">
                                    <td><a href="/${board.role}/delete?boardId=${id}&id=${dto.id}">삭제</a></td>
                                </c:if>
                            </tr>
                            </c:forEach>
                        </table>
                        <c:choose>
<%--                            최근 게시글 페이지에서는 글 작성이 불가--%>
                            <c:when test="${board.name eq '최근 게시글'}"></c:when>
                            <c:otherwise>
                                <p><a href="/${board.role}/writeForm?id=${id}">글작성</a></p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </div>
                <!-- 페이징 처리 -->
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${pagination.endPage}" varStatus="status">
                        <li class="page-item">
                            <a class="page-link"
                               href="/${board.role}?id=${board.id}&page=${status.index}">${status.index}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </main>
    </div>
</div>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa"
        crossorigin="anonymous"></script>
</body>
<script>
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
</html>