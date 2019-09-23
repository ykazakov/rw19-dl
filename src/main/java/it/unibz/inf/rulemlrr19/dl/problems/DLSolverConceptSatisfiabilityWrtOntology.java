package it.unibz.inf.rulemlrr19.dl.problems;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLOntology;

/**
 * Can solve the concept satisfiability problem with respect to an ontology. See
 * Section 2.3 of <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the
 * paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
public interface DLSolverConceptSatisfiabilityWrtOntology {

	/**
	 * Checks satisfiability of a given concept w.r.t. the given ontology. I.e.,
	 * checks existence of an interpretation for which the given concept is
	 * interpreted by a non-empty set and which is a model of the given ontology
	 * 
	 * @param concept
	 *                     the concept that should be satisfied in the
	 *                     interpretation
	 * @param ontology
	 *                     the ontology that should be satisfied in the
	 *                     interpretation
	 * @return {@code true} if the given concept is satisfiable w.r.t. to the
	 *         given ontology and {@code false} otherwise
	 */
	public boolean isSatisfiable(DLConcept concept, DLOntology ontology);

}
