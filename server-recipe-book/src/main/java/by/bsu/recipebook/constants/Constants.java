package by.bsu.recipebook.constants;

import java.io.File;

public class Constants {
    public static final String API_ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification";

    public static final String COMPANY_EMAIL = "recipe.book@gmail.com";

    public static final String AUTHORIZATION = "Authorization";

    public static final String PATH_TO_UPLOADED_FILES_APP =
            "d:" + File.separator + "uploaded-files" + File.separator + "recipe-system";

    public static final String RECIPE_UPLOAD_IMAGES_DIR = "recipe-images";

    public static final String USER_UPLOAD_IMAGES_DIR = "user-images";

    public static final String DATE_TIME_PATTERN = "yyMMddHHmmss-";

    public static final String DEFAULT_AVATAR_LOCATION =
            "d:" + File.separator + "uploaded-files" + File.separator + "recipe-system" +
                    File.separator + "user-images" + File.separator + "default-avatar.png";
}
