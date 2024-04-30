package abc.objects;

public class ImportBillObject {
	private String import_bill_id;
	private String when_created;
	private String deliver_name;
	private double total;
	private String note;
	private String receiver_id;
	private String provider_id;

	public ImportBillObject() {
		super();
	}

	public ImportBillObject(String import_bill_id, String when_created, String deliver_name, double total, String note,
			String receiver_id, String provider_id) {
		super();
		this.import_bill_id = import_bill_id;
		this.when_created = when_created;
		this.deliver_name = deliver_name;
		this.total = total;
		this.note = note;
		this.receiver_id = receiver_id;
		this.provider_id = provider_id;
	}

	public String getImport_bill_id() {
		return import_bill_id;
	}

	public String getWhen_created() {
		return when_created;
	}

	public String getDeliver_name() {
		return deliver_name;
	}

	public double getTotal() {
		return total;
	}

	public String getNote() {
		return note;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public String getProvider_id() {
		return provider_id;
	}

	public void setImport_bill_id(String import_bill_id) {
		this.import_bill_id = import_bill_id;
	}

	public void setWhen_created(String when_created) {
		this.when_created = when_created;
	}

	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
	}
	
}
