package model.data_access;

import model.domain.User;

import java.util.List;

public interface UserRepositoryInterface {

    void outputUsers();

    List<User> findAll();

    void setUsers(List<User> users);

    User findUserByUsername(String username);

    User findUserById(int userId);

    User getUserWithMostFollowers();
}
