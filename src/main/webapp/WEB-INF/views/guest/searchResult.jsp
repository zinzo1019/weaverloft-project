<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 class="mt-4">검색된 게시글</h1>
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
                <th>수정</th>
                <th>삭제</th>
            </tr>
            <c:forEach items="${list.content}" var="dto">
            <tr>
                <td>${dto.id}</td>
                <td>${dto.userName}</td>
                <td><a href="${role}/view?id=${dto.id}"> ${dto.title} </a></td>
<%--                 작성자와 로그인 한 사람의 id가 같다면 수정 & 삭제 버튼 활성화--%>
                <c:if test="${dto.email eq user.email}">
                    <td><a href="/${board.role}/update/view?id=${dto.id}">수정</a></td>
                </c:if>
<%--                작성자와 로그인 한 사람의 id가 같다면 삭제 버튼 활성화--%>
                <c:if test="${dto.email eq user.email}">
                    <td><a href="/delete?id=${dto.id}">삭제</a></td>
                </c:if>
            </tr>
            </c:forEach>
        </table>
        <c:choose>
            <%--            검색된 게시글 페이지에서는 글 작성이 불가--%>
            <c:when test="${board.name eq '검색된 게시글'}"></c:when>
            <c:otherwise>
                <p><a href="/${board.role}/writeForm/${dto.postId}?id=${id}">글작성</a></p>
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

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa"
        crossorigin="anonymous"></script>
