package entity;

import java.sql.Date;

public class Ingredient {
	//nguyên liệu
	private String ingredientId;
	private String ingredientName;
	private double quantity;
	private String description;
	private Date dateOfEntry;
	private Date expirationDate;
	private Unit unit;
	private double price;
	public Object[] toRow() {
		return new Object[] {ingredientId, ingredientName,quantity,description,dateOfEntry,expirationDate,unit==Unit.GRAM?"Gram":"Liter",price};
	}
	public Ingredient() {
		super();
	}
	public Ingredient(String ingredientId) {
		super();
		this.ingredientId = ingredientId;
	}
	
	public Ingredient(String ingredientId, String ingredientName, double quantity, String description,
			Date dateOfEntry, Date expirationDate, Unit unit, double price) {
		super();
		this.ingredientId = ingredientId;
		this.ingredientName = ingredientName;
		this.quantity = quantity;
		this.description = description;
		this.dateOfEntry = dateOfEntry;
		this.expirationDate = expirationDate;
		this.unit = unit;
		this.price = price;
	}
	public String getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Ingredient [ingredientId=" + ingredientId + ", ingredientName=" + ingredientName + ", quantity="
				+ quantity + ", description=" + description + ", dateOfEntry=" + dateOfEntry + ", expirationDate="
				+ expirationDate + ", unit=" + unit + ", price=" + price + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingredientId == null) ? 0 : ingredientId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (ingredientId == null) {
			if (other.ingredientId != null)
				return false;
		} else if (!ingredientId.equals(other.ingredientId))
			return false;
		return true;
	}
	

}
