package huce.edu.vn.appdocsach.constants;

public class AvatarSaver {

    private static String avatarUrl;

    public static String getAvatarUrl() {
        return avatarUrl;
    }

    public static void setAvatarUrl(String avatarUrl) {
        AvatarSaver.avatarUrl = avatarUrl;
    }    
}