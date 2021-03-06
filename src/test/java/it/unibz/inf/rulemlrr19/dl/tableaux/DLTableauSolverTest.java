package it.unibz.inf.rulemlrr19.dl.tableaux;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import it.unibz.inf.rulemlrr19.dl.problems.DLSolverConceptSatisfiability;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptBottom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptConjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptNegation;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptUniversalRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;

public class DLTableauSolverTest {

	DLSolverConceptSatisfiability solver;

	@Before
	public void initialize() {
		solver = new DLTableauSolver();
	}

	/**
	 * Test unsatisfiability of concept (∃ r. ⊥)
	 */
	@Test
	public void testUnsatisfiabilityExistsBottom() {
		DLConcept c = new DLConceptExistentialRestiction(new DLRoleName("r"),
				new DLConceptBottom());
		assertFalse(solver.isSatisfiable(c));
	}

	/**
	 * Test unsatisfiability of concept (A ⊓ (¬ A))
	 */
	@Test
	public void testUnsatisfiabilityAAndNotA() {
		DLConcept c = new DLConceptConjunction(new DLConceptName("A"),
				new DLConceptNegation(new DLConceptName("A")));
		assertFalse(solver.isSatisfiable(c));
	}

	/**
	 * Test unsatisfiability of concept ((A ⊓ B) ⊓ (¬ A ⊔ ¬ B))
	 */
	@Test
	public void testUnsatisfiabilityAandBandNotAorNotB() {
		DLConcept c = new DLConceptConjunction(
				new DLConceptConjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptDisjunction(
						new DLConceptNegation(new DLConceptName("A")),
						new DLConceptNegation(new DLConceptName("B"))));
		assertFalse(solver.isSatisfiable(c));
	}

	/**
	 * Test satisfiability of concept ((A ⊔ B) ⊓ (¬ A ⊔ ¬ B))
	 */
	@Test
	public void testSatisfiabilityAorBandNotAorNotB() {
		DLConcept c = new DLConceptConjunction(
				new DLConceptDisjunction(new DLConceptName("A"),
						new DLConceptName("B")),
				new DLConceptDisjunction(
						new DLConceptNegation(new DLConceptName("A")),
						new DLConceptNegation(new DLConceptName("B"))));
		assertTrue(solver.isSatisfiable(c));
	}

	/**
	 * Test unsatisfiability of concept ((A ⊔ B) ⊓ (A ⊔ (¬ B)) ⊓ (((¬ A) ⊔ B) ⊓
	 * ((¬ A) ⊔ (¬ B))))
	 */
	@Test
	public void testUnsatisfiabilityABAllCombinations() {
		DLConcept a = new DLConceptName("A");
		DLConcept b = new DLConceptName("B");
		DLConcept na = new DLConceptNegation(a);
		DLConcept nb = new DLConceptNegation(b);
		DLConcept c = new DLConceptConjunction(
				new DLConceptConjunction(new DLConceptDisjunction(a, b),
						new DLConceptDisjunction(a, nb)),
				new DLConceptConjunction(new DLConceptDisjunction(na, b),
						new DLConceptDisjunction(na, nb)));
		assertFalse(solver.isSatisfiable(c));
	}

	/**
	 * Test satisfiability of concept (∀ r. ⊥)
	 */
	@Test
	@Ignore
	public void testSatisfiabilityForallBottom() {
		DLConcept c = new DLConceptUniversalRestiction(new DLRoleName("r"),
				new DLConceptBottom());
		assertTrue(solver.isSatisfiable(c));
	}

	/**
	 * Test satisfiability of concept (∃ r. A) ⊓ (∀ r. (¬ B))
	 */
	@Test
	@Ignore
	public void testSatisfiabilityExistsForall() {
		DLConcept c = new DLConceptConjunction(
				new DLConceptExistentialRestiction(new DLRoleName("r"),
						new DLConceptName("A")),
				new DLConceptUniversalRestiction(new DLRoleName("r"),
						new DLConceptNegation(new DLConceptName("B"))));
		assertTrue(solver.isSatisfiable(c));
	}

	/**
	 * Test unsatisfiability of concept (∃ r. A) ⊓ (∀ r. (¬ A))
	 */
	@Test
	@Ignore
	public void testUnsatisfiabilityExistsForall() {
		DLConcept c = new DLConceptConjunction(
				new DLConceptExistentialRestiction(new DLRoleName("r"),
						new DLConceptName("A")),
				new DLConceptUniversalRestiction(new DLRoleName("r"),
						new DLConceptNegation(new DLConceptName("A"))));
		assertFalse(solver.isSatisfiable(c));
	}

	/**
	 * Tests concept satisfiability of concept (∃ r. A) ⊓ ((∀ r. (¬ A)) ⊔ B)
	 * from Examples 12
	 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>
	 */
	@Test
	@Ignore
	public void testExample13() {
		// Satisfiability of
		DLConcept c = new DLConceptConjunction(
				new DLConceptExistentialRestiction(new DLRoleName("r"),
						new DLConceptName("A")),
				new DLConceptDisjunction(
						new DLConceptUniversalRestiction(new DLRoleName("r"),
								new DLConceptNegation(new DLConceptName("A"))),
						new DLConceptName("B")));
		assertTrue(solver.isSatisfiable(c));
	}

	/**
	 * Test unsatisfiability of concept (∃ r. (A ⊓ B)) ⊓ ((∀ r. (¬ A)) ⊔ (∀ r.
	 * (¬ B)))
	 */
	@Test
	@Ignore
	public void testUnsatisfiabilityDeep() {
		DLConcept c = new DLConceptConjunction(
				new DLConceptExistentialRestiction(new DLRoleName("r"),
						new DLConceptConjunction(new DLConceptName("A"),
								new DLConceptName("B"))),
				new DLConceptDisjunction(
						new DLConceptUniversalRestiction(new DLRoleName("r"),
								new DLConceptNegation(new DLConceptName("A"))),
						new DLConceptUniversalRestiction(new DLRoleName("r"),
								new DLConceptNegation(
										new DLConceptName("B")))));
		assertFalse(solver.isSatisfiable(c));
	}

	/**
	 * Test unsatisfiability of concept A ⊓ ((A ⊓ ⊥) ⊔ (¬ A)). This example
	 * shows that when backtracking the application of ⊓-Rule, the label A must
	 * be not removed.
	 */
	@Test
	public void testUnsatisfiabilityBacktracking() {
		DLConcept c = new DLConceptConjunction(new DLConceptName("A"),
				new DLConceptDisjunction(
						new DLConceptConjunction(new DLConceptName("A"),
								new DLConceptBottom()),
						new DLConceptNegation(new DLConceptName("A"))));
		assertFalse(solver.isSatisfiable(c));

	}

}
