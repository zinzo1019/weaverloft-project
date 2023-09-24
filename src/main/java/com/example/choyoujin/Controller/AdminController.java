package com.example.choyoujin.Controller;

import com.example.choyoujin.DTO.ChartDto;
import com.example.choyoujin.DTO.UserDto;
import com.example.choyoujin.Service.ExcelService;
import com.example.choyoujin.Service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
//        List<ChartDto> signUpByDay = userService.countSignUpByDay(get5Months); // 일일 회원가입자 수
//        List<ChartDto> signInByDay = userService.countSignInByDay(get5Months); // 일일 로그인 수
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

    /** 사용자 등록 엑셀 서식 다운 */
    @GetMapping("/download/sample")
    public void downloadExcelSample(HttpServletResponse response) throws IOException {
        String excelFilePath = "user_sample.xlsx"; // 엑셀 파일 이름
        ExcelService.createSampleExcelFile(excelFilePath); // 엑섹 파일 생성

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

    /** 엑셀 파일 -> 사용자 데이터 저장 */
    @PostMapping("/upload/user")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {

        System.out.println("uploadExcel starts....");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 엑셀 파일의 첫 번째 시트를 가져옴

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 각 셀에서 데이터 추출
                String email = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String pw = row.getCell(2).getStringCellValue();
                String phone = row.getCell(3).getStringCellValue();
                String address = row.getCell(4).getStringCellValue();
                String gender = row.getCell(6).getStringCellValue();

                Cell birthCell = row.getCell(5);
                LocalDate birth = null;

                if (birthCell.getCellType() == CellType.NUMERIC) {
                    // 엑셀 셀이 숫자 형식인 경우 (날짜 형식)
                    double numericValue = birthCell.getNumericCellValue();
                    Date javaDate = DateUtil.getJavaDate(numericValue);

                    if (javaDate != null) {
                        // java.util.Date를 java.time.LocalDate로 변환
                        birth = javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } else {
                        // 적절한 날짜로 변환되지 않을 경우에 대한 처리
                        // 예외를 던지거나 기본값을 설정할 수 있음
                    }
                } else {
                    // 엑셀 셀이 다른 형식일 경우 (문자열 등)
                    // 적절한 처리를 수행
                }


                UserDto userDto = new UserDto(email, name, gender, pw, birth, address, phone);

                // 데이터베이스에 저장
                userService.saveUser(userDto, "ROLE_USER", 1, -1);
            }
            workbook.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:";
    }

}
