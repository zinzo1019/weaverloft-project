package com.example.choyoujin.Service;

import com.example.choyoujin.DTO.UserDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelService {
    /** 엑셀 파일 만들기 */
    public static void createExcelFile(List<UserDto> userDtos, String filePath) throws IOException {
        // 회원가입 날짜로 정렬
        userDtos.sort(Comparator.comparing(UserDto::getCreateDate));
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("UserDtos");

        makeHeader(sheet, workbook); // 헤더

        int rowNum = 1;
        int startRow = rowNum; // 병합 시작 행 번호 초기화

        for (int i = 0; i < userDtos.size(); i++) {
            UserDto userDto = userDtos.get(i);
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(userDto.getEmail());
            row.createCell(1).setCellValue(maskName(userDto.getName()));
            row.createCell(2).setCellValue(maskPhoneNumber(userDto.getPhone()));
            row.createCell(3).setCellValue(userDto.getAddress());
            row.createCell(4).setCellValue(userDto.getBirth());
            row.createCell(5).setCellValue(userDto.getGender());

            String createDate = String.valueOf(userDto.getCreateDate());
            // 이전 행과 회원가입 날짜가 같으면 병합하지 않음
            if (i > 0 && createDate.equals(String.valueOf(userDtos.get(i - 1).getCreateDate()))) {
                rowNum++;
                continue;
            }
            // 병합된 영역이 시작 행과 끝 행이 같으면 병합하지 않음
            if (startRow != rowNum) {
                CellRangeAddress mergedCell = new CellRangeAddress(startRow - 1, rowNum - 1, 6, 6);
                sheet.addMergedRegion(mergedCell);
            }
            row.createCell(6).setCellValue(createDate);
            // 다음 병합 영역을 위해 행 번호 갱신
            startRow = rowNum + 1;
            rowNum++;
        }
        // 마지막 병합된 영역 처리
        if (startRow != rowNum) {
            CellRangeAddress mergedCell = new CellRangeAddress(startRow - 1, rowNum - 1, 6, 6);
            sheet.addMergedRegion(mergedCell);
        }
        // 엑셀 파일 저장
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    /** 헤더 만들기 */
    private static void makeHeader(Sheet sheet, Workbook workbook) {
        // 열의 폭 설정
        sheet.setColumnWidth(0, 8000); // 이메일 열
        sheet.setColumnWidth(1, 3000); // 사용자 이름 열
        sheet.setColumnWidth(2, 4000); // 전화번호 열
        sheet.setColumnWidth(3, 9000); // 주소 열
        sheet.setColumnWidth(4, 4000); // 생년월일 열
        sheet.setColumnWidth(5, 2000); // 성별 열
        sheet.setColumnWidth(6, 4000); // 회원가입 날짜 열

        // 헤더 행 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("이메일");
        headerRow.createCell(1).setCellValue("사용자 이름");
        headerRow.createCell(2).setCellValue("전화번호");
        headerRow.createCell(3).setCellValue("주소");
        headerRow.createCell(4).setCellValue("생년월일");
        headerRow.createCell(5).setCellValue("성별");
        headerRow.createCell(6).setCellValue("회원가입 날짜");

        // 헤더 셀 스타일 설정
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // 헤더 배경색
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.BLACK.getIndex()); // 헤더 텍스트 색상
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }
    }

    /** 전화번호 마스킹 */
    private static String maskPhoneNumber(String phone) {
        if (phone != null && phone.length() >= 10) {
            int maskLength = phone.length() - 4; // 마지막 4자리만 표시, 나머지는 마스킹
            StringBuilder maskedPhone = new StringBuilder();

            // "010"까지는 그대로 유지
            maskedPhone.append(phone.substring(0, 3));
            for (int i = 0; i < maskLength; i++) {
                maskedPhone.append('*'); // 마스킹 처리
            }
            // 마지막 4자리는 그대로 유지
            maskedPhone.append(phone.substring(maskLength + 3));
            return maskedPhone.toString();
        } else {
            return phone; // 전화번호가 null이거나 10자리 미만인 경우, 그대로 반환
        }
    }

    /** 이름 마스킹 */
    private static String maskName(String name) {

        String[] nameParts = null;
        // 영어의 경우 공백 기준으로 자르기
        if (name.matches("^[a-zA-Z\\s]*$")) { // 영어의 경우
            nameParts = name.split(" ");
        } else { // 한글의 경우
            nameParts = name.split("");
        }

        if (nameParts.length >= 2) { // 이름이 2개 이상이고, 중간 이름이 있을 경우
            String firstName = nameParts[0];
            String middleName = nameParts[1];
            String lastName = "";

            // 중간 이름 길이만큼 '*'로 대체
            StringBuilder maskedMiddleName = new StringBuilder();
            for (int i = 0; i < middleName.length(); i++) {
                char character = middleName.charAt(i);
                if (Character.isLetter(character)) {
                    maskedMiddleName.append('*');
                } else {
                    maskedMiddleName.append(character);
                }
            }
            // 성(lastName)이 있는 경우 추가
            if (nameParts.length > 2) {
                lastName = nameParts[2];
            }
            // 새로운 이름 생성
            return firstName + " " + maskedMiddleName + " " + lastName;
        } else {
            return name;
        }

    }
}
