package abc.objects;

public class ImportedProductObject {
	private String import_product_id;
	private String import_bill_id;
	private String product_id;
	private int quantity;
	private double import_price;

	public ImportedProductObject() {
		super();
	}

	public ImportedProductObject(String import_product_id, String import_bill_id, String product_id, int quantity,
			double import_price) {
		super();
		this.import_product_id = import_product_id;
		this.import_bill_id = import_bill_id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.import_price = import_price;
	}

	public String getImport_product_id() {
		return import_product_id;
	}

	public String getImport_bill_id() {
		return import_bill_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getImport_price() {
		return import_price;
	}

	public void setImport_product_id(String import_product_id) {
		this.import_product_id = import_product_id;
	}

	public void setImport_bill_id(String import_bill_id) {
		this.import_bill_id = import_bill_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setImport_price(double import_price) {
		this.import_price = import_price;
	}

}
