<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>
<!-- 사이드 바 -->
<nav id="sidebar" style="position: fixed; left: 0; width: 250px; height: 100%; background-color: #e9ecef; color: #333;">
    <div class="p-4" style="width: 200px;">
        <%--                게시판 리스트--%>
        <ul class="list-unstyled components">
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
</nav>
</body>
</html>
<!-- Bootstrap 및 jQuery 스크립트 포함 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>