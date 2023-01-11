
public class TestAvl {

	public static void main(String[] args) {
		AVL<Integer> b = new AVL<Integer>();
		
		try {
			b.insert(90);
			b.insert(80);
			b.insert(70);
			b.insert(60);
			b.insert(50);
			
			System.out.println(b);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		

	}

}
