<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
</head>

<script>

    function pValue() {
        // 부모창에서 email 값 받아옴
        document.getElementById("email").value = opener.document.getElementById("email").value
    }

    function idCheck() {
        var id = document.getElementById("email").value;
        if (!id) {
            alert("아이디를 입력하세요.")
            return true;
        } else {
            var param = "id=" + id
            getX
        }
    }

</script>


<%--중복 체크 화면이 열리면 pValue() 함수가 호출--%>
<body onload="pValue()">

<div id="wrap">
    <b>아이디 중복 체크</b>
    <div id="chk">
        <form id="checkForm">
            <input type="text" name="idinput" id="userId">
<%--            중복 확인 버튼 클릭 시 중복 체크를 하는 idCheck() 호출--%>
            <input type="button" value="중복 확인" onclick="idCheck()">
        </form>
        <input id="camcelBtn" type="button" value="취소" onclick="window.close()">
<%--        사용하기를 클릭하면 sendCheckValue()가 실행되어 회원가입 화면으로 전달--%>
        <input id="useBtn" type="button" value="사용하기" onclick="sendCheckValue()">
    </div>
</div>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>
</html>