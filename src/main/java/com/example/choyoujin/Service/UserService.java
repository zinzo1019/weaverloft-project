package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.UserDao;
import com.example.choyoujin.DTO.ChartDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

import static com.example.choyoujin.Service.FileService.compressBytes;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder pwdEncoder;
    @Autowired
    private FileService fileService;

    /** 사용자 저장 */
    public boolean saveUser(UserDto userDto, String role, int enabled, int imageId) {
        // 회원가입 날짜
        LocalDate createDate = LocalDate.now();
        userDto.setCreateDate(createDate);
        if (isUser(userDto.getEmail()) == false) {
            // 암호화
            String pwd = pwdEncoder.encode(userDto.getPw());
            userDto.setPw(pwd);
            // 사용자 저장
            if (userDto.getBirth_string() != null)
                userDto.setBirth(LocalDate.parse(userDto.getBirth_string())); // 생년월일 String -> LocalDate 형 변환
            userDao.saveUser(userDto, role, enabled, imageId);
            System.out.println("사용자를 저장했습니다.");
            return true; // 첫 회원가입
        } else {
            System.out.println("이미 회원가입된 사용자입니다.");
            return true; // 이미 가입된 회원
        }
    }

    /**
     * 이미 회원가입한 사용자인지 확인
     */
    public boolean isUser(String email) throws UsernameNotFoundException {
        try { // 회원가입 했다면
            UserDto userByEmail = userDao.findUserByEmail(email);
            System.out.println(userByEmail.getEmail());
            return true;
        } catch (Exception e) { // 회원가입 안 했다면
            return false;
        }
    }

    /**
     * 이메일로 사용자 정보 가져오기
     */
    public UserDto findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    /**
     * 전화번호로 사용자 정보 가져오기
     */
    public UserDto findUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    /**
     * 사용자 이미지 저장하기
     */
    public int saveImageAndGetImageId(UserDto userDto) {
        int imageId = 0;
        if (userDto.getImage() != null && !userDto.getImage().isEmpty()) {
            try {
                /** 사진 업로드 */
                if (!userDto.getImage().isEmpty()) {
                    imageId = fileService.saveImage(userDto.getImage());// 이미지 저장
                }
            } catch (IOException e) {
                System.out.println("이미지 업로드를 실패했습니다.");
            }
        } else {
            System.out.println("이미지를 선택하지 않았습니다.");
        }
        return imageId;
    }

    /**
     * 비밀번호 변경
     */
    public void updatePw(String email, String pw) {
        // 암호화
        String pwd = pwdEncoder.encode(pw);
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPw(pwd);
        userDao.updatePw(userDto);
    }

    /**
     * 사용자 정보 변경
     */
    public void updateUser(UserDto userDto) throws IOException {
        // 사용자 정보 업데이트 (이미지 제외)
        userDto.setBirth(LocalDate.parse(userDto.getBirth_string()));
        userDao.updateUser(userDto);

        if (userDto.getImage() != null) {
            // 업데이트된 정보를 토대로 이미지 아이디 가져오기
            int imageId = userDao.findUserByEmail(userDto.getEmail()).getImageId();

            // MultipartFile -> ImageDto 변환
            MultipartFile imageFile = userDto.getImage();
            String name = imageFile.getOriginalFilename(); // 파일 이름
            String type = imageFile.getContentType(); // 파일 타입
            byte[] picByte = compressBytes(imageFile.getBytes()); // 문자열 압축

            ImageDto imageDto = new ImageDto(imageId, name, type, picByte); // ImageDto 생성
            userDao.updateImage(imageDto); // 이미지 정보 업데이트
        }
    }

    /**
     * 사용자의 권한을 리턴
     */
    public String userRoleCheck() {
        // Spring Security에서 현재 사용자의 권한을 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        System.out.println("is user? " + hasUserRole);
        System.out.println("is admin? " + hasAdminRole);

        if (hasUserRole) { // Member 권한 유저
            return "ROLE_USER";
        } else if (hasAdminRole) { // Admin 권한 유저
            return "ROLE_ADMIN";
        } else { // Guest 권한 유저
            return "ROLE_GUEST";
        }
    }

    /**
     * 변수 role에 대해 사용자가 자격이 있는지 확인
     */
    public boolean hasAccess(String url, String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 사용자가 인증되었는지 확인
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role))) {
                    // 사용자가 해당 역할을 가지고 있는 경우
                    return true;
                }
            }
        }

        // 사용자가 인증되지 않았거나 해당 역할을 가지고 있지 않은 경우
        return false;
    }


//    /**
//     * 일일 회원가입자 수
//     */
//    public List<ChartDto> countSignUpByDay(List<YearMonth> get5Months) {
//
//    }
//
//    /**
//     * 일일 로그인 수
//     */
//    public List<ChartDto> countSignInByDay(List<YearMonth> get5Months) {
//    }

    /**
     * 달별 회원가입자 수
     */
    public List<ChartDto> countSignUpByMonth(List<YearMonth> get5Months) {
        List<ChartDto> signUpCounts = new ArrayList<>();
        Map<LocalDate, LocalDate> months = yearMonthToLocalDate(get5Months); // 달마다 시작 날짜와 끝 날짜 가져오기
        for (Map.Entry<LocalDate, LocalDate> entry : months.entrySet()) {
            String date = entry.getKey().getYear() + "년 " + entry.getKey().getMonthValue() + "월"; // LocalDate -> String
            int countUsers = userDao.countSignUpByMonth(entry.getKey(), entry.getValue());// 시작 날짜와 끝 날짜를 기준으로 세기
            ChartDto chartDto = new ChartDto(); chartDto.setDate(date); chartDto.setCount(countUsers);
            signUpCounts.add(chartDto);
        }
        return signUpCounts;
    }

    /**
     * 달별 로그인자 수
     */
    public List<ChartDto> countSignInByMonth(List<YearMonth> get5Months) {
        List<ChartDto> signInCounts = new ArrayList<>();
        Map<LocalDate, LocalDate> months = yearMonthToLocalDate(get5Months); // 달마다 시작 날짜와 끝 날짜 가져오기
        for (Map.Entry<LocalDate, LocalDate> entry : months.entrySet()) {
            String date = entry.getKey().getYear() + "년 " + entry.getKey().getMonthValue() + "월"; // LocalDate -> String
            int countUsers = userDao.countSignInByMonth(entry.getKey(), entry.getValue());// 시작 날짜와 끝 날짜를 기준으로 세기
            ChartDto chartDto = new ChartDto(); chartDto.setDate(date); chartDto.setCount(countUsers);
            signInCounts.add(chartDto);
        }
        return signInCounts;
    }

    /**
     * List<yearMonth> 타입 -> List<LocalDate> 타입 형변환
     */
    private static Map<LocalDate, LocalDate> yearMonthToLocalDate(List<YearMonth> get5Months) {
        Map<LocalDate, LocalDate> localDates = new HashMap<>();
        for (YearMonth yearMonth : get5Months) {
            int year = yearMonth.getYear();
            int month = yearMonth.getMonthValue();
            int lastDayOfMonth = yearMonth.lengthOfMonth();
            // 해당 월의 시작 날짜
            LocalDate startDate = LocalDate.of(year, month, 1);
            // 해당 월의 마지막 날짜
            LocalDate endDate = LocalDate.of(year, month, lastDayOfMonth);
            localDates.put(startDate, endDate);
        }
        return localDates;
    }

    /** now를 기준으로 numberOfMonths 개수의 년도와 월을 리스트로 반환 */
    public List<YearMonth> findPast5Months(int numberOfMonths) {
        List<YearMonth> pastMonths = new ArrayList<>();
        // 현재 날짜의 년도와 월 정보 가져오기
        int currentYear = LocalDate.now().getYear();
        Month currentMonth = LocalDate.now().getMonth();
        // 현재 월부터 numberOfMonths 개수만큼 이전 월을 찾아서 리스트에 추가
        for (int i = 0; i < numberOfMonths; i++) {
            pastMonths.add(YearMonth.of(currentYear, currentMonth)); // 리스트에 년도와 월을 추가
            currentMonth = currentMonth.minus(1); // 이전 월로 이동
            if (currentMonth == Month.JANUARY) { // 현재 월이 1월이면 년도도 이전으로 이동
                currentYear--;
                currentMonth = Month.DECEMBER;
            }
        }
        return pastMonths;
    }

    /** 관리자 페이지 - 모든 사용자 리스트 가져오기 */
    public List<UserDto> findAllUsers() {
        return userDao.findAllUsers();
    }

    /** 가장 최근 이미지 아이디 가져오기 */
    public int findLastImageId() {
        return userDao.findLastImageId();
    }

    /** 엑셀 파일로 사용자 저장 시 null 이미지 데이터 저장 */
    public int saveNullImageAndGetImageId() {
        fileService.saveNullImage();
        return userDao.findLastImageId();
    }

    public List<String> findDormantUsers(Date dormantThresholdDate) {
        return userDao.findDormantUsers(dormantThresholdDate);
    }

    public void updateEnabled(String email) {
        userDao.updateEnabled(email);
    }

    public void updateUserLastActivityDate(String email, Date currentActivityDate) {
        userDao.updateUserLastActivityDate(email, currentActivityDate);
    }
}
