package it.unibz.inf.rulemlrr19.dl.semantics;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import it.unibz.inf.rulemlrr19.dl.syntax.DLAxiom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
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
import it.unibz.inf.rulemlrr19.dl.syntax.DLObject;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRole;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleAssertion;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;

/**
 * Evaluates interpretation on complex {@link DLObject}s. See Table 1 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 *
 * @param <D>
 *                the type of domain elements of the interpretation
 */
public class DLInterpreter<D> implements DLObject.Visitor {

	/**
	 * the interpretation used to compute the evaluation
	 */
	private final DLInterpretation<D> interpretation_;

	/**
	 * the result of the last concept evaluation or {@code null} if no concept
	 * was evaluated yet
	 */
	private Set<D> result4Concept_;

	/**
	 * the result of the last role evaluation or {@code null} if no role was
	 * evaluated yet
	 */
	private BinaryRelation<D> result4Role_;

	/**
	 * the result of the last individual evaluation or {@code null} if no
	 * individual was evaluated yet
	 */
	private D result4Individual_;

	/**
	 * the result of the last axiom evaluation or {@code null} if no axiom was
	 * yet evaluated
	 */
	private boolean result4Axiom_;

	public DLInterpreter(DLInterpretation<D> interpretation) {
		this.interpretation_ = interpretation;
	}

	/**
	 * Evaluates the interpretation on the given concept
	 * 
	 * @param c
	 *              a (possibly complex) concept
	 * @return the interpretation of the given concept
	 */
	public Set<D> interpret(DLConcept c) {
		c.accept(this);
		return result4Concept_;
	}

	/**
	 * Evaluates the interpretation on the given role
	 * 
	 * @param r
	 *              a role
	 * @return the interpretation of the given concept
	 */
	public BinaryRelation<D> interpret(DLRole r) {
		r.accept(this);
		return result4Role_;
	}

	/**
	 * Evaluates the interpretation on the given individual
	 * 
	 * @param i
	 *              an individual
	 * @return the interpretation of the given concept
	 */
	public D interpret(DLIndividual i) {
		i.accept(this);
		return result4Individual_;
	}

	/**
	 * Checks if the given axiom holds (is satisfied) in this interpretation
	 * 
	 * @param a
	 *              an axiom
	 * @return {@code true} if the axiom holds and {@code false} otherwise
	 */
	public boolean interpret(DLAxiom a) {
		a.accept(this);
		return result4Axiom_;
	}

	@Override
	public void visit(DLConceptName concept) {
		result4Concept_ = interpretation_.getInterpretation(concept);

	}

	@Override
	public void visit(DLRoleName role) {
		result4Role_ = interpretation_.getInterpretation(role);
	}

	@Override
	public void visit(DLConceptBottom concept) {
		result4Concept_ = Collections.emptySet();
	}

	@Override
	public void visit(DLConceptTop concept) {
		result4Concept_ = interpretation_.getDomain();
	}

	@Override
	public void visit(DLConceptConjunction concept) {
		concept.getFirstConjunct().accept(this);
		Set<D> result = new HashSet<>(result4Concept_);
		concept.getSecondConjunct().accept(this);
		result.retainAll(result4Concept_);
		result4Concept_ = result;
	}

	@Override
	public void visit(DLConceptDisjunction concept) {
		concept.getFirstDisjunct().accept(this);
		Set<D> result = new HashSet<>(result4Concept_);
		concept.getSecondDisjunct().accept(this);
		result.addAll(result4Concept_);
		result4Concept_ = result;
	}

	@Override
	public void visit(DLConceptNegation concept) {
		Set<D> result = new HashSet<>(interpretation_.getDomain());
		concept.getNegated().accept(this);
		result.removeAll(result4Concept_);
		result4Concept_ = result;
	}

	@Override
	public void visit(DLConceptExistentialRestiction concept) {
		concept.getRelation().accept(this);
		BinaryRelation<D> relation = result4Role_;
		concept.getFiller().accept(this);
		Set<D> result = new HashSet<>();
		result4Concept_
				.forEach(d -> result.addAll(relation.getPredecessorsOf(d)));
		result4Concept_ = result;
	}

	@Override
	public void visit(DLConceptUniversalRestiction concept) {
		// TODO: complete the interpretation
		throw new Error("Not yet implemented");
	}

	@Override
	public void visit(DLConceptInclusion axiom) {
		axiom.getSubConcept().accept(this);
		Set<D> subConceptInterpretation = result4Concept_;
		axiom.getSuperConcept().accept(this);
		Set<D> superConceptInterpretation = result4Concept_;
		result4Axiom_ = superConceptInterpretation
				.containsAll(subConceptInterpretation);
	}

	@Override
	public void visit(DLConceptEquivalence axiom) {
		axiom.getFirstConcept().accept(this);
		Set<D> firstConceptInterpretation = result4Concept_;
		axiom.getSecondConcept().accept(this);
		Set<D> secondConceptInterpretation = result4Concept_;
		result4Axiom_ = firstConceptInterpretation
				.equals(secondConceptInterpretation);
	}

	@Override
	public void visit(DLConceptAssertion axiom) {
		axiom.getType().accept(this);
		Set<D> typeInterpretation = result4Concept_;
		axiom.getInstance().accept(this);
		D instanceInterpretation = result4Individual_;
		result4Axiom_ = typeInterpretation.contains(instanceInterpretation);
	}

	@Override
	public void visit(DLRoleAssertion axiom) {
		axiom.getRelation().accept(this);
		BinaryRelation<D> relationInterpretation = result4Role_;
		axiom.getFirstInstance().accept(this);
		D firstInstanceInterpretation = result4Individual_;
		axiom.getSecondInstance().accept(this);
		D secondInstanceInterpretation = result4Individual_;
		result4Axiom_ = relationInterpretation.getPairs().contains(new Pair<>(
				firstInstanceInterpretation, secondInstanceInterpretation));
	}

	@Override
	public void visit(DLIndividual individual) {
		result4Individual_ = interpretation_.getInterpretation(individual);
	}

}
