package it.unibz.inf.rulemlrr19.dl.semantics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PairTest {

	@Test
	public void testPairEquals() {
		Pair<String, String> p1 = new Pair<>("a", "b");
		Pair<String, String> p2 = new Pair<>("a", "b");
		assertTrue(p1.hashCode() == p2.hashCode());
		assertTrue(p1.equals(p2));
	}

	@Test
	public void testNotEquals() {
		Pair<String, String> p1 = new Pair<>("a", "b");
		Pair<String, String> p2 = new Pair<>("b", "a");
		assertFalse(p1.equals(p2));
	}

}
