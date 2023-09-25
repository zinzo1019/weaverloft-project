<%@ page import="com.example.choyoujin.DTO.UserDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport" content="width=device-width, initial-scale=1">
    <title>비밀번호 찾기 페이지</title>
    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<style>

</style>

<script>
    
    var tempCheck = false; // 임시 비밀번호 일치 여부
    function Validation() {

        console.log("validation() started...")

        var pw = document.getElementById("pw").value;
        var cpw = document.getElementById("cpw").value;
        var tempPw = document.getElementById("tempPw").value;

        var regPw = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]).{4,12}$/;

        // 인증 번호 확인
        if (tempPw == "") {
            alert("임시 비밀번호를 입력해주세요.")
            uid.focus();
            return false;
        } else if (!tempCheck) {
            alert("임시 비밀번호 확인을 눌러주세요.");
            return false;
        }
        // 비밀번호 확인
        if (pw == "") {
            alert("비밀번호를 입력하세요.")
            pw.focus();
            return false;
        }
        // 비밀번호 영어 대소문자 확인
        if (!regPw.test(pw)) {
            alert("비밀번호에는 4~12자 영문 대소문자, 숫자, 특수기호를 모두 포함하세요.")
            pw.focus();
            return false;
        }
        // 비밀번호 확인
        if (cpw !== pw) {
            alert("비밀번호와 동일하지 않습니다.")
            cpw.focus();
            return false;
        }

        if (tempCheck) { // 임시 비밀번호 체크까지 완료했다면
            $.ajax({
                type: "POST",
                url: "/guest/updatePw", // 비밀번호 변경 URL
                data: {"pw": pw}, // 폼 데이터 직렬화
                success: function (response) {
                    alert(response.message);
                    window.location.href = "/loginForm"; // 로그인 폼으로 리턴
                },
                error: function () {
                    alert("비밀번호 변경에 실패했습니다.");
                }
            });
        }
    }

</script>

<section class="vh-100">
    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                     class="img-fluid" alt="Sample image">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">

                <form method="post">
                    <div class="form-outline mb-4">
                        <label class="form-label" for="tempPw">임시 비밀번호</label>
                        <input type="text" id="tempPw" class="form-control form-control-lg"
                               placeholder="임시 비밀번호" name="tempPw"/>
                        <button type="button" class="btn btn-primary btn-lg" style="padding-left: 2.5rem; padding-right: 2.5rem;" id="checkTempPw">확인</button>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="pw">새 비밀번호</label>
                        <input type="password" id="pw" class="form-control form-control-lg"
                               placeholder="영문 대소문자, 숫자, 특수문자 포함" name="pw"/>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="cpw">새 비밀번호 확인</label>
                        <input type="password" id="cpw" class="form-control form-control-lg"
                               placeholder="새 비밀번호 확인" name="cpw"/>
                    </div>
                    <button type="button" id="updatePw" onclick="Validation()" class="btn btn-primary btn-lg" style="padding-left: 2.5rem; padding-right: 2.5rem;">전송</button>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <a href="/loginForm" class="btn btn-secondary">로그인</a>
                    </div>
                </form>

            </div>
        </div>
    </div>
</section>

</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
<script>

    /** 임시 비밀번호 체크 */
    $("#checkTempPw").click(function () {
        console.log("checkTempPw is clicked...")
        var tempPw = document.getElementById("tempPw").value; // 임시 비밀번호

        $.ajax({
            url: "/guest/checkTempPw",
            type: "POST",
            data: {
                tempPwCheck: tempPw
            },
            success: function (data) {
                alert(data.message);
                tempCheck = true;
            }, error: function(error) {
                alert("잘못된 비밀번호입니다. 다시 확인해주세요.");
            }
        });
        return false;
    })

</script>
</html>