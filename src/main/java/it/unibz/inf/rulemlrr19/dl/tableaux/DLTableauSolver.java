package it.unibz.inf.rulemlrr19.dl.tableaux;

import it.unibz.inf.rulemlrr19.dl.normalization.NNF;
import it.unibz.inf.rulemlrr19.dl.problems.DLSolverConceptSatisfiability;
import it.unibz.inf.rulemlrr19.dl.problems.DLSolverConceptSatisfiabilityWrtOntology;
import it.unibz.inf.rulemlrr19.dl.problems.DLSolverConceptSubsumptionWrtOntology;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLOntology;

/**
 * A solver based on the tableau procedure
 * 
 * @author Yevgeny Kazakov
 * 
 *         TODO: implement solvers for other problems
 */
public class DLTableauSolver implements DLSolverConceptSatisfiability,
		DLSolverConceptSatisfiabilityWrtOntology,
		DLSolverConceptSubsumptionWrtOntology {

	@Override
	public boolean isSatisfiable(DLConcept concept) {
		// convert to NNF
		concept = NNF.getNNF(concept);
		// create a new tableau
		DLTableau t = new DLTableau();
		// create a new tableau expander
		DLTableauExpander expander = new DLTableauExpander(t, concept);
		return expander.expand();
	}

	@Override
	public boolean isSubsumed(DLConcept subConcept, DLConcept superConcept,
			DLOntology ontology) {
		// TODO: implement the solver
		return false;
	}

	@Override
	public boolean isSatisfiable(DLConcept concept, DLOntology ontology) {
		// TODO: implement the solver
		return false;
	}

}
