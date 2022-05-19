package swimclub;

import membership.Enum;

public class User {
    private String name;
    private String password;
    private Enum.UserType userType;

    public User(String name, String password, Enum.UserType userType) {
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Enum.UserType getUserType() {
        return userType;
    }
}
