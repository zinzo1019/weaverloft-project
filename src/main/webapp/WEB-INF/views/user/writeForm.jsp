<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    fieldset {
        width: 200px; /* 원하는 너비로 조정하세요 */
        padding: 10px;
        border: 1px solid #ccc;
    }

    legend {
        font-size: 16px; /* 제목의 글꼴 크기를 조정하세요 */
        font-weight: bold;
    }

    label {
        font-size: 14px; /* 라벨의 글꼴 크기를 조정하세요 */
    }

    .preview-image {
        max-width: 100px; /* 미리보기 이미지의 최대 너비 설정 */
        max-height: 100px; /* 미리보기 이미지의 최대 높이 설정 */
        margin: 5px; /* 이미지 간 간격 설정 */
    }
</style>
<script>

</script>
<body>

<article>
    <div class="container" role="main">
        <h2>board Form</h2>
        <form name="form" id="form" role="form" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="title">제목</label>
                <input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해 주세요">
            </div>

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

            <div class="mb-3">
                <label for="content">내용</label>
                <textarea class="form-control" rows="5" name="content" id="content"
                          placeholder="내용을 입력해 주세요"></textarea>
            </div>

            <div class="mb-3" name="accessLevel">
                <fieldset>
                    <legend>공개범위</legend>
                    <c:choose>
                        <c:when test="${role == 'ROLE_GUEST'}">
                            <!-- ROLE_GUEST인 경우에는 "전체 공개"만 표시 -->
                            <input type="checkbox" id="guest" name="accessLevel" value="guest">
                            <label for="guest">전체 공개</label><br>
                        </c:when>
                        <c:when test="${role == 'ROLE_USER'}">
                            <!-- ROLE_USER인 경우에는 "전체 공개"와 "사용자 공개" 표시 -->
                            <input type="checkbox" id="guest" name="accessLevel" value="guest">
                            <label for="guest">전체 공개</label><br>
                            <input type="checkbox" id="user" name="accessLevel" value="user">
                            <label for="user">사용자 공개</label><br>
                        </c:when>
                        <c:when test="${role == 'ROLE_ADMIN'}">
                            <!-- ROLE_ADMIN인 경우에는 "전체 공개", "사용자 공개", "관리자 공개" 모두 표시 -->
                            <input type="checkbox" id="guest" name="accessLevel" value="guest">
                            <label for="guest">전체 공개</label><br>
                            <input type="checkbox" id="user" name="accessLevel" value="user">
                            <label for="user">사용자 공개</label><br>
                            <input type="checkbox" id="admin" name="accessLevel" value="admin">
                            <label for="admin">관리자 공개</label><br>
                        </c:when>
                    </c:choose>
                </fieldset>
            </div>
            <button type="button" class="btn btn-sm btn-primary" id="save">저장</button>
            &nbsp;&nbsp; <a href="/" class="btn btn-secondary"> 목록 보기 </a>
        </form>
    </div>
    </div>
</article>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa"
        crossorigin="anonymous"></script>

</body>
<script>

    // 저장 버튼 클릭 시
    $("#save").click(function () {
        var formData = new FormData($("#form")[0]);
        $.ajax({
            type: "POST",
            url: "/${role}/write?id=${id}",
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                window.location.href = "/${role}/view?id=" + response; // 리다이렉션
            },
            error: function (error) {
                alert("게시글 저장에 실패했습니다.");
            }
        });
    });

    // "전체 공개" 체크박스를 클릭할 때
    document.getElementById('guest').addEventListener('click', function () {
        // "사용자 공개" 체크박스의 상태 가져오기
        var guestCheckbox = document.getElementById('guest');
        var userCheckbox = document.getElementById('user');
        var adminCheckbox = document.getElementById('admin');

        if (adminCheckbox) { // Admin 게시판에서 글을 작성하는 경우
            if (guestCheckbox.checked) {
                // "사용자 공개" 체크박스가 체크된 경우, "관리자 공개" 체크박스도 클릭되도록 설정
                userCheckbox.checked = true;
                adminCheckbox.checked = true;
                userCheckbox.disabled = true;
                adminCheckbox.disabled = true;
            } else {
                // "전체 공개" 체크박스가 체크 해제된 경우, "사용자 공개"와 "관리자 공개" 체크박스를 다시 활성화
                userCheckbox.disabled = false;
                adminCheckbox.disabled = false;
                userCheckbox.checked = false;
                adminCheckbox.checked = false;
            }
        } else { // User 게시판에서 글을 작성하는 경우
            if (guestCheckbox.checked) {
                // "사용자 공개" 체크박스가 체크된 경우, "관리자 공개" 체크박스도 클릭되도록 설정
                userCheckbox.checked = true;
                userCheckbox.disabled = true;
            } else {
                // "전체 공개" 체크박스가 체크 해제된 경우, "사용자 공개"와 "관리자 공개" 체크박스를 다시 활성화
                userCheckbox.disabled = false;
                userCheckbox.checked = false;
            }
        }
    });

    // "사용자 공개" 체크박스를 클릭할 때
    document.getElementById('user').addEventListener('click', function () {
        // "사용자 공개" 체크박스의 상태 가져오기
        var userCheckbox = document.getElementById('user');
        var adminCheckbox = document.getElementById('admin');

        if (adminCheckbox) { // Admin 게시판에서 글으 작성하는 경우
            if (userCheckbox.checked) {
                // "사용자 공개" 체크박스가 체크된 경우, "관리자 공개" 체크박스도 클릭되도록 설정
                adminCheckbox.checked = true;
                adminCheckbox.disabled = true;
            } else {
                // "사용자 공개" 체크박스가 체크 해제된 경우, "관리자 공개" 체크박스를 다시 활성화
                adminCheckbox.disabled = false;
                adminCheckbox.checked = false;
            }
        }
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