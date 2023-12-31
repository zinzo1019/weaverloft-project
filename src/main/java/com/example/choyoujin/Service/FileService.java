package com.example.choyoujin.Service;

import com.example.choyoujin.ApiResponse;
import com.example.choyoujin.DAO.FileDao;
import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import com.example.choyoujin.DTO.UserDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class FileService {
    @Autowired
    FileDao fileDao;
    @Autowired
    ValidationService validService;
    @Autowired
    UserService userService;

    /**
     * 사용자 이미지 저장
     */
    public int saveImage(MultipartFile file) throws IOException {
        try {
            String name = file.getOriginalFilename(); // 파일 이름
            String type = file.getContentType(); // 파일 타입
            byte[] picByte = compressBytes(file.getBytes()); // 문자열 압축
            ImageDto imageDto = new ImageDto(name, type, picByte); // ImageDto 생성
            fileDao.saveImage(imageDto); // 이미지 저장
            return imageDto.getId(); // 이미지를 저장하고 기본키가 리턴됨
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 실패 시 -1 리턴
        }
    }

    public ImageDto findImageById(int id) {
        return fileDao.findImageById(id);
    }

    int findIdByEmail(String email) {
        return fileDao.findIdByEmail(email);
    }

    /**
     * 파일 다운로드
     */
    public void downloadFile(HttpServletResponse response, HttpServletRequest request, FileDto fileDto, File file) {
        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try {
            String downName = null;
            String browser = request.getHeader("User-Agent");
            //파일 인코딩
            if (browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {//브라우저 확인 파일명 encode
                downName = URLEncoder.encode(fileDto.getName(), "UTF-8").replaceAll("\\+", "%20");

            } else {
                downName = new String(fileDto.getName().getBytes("UTF-8"), "ISO-8859-1");
            }

            response.setHeader("Content-Disposition", "attachment;filename=\"" + downName + "\"");
            response.setContentType("application/octer-stream");
            response.setHeader("Content-Transfer-Encoding", "binary;");

            fileInputStream = new FileInputStream(file);
            servletOutputStream = response.getOutputStream();

            byte b[] = new byte[1024];
            int data = 0;

            while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
                servletOutputStream.write(b, 0, data);
            }

            servletOutputStream.flush();//출력

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 문자열 압축
     */
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);

            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        return outputStream.toByteArray();
    }

    /**
     * 문자열 압축 풀기 & 인코딩
     */
    public static String decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray()); // img로 띄우기 위해 인코딩
    }

    /**
     * 이미지 삭제하기
     */
    public void deleteImageById(int id) {
        fileDao.deleteImageById(id);
    }

    /**
     * 파일 삭제하기
     */
    public void deltetFileById(int id) {
        fileDao.deleteFileById(id);
    }

    /**
     * 댓글 삭제하기
     */
    public void deleteCommentById(int id) {
        fileDao.deleteCommentById(id);
    }

    /**
     * null 사진 저장하기
     */
    public void saveNullImage() {
        fileDao.saveNullImage();
    }

    /**
     * 엑셀 파일 -> 사용자 저장
     */
    public ResponseEntity<ApiResponse> saveExcelUsersAndReturnResponse(MultipartFile file) {
        InputStream inputStream = null;
        Workbook workbook = null;

        try {
            inputStream = file.getInputStream();
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 엑셀 파일의 첫 번째 시트를 가져옴

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 각 셀에서 데이터 추출
                String email = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String pw = row.getCell(2).getStringCellValue();
                String address = row.getCell(4).getStringCellValue();
                String gender = row.getCell(6).getStringCellValue();

                // 전화번호와 날짜
                Cell phoneCell = row.getCell(3), birthCell = row.getCell(5);
                String phone = null;
                LocalDate birth = null;
                phone = validService.isValidPhone(phoneCell);
                birth = validService.isValidBirth(birthCell, birth);

                if (!validService.isValidEmail(email)) { // 가입한 적 없는 회원
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("이미 가입된 이메일입니다.")); }
                // 각 필드의 유효성 검사를 수행 (예: 이메일 형식, 전화번호 형식 등)
                else if (phone == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("전화번호는 하이픈을 포함한 13자리여야 합니다."));
                } else if (!validService.isValidName(name)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("이름 형식을 확인하세요. (최소 2글자 - 최대 50글자)"));
                } else if (!validService.isValidGender(gender)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("성별은 '여자' 또는 '남자'로 입력해주세요."));
                } else if (birth == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("생년월일 형식을 yyyy-mm-dd에 맞춰주세요."));
                } else { // 가입한 적 있는 회원이라면
                    UserDto userDto = new UserDto(email, name, gender, pw, birth, address, phone);
                    int imageId = userService.saveNullImageAndGetImageId(); // null 이미지 저장
                    userService.saveUser(userDto, "ROLE_USER", 1, imageId + 1); // 사용자 데이터 저장
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("예외 발생: " + e.getMessage()));
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(new ApiResponse("사용자를 저장했습니다."));
    }
}
