package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper // 다음 인터페이스의 구현을 xml로 할 것이다
public interface UserDao {
    List<UserDto> list();
    UserDto findUserByEmail(String email);
    void saveUser(UserDto userDto, String role, int enabled, int imageId);
    void updatePw(UserDto userDto);
    Map<String, Object> findUserByPhone(String phone);
    void updateUser(UserDto userDto);

    void updateImage(ImageDto image);
    int countSignUpByMonth(LocalDate startDate, LocalDate endDate);

    int countSignInByMonth(YearMonth month);

    int countSignInByMonth(LocalDate startDate, LocalDate endDate);

    List<UserDto> findAllUsers(); // 모든 사용자 리스트 가져오기

    int findLastImageId(); // 가장 최근 이미지 아이디 가져오기

    void saveNullImage(); // null 이미지 저장하기

    List<String> findDormantUsers(Date dormantThresholdDate); // 60일 이상 활동이 없는 사용자 조회

    void updateUserLastActivityDate(String email, Date lastActivityDate);

    void updateEnabled(String email); // 휴면 처리
}
