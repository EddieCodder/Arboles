public class AVL<E extends Comparable<E>> {
	
	class Node {
		// No es necesario que Node tenga <E> Ya que esta clase (Node) est� inculuida en una clase gen�rica
		protected E data;
		protected Node left;
		protected Node right;
		protected int fb;
		
		public Node(E data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
			this.fb = 0;
		}
	
		// Para los nuevos nodos hoja (No tienen hijos)
		public Node(E data) {
			// Llamamos al constructor
			this(data, null, null);
		}
	}
		
	// ATRIBUTO DE LA CLASE AVL
	private Node root;
	private boolean height; // Si pasa a true es cambio de altura, false lo contrario
		
	// CONSTRUCTORES
	public AVL() {
		this.root = null;
	}
		
	// M�TODOS DEL �RBOL
	
		// Para saber si el �rbol est� vac�o
	public boolean isEmpty() {
		return this.root == null;
	}
	
	public void insert(E x) throws ItemDuplicated {
		this.height = false; // Por si el nodo no se inserta
		this.root = insertRec(x, this.root);
	}
	
	private Node insertRec(E x, Node current) throws ItemDuplicated {
		Node res = current;
		if(current == null) {
			// En el caso base cuando hay inserci�n debemos consultar el cambio de altura
			this.height = true; 
			res = new Node(x);
		}
		else {
			// resC == resultado de la comparaci�n
			int resC = current.data.compareTo(x);
			if (resC == 0)
				throw new ItemDuplicated("El dato " + x + " ya fue insertado antes.");
			if (resC < 0) {
				res.right = insertRec(x, current.right);
				if (this.height) { // Si existe un cambio en la altura
					switch(res.fb) {// Casos de fb si insertamos por la derecha
					// En inserci�n si fb pasa de -1 o 1 a 0 entonces no hay cambio de altura
					case -1: res.fb = 0; this.height = false; break; // Se cancela el rec�lculo de altura
					case 0: res.fb = 1; this.height = true; break; // Seguimos recalculando
					case 1: //res.fb = 2; debemos balancear el �rbol
						res = balanceToLeft(res);
						this.height = false; // Cuando hacemos la rotaci�n el �rbol debe quedar balanceado
					}
				}
			}
			else {
				res.left = insertRec(x, current.left);
				if(this.height) { // Si existe un cambio en la altura
					switch(res.fb) { // Casos de fb si insertamos por la derecha
					// En inserci�n si fb pasa de -1 o 1 a 0 entonces no hay cambio de altura
					case 1: res.fb = 0; this.height = false; break;// Se cancela el rec�lculo de altura
					case 0: res.fb = -1; this.height = true; break; // Seguimos recalculando
					case -1: //res.fb = -2; debemos balancear el �rbol
						res = balanceToRight(res);
						this.height = false; // Cuando hacemos la rotaci�n el �rbol debe quedar balanceado
					}
				}

			}

		}
		return res;
	}
	
	// Para balancear a la izquierda (�rbol desbalanceado a la derecha)
	private Node balanceToLeft(Node node) {
		Node son = node.right; // Para balancear a la izquierda debemos trabajar con el hijo derecho
		switch(son.fb) {
		case 1 : //Rotaci�n simple (Hijo y padre tienden a la derecha)
			// Como ser�n rotados el fb ser� 0
			node.fb = 0;
			son.fb = 0;
			node = rotateSL(node);
			break;
		case -1: // Rotaci�n doble (Padre desbalanceado a la derecha, hijo a la izquierda)
			Node grandson = son.left;
			switch(grandson.fb) { // Actualizamos los fb
			case -1: node.fb = 0; son.fb = -1; break;
			case 0: node.fb = 0; son.fb = 0; break;
			case 1: node.fb = 1; son.fb = 0; break;		
			}
			grandson.fb = 0; // El nieto queda balanceado
			//Hacemos las rotaciones
			node.right = rotateSR(son);
			node = rotateSL(node);
			break;
		}
		return node;
	}
	
	// Para balancear a la derecha (�rbol desbalanceado a la izquierda)
	private Node balanceToRight(Node node) {
		Node son = node.left; // Para balancear a la derecha debemos trabajar con el hijo izquierdo
		switch(son.fb) {
		case -1 : //Rotaci�n simple (Hijo y padre tienden a la izquierda)
			// Como ser�n rotados el fb ser� 0
			node.fb = 0;
			son.fb = 0;
			node = rotateSR(node);
			break;
		case 1: // Rotaci�n doble (Padre desbalanceado a la izquierda, hijo a la derecha)
			Node grandson = son.right;
			switch(grandson.fb) { // Actualizamos los fb
			case 1: node.fb = 0; son.fb = -1; break;
			case 0: node.fb = 0; son.fb = 0; break;
			case -1: node.fb = 1; son.fb = 0; break;
				
			}
			grandson.fb = 0; // El nieto queda balanceado
			//Hacemos las rotaciones
			node.left = rotateSL(son);
			node = rotateSR(node);
			break;
		}
		return node;
	}
	
	private Node rotateSL(Node node) {
		Node son = node.right;
		node.right = son.left;
		son.left = node;
		node = son;
		return node;
	}
	
	private Node rotateSR (Node node) {
		Node son = node.left;
		node.left = son.right;
		son.right = node;
		node = son;
		return node;
	}
	
	// B�SQUEDA
	public E search(E x) throws ItemNotFound {
		Node res = searchNode(x, root);
		if(res == null)
			throw new ItemNotFound ("El dato "+ x + " no est� ...");
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
	

	//Busca el m�nimo de un sub�rbol
	private Node minRecover(Node actual) {
		while (actual.left != null)  //busca el m�nimo
			actual = actual.left;
		return actual;
		
		//OTRO MODO
//		if(current.left == null) return current;
//		else return minRecover(current.left);
	}
	
	public String toString() {
		if (isEmpty())
			return "Arbol Vacio...";
//		String str = "PREORDEN: " + preOrden(this.root) +
//					 "\nINORDEN: " + inOrden(this.root) + 
//					 "\nPOSTORDEN: " + postOrden(this.root);
		String str = inOrden(this.root);
		return str;
		
	}
	
	private String preOrden(Node current) {
		//RID
		String str = "";
		str += current.data.toString() + "["+ current.fb +"], ";
		if (current.left != null) str += preOrden(current.left);
		if (current.right != null) str += preOrden(current.right);	
		return str;	
	}
	
	private String inOrden(Node current) {
		//IRD
		String str = "";
		if (current.left != null) str += inOrden(current.left);
		str += current.data.toString() + "["+ current.fb +"], ";
		if (current.right != null) str += inOrden(current.right);	
		return str;	
	}
	
	private String postOrden(Node current) {
		//IRD 
		String str = "";
		if (current.left != null) str += postOrden(current.left);
		str += current.data.toString() + "["+ current.fb +"], ";
		if (current.right != null) str += postOrden(current.right);
		return str;	
	}
}