
// El árbol debe ser genérico
// El árbol debe ser ordenado, por lo tanto hay que comparar elementos
// E debe ser un elemento que pueda compararse (E:dato genérico)
public class BST<E extends Comparable<E>> {
	// Antes teníamos a nodo en otra clase
	// Pero podemos crear al nodo dentro de la clase BST
	// Estas se llaman class in line
	class Node {
		// No es necesario que Node tenga <E> Ya que esta clase (Node) está inculuida en una clase genérica
		protected E data;
		protected Node left;
		protected Node right;
		
		public Node(E data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	
		// Para los nuevos nodos hoja (No tienen hijos)
		public Node(E data) {
			// Llamamos al constructor
			this(data, null, null);
		}
	}
		
	// ATRIBUTO DE LA CLASE BST
	private Node root;
		
	// CONSTRUCTORES
	public BST() {
		this.root = null;
	}
		
	// MÉTODOS DEL ÁRBOL
	public <E extends Comparable<E>> boolean verificarBST(Node root){
		if(root.left!= null) {
			if(root.data.compareTo(root.left.data) > 0)
				verificarBST(root.left);
			else
				return false;
		}
		
		if(root.right!= null) {
			if(root.data.compareTo(root.right.data) < 0)
				verificarBST(root.right);
			else
				return false;
		}	
		return true;
	}
		// Para saber si el árbol está vacío
	public boolean isEmpty() {
		return this.root == null;
	}
	
	public Node getRoot() {
		return this.root;
	}
	
	public void insert(E x) throws ItemDuplicated {
		this.root = insertRec(x, this.root);
	}
	
	private Node insertRec(E x, Node current) throws ItemDuplicated {
		Node res = current;
		if(current == null)
			res = new Node(x);
		else {
			// resC: resultado de la comparación
			int resC = current.data.compareTo(x);
			if (resC == 0)
				throw new ItemDuplicated("El dato " + x + " ya fue insertado antes.");
			if (resC < 0)
				res.right = insertRec(x, current.right);
			else
				res.left = insertRec(x, current.left);

		}
		return res;
	}
	
	// BÚSQUEDA
	public E search(E x) throws ItemNotFound {
		Node res = searchNode(x, root);
		if(res == null)
			throw new ItemNotFound ("El dato "+ x + " no está ...");
		return res.data;
	}
	
	private Node searchNode(E x, Node n){
		if (n == null) return null;
		else {
			int resC = n.data.compareTo(x);
			if (resC < 0) return searchNode(x, n.right);
			else if (resC > 0) return searchNode(x, n.left);
			else return n;
		}
	}
	
	// ELIMINACIÓN
	
	public void remove(E x) throws ItemNotFound {
		this.root = removeNode(x, this.root);
	}
	
	private Node removeNode(E x, Node actual) throws ItemNotFound {
		Node res = actual;
		if (actual == null) throw new ItemNotFound(x + " no esta");
		int resC = actual.data.compareTo(x);
		if (resC < 0) res.right = removeNode(x, actual.right);
		else if (resC > 0) res.left = removeNode(x, actual.left);
		// Cuando ya encontró al nodo por ende resc == 0
		// Tercer caso
		else if(actual.left != null && actual.right != null) {//dos hijos
//			//HACE DOS RECORRIDOS: minRecover y minRemove (- eficiente)
//			// Halla al sucesor y reemplaza su valor
//			res.data = minRecover(actual.right).data;
//			//Se borra el valor
//			res.right = minRemove(actual.right);
			
			// UN SOLO RECORRIDO (+ eficiente)
			res.right = minRemoveNode(actual.right);
			res.data = this.lastRemoved.data;	
		} 
		// Primer y Segundo Caso
		else  {//1 hijo o ninguno
			res = (actual.left != null) ? actual.left : actual.right;
		}
		return res;
	}
	
	//Elimina el nodo y almacena el nodo eliminado en aux
	private Node lastRemoved;
	private Node minRemoveNode(Node actual) {
		Node res = actual;
		if(actual.left != null) res.left = minRemoveNode(actual.left);
		this.lastRemoved = res;
		res = actual.right;
		return res; 
	}
	
	//Elimina el menor de la izquierda de un subárbol
	private Node minRemove(Node actual) {
		if (actual.left != null)  //busca el mínimo
			actual.left = minRemove(actual.left);
		else  //elimina el mínimo // actual.right es por si tiene hijo derecho y no perder su descendencia
			actual = actual.right;
		return actual;
	}
	
	//Busca el mínimo de un subárbol
	private Node minRecover(Node current) {
		if(current.left == null) return current;
		else return minRecover(current.left);
	}
	
	//Precondition: !isEmpty() - Devuelve el mín y lo elimina
	public E minRemove() {
		E min = minRecover(); //devuelve el menor del árbol
		this.root = minRemove(this.root);
		return min;
	}
	
	//Devuelve el mínimo del árbol
	public E minRecover() {
		return minRecover(this.root).data;
	}
	
	public String toString() {
		if (isEmpty())
			return "Arbol Vacio...";
		String str = "PREORDEN: " + preOrden(this.root) +
					 "\nINORDEN: " + inOrden(this.root) + 
					 "\nPOSTORDEN: " + postOrden(this.root);
		return str;
		
	}
	
	private String preOrden(Node current) {
		//RID
		String str = "";
		str += current.data.toString() + ", ";
		if (current.left != null) str += preOrden(current.left);
		if (current.right != null) str += preOrden(current.right);	
		return str;	
	}
	
	private String inOrden(Node current) {
		//IRD
		String str = "";
		if (current.left != null) str += inOrden(current.left);
		str += current.data.toString() + ", ";
		if (current.right != null) str += inOrden(current.right);	
		return str;	
	}
	
	private String postOrden(Node current) {
		//IRD 
		String str = "";
		if (current.left != null) str += postOrden(current.left);
		if (current.right != null) str += postOrden(current.right);
		str += current.data.toString() + ", ";
		return str;	
	}
}
