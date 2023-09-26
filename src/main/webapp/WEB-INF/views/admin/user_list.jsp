<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <title>관리자 페이지</title>
</head>

<style>

</style>

<script>

</script>
<body>

<!-- 사이드 바를 포함 -->
<jsp:include page="../admin_sidebar.jsp"/>


<!-- 오른쪽 컨텐츠에 왼쪽 마진을 주어 겹치지 않게 설정 -->
<div id="content" style="margin-left: 300px;">
    `
    <div class="container mt-5">
        <h1 class="text-center">사용자 정보 다운로드</h1>
        <div class="text-center mt-3">
            <a class="btn btn-primary" href="/ROLE_ADMIN/download/user">사용자 리스트 다운로드</a>
        </div>
    </div>

    <div class="container mt-5">
        <h1 class="text-center">사용자 등록 엑셀 서식</h1>
        <div class="text-center mt-3">
            <a class="btn btn-primary" href="/ROLE_ADMIN/download/sample">서식 다운로드</a>
        </div>
    </div>
    <br><br>

    <h1 class="text-center">사용자 데이터 업로드</h1>
    <div class="card-body">
        <form enctype="multipart/form-data">
            <div class="d-flex justify-content-center align-items-center">
                <div class="d-flex justify-content-center align-items-center">
                    <div class="input-group">
                        <input type="file" id="excelFile" accept=".xlsx, .xls" class="form-control" required
                               style="max-width: 200px;"><br><br><br>
                        <div class="input-group-append">
                            <button class="btn btn-outline-primary" type="button" style="height: 38px;"
                                    onclick="document.getElementById('excelFile').click()">
                                파일 선택
                            </button>
                        </div>
                    </div>
                </div>

            </div>
            <div class="form-group text-center">
                <button class="btn btn-primary" id="uploadExcel">사용자 업로드</button>
            </div>
        </form>
    </div>

</div>

<!-- 부트스트랩 JS 및 jQuery 추가 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

<script>

    /** 사용자 엑셀 파일 업로드 */
    $("#uploadExcel").click(function (e) {
        e.preventDefault(); // 폼 제출 막기
        var formData = new FormData();
        var excelFileInput = document.getElementById('excelFile'); // 파일 입력 필드
        var file = excelFileInput.files[0]; // 선택한 파일
        formData.append('file', file);

        $.ajax({
            url: '/ROLE_ADMIN/upload/user',
            type: 'POST',
            data: formData,
            processData: false, // 데이터 처리를 jQuery에 맡김
            contentType: false, // 컨텐츠 타입을 false로 설정하여 jQuery가 자동으로 설정
            success: function (data) {
                alert(data.message)
            },
            error: function (xhr, status, error) {
                var errorMessage = xhr.responseJSON.message; // 에러 응답에서 메시지 추출
                alert(errorMessage); // 에러 메시지 출력
            },
            complete: function () {
            }
        });
    });

</script>
</html>