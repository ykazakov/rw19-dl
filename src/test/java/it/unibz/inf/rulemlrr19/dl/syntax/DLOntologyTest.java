package it.unibz.inf.rulemlrr19.dl.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptAssertion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptEquivalence;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptInclusion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptTop;
import it.unibz.inf.rulemlrr19.dl.syntax.DLIndividual;
import it.unibz.inf.rulemlrr19.dl.syntax.DLOntology;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleAssertion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLSignature;

public class DLOntologyTest {

	@Test
	public void testExampleOntology() {
		// Tests the ontology from Example 1 of the paper

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
