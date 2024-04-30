package abc.objects;

public class BatteryObject {
	private String product_id;
	private float capacity;
	private float changer_capacity;

	public BatteryObject() {
		super();
	}

	public BatteryObject(String product_id, float capacity, float changer_capacity) {
		super();
		this.product_id = product_id;
		this.capacity = capacity;
		this.changer_capacity = changer_capacity;
	}

	public String getProduct_id() {
		return product_id;
	}

	public float getCapacity() {
		return capacity;
	}

	public float getChanger_capacity() {
		return changer_capacity;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public void setChanger_capacity(float changer_capacity) {
		this.changer_capacity = changer_capacity;
	}

}
