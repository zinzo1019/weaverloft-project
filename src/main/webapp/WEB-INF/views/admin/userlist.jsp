<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>

    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <title>게시글 개인 뷰 페이지</title>
</head>

<body>

<div class="container-fluid px-4">
    <h1 class="mt-4">Board</h1>
    <div class="card mb-4">
        <div class="card-header">
        </div>
        <div class="card-body">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>이메일</th>
                    <th>이름</th>
                    <th>성별</th>
                    <th>전화번호</th>
                    <th>주소</th>
                    <th>사진</th>
                </tr>
                <c:forEach items="${users}" var="dto">
                <tr>
                    <td>${dto.email}</td>
                    <td>${dto.name}</td>
<%--                    <td><a href="view?id=${dto.id}"> ${dto.title} </a></td>--%>
<%--                    <td><a href="delete?id=${dto.id}"> 삭제 </a></td>--%>
                </tr>
                </c:forEach>
            </table>

            <p><a href="list">게시글 목록</a> </p>

        </div>
    </div>
</div>


<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>
</html>