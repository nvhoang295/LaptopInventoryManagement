package abc.objects;

public class ColorObject {
	private String color_id;
	private String name;
	private String product_id;

	public ColorObject() {
		super();
	}

	public ColorObject(String color_id, String name, String product_id) {
		super();
		this.color_id = color_id;
		this.name = name;
		this.product_id = product_id;
	}

	public String getColor_id() {
		return color_id;
	}

	public String getName() {
		return name;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

}
