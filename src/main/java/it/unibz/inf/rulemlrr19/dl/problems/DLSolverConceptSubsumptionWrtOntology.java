package it.unibz.inf.rulemlrr19.dl.problems;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLOntology;

/**
 * Can solve the concept subsumption problem with respect to an ontology. See
 * Section 2.3 of <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the
 * paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
public interface DLSolverConceptSubsumptionWrtOntology {

	/**
	 * Checks entailment of concept inclusion formed from the two given concept
	 * from the given ontology.
	 * 
	 * @param subConcept
	 *                         the sub-concept of the concept inclusion to be
	 *                         checked for the entailment
	 * @param superConcept
	 *                         the super-concept of the concept inclusion to be
	 *                         checked for the entailment
	 * @param ontology
	 *                         the ontology for which the entailment needs to be
	 *                         checked
	 * @return {@code true} if the entailment holds and {@code false} otherwise
	 */
	boolean isSubsumed(DLConcept subConcept, DLConcept superConcept,
			DLOntology ontology);

}
