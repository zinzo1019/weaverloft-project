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
<jsp:include page="../admin_sidebar.jsp" />

<!-- 오른쪽 컨텐츠에 왼쪽 마진을 주어 겹치지 않게 설정 -->
<div id="content" style="margin-left: 300px;">

    <h2>달별 회원가입 수</h2>
    <div style="width: 80%; margin: 0 auto;">
        <canvas id="signUpByMonth"></canvas>
    </div>
    <h2>달별 로그인 수</h2>
    <div style="width: 80%; margin: 0 auto;">
        <canvas id="signIpByMonth"></canvas>
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

</script>
</html>