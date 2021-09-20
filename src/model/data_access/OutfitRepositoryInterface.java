package model.data_access;

import model.domain.Outfit;

import java.util.List;

public interface OutfitRepositoryInterface {

    List<Outfit> findAll();

    void outputOutfits();

    void setOutfits(List<Outfit> outfits);

    Outfit findOutfitById(int outfitId) throws IllegalStateException;

    Outfit getOutfitWithMostLikes();

    Outfit getOutfitWithMostDislikes();
}
