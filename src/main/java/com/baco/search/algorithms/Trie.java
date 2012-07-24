package com.baco.search.algorithms;

import java.util.List;
import java.util.Set;

/**
 *  Interface para una estructura Trie
 * @see  http://stevedaskam.wordpress.com/2009/05/28/trie-structures/
 * @author Steve Daskam
 * @param <T>
 */
public interface Trie<T> {
	public void add(String key, T value);

	public T find(String key);

	public List<T> search(String prefix);

	public boolean contains(String key);

	public Set<String> getAllKeys();

	public int size();
}