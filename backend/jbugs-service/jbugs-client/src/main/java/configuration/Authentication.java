package configuration;

public class Authentication {
    private String loggedInUsername;

    private static Authentication authentication;

    public static Authentication getAuthentication(){
        return authentication;
    }


    public void login(String username) {
        loggedInUsername = username;
    }

    public void logout() {
        loggedInUsername = null;
    }

    public String getLoggedInUsername(){
        return loggedInUsername;
    }
}
