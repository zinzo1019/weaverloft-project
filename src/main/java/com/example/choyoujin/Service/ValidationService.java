package com.example.choyoujin.Service;

import com.example.choyoujin.DTO.UserDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ValidationService {

    @Autowired
    UserService userService;

    /**
     * birthCell -> LocalDate 형 변환
     */
    public static LocalDate isValidBirth(Cell birthCell, LocalDate birth) {
        if (birthCell.getCellType() == CellType.NUMERIC) {
            // 엑셀 셀이 숫자 형식인 경우 (날짜 형식)
            double numericValue = birthCell.getNumericCellValue();
            Date javaDate = DateUtil.getJavaDate(numericValue);
            if (javaDate != null) {
                // java.util.Date를 java.time.LocalDate로 변환
                birth = javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else { // 생년월일 데이터가 없다면
            }
        } else {
            System.out.println("생년월일 형식을 yyyy-mm-dd에 맞춰주세요.");
            return null;
        }
        return birth;
    }

    public boolean isValidGender(String gender) {
        // 유효한 성별 값인지 확인
        return gender != null && (gender.equals("남자") || gender.equals("여자"));
    }

    public boolean isValidAddress(String address) {
        // 주소가 null이 아니며, 공백이 아니며, 최소 길이와 최대 길이 사이에 있는 경우 유효
        int minLength = 5; // 최소 길이 (예: 5자)
        int maxLength = 100; // 최대 길이 (예: 100자)
        return address != null && !address.trim().isEmpty() && address.length() >= minLength && address.length() <= maxLength;
    }

    public String isValidPhone(Cell phoneCell) {
        String phone = null;
        if (phoneCell.getCellType() == CellType.NUMERIC) { // 엑셀 셀이 숫자 형식인 경우
            double numericValue = phoneCell.getNumericCellValue();
            phone = String.valueOf((long) numericValue); // 숫자를 문자열로 변환
        } else { // 엑셀 셀이 숫자 형식이 아닐 경우 (하이픈)
            phone = phoneCell.getStringCellValue();
        }
        // 숫자만 남기고 나머지 문자 제거
        phone = phone.replaceAll("[^0-9]", "");
        // 10자리 이상인 경우 유효한 전화번호로 간주
        // 11자리인 경우 하이픈(`-`)을 포함한 형태로 변환
        if (phone.length() == 11) {
            return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        } else {
            // 그 외의 경우는 그대로 반환
            System.out.println("전화번호는 하이픈을 포함하여 13자리여야 합니다.");
            return null;
        }
    }

    public boolean isValidPw(String pw) {
        // 비밀번호는 최소 8자 이상, 대/소문자, 숫자, 특수문자를 포함해야 유효
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        // 비밀번호가 유효한 형식인지 검사
        if (pw.matches(passwordRegex)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidName(String name) {
        // 이름이 비어 있지 않고, 최소 길이와 최대 길이를 설정하여 유효성 검사
        int minLength = 2; // 최소 길이 (예: 2자)
        int maxLength = 50; // 최대 길이 (예: 50자)
        // 이름이 null이거나 공백이 아니며, 최소 길이보다 크고 최대 길이보다 작은 경우 유효
        if (name != null && !name.trim().isEmpty() && name.length() >= minLength && name.length() <= maxLength) {
            // 이름에 자음 또는 모음만 있는지 검사
            if (containsOnlyVowelsOrConsonants(name)) {
                System.out.println("이름에 자음 또는 모음만 포함될 수 없습니다.");
                return false;
            } else {
                return true;
            }
        } else {
            System.out.println("이름 형식을 확인하세요. (최소 2글자 - 최대 50글자)");
            return false;
        }
    }

    public boolean containsOnlyVowelsOrConsonants(String name) {
        // 한글 자음과 모음을 정규 표현식으로 검사
        String regex = "^[ㄱ-ㅎㅏ-ㅣ]*$";
        return name.matches(regex);
    }

    public boolean isValidEmail(String email) {
        // 이메일 유효성 검사 정규 표현식
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        // 이메일이 유효한 형식인지 검사
        if (email.matches(emailRegex)) {
            // 이미 저장된 사용자가 아닌지 확인하는 로직
            UserDto userDto = userService.findUserByEmail(email);
            if (userDto == null) return true;
            else {
                return false;
            }
        } else {
            System.out.println("이메일 형식을 확인하세요.");
            return false;
        }
    }

}
