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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Chart.js CSS 및 JS 추가 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>

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
    </div><br><br>

    <h1 class="text-center">사용자 데이터 업로드</h1>
    <div class="card-body">
        <form enctype="multipart/form-data">
                <div class="d-flex justify-content-center align-items-center">
                    <div class="d-flex justify-content-center align-items-center">
                        <div class="input-group">
                            <input type="file" id="excelFile" accept=".xlsx, .xls" class="form-control" required style="max-width: 200px;"><br><br><br>
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
                <button class="btn btn-primary" onclick="uploadExcel()">사용자 업로드</button>
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
    $.ajax({
        url: '/ROLE_ADMIN/signUpByMonth',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var dates = [];
            var counts = [];

            // 받은 데이터를 배열에 저장
            for (var i = 0; i < data.length; i++) {
                dates.push(data[i].date);
                counts.push(data[i].count);
            }

            // 차트 생성
            var ctx = document.getElementById('signUpByMonth').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: dates,
                    datasets: [{
                        label: 'Data',
                        data: counts,
                        backgroundColor: 'rgba(192,75,96,0.2)',
                        borderColor: 'rgb(192,75,102)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        },
        error: function () {
            console.error('데이터를 불러오는 중 오류가 발생했습니다.');
        }
    });

    $.ajax({
        url: '/ROLE_ADMIN/signIpByMonth',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var dates = [];
            var counts = [];

            // 받은 데이터를 배열에 저장
            for (var i = 0; i < data.length; i++) {
                dates.push(data[i].date);
                counts.push(data[i].count);
            }

            // 차트 생성
            var ctx = document.getElementById('signIpByMonth').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: dates,
                    datasets: [{
                        label: 'Data',
                        data: counts,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        },
        error: function () {
            console.error('데이터를 불러오는 중 오류가 발생했습니다.');
        }
    });

    /** 사용자 엑셀 파일 업로드 */
    function uploadExcel() {
        var formData = new FormData();
        var excelFileInput = document.getElementById('excelFile'); // 파일 입력 필드
        var file = excelFileInput.files[0]; // 선택한 파일
        formData.append('file', file);
        $.ajax({
            url: 'upload/user',
            type: 'POST',
            data: formData,
            processData: false, // 데이터 처리를 jQuery에 맡김
            contentType: false, // 컨텐츠 타입을 false로 설정하여 jQuery가 자동으로 설정
            success: function (data) {
            },
            error: function () {
            },
            complete: function () {
                location.reload();
            }
        });
    }
</script>
</html>