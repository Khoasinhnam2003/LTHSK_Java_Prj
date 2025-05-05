package entity;

import java.util.Objects;

public class Customer {
	//khách hàng
	
	private String customerID;
	private String customerName;
	private String phone;
	private int point;
	public Object[] toRow() {
		// TODO Auto-generated method stub
		return new Object[] {customerID,customerName,point,phone};
	}
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	public Customer(String customerID) {
		super();
		this.customerID = customerID;
	}
	
	public Customer(String customerID, String customerName, String phone, int point) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.point = point;
	}

	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", customerName=" + customerName + ", phone=" + phone + ", point="
				+ point + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(customerID, customerName, phone, point);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerID, other.customerID) && Objects.equals(customerName, other.customerName)
				&& Objects.equals(phone, other.phone) && point == other.point;
	}

	
	

}
