package beans;

public class Home {
	private int id;
	private String address;
	private int price;
	
	public Home() {
		
	}
	public Home(int id, String address, int price) {
		super();
		this.id = id;
		this.address = address;
		this.price = price;
	}
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
//	Robert'); DROP TABLE Students;--
}
