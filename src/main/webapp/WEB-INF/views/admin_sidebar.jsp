<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>
<!-- 사이드 바 -->
<nav id="sidebar" style="position: fixed; left: 0; width: 250px; height: 100%; background-color: #e9ecef; color: #333;">
    <div class="p-4">
        <h5 style="font-size: 20px; font-weight: bold; color: #000;">메뉴</h5>
        <ul class="list-unstyled components">
            <li style="margin-bottom: 10px;">
                <a href="/ROLE_GUEST" style="font-size: 16px; font-weight: bold; color: #000;">
                    <i class="bi bi-house-door"></i> 메인 페이지
                </a>
            </li>
            <!-- 간격을 넓히려면 여기에 스타일 추가 -->
            <li style="margin-bottom: 10px;">
                <a href="chart" style="font-size: 16px; font-weight: bold; color: #000;">
                    <i class="bi bi-house-door"></i> 달별 회원가입 / 로그인 추이
                </a>
            </li>
            <!-- 간격을 넓히려면 여기에 스타일 추가 -->
            <li style="margin-bottom: 10px;">
                <a href="board" style="font-size: 16px; font-weight: bold; color: #000;">
                    <i class="bi bi-person"></i> 게시판 관리
                </a>
            </li>
            <!-- 간격을 넓히려면 여기에 스타일 추가 -->
            <li style="margin-bottom: 10px;">
                <a href="userList" style="font-size: 16px; font-weight: bold; color: #000;">
                    <i class="bi bi-gear"></i> 사용자 리스트
                </a>
            </li>
        </ul>
    </div>
</nav>
<!-- 메인 컨텐츠 -->
<div id="content">
    <button type="button" id="sidebarCollapse" class="btn btn-secondary">
        <i class="bi bi-list"></i>
    </button>
</div>

<!-- Bootstrap 및 jQuery 스크립트 포함 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<!-- 사이드 바 열고 닫기 처리 -->
<script>
    $(document).ready(function () {
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
        });
    });
</script>
</body>
</html>
<!-- 부트스트랩 스크립트 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>