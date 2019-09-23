package it.unibz.inf.rulemlrr19.dl.tableaux;

/**
 * A visitor that does nothing
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauRuleApplicationDummyVisitor
		implements DLTableauRuleApplication.Visitor {

	@Override
	public void visit(DLTableauClashRuleApplication app) {
	}

	@Override
	public void visit(DLTableauConjunctionRuleApplication app) {
	}

	@Override
	public void visit(DLTableauDisjunctionFirstRuleApplication app) {
	}

	@Override
	public void visit(DLTableauDisjunctionSecondRuleApplication app) {
	}

	@Override
	public void visit(DLTableauExistentialRuleApplication app) {
	}

	@Override
	public void visit(DLTableauUniversalRuleApplication app) {
	}

}
