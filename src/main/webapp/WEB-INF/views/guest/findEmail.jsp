<%@ page import="com.example.choyoujin.DTO.UserDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport" content="width=device-width, initial-scale=1">
    <title>이메일 찾기 페이지</title>
    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<style>

</style>

<script>
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

<section class="vh-100">
    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                     class="img-fluid" alt="Sample image">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                <form method="post">
<%--                    이름--%>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="name">이름</label>
                        <input type="text" id="name" class="form-control form-control-lg"
                               placeholder="이름을 입력해주세요." name="name"/>
                    </div>
<%--                    전화번호--%>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="phone">핸드폰 번호</label>
                        <input type="text" id="phone" class="form-control form-control-lg"
                               name="phone" placeholder="000-0000-0000" onkeyup="formatPhoneNumber(this);"/>
                    </div>

                    <button type="button" class="btn btn-primary btn-lg" style="padding-left: 2.5rem; padding-right: 2.5rem;" id="findEmail">찾기</button>

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

    $("#findEmail").click(function (e) {
        var name = document.getElementById("name").value;
        var phone = document.getElementById("phone").value;

        var formData = new FormData();
        formData.append("name", name);
        formData.append("phone", phone);

        $.ajax({
            type: "POST",
            url: "/guest/findEmail",
            data: formData,
            processData: false, // 중요: FormData를 처리하지 않도록 설정
            contentType: false, // 중요: 컨텐츠 타입을 false로 설정
            success: function (data) {
                alert(data.message);
            },
            error: function (error) {
                alert("이름 또는 핸드폰 번호가 맞지 않습니다. 다시 확인해주세요.")
            }
        })
    })

</script>
</html>