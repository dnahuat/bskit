package com.baco.search.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * Nodo de una estructura Trie
 * @see  http://stevedaskam.wordpress.com/2009/05/28/trie-structures/
 * @author Steve Daskam
 * @param <T>
 */
public class TrieNode<T> {
	private Character nodeKey;
	private T nodeValue;
	private boolean terminal;
	private Map<Character, TrieNode<T>> children = new HashMap<Character, TrieNode<T>>();

	public Character getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(Character nodeKey) {
		this.nodeKey = nodeKey;
	}

	public T getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(T nodeValue) {
		this.nodeValue = nodeValue;
	}

	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public Map<Character, TrieNode<T>> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, TrieNode<T>> children) {
		this.children = children;
	}
}