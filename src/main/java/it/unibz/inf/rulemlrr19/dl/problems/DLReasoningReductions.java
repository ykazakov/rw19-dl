package it.unibz.inf.rulemlrr19.dl.problems;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptAssertion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptTop;
import it.unibz.inf.rulemlrr19.dl.syntax.DLEntities;
import it.unibz.inf.rulemlrr19.dl.syntax.DLOntology;

/**
 * Reductions between standard reasoning problems. See Section 2.4 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
public class DLReasoningReductions {

	/**
	 * Reduce the problem of concept satisfiability w.r.t. ontology to the
	 * problem of ontology satisfiability using Lemma 1. The lemma states that a
	 * concept C is satisfiable w.r.t. O iff O ∪ {C(a)} is satisfiable, where
	 * the individual a does not appear in O.
	 * 
	 * @param solver
	 *                   a solver for the ontology satisfiability problem
	 * @return a solver for concept satisfiability w.r.t. ontology
	 */
	public static DLSolverConceptSatisfiabilityWrtOntology reduceLemma1(
			DLSolverOntologySatisfiability solver) {

		return new DLSolverConceptSatisfiabilityWrtOntology() {

			@Override
			public boolean isSatisfiable(DLConcept c, DLOntology o) {
				DLOntology extended = new DLOntology();
				o.axioms().forEach(ax -> extended.add(ax));
				extended.add(new DLConceptAssertion(c, DLEntities
						.getFreshIndividual(o.getSignature().individuals())));
				return solver.isSatisfiable(extended);
			}

		};
	}

	/**
	 * Reduce the problem of concept subsumption w.r.t. ontology to the problem
	 * of concept satisfiability w.r.t. ontology using Lemma 2. The lemma states
	 * that O ⊨ C ⊑ D does NOT hold iff (C ⊓ ¬ D) is satisfiable w.r.t. O.
	 * 
	 * @param solver
	 *                   a solver for concept satisfiability problem w.r.t.
	 *                   ontology
	 * @return a solver for concept subsumption w.r.t. ontology
	 */
	public static DLSolverConceptSubsumptionWrtOntology reduceLemma2(
			DLSolverConceptSatisfiabilityWrtOntology solver) {
		// TODO: implement the reduction
		return null;

	}

	// TODO: implement reductions by Lemmas 3 - 4

	/**
	 * Reduce the problem of ontology satisfiability to the problem of concept
	 * satisfiability w.r.t. ontology using Lemma 5. The lemma states that O is
	 * satisfiable iff ⊤ is satisfiable w.r.t. O.
	 * 
	 * @param solver
	 *                   a solver for ontology satisfiability problem
	 * @return a solver for concept satisfiability w.r.t. ontology
	 */
	public static DLSolverOntologySatisfiability reduceLemma5(
			DLSolverConceptSatisfiabilityWrtOntology solver) {

		return new DLSolverOntologySatisfiability() {

			@Override
			public boolean isSatisfiable(DLOntology ontology) {
				return solver.isSatisfiable(new DLConceptTop(), ontology);
			}

		};

	}

}
