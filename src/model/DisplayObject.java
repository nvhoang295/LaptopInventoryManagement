package abc.objects;

public class DisplayObject {
	private String product_id;
	private String size;
	private String resolution;
	private int refresh_rate;
	private String color_gamut;
	private String panel_type;
	private boolean is_touch_screen;

	public DisplayObject() {
		super();
	}

	public DisplayObject(String product_id, String size, String resolution, int refresh_rate, String color_gamut,
			String panel_type, boolean is_touch_screen) {
		super();
		this.product_id = product_id;
		this.size = size;
		this.resolution = resolution;
		this.refresh_rate = refresh_rate;
		this.color_gamut = color_gamut;
		this.panel_type = panel_type;
		this.is_touch_screen = is_touch_screen;
	}

	public String getProduct_id() {
		return product_id;
	}

	public String getSize() {
		return size;
	}

	public String getResolution() {
		return resolution;
	}

	public int getRefresh_rate() {
		return refresh_rate;
	}

	public String getColor_gamut() {
		return color_gamut;
	}

	public String getPanel_type() {
		return panel_type;
	}

	public boolean isIs_touch_screen() {
		return is_touch_screen;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public void setRefresh_rate(int refresh_rate) {
		this.refresh_rate = refresh_rate;
	}

	public void setColor_gamut(String color_gamut) {
		this.color_gamut = color_gamut;
	}

	public void setPanel_type(String panel_type) {
		this.panel_type = panel_type;
	}

	public void setIs_touch_screen(boolean is_touch_screen) {
		this.is_touch_screen = is_touch_screen;
	}

}
