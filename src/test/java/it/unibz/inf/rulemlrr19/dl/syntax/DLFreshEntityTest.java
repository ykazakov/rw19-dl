package it.unibz.inf.rulemlrr19.dl.syntax;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Test;

public class DLFreshEntityTest {

	void testUnprexify(String p, String s) {
		String u = DLEntities.unprexify(p, s);
		assertTrue(u.startsWith(p));
		assertFalse(s.startsWith(u));
	}

	@Test
	public void testUnprexify_A_AB() {
		testUnprexify("A", "AB");
	}

	@Test
	public void testUnprexify_A_AA() {
		testUnprexify("A", "AA");
	}

	@Test
	public void testUnprexify__A() {
		testUnprexify("", "A");
	}

	@Test
	public void testFreshName() {
		String[] names = { "a", "b", "c" };
		String fresh = DLEntities.getFreshName(Stream.of(names));
		Stream.of(names).forEach(name -> assertNotEquals(fresh, name));
	}

}
