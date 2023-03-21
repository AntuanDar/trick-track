package ru.spb.taranenkoant.trick.track.client.dao;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class LoginDao extends BaseDao {

    LoginDao() {
    }

    LoginDao(DaoParams params) {
        super(params);
    }

//    public UserContext getUserContext() {
//        UserContext userContext = getForObject(buildUri("me"), UserContext.class);
//        return userContext;
//    }
//
//    public LoginStatus login(String login, String password) {
//        UserDTO user = new UserDTO();
//        user.setLogin(login);
//        user.setPass(password);
//        return login(user);
//    }

}
