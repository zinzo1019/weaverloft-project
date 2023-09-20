package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.FileDao;
import com.example.choyoujin.DTO.FileDto;
import com.example.choyoujin.DTO.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class FileService {
    @Autowired
    FileDao fileDao;

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

    /** 댓글 삭제하기 */
    public void deleteCommentById(int id) {
        fileDao.deleteCommentById(id);
    }
}
