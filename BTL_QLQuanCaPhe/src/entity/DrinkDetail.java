package entity;

import java.util.Objects;

public class DrinkDetail {
	
	private Ingredient ingredient;
	private Drink drink;
	private double quantity;
	
	public DrinkDetail() {
		// TODO Auto-generated constructor stub
	}

	public DrinkDetail(Ingredient ingredient, Drink drink, double quantity) {
		super();
		this.ingredient = ingredient;
		this.drink = drink;
		this.quantity = quantity;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Drink getDrink() {
		return drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "DrinkDetail [ingredient=" + ingredient + ", drink=" + drink + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(drink, ingredient, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrinkDetail other = (DrinkDetail) obj;
		return Objects.equals(drink, other.drink) && Objects.equals(ingredient, other.ingredient)
				&& Double.doubleToLongBits(quantity) == Double.doubleToLongBits(other.quantity);
	}
	
	

}
