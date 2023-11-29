<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    body {
        width: 100%;
        max-width: 2000px;
        margin: 0 auto;
    }
</style>

<script>
    var isDuplicated = null; // 이메일 중복 여부
    var verifyCheck = false; // 메일 인증 여부
    var mailSendCheck = false; // 메일 인증 번호 발송 여부

    // 아이디 중복 체크
    $(document).ready(function (e) {
        $("#idCheck").click(function () {
            $.ajax({
                url: "/guest/id/check",
                type: "GET",
                data: {
                    email: $('#email').val()
                },
                success: function (result) {
                    alert("사용가능한 아이디입니다.")
                    isDuplicated = false;
                }, error: function(error) {
                    alert("중복된 아이디입니다.")
                    isDuplicated = true;
                }
            });
            return false;
        })
    })

    function Validation() {
        //변수에 저장
        var email = document.getElementById("email")
        var pw = document.getElementById("pw")
        var cpw = document.getElementById("cpw")
        var name = document.getElementById("name")
        var gender = document.querySelector('input[name="gender"]:checked').value;
        var address = document.getElementById("address")
        var birth_string = document.getElementById("birth")
        var phone = document.getElementById("phone")
        var code = document.getElementById("verificationCode")
        var image = document.getElementById("imageInput")

        // 이메일
        var regId = /(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/
        // 비밀번호
        var regPw = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]).{4,12}$/;
        // 이름
        var regName = /^[가-힣a-zA-Z]{2,15}$/;
        // 생년월일
        var regBirth = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
        // 전화번호
        var regPhone = /^[0-9\-]+$/;

        // 아이디 확인
        if (email.value == "") {
            alert("이메일을 입력하세요.")
            email.focus();
            return false;
        } else if (!regId.test(email.value)) {
            alert("이메일 양식에 맞게 입력해주세요.")
            email.focus();
            return false;
        }
        // 비밀번호 확인
        if (pw.value == "") {
            alert("비밀번호를 입력하세요.")
            pw.focus();
            return false;
        }
        // 아이디 중복 체크 (비어있으면 중복 체크 전)
        if (isDuplicated == null) {
            alert("아이디 중복 체크를 해주세요.")
            return false;
        }
        // 비밀번호 영어 대소문자 확인
        if (!regPw.test(pw.value)) {
            alert("비밀번호에는 4~12자 영문 대소문자, 숫자, 특수기호를 모두 포함하세요.")
            pw.focus();
            return false;
        }
        // 메일 인증 여부
        if (!mailSendCheck) {
            alert("메일 인증을 진행해주세요.")
            return false;
        }
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
        // 인증 번호 확인
        if (code.value == "") {
            alert("인증 번호를 입력해주세요.")
            code.focus();
            return false;
        }
        // 주소 확인
        if (address.value == "") {
            alert("주소를 작성해주세요")
            return false;
        }
        // 이미지 확인
        if (image.value == "") {
            alert("이미지를 선택해주세요")
            return false;
        }
        // 비밀번호 확인
        if (cpw.value !== pw.value) {
            alert("비밀번호와 동일하지 않습니다.")
            cpw.focus();
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

        /** 유효성 문제 없을 시 폼에 submit */
        if (verifyCheck) {
            var formData = new FormData();
            formData.append("email", $("#email").val());
            formData.append("pw", $("#pw").val());
            formData.append("name", $("#name").val());
            formData.append("gender", document.querySelector('input[name="gender"]:checked').value);
            formData.append("address", $("#address").val());
            formData.append("phone", $("#phone").val());
            formData.append("birth_string", $("#birth").val());
            formData.append("image", $("#imageInput")[0].files[0]);

            $.ajax({
                type: "POST",
                url: "register-process", // 회원가입 처리 URL
                data: formData, // 폼 데이터 직렬화
                processData: false, // multipart 타입 전송
                contentType: false, // multipart 타입 전송
                success: function (response) {
                    // 폼 제출 성공 후 처리
                    alert("회원가입 성공!");
                    window.location.href = "/loginForm";
                },
                error: function () {
                    // 폼 제출 실패 후 처리
                    alert("회원가입 실패!");
                }
            });
        }
    }

    $(document).ready(function () {
        // 인증 번호 발송 버튼 클릭
        $("#sendVerification").click(function () {
            var email = $("#email").val(); // 이메일
            $.ajax({
                url: "/guest/send-verification",
                type: "POST",
                data: {email: email},
                success: function (data) {
                    $("#message").text(data.message);
                },
                error: function (error) {
                    $("#message").text(error.responseJSON.message);
                }
            });
            mailSendCheck = true; // 메일 전송 완료
            // 페이지가 새로고침 되지 않도록
            return false;
        });

        // 인증 번호 확인 클릭
        $("#verifyCode").click(function () {
            var code = $("#verificationCode").val();
            $.ajax({
                url: "/guest/verify-code",
                type: "POST",
                data: {code: code},
                success: function (data) {
                    $("#message").text(data.message);
                    verifyCheck = true; // 메일 인증 성공
                    alert("인증에 성공했습니다!");
                },
                error: function (error) {
                    $("#message").text(error.responseJSON.message);
                    alert("인증에 실패하였습니다. 다시 시도해주세요.");
                }
            });
            // 페이지가 새로고침 되지 않도록
            return false;
        });
    });

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

    /** 생년월일 양식 */
    $(function () {
        $("#birth").datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: "1900:2050",
            dateFormat: "yy-mm-dd" // 날짜 형식 설정 (년-월-일)
        });
    });

    /** 전화번호 양식 */
    function formatPhoneNumber(input) {
        // 입력에서 숫자만 추출
        var phone = input.value.replace(/\D/g, '');

        // 11자리 이상의 번호를 방지
        if (phone.length > 11) {
            phone = phone.slice(0, 11);
        }

        // 전화번호를 형식에 맞게 변환
        if (phone.length < 4) {
            input.value = phone;
        } else if (phone.length < 7) {
            input.value = phone.slice(0, 3) + '-' + phone.slice(3);
        } else if (phone.length < 11) {
            input.value = phone.slice(0, 3) + '-' + phone.slice(3, 7) + '-' + phone.slice(7);
        } else {
            input.value = phone.slice(0, 3) + '-' + phone.slice(3, 7) + '-' + phone.slice(7, 11);
        }
    }
</script>

<body>
<section class="vh-100">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card text-black" style="border-radius: 25px;">
                    <div class="card-body p-md-5">
                        <div class="row justify-content-center">
                            <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">회원 회원가입</p>

<%--                                form 태그에 유효성 검사 등록--%>
<%--                                파일 업로드 기능이 필요할 경우 multipart 타입 지정--%>
                                <form id="joinForm" class="mx-1 mx-md-4" enctype="multipart/form-data">

                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>


<%--                                        이메일--%>
                                        <div class="form-outline flex-fill mb-0">
                                            <input type="email" id="email" class="form-control" name="email"/>
                                            <label class="form-label" for="email">Your Email</label>

<%--                                            중복 확인--%>
                                            <button id="idCheck" name="idDuplication" type="submit" class="btn btn-primary btn-lg">중복 확인</button><br><br>
<%--                                            이메일 중복 체크--%>
                                            <d id="idAvailable" class="valid-feedback" style="display: none;"></d>
                                            <d id="idNotAvailable" class="invalid-feedback" style="display: none;"></d>

<%--                                            메일 인증--%>
                                            <div id="verificationSection">
                                                <input type="text" id="verificationCode" placeholder="인증번호">
                                                <button id="verifyCode">확인</button>
                                                <button id="sendVerification" class="btn btn-primary btn-lg">인증번호 발송</button>
                                            </div>
                                            <p id="message"> ${message} </p>

                                        </div>
                                    </div>
<%--                                        이름--%>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-label" for="name">이름</label>
                                                <input type="text" id="name" class="form-control" name="name"/>
                                            </div>
                                        </div>

<%--                                    성별--%>
                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <label class="form-label">성별</label>
                                            <input type="radio" id="male" name="gender" value="남자">
                                            <label for="male">남자</label>
                                            <input type="radio" id="female" name="gender" value="여자">
                                            <label for="female">여자</label><br>
                                        </div>
                                    </div>

<%--                                    생년월일--%>
                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <label class="form-label" for="birth">생년월일</label>
                                            <input type="text" id="birth" name="birth" pattern="[0-9]{4}" placeholder="예: 2000-10-19" class="form-control" ><br>
                                        </div>
                                    </div>

<%--                                    전화번호--%>
                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <label class="form-label" for="phone">전화번호</label>
                                            <input type="text" id="phone" name="phone" class="form-control" placeholder="000-0000-0000" onkeyup="formatPhoneNumber(this);"><br>
                                        </div>
                                    </div>

<%--                                    도로명 주소 검색 필드--%>
                                    <input type="text" name="address" id="address" class="form-control" readonly>
                                    <button type="button" id="addressBtn" onclick="searchAddress()" class="btn btn-primary btn-lg">주소 검색</button>
                                    <div id="addressResult"></div><br>

<%--                                      비밀번호--%>
                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <label class="form-label" for="pw">비밀번호</label>
                                            <input type="password" id="pw" class="form-control" name="pw"/>
                                        </div>
                                    </div>

<%--                                       2차 비밀번호--%>
                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <label class="form-label" for="cpw">비밀번호 확인</label>
                                            <input type="password" id="cpw" class="form-control" name="cpw"/>
                                        </div>
                                    </div><br>

<%--                                       사진 첨부--%>
                                    <input type="file" name="image" id="imageInput" required><br><br><br>
                                    <p id="imageErrorMessage" class="text-danger"></p>

                                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
<%--                                        제출 전 유효성 검사를 하도록 onClick 지정--%>
                                        <button type="button" id="uploadButton" class="btn btn-primary btn-lg" onclick="Validation()">등록</button>
                                    </div>
                                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                        <a href="/guest/admin-register" class="btn btn-secondary">관리자입니까?</a>
                                        <a href="/loginForm" class="btn btn-secondary">로그인</a>
                                    </div>

                            </div>
                            <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                     class="img-fluid" alt="Sample image">
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</html>

<script>
    /** 이미지 확장자 제한 */
    // const imageInput = document.getElementById('imageInput');
    // const imageErrorMessage = document.getElementById('imageErrorMessage');
    // const allowedExtensions = ['.jpg', '.jpeg', '.png', '.gif'];
    //
    // imageInput.addEventListener('change', function () {
    //     const fileName = this.value;
    //     const fileExtension = fileName.split('.').pop().toLowerCase();
    //     if (!allowedExtensions.includes('.' + fileExtension)) {
    //         imageErrorMessage.textContent = '올바른 이미지 파일을 선택하세요.';
    //         this.value = ''; // 파일 선택 취소
    //     } else {
    //         imageErrorMessage.textContent = '';
    //     }
    // });
</script>





