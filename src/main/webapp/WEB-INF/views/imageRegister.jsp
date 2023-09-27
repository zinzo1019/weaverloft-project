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
<script type="text/javascript">
    $(document).ready(function () {
        // 다중 파일 등록
        var formObj = $("#item");
        $("#btnRegister").on("click", function () {
            formObj.attr("action", "/guest/file/register"); // 매핑
            formObj.attr("method", "post"); // 메소드
            formObj.submit(); // 제출
        })
    })

    // 파일 리스트
    $("#btnList").on("click", function () {
        self.location = "/guest/file/list";
    });

    function getOriginalName(fileName) {
        if (checkImageType(fileName)) {
            return;
        }
        var idx = fileName.indexOf("_") + 1;
        return fileName.substr(idx);
    }

    function getThumbnailName(fileName) {
        var front = fileName.substr(0, 12);
        var end = fileName.substr(12);

        console.log("front: " + front);
        console.log("end: " + end);

        return front + "s_" + end;
    }

    function checkImageType(fileName) {
        var pattern = /jpg|gif|png|jpeg/i;
        return fileName.match(pattern);
    }

    $("#item").submit(function (event) {
        event.preventDefault();
        var that = $(this);
        var str = "";
        $(".uploadedList a").each(function (index) {
            var value = $(this).attr("href");

            str += "<input"
        })
    })

</script>
<body>

<%--    bootstrap--%>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>

</body>
</html>