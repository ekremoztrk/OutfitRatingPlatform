package model.domain;

import model.utilities.Observer;
import model.utilities.Subject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Collection implements Subject {

	private int id;
	private String name;
	private User creator;
	private Date creationDate;
	private List<Outfit> outfits;
	private boolean changed;
	private List<Observer> observers;
	private static int idCounter = 10;

	public Collection(int id, String name, User creator) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.creationDate = new Date();
		this.outfits = new ArrayList<Outfit>();;
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}
	public Collection(String name, User creator) {
		this.id = idCounter;
		idCounter++;
		this.name = name;
		this.creator = creator;
		this.creationDate = new Date();
		this.outfits = new ArrayList<Outfit>();;
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}
	public Collection(int id, String name, User creator, Date date, List<Outfit> outfits) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.creationDate = date;
		this.outfits = outfits;
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}

	public void addOutfit(Outfit outfit) throws IllegalStateException {
		List<Outfit> outfits = getOutfits();
		if (outfits.contains(outfit)) {
			throw new IllegalStateException("Collection already contains outfit.");
		}
		outfits.add(outfit);
		setOutfits(outfits);
	}

	public void removeOutfit(Outfit outfit) throws IllegalStateException {
		List<Outfit> outfits = getOutfits();
		if (!outfits.contains(outfit)) {
			throw new IllegalStateException("Collection does not contain outfit.");
		}
		outfits.remove(outfit);
		setOutfits(outfits);
	}

	public Outfit findOutfitById(int id) {
		List<Outfit> outfits = getOutfits();
		for (Outfit outfit : outfits) {
			if (outfit.getId() == id) {
				return outfit;
			}
		}
		return null;
	}
	
	// GETTERS AND SETTERS
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		setChanged(true);
		notifyObservers();
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public List<Outfit> getOutfits() {
		return outfits;
	}

	public void setOutfits(List<Outfit> outfits) {
		setChanged(true);
		notifyObservers();
		this.outfits = outfits;
	}
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	// OBSERVATION METHODS

	@Override
	public void register(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.add(obj);
			setObservers(observers);
		}
	}

	@Override
	public void unregister(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.remove(obj);
			setObservers(observers);
		}
	}

	@Override
	public void notifyObservers() {
		if (!isChanged()) {
			return;
		}
		setChanged(false);
		List<Observer> observers = getObservers();
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
