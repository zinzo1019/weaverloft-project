<%@ page import="com.example.choyoujin.DTO.UserDto" %>
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

<script>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
</head>
<body>

<section class="vh-100">
    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="data:${userDto.type};base64,${encoding}" class="img-fluid" alt="사용자 이미지">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">

                <form id="form" enctype="multipart/form-data">

                    <!-- 이미지 수정 영역 -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="image">이미지 수정</label>
                        <input type="file" id="image" class="form-control form-control-lg" name="image"/>
                    </div>


                    <%--                    이메일--%>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="email">이메일</label>
                        <input type="email" id="email" class="form-control form-control-lg" name="email"
                               value="${userDto.email}" readonly/>
                    </div>

                    <%--                    이름--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="name">이름</label>
                        <input type="text" id="name" class="form-control form-control-lg" name="name"
                               value="${userDto.name}"/>
                    </div>

                    <%--                    생년월일--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="birth">생년월일</label>
                        <input type="text" id="birth" pattern="[0-9]{4}" class="form-control form-control-lg"
                               name="birth" value="${userDto.birth}"/>
                    </div>

                    <%--                    성별--%>
                    <% UserDto userDto = (UserDto) request.getAttribute("userDto"); /* 모델로부터 userDto 객체를 가져옵니다. */%>
                    <div class="form-outline mb-3">
                        <label class="form-label">성별</label>
                        <input type="radio" id="male" name="gender"
                               value="남자" <%= "남자".equals(userDto.getGender()) ? "checked" : "" %>>
                        <label for="male">남자</label>
                        <input type="radio" id="female" name="gender"
                               value="여자" <%= "여자".equals(userDto.getGender()) ? "checked" : "" %>>
                        <label for="female">여자</label><br>
                    </div>

                    <%--                    전화번호--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="gender">전화번호</label>
                        <input type="text" id="phone" class="form-control form-control-lg" name="phone"
                               value="${userDto.phone}"/>
                    </div>

                    <%--                    성별--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="gender">성별</label>
                        <input type="text" id="gender" class="form-control form-control-lg" name="gender"
                               value="${userDto.gender}"/>
                    </div>

                    <%--                    주소--%>


                    <div class="form-outline mb-3">
                        <label class="form-label" for="address">주소</label>

                        <input type="text" name="address" id="address" class="form-control form-control-lg" value="${userDto.address}"
                               readonly>
                        <button type="button" id="addressBtn" onclick="searchAddress()" class="btn btn-primary btn-lg" style="margin-top: 15px;">
                            주소 검색
                        </button>
                        <div id="addressResult"></div>
                        <br>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <a href="/guest/findPw" class="text-body">비밀번호 찾기 / 변경하기</a><br><br><br>
                    </div>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <button type="button" id="updateBtn" class="btn btn-primary btn-lg" onclick="update()">수정
                        </button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</section>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa"
        crossorigin="anonymous"></script>

</body>

<script>
    function update() {
        var email = document.getElementById("email").value;
        var name = document.getElementById("name").value;
        var birth = document.getElementById("birth").value;
        var gender = document.getElementById("gender").value;
        var phone = document.getElementById("phone").value;
        var address = document.getElementById("address").value;

        var formData = new FormData();
        formData.append("email", email);
        formData.append("name", name);
        formData.append("birth", birth);
        formData.append("gender", gender);
        formData.append("phone", phone);
        formData.append("address", address);

        // 수정할 이미지를 넣은 경우에만 append
        var image = $("#image")[0].files[0];
        if (image) {
            formData.append("image", image);
        }

        $.ajax({
            url: "updateUser",
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

    /** 도로명 주소 API */
    function searchAddress() {
        new daum.Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기한다.
                var addr = ''; // 주소 변수
                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("address").value = addr;
            }
        }).open();
    }
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
</html>