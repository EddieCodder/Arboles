
public class Test {

	public static void main(String[] args) {
		BST<Integer> b2 = new BST<Integer>();
		BST<Integer> b = new BST<Integer>();
		
		try {
//			b.insert(30);
//			b.insert(40);
//			b.insert(10);
//			b.insert(6);
//			b.insert(19);
//			b.insert(35);
//			b.insert(12);
//			b.insert(7);
//			b.insert(42);
//			b.insert(21);
//			System.out.println(b);
//			b.insert(21);
			b.insert(7);
			b.insert(2);
			b.insert(9);
			b.insert(1);
			b.insert(5);
			b.insert(3);
			System.out.println(b);

			System.out.println("\nHACIENDO LA ELIMINACIÓN\n");
			
			b.remove(7);
			b.remove(1);
			System.out.println(b);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\nCOMPROBAR SI ES BST\n"+b.verificarBST(b.getRoot()));
		
		
		
//		BSTDictionary<Integer, String> b = new BSTDictionary<Integer, String>();
//		
//
//		try {
//			b.insert(30, "Carlos");
//			b.insert(40, "Luisa");
//			b.insert(10, "Maria");
//			b.insert(6, "Julio");
//			b.insert(19, "Patty");
//			b.insert(35, "Marco");
//			b.insert(14, "Javier");
//			b.insert(7, "Angela");
//			b.insert(12, "Jose");
//			b.insert(21, "Pedro");
//			System.out.println(b);
//			System.out.println("Valor de 12? " + b.search(12));
//			System.out.println("\nELIMINACIÓN DE 30");
//			b.remove(30);
//			System.out.println(b);
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		
	}
}
