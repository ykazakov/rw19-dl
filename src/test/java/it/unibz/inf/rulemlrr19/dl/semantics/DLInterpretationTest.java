package it.unibz.inf.rulemlrr19.dl.semantics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptAssertion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptBottom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptConjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptEquivalence;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptInclusion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptNegation;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptTop;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptUniversalRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLIndividual;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleAssertion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;

/**
 * Tests the interpretation from Examples 3 and 4 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>
 * 
 * @author Yevgeny Kazakov
 */
public class DLInterpretationTest {

	DLInterpreter<String> intp;

	@Before
	public void initialize() {
		// Δ = {a,b,c}
		DLInterpretation<String> in = new DLInterpretation<>(
				Stream.of("a", "b", "c"));
		// Parent = {a,b}
		in.assign(new DLConceptName("Parent"), Stream.of("a", "b"));
		// GrandParent = {a}
		in.assign(new DLConceptName("GrandParent"), Stream.of("a"));
		// hasChild = {<a,b>,<b,c>}
		in.assign(new DLRoleName("hasChild"),
				Stream.of(new Pair<>("a", "b"), new Pair<>("b", "c")));
		// john = a
		in.assign(new DLIndividual("john"), "a");
		// mary = b
		in.assign(new DLIndividual("mary"), "b");
		// create an interpreter for calculating of interpretation of complex
		// objects
		intp = new DLInterpreter<>(in);
	}

	@Test
	public void testTop() {
		// ⊤ = {a,b,c}
		assertEquals(new HashSet<>(Arrays.asList("a", "b", "c")),
				intp.interpret(new DLConceptTop()));
	}

	@Test
	public void testBottom() {
		// ⊥ = {}
		assertEquals(Collections.emptySet(),
				intp.interpret(new DLConceptBottom()));
	}

	@Test
	public void testParentAndGrandParent() {
		// Parent ⊓ GrandParent = {a}
		assertEquals(new HashSet<>(Arrays.asList("a")),
				intp.interpret(
						new DLConceptConjunction(new DLConceptName("Parent"),
								new DLConceptName("GrandParent"))));
	}

	@Test
	public void testParentOrGrandParent() {
		// Parent ⊔ GrandParent = {a,b}
		assertEquals(new HashSet<>(Arrays.asList("a", "b")),
				intp.interpret(
						new DLConceptDisjunction(new DLConceptName("Parent"),
								new DLConceptName("GrandParent"))));
	}

	@Test
	public void testNotGrandParent() {
		// ¬ GrandParent = {b,c}
		assertEquals(new HashSet<>(Arrays.asList("b", "c")), intp.interpret(
				new DLConceptNegation(new DLConceptName("GrandParent"))));
	}

	@Test
	public void testParentAndNotGrandParent() {
		// Parent ⊓ (¬ GrandParent) = {b}
		assertEquals(new HashSet<>(Arrays.asList("b")),
				intp.interpret(new DLConceptConjunction(
						new DLConceptName("Parent"), new DLConceptNegation(
								new DLConceptName("GrandParent")))));
	}

	@Test
	public void testExistsHasChildTop() {
		// ∃ hasChild.⊤ = {a,b}
		assertEquals(new HashSet<>(Arrays.asList("a", "b")),
				intp.interpret(new DLConceptExistentialRestiction(
						new DLRoleName("hasChild"), new DLConceptTop())));
	}

	@Test
	public void testExistsHasChildParent() {
		// ∃ hasChild.Parent = {a,b}
		assertEquals(new HashSet<>(Arrays.asList("a")),
				intp.interpret(new DLConceptExistentialRestiction(
						new DLRoleName("hasChild"),
						new DLConceptName("Parent"))));
	}

	@Test
	@Ignore
	public void testForallHasChildParent() {
		// ∀ hasChild.Parent = {a,c}
		assertEquals(new HashSet<>(Arrays.asList("a", "c")),
				intp.interpret(new DLConceptUniversalRestiction(
						new DLRoleName("hasChild"),
						new DLConceptName("Parent"))));
	}

	@Test
	@Ignore
	public void testForallHasChildGrandParent() {
		// ∀ hasChild.GrandParent = {c}
		assertEquals(new HashSet<>(Arrays.asList("c")),
				intp.interpret(new DLConceptUniversalRestiction(
						new DLRoleName("hasChild"),
						new DLConceptName("GrandParent"))));
	}

	@Test
	@Ignore
	public void testForallHasForallHasChildBottom() {
		// ∀ hasChild.∀ hasChild.⊥ = {b,c}
		assertEquals(new HashSet<>(Arrays.asList("b", "c")),
				intp.interpret(new DLConceptUniversalRestiction(
						new DLRoleName("hasChild"),
						new DLConceptUniversalRestiction(
								new DLRoleName("hasChild"),
								new DLConceptBottom()))));
	}

	@Test
	public void testGrandParentIsParent() {
		assertTrue(intp.interpret(
				new DLConceptInclusion(new DLConceptName("GrandParent"),
						new DLConceptName("Parent"))));
	}

	@Test
	public void testParentSubsumeGrandParent() {
		assertFalse(intp
				.interpret(new DLConceptInclusion(new DLConceptName("Parent"),
						new DLConceptName("GrandParent"))));
	}

	@Test
	public void testExistsHasChildGrandParentEquivalentBottom() {
		assertTrue(intp.interpret(new DLConceptEquivalence(
				new DLConceptExistentialRestiction(new DLRoleName("hasChild"),
						new DLConceptName("GrandParent")),
				new DLConceptBottom())));
	}

	@Test
	public void testJohnInstanceExistsHasChildParent() {
		assertTrue(intp.interpret(new DLConceptAssertion(
				new DLConceptExistentialRestiction(new DLRoleName("hasChild"),
						new DLConceptName("Parent")),
				new DLIndividual("john"))));
	}

	@Test
	public void testJohnMaryInstanceHasChild() {
		assertTrue(
				intp.interpret(new DLRoleAssertion(new DLRoleName("hasChild"),
						new DLIndividual("john"), new DLIndividual("mary"))));
	}

	@Test
	public void testMaryJohnInstanceHasChild() {
		assertFalse(
				intp.interpret(new DLRoleAssertion(new DLRoleName("hasChild"),
						new DLIndividual("mary"), new DLIndividual("john"))));
	}

	@Test
	@Ignore
	public void testMaryInstanceForallHasChildNotParent() {
		assertTrue(
				intp.interpret(new DLConceptAssertion(
						new DLConceptUniversalRestiction(
								new DLRoleName("hasChild"),
								new DLConceptNegation(
										new DLConceptName("Parent"))),
						new DLIndividual("mary"))));
	}

}
