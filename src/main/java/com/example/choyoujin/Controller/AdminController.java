package com.example.choyoujin.Controller;

import com.example.choyoujin.DTO.ChartDto;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.ExcelService;
import com.example.choyoujin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/ROLE_ADMIN")
public class AdminController {

    @Autowired
    UserService userService;

    /** 차트 페이지 */
    @GetMapping("/chart")
    public String adminPage() {
        return "admin/admin_page";
    }

    /** 달별 회원가입자 수 */
    @GetMapping("/signUpByMonth")
    @ResponseBody
    public List<ChartDto> getsignUpByMonth(Model model) {
        List<YearMonth> get5Months = userService.findPast5Months(5); // 최근 5달 가져오기
//        List<Integer> signUpByDay = userService.countSignUpByDay(get5Months); // 일일 회원가입자 수
//        List<Integer> signInByDay = userService.countSignInByDay(get5Months); // 일일 로그인 수
        List<ChartDto> signUpByMonth = userService.countSignUpByMonth(get5Months); // 달별 회원가입자 수
        return signUpByMonth;
    }

    /** 달별 로그인자 수 */
    @GetMapping("/signIpByMonth")
    @ResponseBody
    public List<ChartDto> getsignIpByMonth(Model model) {
        List<YearMonth> get5Months = userService.findPast5Months(5); // 최근 5달 가져오기
        List<ChartDto> signIpByMonth = userService.countSignInByMonth(get5Months); // 달별 로그인자 수
        return signIpByMonth;
    }

    /** 사용자 라스트 엑셀 파일 다운 */
    @GetMapping("/download/user")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        List<UserDto> users = userService.findAllUsers(); // 사용자 리스트
        // 엑셀 파일 생성 및 다운로드
        String excelFilePath = "users.xlsx"; // 엑셀 파일 이름
        ExcelService.createExcelFile(users, excelFilePath);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + excelFilePath);

        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] bytes = Files.readAllBytes(Paths.get(excelFilePath));
            outputStream.write(bytes);
        }
        Files.deleteIfExists(Paths.get(excelFilePath));
    }

    /** 사용자 리스트 페이지 */
    @GetMapping("/userList")
    public String userListPage() {
        return "admin/user_list";
    }

}
