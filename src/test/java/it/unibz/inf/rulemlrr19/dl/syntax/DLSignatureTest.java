package it.unibz.inf.rulemlrr19.dl.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DLSignatureTest {

	@Test
	public void testSignature() {
		DLSignature s = new DLSignature();
		// additions
		s.add(new DLConceptName("1"));
		s.add(new DLConceptName("2"));
		assertEquals(2, s.conceptNames().count());
		s.add(new DLConceptName("1"));
		assertEquals(2, s.conceptNames().count());
		s.add(new DLRoleName("1"));
		s.add(new DLRoleName("2"));
		s.add(new DLRoleName("3"));
		assertEquals(3, s.roleNames().count());
		s.add(new DLRoleName("2"));
		assertEquals(3, s.roleNames().count());
		assertEquals(0, s.individuals().count());
		s.add(new DLIndividual("1"));
		assertEquals(1, s.individuals().count());
		s.add(new DLIndividual("2"));
		assertEquals(2, s.individuals().count());
		s.add(new DLIndividual("1"));
		assertEquals(2, s.individuals().count());
		assertEquals(7, s.entities().count());
		// removals
		s.remove(new DLRoleName("1"));
		assertEquals(2, s.roleNames().count());
		s.remove(new DLRoleName("1"));
		assertEquals(2, s.roleNames().count());
		s.remove(new DLRoleName("2"));
		assertEquals(1, s.roleNames().count());
		s.remove(new DLRoleName("3"));
		assertEquals(0, s.roleNames().count());
		assertEquals(2, s.conceptNames().count());
		s.remove(new DLConceptName("1"));
		assertEquals(1, s.conceptNames().count());
		s.remove(new DLConceptName("2"));
		assertEquals(0, s.conceptNames().count());
		s.remove(new DLIndividual("3"));
		assertEquals(2, s.individuals().count());
		s.remove(new DLIndividual("2"));
		assertEquals(1, s.individuals().count());
		s.remove(new DLIndividual("2"));
		assertEquals(1, s.individuals().count());
		s.remove(new DLIndividual("1"));
		assertEquals(0, s.individuals().count());
		assertEquals(0, s.entities().count());
	}

}
