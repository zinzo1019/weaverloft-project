<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
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
    body {
        width: 100%;
        max-width: 2000px;
        margin: 0 auto;
    }
</style>

<body>
<main class="mt-5 pt-5">
    <div class="container-fluid px-4">
        <h1 class="mt-4">Board</h1>
        <div class="card mb-4">
            <div class="card-body">
                <form method="post">
                    <div class="mb-3 mt-3">
                        <label for="bno" class="form-label">bno</label>
                        <input type="text" class="form-control" id="bno" name="bno" value="${dto.id}" disabled>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">title</label>
                        <input type="text" class="form-control" id="title" name="title"
                               value="${dto.title}" disabled>
                    </div>

                    <%--                    다중 이미지--%>
                    <c:choose>
                        <c:when test="${not empty images}">
                            <div class="mb-3">
                                <label class="img-thumbnail">image</label>
                                <c:forEach items="${images}" var="image">
                                    <div class="col-md-9 col-lg-6 col-xl-5">
                                        <img src="data:${image.type};base64,${image.encoding}" class="img-fluid">
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                    </c:choose>

                    <%--                    다중 파일--%>
                    <c:choose>
                        <c:when test="${not empty files}">
                            <div class="mb-3">
                                <label class="form-label">파일 목록</label>
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
                        </c:when>
                    </c:choose>
                    <div class="mb-3">
                        <label for="content" class="form-label">content</label>
                        <textarea class="form-control" id="content" name="content" disabled>${dto.content}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="writer" class="form-label">writer</label>
                        <input type="text" class="form-control" id="writer" name="writer" value="${dto.userName}" disabled>
                    </div>
                    <a href="/${role}?id=${dto.boardId}" class="btn btn-outline-secondary">list</a>
                </form>
            </div>
        </div>

        <%--        댓글--%>
        <div class="card mb-2">
            <div class="card-header bg-light">
                <i class="fa fa-comment fa"></i> 댓글
            </div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <%--                    게시글에 댓글 달기--%>
                        <form id="commentForm">
                            <textarea class="form-control" id="comment" rows="3"></textarea>
                            <button type="button" class="btn btn-dark mt-3" id="saveComment">댓글 달기</button>
                            <br><br><br>
                        </form>
                    </li>
                </ul>
                <div class="card">
                    <ul class="list-group">
                        <%--                    게시글의 댓글 리스트--%>
                        <c:forEach items="${comments}" var="comment">
                            <li class="list-group-item">
                                <div class="" style="padding-left: ${(comment.level -1) * 30}px">
                                    <img src="data:${comment.type};base64,${comment.encoding}" class="mr-3"
                                         alt="User Image"
                                         style="width: 50px; height: 50px;">
                                    <div class="">
                                        <div class="justify-content-between">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div>
                                                    <h5 class="mt-0">${comment.name}</h5>
                                                        ${comment.content}
                                                </div>
                                                <div class="justify-content-between">
                                                    <button class="showReplyForm btn btn-outline-dark mr-2">대댓글 작성
                                                    </button>
                                                    <button class="btn btn-outline-danger">삭제</button>
                                                </div>
                                            </div>
                                            <form class="replyForm" style="display: none; padding-left: 30px"><br>
                                                <textarea class="replyText form-control" placeholder="댓글 내용"
                                                          style="background-color: #f2f2f2;"></textarea><br>
                                                <button type="button" class="replyButton btn btn-dark"
                                                        data-comment-id="${comment.id}">댓글 작성
                                                </button>
                                                <br><br>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
</main>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa"
        crossorigin="anonymous"></script>

</body>
<script>

    /** 게시글 수정 */
    function modifyPost() {
        var requestData = {

        };

        $.ajax({
            type: "POST",
            url: "/${dto.role}/modify?id=${dto.id}",
            data: requestData,
            success: function(response) {
                console.log("게시물 수정 요청 성공:", response);
            },
            error: function(error) {
                console.error("게시물 수정 요청 실패:", error);
            }
        });
    }

    /** 댓글 작성 버튼 클릭 */
    $("#saveComment").click(function () {
        var comment = document.getElementById("comment").value;
        $.ajax({
            type: "POST",
            url: "/${dto.role}/comment?id=${dto.id}", // 게시글 아이디 함께 전달
            data: {"content": comment},
            success: function () {
                location.reload(); // 페이지 새로고침
            }
        });
    })

    var replyForms = document.querySelectorAll('.replyForm'); // 여러 개의 대댓글 폼
    var showReplyFormButtons = document.querySelectorAll('.showReplyForm'); // 여러 개의 대댓글 보여주기 버튼
    var replyTexts = document.querySelectorAll('.replyText'); // 여러 개의 대댓글 내용
    var replyButtons = document.querySelectorAll('.replyButton'); // 여러 개의 대댓글 작성 완료 버튼

    /** 대댓글 폼을 보여주는 버튼 클릭 시 */
    showReplyFormButtons.forEach(function (button, index) {
        button.addEventListener('click', function () {
            toggleReplyForm(index);
        });
    });

    function toggleReplyForm(index) { // 인덱스로 대댓글 폼 구분
        if (replyForms[index].style.display === 'block') {
            replyForms[index].style.display = 'none'; // 숨김
        } else {
            replyForms[index].style.display = 'block'; // 보임
        }
    }

    /** 대댓글 저장 버튼 클릭 시 */
    replyButtons.forEach(function (button, index) {
        button.addEventListener('click', function () {
            var reply = replyTexts[index].value; // 인덱스에 맞는 대댓글 내용 가져오기
            var commentId = this.getAttribute('data-comment-id'); // 댓글 아이디 가져오기

            $.ajax({
                type: "POST",
                url: "/${dto.role}/reply?id=" + commentId, // 댓글 아이디 함께 전달
                data: {"content": reply},
                success: function () {
                    location.reload(); // 페이지 새로고침
                }
            });
        });
    });
</script>
</html>