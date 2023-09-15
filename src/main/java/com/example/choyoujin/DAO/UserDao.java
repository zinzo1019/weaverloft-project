package com.example.choyoujin.DAO;

import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

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
}
