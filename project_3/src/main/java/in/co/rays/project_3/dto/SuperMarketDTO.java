package in.co.rays.project_3.dto;

public class SuperMarketDTO extends BaseDTO {

	private String productName;
	private long quantity;
	private String available;
	private String price;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	// 🔥 REQUIRED FOR HIBERNATE
	public String getName() {
		return productName;
	}

	public void setName(String name) {
		this.productName = name;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return productName;
	}
}
