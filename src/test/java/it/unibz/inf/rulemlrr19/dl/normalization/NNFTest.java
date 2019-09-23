package it.unibz.inf.rulemlrr19.dl.normalization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptConjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptNegation;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptUniversalRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;

public class NNFTest {

	/**
	 * Tests that NNF((∃ r. A) ⊓ ¬ ((∃ r. A) ⊓ (¬ B))) = ((∃ r. A) ⊓ ((∀ r. (¬
	 * A)) ⊔ B))
	 */
	@Test
	public void testExample12() {
		DLConcept given = new DLConceptConjunction(
				new DLConceptExistentialRestiction(new DLRoleName("r"),
						new DLConceptName("A")),
				new DLConceptNegation(new DLConceptConjunction(
						new DLConceptExistentialRestiction(new DLRoleName("r"),
								new DLConceptName("A")),
						new DLConceptNegation(new DLConceptName("B")))));
		DLConcept expected = new DLConceptConjunction(
				new DLConceptExistentialRestiction(new DLRoleName("r"),
						new DLConceptName("A")),
				new DLConceptDisjunction(
						new DLConceptUniversalRestiction(new DLRoleName("r"),
								new DLConceptNegation(new DLConceptName("A"))),
						new DLConceptName("B")));
		assertEquals(expected, NNF.getNNF(given));

	}

}
