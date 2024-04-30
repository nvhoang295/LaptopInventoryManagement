package abc.objects;

public class ProductStatusObject {
	private String product_id;
	private String color_id;
	private int remained;

	public ProductStatusObject() {
		super();
	}

	public ProductStatusObject(String product_id, String color_id, int remained) {
		super();
		this.product_id = product_id;
		this.color_id = color_id;
		this.remained = remained;
	}

	public String getProduct_id() {
		return product_id;
	}

	public String getColor_id() {
		return color_id;
	}

	public int getRemained() {
		return remained;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}

	public void setRemained(int remained) {
		this.remained = remained;
	}

}
