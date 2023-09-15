package com.example.choyoujin.Service;

import com.example.choyoujin.DAO.FileDao;
import com.example.choyoujin.DTO.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class FileService {
    @Autowired
    FileDao fileDao;

    /** 사용자 이미지 저장 */
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
    public int findIdByEmail(String email) {
        return fileDao.findIdByEmail(email);
    }

    /** 문자열 압축 */
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

    /** 문자열 압축 풀기 */
    public static byte[] decompressBytes(byte[] data) {
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

        return outputStream.toByteArray();
    }

}
