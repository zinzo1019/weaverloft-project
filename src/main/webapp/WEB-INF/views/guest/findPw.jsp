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
                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="email">이메일</label>
                        <input type="email" id="email" class="form-control form-control-lg"
                               placeholder="이메일 주소를 입력해주세요." name="email"/>
                    </div>

                    <button type="button" class="btn btn-primary btn-lg" style="padding-left: 2.5rem; padding-right: 2.5rem;" id="mailSend">전송</button>

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

    /** 임시 비밀번호 전송하기 */
    $("#mailSend").click(function(e) {
        var email = document.getElementById("email").value;
        $.ajax({
            type: "POST",
            url: "/guest/findPw",
            data: {email: email},
            success: function(data) {
                alert(data.message)
                window.location.href = "/guest/updatePw"; // 비밀번호 변경 페이지로 이동
            },
            error: function(error) {
                alert(error.message)
            }
        });
    });

</script>
</html>