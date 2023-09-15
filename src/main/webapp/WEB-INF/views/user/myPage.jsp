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

<style>

    /* datepicker의 불투명도 조절 스타일 */
    .ui-datepicker {
        opacity: 1.0; /* 불투명도 값 (0.0 - 1.0) */
        background-color: white; /* 배경색 설정 */
        border: 1px solid #ccc; /* 테두리 스타일 설정 */
        font-size: 16px; /* 폰트 크기 설정 */
    }
    .ui-datepicker-header {
        background-color: #4CAF50; /* 헤더 배경색 설정 */
        color: white; /* 헤더 텍스트 색상 설정 */
    }
    .ui-datepicker-title {
        font-weight: bold; /* 헤더 제목 굵게 설정 */
    }
    .ui-state-default {
        background-color: #f2f2f2; /* 날짜 셀 배경색 설정 */
    }
    .ui-state-default:hover {
        background-color: #ddd; /* 마우스 오버 시 배경색 변경 */
    }
    .ui-datepicker-calendar a {
        color: #333; /* 날짜 텍스트 색상 설정 */
    }

</style>

<script>

    /** 사용자 정보 수정 */
    function update() {

            console.log("updateBtn is clicked ... ")

            var name = $("#name").val();
            var address = $("#address").val();
            var birth = $("#birth").val();
            var updateImage = $("#updateImage")[0].files[0];

            var formData = new FormData();
            formData.append("name", name);
            formData.append("address", address);
            formData.append("birth", birth);
            formData.append("image", updateImage);

            $.ajax({
            url: "/user/updateUser",
            type: "POST",
            data: formData,
            processData: false, // multipart 타입 전송
            contentType: false, // multipart 타입 전송
            success: function (response) {
                alert("수정을 완료했습니다.")
            }, error: function(error) {
                alert("수정에 실패했습니다. 다시 시도해주세요.")
            }
        });
        return false;
    }

    /** 생년월일 구조 */
    $(function () {
        $("#birth").datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: "1900:2050",
            dateFormat: "yy-mm-dd" // 날짜 형식 설정 (년-월-일)
        });
    });

</script>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
</head>
<body>

<section class="vh-100">
    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="data:${userDto.type};base64,${encoding}" class="img-fluid" alt="사용자 이미지">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">

                <form enctype="multipart/form-data">

                    <!-- 이미지 수정 영역 -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="image">이미지 수정</label>
                        <input type="file" id="image" class="form-control form-control-lg" name="image"/>
                    </div>


<%--                    이메일--%>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="email">이메일</label>
                        <input type="email" id="email" class="form-control form-control-lg" name="email" value="${userDto.email}" readonly/>
                    </div>

<%--                    이름--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="name">이름</label>
                        <input type="text" id="name" class="form-control form-control-lg" name="name" value="${userDto.name}"/>
                    </div>

<%--                    생년월일--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="birth">생년월일</label>
                        <input type="text" id="birth" pattern="[0-9]{4}" class="form-control form-control-lg" name="birth" value="${userDto.birth}"/>
                    </div>

                    <%--                    성별--%>
                    <% UserDto userDto = (UserDto) request.getAttribute("userDto"); /* 모델로부터 userDto 객체를 가져옵니다. */%>
                    <div class="form-outline mb-3">
                        <label class="form-label">성별</label>
                        <input type="radio" id="male" name="gender" value="남자" <%= "남자".equals(userDto.getGender()) ? "checked" : "" %>>
                        <label for="male">남자</label>
                        <input type="radio" id="female" name="gender" value="여자" <%= "여자".equals(userDto.getGender()) ? "checked" : "" %>>
                        <label for="female">여자</label><br>
                    </div>

<%--                    전화번호--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="gender">전화번호</label>
                        <input type="text" id="phone" class="form-control form-control-lg" name="phone" value="${userDto.phone}"/>
                    </div>

<%--                    성별--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="gender">성별</label>
                        <input type="text" id="gender" class="form-control form-control-lg" name="gender" value="${userDto.gender}"/>
                    </div>

<%--                    주소--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="address">주소</label>
                        <input type="text" id="address" class="form-control form-control-lg" name="address" value="${userDto.address}"/>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <a href="/guest/findPw" class="text-body">비밀번호 찾기 / 변경하기</a><br><br><br>
                    </div>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <button type="button" id="updateBtn" class="btn btn-primary btn-lg" onclick="update()">수정</button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</section>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>

<script>
    function update() {
        var email = document.getElementById("email").value;
        var image = $("#image")[0].files[0];
        var name = document.getElementById("name").value;
        var birth = document.getElementById("birth").value;
        var gender = document.getElementById("gender").value;
        var phone = document.getElementById("phone").value;
        var address = document.getElementById("address").value;

        var formData = new FormData();
        formData.append("email", email);
        formData.append("image", image);
        formData.append("name", name);
        formData.append("birth", birth);
        formData.append("gender", gender);
        formData.append("phone", phone);
        formData.append("address", address);

        $.ajax({
            url: "/user/updateUser",
            type: "POST",
            data: formData,
            processData: false, // 중요: FormData를 처리하지 않도록 설정
            contentType: false, // 중요: 컨텐츠 타입을 false로 설정
            success: function (data) {
                alert(data.message);
                location.reload();
            },
            error: function (error) {
                alert("수정에 실패했습니다.");
            }
        })
    }
</script>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>

</html>