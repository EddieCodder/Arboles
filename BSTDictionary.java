
public class BSTDictionary<C extends Comparable<C>, V> implements Dictionary<C, V> {

	
	private BST<EntryDic<C, V>> dict;
	
	public BSTDictionary() {
		dict = new BST<EntryDic<C, V>>();
	}
	
	public void insert(C key, V val) throws ItemDuplicated {
		dict.insert(new EntryDic<C,V>(key, val));
	}
	
	public void remove(C key) throws ItemNotFound {
		dict.remove(new EntryDic<C,V>(key));
	}
	public V search(C key) throws ItemNotFound {
		return dict.search(new EntryDic<C, V>(key)).getValue();
	}
	
	public boolean isEmpty() {
		return dict.isEmpty();
	}
	
	public String toString() {
		return dict.toString();
	}
		
	
}
