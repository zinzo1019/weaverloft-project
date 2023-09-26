<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%--    bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport"
          content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Chart.js CSS 및 JS 추가 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>

    <%--        jquery 사용--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <title>관리자 페이지</title>
</head>

<style>

    .boardForm {
        display: flex;
        align-items: center;
    }

    .boardText {
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-right: 10px;
    }

    .boardButton {
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        padding: 5px 10px;
        cursor: pointer;
    }

    .boardButton:hover {
        background-color: #0056b3;
    }

    /* 추가 버튼 스타일 */
    .showBoardForm {
        background-color: #007bff; /* 배경색 */
        color: #fff; /* 글자색 */
        border: none; /* 테두리 없음 */
        border-radius: 5px; /* 둥근 모서리 */
        padding: 4px 8px; /* 내부 여백 */
        cursor: pointer; /* 커서 모양 변경 (손가락 모양) */
    }

    .showBoardForm:hover {
        background-color: #0056b3; /* 호버 상태에서 배경색 변경 */
    }

    /* 추가 버튼 스타일 */
    .deleteBoard {
        background-color: #007bff; /* 배경색 */
        color: #fff; /* 글자색 */
        border: none; /* 테두리 없음 */
        border-radius: 5px; /* 둥근 모서리 */
        padding: 4px 8px; /* 내부 여백 */
        cursor: pointer; /* 커서 모양 변경 (손가락 모양) */
    }

    .deleteBoard:hover {
        background-color: #0056b3; /* 호버 상태에서 배경색 변경 */
    }

</style>

<script>
</script>
<body>

<!-- 사이드 바를 포함 -->
<jsp:include page="../admin_sidebar.jsp"/>


<!-- 오른쪽 컨텐츠에 왼쪽 마진을 주어 겹치지 않게 설정 -->
<div id="content" style="margin-left: 300px;">
    <div class="p-4" style="width: 600px;">
        <%--                일반 리스트--%>
        <ul class="list-unstyled components">
            <div style="display: flex; align-items: center; width: 200px;">
                <h5 style="font-size: 20px; font-weight: bold; color: #193990; flex: 1;">일반 게시판</h5>
                <button class="showBoardForm" style="margin-left: 10px;">추가</button>
            </div>
            <%--            일반 게시판 - 부모--%>
            <form class="boardForm" style="display: none;"><br>
                <input type="text" class="boardText" placeholder="게시판 이름">
                <button class="boardButton" data-board-id="${board.id}" data-board-role="ROLE_GUEST">전송</button>
            </form>

            <%--            일반 게시판 자식--%>
            <div style="height: 10px;"></div>
            <c:forEach items="${guestBoards}" var="board">
                <div style="display: flex; align-items: center; width: 600px;">
                    <li><a href="/${board.role}?id=${board.id}"
                           style="font-size: 16px; font-weight: bold; color: #000; padding-left: ${(board.level -1) * 10}px;"> ${board.name} </a>
                    </li>
                    <button class="showBoardForm" style="margin-left: 10px;">추가</button>
                    <button class="deleteBoard" style="margin-left: 10px;" data-role-id="ROLE_ADMIN"
                            data-board-id="${board.id}">삭제
                    </button>
                </div>

                <%--                일반 게시판 자식 생성--%>
                <form class="boardForm" style="display: none; margin-left: 10px;"><br>
                    <input type="text" class="boardText" placeholder="게시판 이름">
                    <button class="boardButton" data-board-id="${board.id}">전송
                    </button>
                </form>
                <div style="height: 10px;"></div>
            </c:forEach><br><br>

            <%--            사용자 게시판--%>
            <div style="display: flex; align-items: center; width: 200px;">
                <h5 style="font-size: 20px; font-weight: bold; color: #193990; flex: 1;">사용자 게시판</h5>
                <button class="showBoardForm" style="margin-left: 10px;">추가</button>
            </div>
            <%--            사용자 게시판 부모--%>
            <form class="boardForm" style="display: none;"><br>
                <input type="text" class="boardText" placeholder="게시판 이름">
                <button class="boardButton" data-board-id="${board.id}" data-board-role="ROLE_USER">전송</button>
            </form>
            <div style="height: 10px;"></div>

            <%--            사용자 게시판 자식--%>
            <c:forEach items="${memberBoards}" var="board">
                <div style="display: flex; align-items: center; width: 600px;">
                    <li><a href="/${board.role}?id=${board.id}"
                           style="font-size: 16px; font-weight: bold; color: #000; padding-left: ${(board.level -1) * 10}px;"> ${board.name} </a>
                    </li>
                    <button class="showBoardForm" style="margin-left: 10px;">추가</button>
                    <button class="deleteBoard" style="margin-left: 10px;" data-role-id="ROLE_ADMIN"
                            data-board-id="${board.id}">삭제
                    </button>
                </div>

                <%--                게시판 폼--%>
                <form class="boardForm" style="display: none; margin-left: 10px;"><br>
                    <input type="text" class="boardText" placeholder="게시판 이름">
                    <button class="boardButton" data-board-id="${board.id}">전송</button>
                </form>
                <div style="height: 10px;"></div>
            </c:forEach><br><br>

            <%--            관리자 게시판--%>
            <div style="display: flex; align-items: center; width: 200px;">
                <h5 style="font-size: 20px; font-weight: bold; color: #193990; flex: 1;">관리자 게시판</h5>
                <button class="showBoardForm" style="margin-left: 10px;" data-role-id="ROLE_ADMIN">추가</button>
            </div>

            <%--                관리자 게시판 부모--%>
            <form class="boardForm" style="display: none;"><br>
                <input type="text" class="boardText" placeholder="게시판 이름">
                <button class="boardButton" data-board-id="${board.id}" data-board-role="ROLE_ADMIN">전송</button>
            </form>
            <div style="height: 10px;"></div>

            <%--            관리자 게시판 자식--%>
            <c:forEach items="${adminBoards}" var="board">
                <div style="display: flex; align-items: center; width: 600px;">
                    <li><a href="/${board.role}?id=${board.id}"
                           style="font-size: 16px; font-weight: bold; color: #000; padding-left: ${(board.level -1) * 10}px;"> ${board.name} </a>
                    </li>
                    <button class="showBoardForm" style="margin-left: 10px;">추가</button>
                    <button class="deleteBoard" style="margin-left: 10px;" data-role-id="ROLE_ADMIN"
                            data-board-id="${board.id}">삭제
                    </button>
                </div>

                <%--                관리자 게시판 자식 생성--%>
                <form class="boardForm" style="display: none;"><br>
                    <input type="text" class="boardText" placeholder="게시판 이름">
                    <button class="boardButton" data-board-id="${board.id}">전송
                    </button>
                </form>
                <div style="height: 10px;"></div>

            </c:forEach>
        </ul>
        <br>
    </div>
</div>

<!-- 부트스트랩 JS 및 jQuery 추가 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

<script>
    var showBoardForms = document.querySelectorAll('.showBoardForm'); // 게시판 추가 버튼
    var boardForms = document.querySelectorAll('.boardForm'); // 게시글 작성 폼
    var boardButtons = document.querySelectorAll('.boardButton'); // 게시판 추가 전송 버튼
    var boardTexts = document.querySelectorAll('.boardText'); // 게시판 제목
    var deleteBoards = document.querySelectorAll('.deleteBoard'); // 게시판 삭제

    /** 게시판 추가 버튼 클릭 시 - 제목 입력 폼 생성 */
    showBoardForms.forEach(function (button, index) {
        button.addEventListener('click', function () {
            toggleBoardForm(index);
        });
    });

    function toggleBoardForm(index) {
        if (boardForms[index].style.display === 'block') {
            boardForms[index].style.display = 'none'; // 숨김
        } else {
            boardForms[index].style.display = 'block'; // 보임
        }
    }

    /** 게시판 저장 버튼 클릭 시 */
    boardButtons.forEach(function (button, index) {
        button.addEventListener('click', function () {
            var board = boardTexts[index].value; // 인덱스에 맞는 게시판 내용 가져오기
            var boardId = this.getAttribute('data-board-id'); // 게시판 아이디 가져오기
            var role = this.getAttribute('data-board-role'); // 게시판 아이디 가져오기
            $.ajax({
                url: '/ROLE_ADMIN/board/save?boardId=' + boardId,
                type: 'POST',
                data: {
                    "board": board,
                    "role": role
                },
                success: function (data) {
                    location.reload();
                },
                error: function () {
                    location.reload();
                },
                complete: function () {
                    // 요청이 완료되면 항상 새로고침
                    location.reload();
                }
            });
        });
    });

    deleteBoards.forEach(function (button, index) {
        button.addEventListener('click', function () {
            var boardId = this.getAttribute('data-board-id'); // 게시판 아이디 가져오기
            $.ajax({
                url: '/ROLE_ADMIN/isPost?boardId=' + boardId,
                type: 'GET',
                success: function (data) { // 게시물 없음
                    alert("게시판을 삭제합니다.");
                    $.ajax({
                        url: '/ROLE_ADMIN/board/delete?boardId=' + boardId,
                        type: 'POST',
                        success: function (data) {
                            location.reload();
                        },
                        error: function () {
                        },
                        complete: function () {
                            // 요청이 완료되면 항상 새로고침
                            location.reload();
                        }
                    });
                },
                error: function () { // 게시물 있음
                    var result = confirm("게시글이 있습니다. 정말로 삭제하시겠습니까?");
                    if (result) { // 확인
                        alert("게시판을 삭제합니다.");
                        $.ajax({
                            url: '/ROLE_ADMIN/board/delete?boardId=' + boardId,
                            type: 'POST',
                            success: function (data) {
                                location.reload();
                            },
                            error: function () {
                            },
                            complete: function () {
                                // 요청이 완료되면 항상 새로고침
                                location.reload();
                            }
                        });
                    } else { // 취소
                        alert("삭제가 취소되었습니다.");
                    }
                }
            });
        });
    })

</script>
</html>