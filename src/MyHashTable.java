import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class creates a hash table that is used for compression.
 *
 * @author Casey Morrison and Vladimir Gudzyuk
 * @version Dec 4, 2013 TCSS 242A Fall 2013
 * @param <K>
 * @param <V>
 */
public class MyHashTable<K, V> {
	
	/** The key for hash table */
	private K my_key;
	
	/** The value for the hash table */
	private V my_code;
	
	/** The capacity of the buckets. */
	private int my_capacity;
	
	/** The number of filled buckets. */
	private double my_filled_buckets;
	
	/** The number of empty buckets. */
	private double my_empty_buckets;
	
	/** The number of entries. */
	private int my_entries;
	
	/** The list of arraylist that are the buckets. */
	private List<ArrayList<V>> my_global_list;
	
	/** The table size used for the histogram */
	private List<Integer> my_table_sizes;
	
	/**
	 * Constructor that takes the capacity of the buckets.
	 * @param the_capacity
	 */
	public MyHashTable( int the_capacity) {
		
		my_capacity = the_capacity;
		
		my_table_sizes = new ArrayList<Integer>();
		
		my_entries = 0;
		
		my_global_list = new ArrayList<ArrayList<V>>(the_capacity);
		for (int i = 0; i < the_capacity; i++) {
			my_global_list.add(new ArrayList<V>());
		}
		System.out.println("list created");
		
	}
	
	/**
	 * The stats of this hash table displaying key components.
	 */
	public void stats() {
		double sum = 0;
		int largest = 0;
		for (int i = 0; i < my_global_list.size(); i++) {
			if (my_global_list.get(i).isEmpty()) {
				my_empty_buckets++;
			} else {
				my_filled_buckets++;
				sum += my_global_list.get(i).size();
				if (my_global_list.get(i).size() > largest) {
					largest = my_global_list.get(i).size();
					if (!my_table_sizes.contains(largest)) {
						my_table_sizes.add(largest);
					}
				}
			}
		}
		
		double average = sum/my_filled_buckets;
		double total = my_filled_buckets + my_empty_buckets;
		System.out.println("HashTable Stats");
		System.out.println("==================");
		System.out.println("Number of Entries: " + my_entries);
		System.out.println("Number of Buckets: " + (int)(total));
		// Not exactly sure what this is supposed to mean or print out, there was no real
		// Description on this but this is how we interpreted it:
		System.out.println("Histogram of Bucket Sizes: " + my_table_sizes);
		System.out.print("Fill Percentage: ");
		double percentage = ((my_filled_buckets/(total)) * 100);
		System.out.printf("%4.2f", percentage);
		System.out.println("%");
		System.out.print("Average Non-Empty Bucket: ");
		System.out.printf("%4.8f\n", average);
	}
	
	/**
	 * Puts a key value pair into the bucket system.
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		my_global_list.get(hash(key)).add(value);
		my_entries ++;
		//System.out.println("added code to hash");
		
	}
	
	/**
	 * Returns the value that is associated with the key.
	 * @param search_key
	 * @return
	 */
	public V get(K search_key) {
//		V value = null;
//		if (my_global_list.get((hash(search_key))).size() == 0) {
//			value = (V) my_global_list.get((hash(search_key))).get(0);
//			return value;
//		} else {
//			for (int i = 0; i < my_global_list.get((hash(search_key))).size(); i++) {
//				 value = (V) my_global_list.get((hash(search_key))).get(i);
//			}
//		}
//		return value;
		return (V) my_global_list.get((hash(search_key))).get(0);
	}
	
	/**
	 * Hashes the input based on the algorithm. 
	 * @param key
	 * @return
	 */
	private int hash(K key) {
		//System.out.println(key.hashCode() % ((my_capacity/2) + (my_capacity/2)));
		return key.hashCode()%(my_capacity/2)+(my_capacity/2);
	}

}
