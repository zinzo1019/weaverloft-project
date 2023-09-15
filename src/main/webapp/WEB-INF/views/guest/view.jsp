<%@ page import="com.example.choyoujin.DTO.UserDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport" content="width=device-width, initial-scale=1">
    <title>메인 페이지</title>
    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<body>
<main class="mt-5 pt-5">
    <div class="container-fluid px-4">
        <h1 class="mt-4">Board</h1>
        <div class="card mb-4">
            <div class="card-body">
                <form method="post">
                    <div class="mb-3 mt-3">
                        <label for="bno" class="form-label">bno</label> <input type="text"
                                                                               class="form-control" id="bno" name="bno" value="${dto.id}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">title</label>
                        <input type="text" class="form-control" id="title" name="title"
                            value="${dto.title}">
                    </div>
<%--                    다중 이미지--%>
                    <div class="mb-3">
                        <label for="title" class="img-thumbnail">image</label>
                        <c:forEach items="${images}" var="image">
                            <div class="col-md-9 col-lg-6 col-xl-5">
                                <img src="data:${image.type};base64,${image.encoding}" class="img-fluid">
                            </div>
                        </c:forEach>
                    </div>

<%--                    다중 파일--%>
                    <div class="mb-3">
                        <label for="title" class="form-label">파일 목록</label>
                        <table class="table table-striped">
                            <tr>
                                <th>ID</th>
                                <th>파일 이름</th>
                                <th>다운로드</th>
                            </tr>
                            <c:forEach items="${files}" var="file">
                                <tr>
                                    <td>${file.id}</td>
                                    <td>${file.name}</td>
                                    <td>
                                        <a href="download/${dto.id}?id=${file.id}">다운로드</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="mb-3">
                        <label for="content" class="form-label">content</label>
                        <textarea class="form-control" id="content" name="content">${dto.content}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="writer" class="form-label">writer</label> <input
                            type="text" class="form-control" id="writer" name="writer"
                            value="${dto.writer}" disabled>
                    </div>
                    <a href="/" class="btn btn-outline-secondary">list</a>
                    <button class="btn btn-outline-warning">modify</button>
                </form>
            </div>
        </div>

<%--        댓글--%>
        <div class="card mb-2">
            <div class="card-header bg-light">
                <i class="fa fa-comment fa"></i> REPLY
            </div>
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <form id="commentForm">
                        <textarea class="form-control" id="comment" rows="3"></textarea>
                        <button type="button" class="btn btn-dark mt-3" id="saveComment">post reply</button>
                    </form>
                </li>
            </ul>
            <div class="card">
                <div class="card-header">댓글</div>
                <ul class="list-group">
                    <c:forEach items="${comments}" var="comment">
<%--                        댓글 사용자--%>
                        <div class="">
                            <img src="data:${comment.type};base64,${comment.encoding}" style="width: 50px; height: 50px;">
                            <div class="font-italic">${comment.name}</div>
                        </div>

                        <li class="list-group-item d-flex justify-content-between">
                        <div>${comment.content}</div>
                        <div class="d-flex">
                            <button class="badge">삭제</button>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</main>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>
<script>
    $(document).ready(function () {
        /** 댓글 작성 버튼 클릭 */
        $("#saveComment").click(function () {

            console.log("start")
            var comment = document.getElementById("comment").value;

            console.log("content is " + comment)

            $.ajax({
                type: "POST",
                url: "/${dto.role}/comment?id=${dto.id}", // 게시글 아이디 함께 전달
                data: {"content": comment},
                success: function() {
                    location.reload(); // 페이지 새로고침
                }
            });
        })
    });
    </script>
</html>