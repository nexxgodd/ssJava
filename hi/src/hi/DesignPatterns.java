package hi;

public class DesignPatterns {

	public static void main(String[] args) {
		
		
		
		new Thread(()->{
			System.out.println("f");
		}).start();

		Thread thread = 
		new Thread(()-> {
			for(int i=0; i<100; i++) {
				System.out.println(i);
			}
		});
		thread.start();
		
		
		System.out.println("wwwwwwwwwwww");
		
		
		
		String A= "apple";
		String B = new StringBuilder().append(A).reverse().toString();
		System.out.println(A);
		System.out.println(B);
		
		
		StringBuilder C = new StringBuilder(A).reverse();
		System.out.println(C);
	}

}
