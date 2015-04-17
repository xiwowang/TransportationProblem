/**
 * @usage: A Common Pair 
 * @author Mizzle Qiu
 */
package util;

public class Pair <K, V>{
	public K k;
	public V v;
	
	public Pair(K k, V v) {
		super();
		this.k = k;
		this.v = v;
	}
	
	@Override
	public int hashCode() {
		return this.k.hashCode() + this.v.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pair){
			return this.k.equals(((Pair<?, ?>) obj).k) && this.v.equals(((Pair<?, ?>) obj).v);
		}else{
			return false;
		}
	}
	
	@Override
    public String toString() {
	    return k + ": " + v;
	}
}
