package entity;

import java.util.Objects;

public class Drink {
    private String drinkId;
    private String drinkName;
    private String image;
    private String description;
    private double price;
    private String categoryId;
    private double vat;
    
	public Drink() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Drink(String drinkId, String drinkName, String image, String description, double price, String categoryId,
			double vat) {
		super();
		this.drinkId = drinkId;
		this.drinkName = drinkName;
		this.image = image;
		this.description = description;
		this.price = price;
		this.categoryId = categoryId;
		this.vat = vat;
	}
	public String getDrinkId() {
		return drinkId;
	}
	public void setDrinkId(String drinkId) {
		this.drinkId = drinkId;
	}
	public String getDrinkName() {
		return drinkName;
	}
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	@Override
	public int hashCode() {
		return Objects.hash(drinkId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drink other = (Drink) obj;
		return Objects.equals(drinkId, other.drinkId);
	}
    
    // Constructor, getter, setter
}

