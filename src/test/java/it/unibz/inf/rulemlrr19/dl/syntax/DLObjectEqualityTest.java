package it.unibz.inf.rulemlrr19.dl.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DLObjectEqualityTest {

	@Test
	public void testConceptEqualityAvsA() {
		// A = A
		assertEquals(new DLConceptName("A"), new DLConceptName("A"));
	}

	@Test
	public void testConceptInequalityAvsB() {
		// A /= B
		assertNotEquals(new DLConceptName("A"), new DLConceptName("B"));
	}

	@Test
	public void testConceptEqualityAandBvsAandB() {
		// A ⊓ B = A ⊓ B
		assertEquals(
				new DLConceptConjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptConjunction(new DLConceptName("A"),
						new DLConceptName("B")));
	}

	@Test
	public void testConceptInequalityAandBvsBandA() {
		// A ⊓ B /= B ⊓ A
		assertNotEquals(
				new DLConceptConjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptConjunction(new DLConceptName("B"),
						new DLConceptName("A")));
	}

	@Test
	public void testConceptEqualityAorBvsAorB() {
		// A ⊔ B = A ⊔ B
		assertEquals(
				new DLConceptDisjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptDisjunction(new DLConceptName("A"),
						new DLConceptName("B")));
	}

	@Test
	public void testConceptInequalityAorBvsBorA() {
		// A ⊔ B /= B ⊔ A
		assertNotEquals(
				new DLConceptDisjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptConjunction(new DLConceptName("B"),
						new DLConceptName("A")));
	}

	@Test
	public void testConceptInequalityAandBvsAorB() {
		// A ⊓ B /= A ⊔ B
		assertNotEquals(
				new DLConceptConjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptDisjunction(new DLConceptName("A"),
						new DLConceptName("B")));
	}

}
