<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport" content="width=device-width, initial-scale=1">
    <title>메인 페이지</title>
</head>

<style>
    .bd-navbar {
        position: sticky;
        top: 0;
        z-index: 1071;
        min-height: 4rem;
        box-shadow: 0 0.5rem 1rem rgba(0,0,0,.05), inset 0 -1px 0 rgba(0,0,0,.1);
    }
    .bd-sidebar {
        position: sticky;
        top: 4rem;
        z-index: 1000;
        height: calc(100vh - 4rem);
        background: #eee;
        border-right: 1px solid rgba(0,0,0,.1);
        overflow-y: auto;
        min-width: 160px;
        max-width: 220px;
    }
    .bd-sidebar .nav {
        display: block;
    }
    .bd-sidebar .nav>li>a {
        display: block;
        padding: .25rem 1.5rem;
        font-size: 90%;
    }
</style>
<body>
<div class="container-fluid">
    <div class="row flex-nowrap">
        <div class="col-3 bd-sidebar">
<%--            게시판 리스트--%>
            <ul class="nav">
                <li>일반 게시판fff</li>
                <c:forEach items="${guestBoards}" var="board">
                    <li><a href="/${board.role}/board?id=${board.id}" >${board.name} </a></li>
                </c:forEach>

                <li>사용자 게시판</li>
                <c:forEach items="${memberBoards}" var="board">
                    <li><a href="/${board.role}/board?id=${board.id}" >${board.name} </a></li>
                </c:forEach>

                <li>관리자 게시판</li>
                <c:forEach items="${adminBoards}" var="board">
                    <li><a href="/${board.role}/board?id=${board.id}" >${board.name} </a></li>
                </c:forEach>


            </ul>
            <br>
        </div>
        <main class="col-9 py-md-3 pl-md-5 bd-content" role="main">
            <div class="container-fluid px-4">
                <div class="px-3 py-2 border-bottom mb-3">
                    <div class="container d-flex flex-wrap justify-content-center">
                        <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto" role="search">
                            <input type="search" class="form-control" placeholder="Search..." aria-label="Search">
                        </form>

                        <div class="text-end">
                            <%--                    로그인 상태--%>
                            <c:if test="${not empty pageContext.request.userPrincipal }">
                                <p><a href="/member/myPage"> ${user.name} </a>님, 환영합니다! </p>
                                <a href="/logout">로그아웃</a><br/>
                                <%--                    로그아웃 상태    --%>
                            </c:if>
                            <c:if test="${empty pageContext.request.userPrincipal }">
                                <button type="button" class="btn btn-light text-dark me-2" onclick="location.href='/loginForm'">로그인</button>
                                <button type="button" class="btn btn-primary" onclick="location.href='/guest/register'">회원가입</button>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <h1 class="mt-4">게시판</h1>
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
                            <th>삭제</th>
                        </tr>
                        <c:forEach items="${list}" var="dto">
                        <tr>
                            <td>${dto.id}</td>
                            <td>${dto.name}</td>
                            <td><a href="/board/view?id=${dto.id}"> ${dto.title} </a></td>
                            <td><a href="/board/delete?id=${dto.id}"> 삭제 </a></td>
                        </tr>
                        </c:forEach>
                    </table>
                    <p><a href="/member/writeForm">글작성</a> </p>
                </div>
            </div>
        </main>

        <!-- 페이징 처리 -->
        <div class="pagination">
            <c:if test="${posts.hasPrevious}">
                <a href="?page=0">첫 페이지</a>
                <a href="?page=${posts.number - 1}">이전</a>
            </c:if>

            <c:forEach begin="0" end="${posts.totalPages - 1}" varStatus="status">
                <c:choose>
                    <c:when test="${status.index == posts.number}">
                        <span>${status.index + 1}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${status.index}">${status.index + 1}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${posts.hasNext}">
                <a href="?page=${posts.number + 1}">다음</a>
                <a href="?page=${posts.totalPages - 1}">마지막 페이지</a>
            </c:if>
        </div>


    </div>
</div>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>
</html>