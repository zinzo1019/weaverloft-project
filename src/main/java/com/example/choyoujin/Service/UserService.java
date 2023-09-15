package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.UserDao;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.example.choyoujin.Service.FileService.compressBytes;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder pwdEncoder;
    @Autowired
    private FileService fileService;

    public boolean saveUser(UserDto userDto, String role, int enabled, int imageId) {
        if (isUser(userDto.getEmail()) == false) {
            // 암호화
            String pwd = pwdEncoder.encode(userDto.getPw());
            userDto.setPw(pwd);
            // 사용자 저장
            userDao.saveUser(userDto, role, enabled, imageId);
            System.out.println("사용자를 저장했습니다.");
            return true; // 첫 회원가입

        } else {
            System.out.println("이미 회원가입된 사용자입니다.");
            return true; // 이미 가입된 회원
        }
    }

    /** 이미 회원가입한 사용자인지 확인 */
    public boolean isUser(String email) throws UsernameNotFoundException {
        try { // 회원가입 했다면
            UserDto userByEmail = userDao.findUserByEmail(email);
            System.out.println(userByEmail.getEmail());
            return true;
        } catch (Exception e) { // 회원가입 안 했다면
            return false;
        }
    }

    /** 이메일로 사용자 정보 가져오기 */
    public UserDto findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    /** 전화번호로 사용자 정보 가져오기 */
    public Map<String, Object> findUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    /** 사용자 이미지 저장하기 */
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

    /** 비밀번호 변경 */
    public void updatePw(String email, String pw) {
        // 암호화
        String pwd = pwdEncoder.encode(pw);
        UserDto userDto = new UserDto();
        userDto.setEmail(email); userDto.setPw(pwd);
        userDao.updatePw(userDto);
    }

    /** 사용자 정보 변경 */
    public void updateUser(UserDto userDto) throws IOException {
        // 사용자 정보 업데이트 (이미지 제외)
        userDao.updateUser(userDto);
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

    /** 사용자의 권한을 리턴 */
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

    /** 변수 role에 대해 사용자가 자격이 있는지 확인 */
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


}
