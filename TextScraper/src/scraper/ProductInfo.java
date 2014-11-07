package scraper;

/**
 * 
 * @author wenbolin
 *
 */
public class ProductInfo {
	
	private String name;
	private double price;
	private String vendor;
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * 
	 * @param vendor
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getVendor() {
		return this.vendor;
	}
}
