package it.unibz.inf.rulemlrr19.dl.tableaux;

/**
 * A visitor that does nothing
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauRuleApplicationDummyVisitor
		implements DLTableauRuleApplication.Visitor {

	public void defaultVisit(DLTableauRuleApplication<?> app) {
		// does nothing by default
	}

	@Override
	public void visit(DLTableauClashRuleApplication app) {
		defaultVisit(app);
	}

	@Override
	public void visit(DLTableauConjunctionRuleApplication app) {
		defaultVisit(app);
	}

	@Override
	public void visit(DLTableauDisjunctionFirstRuleApplication app) {
		defaultVisit(app);
	}

	@Override
	public void visit(DLTableauDisjunctionSecondRuleApplication app) {
		defaultVisit(app);
	}

	@Override
	public void visit(DLTableauExistentialRuleApplication app) {
		defaultVisit(app);
	}

	@Override
	public void visit(DLTableauUniversalRuleApplication app) {
		defaultVisit(app);
	}

}
