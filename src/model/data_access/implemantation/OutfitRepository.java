package model.data_access.implemantation;

import model.data_access.OutfitRepositoryInterface;
import model.domain.Outfit;
import java.util.List;

public class OutfitRepository implements OutfitRepositoryInterface {
	
	private InputOutput io;
	private List<Outfit> outfitList;
	
	public OutfitRepository(InputOutput io, UserRepository userRepository) {
		this.io = io;
		outfitList = io.inputOutfits(userRepository);
	}

	public List<Outfit> findAll(){
		return outfitList;
	}

	public void outputOutfits(){
		io.outputOutfits(outfitList);
	}
	
	public void setOutfits(List<Outfit> outfits) {
		this.outfitList = outfits;
	}


	public Outfit findOutfitById(int outfitId) throws IllegalStateException {
		for (Outfit outfit : outfitList) {
			if (outfit.getId() == outfitId) {
				return outfit;
			}
		}
		throw new IllegalStateException("No outfit found with the given id.");
	}

	public Outfit getOutfitWithMostLikes() {
		Outfit mostLikedOutfit = null;
		for (Outfit outfit : outfitList) {
			if (mostLikedOutfit == null) {
				mostLikedOutfit = outfit;
			} else {
				if (mostLikedOutfit != null && outfit.getLikes() > mostLikedOutfit.getLikes()) {
					mostLikedOutfit = outfit;
				}
			}
		}
		return mostLikedOutfit;
	}

	public Outfit getOutfitWithMostDislikes() {
		Outfit mostDislikedOutfit = null;
		for (Outfit outfit : outfitList) {
			if (mostDislikedOutfit == null) {
				mostDislikedOutfit = outfit;
			} else {
				if (mostDislikedOutfit != null && outfit.getDislikes() > mostDislikedOutfit.getDislikes()) {
					mostDislikedOutfit = outfit;
				}
			}
		}
		return mostDislikedOutfit;
	}
	
}