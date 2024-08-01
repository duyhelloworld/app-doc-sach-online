package huce.edu.vn.appdocsach.services.impl.auth.users;

import java.util.Map;

import huce.edu.vn.appdocsach.entities.TokenProvider;

public abstract class OAuthFactory {

    public static SupportedOAuth2User create(TokenProvider provider, Map<String, Object> attributes) {
        switch (provider) {
            case GOOGLE:
                return new GoogleUser(attributes);
            case GITHUB:
                return new GithubUser(attributes);
            default:
                return null;
        }
    }
    
}
