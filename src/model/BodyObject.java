package abc.objects;

public class BodyObject {
	private String product_id;
	private float width;
	private float height;
	private float depth;
	private String material;
	private float weight;
	private String backlit_keyboard;
	private String durability;

	public BodyObject() {
		super();
	}

	public BodyObject(String product_id, float width, float height, float depth, String material, float weight,
			String backlit_keyboard, String durability) {
		super();
		this.product_id = product_id;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.material = material;
		this.weight = weight;
		this.backlit_keyboard = backlit_keyboard;
		this.durability = durability;
	}

	public String getProduct_id() {
		return product_id;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getDepth() {
		return depth;
	}

	public String getMaterial() {
		return material;
	}

	public float getWeight() {
		return weight;
	}

	public String getBacklit_keyboard() {
		return backlit_keyboard;
	}

	public String getDurability() {
		return durability;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setBacklit_keyboard(String backlit_keyboard) {
		this.backlit_keyboard = backlit_keyboard;
	}

	public void setDurability(String durability) {
		this.durability = durability;
	}

}
