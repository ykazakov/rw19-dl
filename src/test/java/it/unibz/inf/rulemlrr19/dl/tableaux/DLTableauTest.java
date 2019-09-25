package it.unibz.inf.rulemlrr19.dl.tableaux;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRole;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;

public class DLTableauTest {

	private DLTableau tableau;

	@Before
	public void initialize() {
		tableau = new DLTableau();
	}

	@Test(expected = Error.class)
	public void testAddUnreservedNode() {
		// no tableau nodes before
		assertEquals(0, tableau.getNodes().size());
		// no nodes can be added without reserving it first
		tableau.addNode(1);
	}

	@Test
	public void testReservedNode() {
		// no tableau nodes before
		assertEquals(0, tableau.getNodes().size());
		// reserve a node
		int node = tableau.reserveFreshNode();
		// should be OK
		tableau.addNode(node);
		assertEquals(1, tableau.getNodes().size());
	}

	@Test
	public void testDLNodeAddition() {
		assertEquals(0, tableau.getNodes().size());
		DLTableauModification addition = new DLTableauNodeAddition(0,
				tableau.reserveFreshNode());
		assertFalse(tableau.isAlreadyApplied(addition));
		tableau.apply(addition);
		assertTrue(tableau.isAlreadyApplied(addition));
		assertEquals(1, tableau.getNodes().size());
		tableau.revert(addition);
		assertEquals(0, tableau.getNodes().size());
	}

	@Test
	public void testDLNodeLabelAddition() {
		assertEquals(0, tableau.getNodes().size());
		int n = tableau.reserveFreshNode();
		DLConcept c = new DLConceptName("A");
		DLTableauNodeLabelAddition labelAddition = new DLTableauNodeLabelAddition(
				0, n, c);
		assertFalse(tableau.isAlreadyApplied(labelAddition));
		tableau.addNode(n);
		assertFalse(tableau.isAlreadyApplied(labelAddition));
		tableau.apply(labelAddition);
		assertTrue(tableau.isAlreadyApplied(labelAddition));
		tableau.revert(labelAddition);
		assertFalse(tableau.isAlreadyApplied(labelAddition));
	}

	@Test
	public void testDLEdgeLabelAddition() {
		assertEquals(0, tableau.getNodes().size());
		int n1 = tableau.reserveFreshNode();
		int n2 = tableau.reserveFreshNode();
		DLRole r = new DLRoleName("r");
		DLTableauEdgeLabelAddition labelAddition = new DLTableauEdgeLabelAddition(
				0, n1, r, n2);
		assertFalse(tableau.isAlreadyApplied(labelAddition));
		tableau.addNode(n1);
		tableau.addNode(n2);
		assertFalse(tableau.isAlreadyApplied(labelAddition));
		tableau.apply(labelAddition);
		assertTrue(tableau.isAlreadyApplied(labelAddition));
		tableau.revert(labelAddition);
		assertFalse(tableau.isAlreadyApplied(labelAddition));
	}

}
