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
    <!-- jQuery 및 jQuery UI 스크립트 추가 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <!-- jQuery UI CSS -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <title>메인 페이지</title>
    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<style>
</style>

<script>
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
                    <div class="form-outline mb-3">
                        <label class="form-label">성별</label>
                        <input type="radio" id="male" name="gender" value="남자" ${userDto.gender eq '남자' ? 'checked' : ''}>
                        <label for="male">남자</label>
                        <input type="radio" id="female" name="gender" value="여자" ${userDto.gender eq '여자' ? 'checked' : ''}>
                        <label for="female">여자</label><br>
                    </div>

                    <%--                    전화번호--%>
                    <div class="form-outline mb-3">
                        <label class="form-label" for="phone">전화번호</label>
                        <input type="text" id="phone" class="form-control form-control-lg" name="phone"
                               value="${userDto.phone}"/>
                    </div>

                    <%--                    주소--%>

                    <div class="form-outline mb-3">
                        <label class="form-label" for="address">주소</label>

                        <input type="text" name="address" id="address" class="form-control form-control-lg"
                               value="${userDto.address}"
                               readonly>
                        <button type="button" id="addressBtn" onclick="searchAddress()" class="btn btn-primary btn-lg"
                                style="margin-top: 15px;">
                            주소 검색
                        </button>
                        <div id="addressResult"></div>
                        <br>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <a href="/guest/findPw" class="text-body">비밀번호 찾기 / 변경하기</a><br><br><br>
                    </div>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <button type="button" id="updateBtn" class="btn btn-primary btn-lg" onclick="Validation()">수정
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

    function Validation() {
        var email = document.getElementById("email")
        var name = document.getElementById("name")
        var gender = document.querySelector('input[name="gender"]:checked').value;
        var address = document.getElementById("address")
        var birth_string = document.getElementById("birth")
        var phone = document.getElementById("phone")

        // 이름
        var regName = /^[가-힣a-zA-Z]{2,15}$/;
        // 생년월일
        var regBirth = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
        // 전화번호
        var regPhone = /^[0-9\-]+$/;

        // 이름 확인
        if (!regName.test(name.value)) {
            alert("최소 2글자 이상, 한글과 영어만 입력하세요.")
            name.focus();
            return false;
        }
        // 성별 확인
        if (gender.value == "") {
            alert("성별을 선택해주세요.");
            gender.focus();
            return false;
        }
        // 주소 확인
        if (address.value == "") {
            alert("주소를 작성해주세요")
            return false;
        }
        // 생일 확인
        if (birth_string.value == "") {
            alert("생년월일를 입력하세요.")
            birth_string.focus();
            return false;
        } else if (!regBirth.test(birth_string.value)) {
            alert("생년월일를 정확하게 입력해주세요.")
            birth_string.focus();
            return false;
        }
        // 전화번호 확인
        if (phone.value == "") {
            alert("전화번호를 입력하세요.")
            phone.focus();
            return false;
        } else if (!regPhone.test(phone.value)) {
            alert("숫자와 하이픈만 포함하세요.")
            phone.focus();
            return false;
        }
        update(); // 수정 함수
    }

    function update() {
        var email = document.getElementById("email").value;
        var name = document.getElementById("name").value;
        var birth_string = document.getElementById("birth").value;
        var phone = document.getElementById("phone").value;
        var address = document.getElementById("address").value;

        var formData = new FormData();
        formData.append("email", email);
        formData.append("name", name);
        formData.append("birth_string", birth_string);
        formData.append("phone", phone);
        formData.append("address", address);

        // 선택된 성별 값 가져오기
        var gender = document.querySelector('input[name="gender"]:checked');
        if (gender) {
            formData.append("gender", gender.value);
        }

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
                var addr = ''; // 주소 변수
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

    /** 생년월일 양식 */
    $(function () {
        $("#birth").datepicker({
            dateFormat: "yy-mm-dd", // 날짜 형식
            changeMonth: true, // 월 변경 가능
            changeYear: true, // 년도 변경 가능
            yearRange: "1900:2099" // 허용 년도 범위
        });
    });

</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
</html>