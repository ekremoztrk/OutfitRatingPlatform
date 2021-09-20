package model.data_access;

import model.data_access.implemantation.UserRepository;
import model.domain.Outfit;
import model.domain.User;

import java.util.List;

public interface InputOutputInterface {

    List<User> getUsers();

    List<Outfit> getOutfits();

    void outputOutfits(List<Outfit> outfits);

    List<Outfit> inputOutfits(UserRepository userRepository);

    void xmlOutput(List<User> users);

    List<User> inputUsers();

}
