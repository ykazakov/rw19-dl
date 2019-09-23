package it.unibz.inf.rulemlrr19.dl.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DLConceptTest {

	DLConcept human, male, female, country, dead, alive;
	DLRole hasChild, hasLocation;
	DLIndividual germany, john;

	@Before
	public void initialize() {
		// atomic concepts
		human = new DLConceptName("Human");
		male = new DLConceptName("Male");
		female = new DLConceptName("Female");
		country = new DLConceptName("Country");
		dead = new DLConceptName("Dead");
		alive = new DLConceptName("Alive");
		// atomic roles
		hasChild = new DLRoleName("hasChild");
		hasLocation = new DLRoleName("hasLocation");
		// individuals
		germany = new DLIndividual("germany");
		john = new DLIndividual("john");
	}

	@Test
	public void testMaleAndHuman() {
		// Male ⊓ Human
		DLConcept c = new DLConceptConjunction(male, human);
		// System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(2, s.conceptNames().count());
	}

	@Test
	public void testDeadOrAlive() {
		// Dead ⊔ Alive
		DLConcept c = new DLConceptDisjunction(dead, alive);
		// System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(2, s.conceptNames().count());
	}

	@Test
	public void testNotMale() {
		// ¬ Male
		DLConcept c = new DLConceptNegation(male);
		// System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(1, s.conceptNames().count());
	}

	@Test
	public void testNotMaleAndHuman() {
		// (¬ Male) ⊓ Human
		DLConcept c = new DLConceptConjunction(new DLConceptNegation(male),
				human);
		// System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(2, s.conceptNames().count());
	}

	@Test
	public void testExistsHasChildHuman() {
		// ∃ hasChild. Human
		DLConcept c = new DLConceptExistentialRestiction(hasChild, human);
		// System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(1, s.conceptNames().count());
		assertEquals(1, s.roleNames().count());
	}

	public void testForAllHasChildFemale() {
		// ∀ hasChild. Female
		DLConcept c = new DLConceptUniversalRestiction(hasChild, female);
		System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(1, s.conceptNames().count());
		assertEquals(1, s.roleNames().count());
	}

	@Test
	public void testMaleAndForAllHasChildNotMale() {
		// Male ⊓ (∀ hasChild. (¬ Male))
		DLConcept c = new DLConceptConjunction(male,
				new DLConceptUniversalRestiction(hasChild,
						new DLConceptNegation(male)));
		System.out.println(c);
		DLSignature s = DLSignature.getSignatureOf(c);
		assertEquals(1, s.conceptNames().count());
		assertEquals(1, s.roleNames().count());
	}

}
