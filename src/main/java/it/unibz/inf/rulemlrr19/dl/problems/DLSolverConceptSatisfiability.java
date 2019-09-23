package it.unibz.inf.rulemlrr19.dl.problems;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;

/**
 * Can solve the concept satisfiability problem. It is a special case of
 * {@link DLSolverConceptSatisfiabilityWrtOntology} when the ontology is empty.
 * 
 * @author Yevgeny Kazakov
 */
public interface DLSolverConceptSatisfiability {

	/**
	 * Checks satisfiability of a given concept w.r.t. the given ontology. I.e.,
	 * checks existence of an interpretation for which the given concept is
	 * interpreted by a non-empty set.
	 * 
	 * @param concept
	 *                    the concept that should be satisfied in the
	 *                    interpretation
	 * @return {@code true} if the given concept is satisfiable and
	 *         {@code false} otherwise
	 */
	boolean isSatisfiable(DLConcept concept);

}
