package it.unibz.inf.rulemlrr19.dl.problems;

import it.unibz.inf.rulemlrr19.dl.syntax.DLOntology;

/**
 * Can solve the ontology satisfiability problem. See Section 2.3 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
public interface DLSolverOntologySatisfiability {

	/**
	 * Checks satisfiability of the given ontology. An ontology is satisfiable
	 * if there exists an interpretation that satisfies all axioms in the
	 * ontology.
	 * 
	 * @param ontology
	 *                     the ontology to be checked for satisfiability
	 * @return {@code true} if the ontology is satisfiable and {@code false}
	 *         otherwise
	 */
	public boolean isSatisfiable(DLOntology ontology);

}
