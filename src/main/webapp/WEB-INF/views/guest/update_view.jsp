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
</style>

<body>
<main class="mt-5 pt-5">
    <div class="container-fluid px-4">
        <h1 class="mt-4">Board</h1>
        <div class="card mb-4">
            <div class="card-body">
                <form name="form" id="form" role="form" enctype="multipart/form-data">
                    <div class="mb-3 mt-3">
                        <label for="bno" class="form-label">bno</label>
                        <input type="text" class="form-control" id="bno" name="bno" value="${dto.id}" disabled>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">title</label>
                        <input type="text" class="form-control" id="title" name="title"
                               value="${dto.title}">
                    </div>
                    <form name="form" id="form" role="form" enctype="multipart/form-data">
                        <!-- 다중 이미지 업로드 필드 -->
                        <div class="mb-3">
                            <label for="images">이미지 업로드</label>
                            <input type="file" class="form-control" name="images" id="images" multiple accept="image/*">
                        </div>
                        <!-- 이미지 미리보기 영역 -->
                        <div id="image-preview" class="mb-3"></div>

                        <!-- 다중 파일 업로드 필드 -->
                        <div class="mb-3">
                            <label for="files">파일 업로드</label>
                            <input type="file" class="form-control" name="files" id="files" multiple>
                        </div>
                        <%--            파일 미리보기--%>
                        <div id="file-list"></div>
                    </form>

                    <%--                    다중 이미지--%>
                    <c:choose>
                        <c:when test="${not empty images}">
                            <div class="mb-3">
                                <label class="img-thumbnail">image</label>
                                <c:forEach items="${images}" var="image">
                                    <div class="col-md-9 col-lg-6 col-xl-5">
                                        <img src="data:${image.type};base64,${image.encoding}" class="img-fluid">
                                        <a href="javascript:void(0);" class="delete-image" data-image-id="${image.id}">삭제</a>
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
                                        <th>삭제</th>
                                    </tr>
                                    <c:forEach items="${files}" var="file">
                                        <tr>
                                            <td>${file.id}</td>
                                            <td>${file.name}</td>
                                            <td>
                                                <a href="download/${dto.id}?id=${file.id}">다운로드</a>
                                            </td>
                                            <td><a href="javascript:void(0);" class="delete-file"
                                                   data-file-id="${file.id}">삭제</a></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:when>
                    </c:choose>
                    <div class="mb-3">
                        <label for="content" class="form-label">content</label>
                        <textarea class="form-control" id="content" name="content">${dto.content}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="writer" class="form-label">writer</label>
                        <input type="text" class="form-control" id="writer" name="writer" value="${dto.userName}"
                               disabled>
                    </div>
                    <a href="/${role}?id=${dto.boardId}" class="btn btn-outline-secondary">list</a>
                    <button class="btn btn-outline-warning" onclick="modifyPost()" id="modify">modify</button>
                </form>
            </div>
        </div>

        <%--        댓글--%>
        <div class="card mb-2">
            <div class="card-header bg-light">
                <i class="fa fa-comment fa"></i> 댓글
            </div>
            <div class="card-body">
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
                                                    <button class="btn btn-outline-danger delete-reply"
                                                            data-reply-id="${comment.id}">삭제
                                                    </button>
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
        var title = $("#title").val();
        var content = $("#content").val();
        $.ajax({
            type: "POST",
            url: "/${dto.role}/modify?id=${dto.id}",
            data: {
                title: title,
                content: content
            },
            success: function (response) {
                console.log("게시물 수정 요청 성공:", response);
                location.reload();
            },
            error: function (error) {
                console.error("게시물 수정 요청 실패:", error);
            }
        });
    }

    /** 이미지 & 파일 추가 */
    $(document).ready(function () {
        $("#modify").click(function () {
            var formData = new FormData($("#form")[0]);
            debugger;
            $.ajax({
                type: "POST",
                url: "/${role}/addFiles?id=${dto.id}",
                data: formData,
                processData: false,
                contentType: false,
                complete: function () {
                    location.reload();
                }
            });
        });
    });

    /** 이미지 삭제 */
    $(".delete-image").on("click", function () {
        var imageId = $(this).data("image-id");
        $.ajax({
            type: "POST",
            url: "/${role}/image/delete?id=" + imageId,
            success: function (response) {
                console.log("이미지 삭제 성공:", response);
            },
            error: function (error) {
                console.error("이미지 삭제 실패:", error);
            },
            complete: function () {
                // 요청이 완료되면 항상 새로고침
                location.reload();
            }
        });
    });

    /** 파일 삭제 */
    $(".delete-file").on("click", function () {
        var fileId = $(this).data("file-id");
        $.ajax({
            type: "POST",
            url: "/${role}/file/delete?id=" + fileId,
            success: function (response) {
                console.log("파일 삭제 성공:", response);
            },
            error: function (error) {
                console.error("파일 삭제 실패:", error);
            },
            complete: function () {
                location.reload();
            }
        });
    });

    /** 댓글 삭제 */
    $(".delete-reply").on("click", function () {
        var replyId = $(this).data("reply-id");
        $.ajax({
            type: "POST",
            url: "/${role}/comment/delete?id=" + replyId,
            success: function (response) {
            },
            error: function (error) {
            },
            complete: function () {
                location.reload();
            }
        });
    });

    // 이미지 업로드 필드가 변경되었을 때 실행되는 함수
    document.getElementById('images').addEventListener('change', function () {
        displayImagePreview(this);
    });

    function displayImagePreview(input) {
        var preview = document.getElementById('image-preview');
        preview.innerHTML = ''; // 미리보기 영역 초기화

        if (input.files && input.files.length > 0) {
            for (var i = 0; i < input.files.length; i++) { // 업로드 이미지 수만큼 반복
                var file = input.files[i];
                if (file.type.match('image.*')) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        var img = document.createElement('img');
                        img.src = e.target.result;
                        img.className = 'preview-image';

                        // 삭제 버튼 생성
                        var deleteButton = document.createElement('button');
                        deleteButton.textContent = 'X';
                        deleteButton.className = 'delete-button';

                        deleteButton.addEventListener('click', function () { // 삭제 버튼 클릭 시
                            // 이미지 및 삭제 버튼을 포함한 래퍼 요소 삭제
                            var imageWrapper = this.parentNode;
                            imageWrapper.parentNode.removeChild(imageWrapper);
                        });

                        // 이미지와 삭제 버튼을 래퍼 요소에 추가
                        var imageWrapper = document.createElement('div'); // 이미지와 삭제 버튼을 감싸는 래퍼 요소
                        imageWrapper.className = 'image-wrapper';
                        imageWrapper.appendChild(img);
                        imageWrapper.appendChild(deleteButton);

                        // 래퍼 요소를 미리보기 영역에 추가
                        preview.appendChild(imageWrapper);
                    };
                    reader.readAsDataURL(file); // 이미지를 Data URL로 읽어옴
                }
            }
        }
    }

    // 파일 업로드 필드가 변경되었을 때 실행되는 함수
    document.getElementById('files').addEventListener('change', function () {
        displayFileList(this);
    });

    function displayFileList(input) {
        var fileList = document.getElementById('file-list');
        fileList.innerHTML = ''; // 파일 목록 초기화

        if (input.files && input.files.length > 0) {
            for (var i = 0; i < input.files.length; i++) {
                var file = input.files[i];
                var fileNameDiv = document.createElement('div');
                fileNameDiv.textContent = file.name;

                // 삭제 버튼 생성
                var deleteButton = document.createElement('button');
                deleteButton.textContent = 'X';
                deleteButton.className = 'file-delete-button';

                deleteButton.addEventListener('click', function (fileToRemove) {
                    // 파일 목록에서 해당 파일 이름 삭제
                    fileList.removeChild(this.parentNode);
                });

                // 파일 목록에 파일 이름과 삭제 버튼 추가
                var fileDiv = document.createElement('div');
                fileDiv.className = 'file-item';
                fileDiv.appendChild(fileNameDiv);
                fileDiv.appendChild(deleteButton);
                fileList.appendChild(fileDiv);
            }
        }
    }

</script>
</html>