package huce.edu.vn.appdocsach.utils;

import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;

public class NamingUtil {
    public static String normalizeFolderName(CreateChapterDto createChapterDto) {
        StringBuilder builder = new StringBuilder();
        builder.append(createChapterDto.getBookId())
                .append(AppConst.DELIMITER_IN_CHAPTER_FOLDER_NAME)
                .append(createChapterDto.getTitle()
                    .replace(" ", "")
                    .toLowerCase());
        return builder.toString();
    }

    public static String hideLetter(String value) {
        if (value.length() < 3) {
            return value;
        }
        int middleIndex = value.length() / 2;
        return value.substring(0, middleIndex) + "*".repeat(value.length() - middleIndex);
    }
}
