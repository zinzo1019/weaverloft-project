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
        <main class="" role="main">

            <!-- 사이드 바를 포함 -->
            <jsp:include page="../main_sidebar.jsp" />
<%--            헤더--%>
            <!-- 오른쪽 컨텐츠에 왼쪽 마진을 주어 겹치지 않게 설정 -->
            <div id="content" style="margin-left: 300px;">
            <%@ include file="../header.jsp" %>

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
</script>
</html>