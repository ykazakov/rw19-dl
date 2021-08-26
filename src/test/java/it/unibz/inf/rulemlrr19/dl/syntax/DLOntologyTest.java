package it.unibz.inf.rulemlrr19.dl.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DLOntologyTest {

	/**
	 * Tests the ontology from Example 1 of
	 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
	 */
	@Test
	public void testExample1() {
		DLOntology o = new DLOntology();
		// Human ⊑ (Dead ⊔ Alive)
		o.add(new DLConceptInclusion(new DLConceptName("Human"),
				new DLConceptDisjunction(new DLConceptName("Dead"),
						new DLConceptName("Alive"))));
		// Parent ≡ (∃hasChild.⊤)
		o.add(new DLConceptEquivalence(new DLConceptName("Parent"),
				new DLConceptExistentialRestiction(new DLRoleName("hasChild"),
						new DLConceptTop())));
		// Male(john)
		o.add(new DLConceptAssertion(new DLConceptName("Male"),
				new DLIndividual("john")));
		// bornIn(einstein, ulm)
		o.add(new DLRoleAssertion(new DLRoleName("bornIn"),
				new DLIndividual("einstein"), new DLIndividual("ulm")));

		// uncomment to check how the axioms are printed
		// o.axioms().forEach(ax -> System.out.println(ax));

		// check the signature of the ontology
		DLSignature s = o.getSignature();
		// concept names = Human, Dead, Alive, Parent, Male
		assertEquals(5, s.conceptNames().count());
		// role names = hasChild, bornIn
		assertEquals(2, s.roleNames().count());
		// individuals = john, einstein, ulm,
		assertEquals(3, s.individuals().count());
	}

}
