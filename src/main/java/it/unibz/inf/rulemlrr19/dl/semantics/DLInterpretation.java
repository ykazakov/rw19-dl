package it.unibz.inf.rulemlrr19.dl.semantics;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLIndividual;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRoleName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLSignature;

/**
 * Specifies how the signature elements are interpreted. See Section 2.2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @see DLSignature
 * 
 * @author Yevgeny Kazakov
 *
 * @param <D>
 *                the type of domain elements of the signature
 */
public class DLInterpretation<D> {

	/**
	 * A nonempty set of domain elements
	 */
	private final Set<D> domain_;

	/**
	 * Interpretation of atomic concepts
	 */
	private final Map<DLConceptName, Set<D>> conceptAssignment_ = new HashMap<>();

	/**
	 * Interpretation of atomic roles
	 */
	private final Map<DLRoleName, BinaryRelation<D>> roleAssignment_ = new HashMap<>();

	/**
	 * Interpretation of individuals
	 */
	private final Map<DLIndividual, D> individualAssignment_ = new HashMap<>();

	/**
	 * The default interpretation of all individuals
	 */
	private D defaultIndividualAssignment_;

	/**
	 * Creates a new interpretation with the given elements in the domain
	 * 
	 * @param elements
	 */
	public DLInterpretation(Stream<D> elements) {
		this.domain_ = new HashSet<>();
		elements.forEach(d -> {
			Objects.requireNonNull(d);
			domain_.add(d);
			defaultIndividualAssignment_ = d;
		});
		if (domain_.isEmpty()) {
			throw new Error(
					"There should be at least one element in the domain!");
		}
	}

	/**
	 * @return the domain of this interpretation
	 */
	public Set<D> getDomain() {
		// protect against direct modifications
		return Collections.unmodifiableSet(domain_);
	}

	/**
	 * @param c
	 *              a concept name
	 * @return the interpretation of the given concept name
	 */
	public Set<D> getInterpretation(DLConceptName c) {
		Set<D> result = conceptAssignment_.get(c);
		if (result != null) {
			// protect against direct modifications
			return Collections.unmodifiableSet(result);
		}
		// else
		return Collections.emptySet();
	}

	/**
	 * @param r
	 *              a role name
	 * @return the interpretation of the given role me
	 */
	public BinaryRelation<D> getInterpretation(DLRoleName r) {
		BinaryRelation<D> result = roleAssignment_.get(r);
		if (result != null) {
			return result;
		}
		// else
		return new BinaryRelation<>(Stream.empty());
	}

	/**
	 * @param i
	 *              an individual
	 * @return the interpretation of the given individual
	 */
	public D getInterpretation(DLIndividual i) {
		D result = individualAssignment_.get(i);
		if (result == null) {
			// the individual was not assigned
			return defaultIndividualAssignment_;
		}
		return result;
	}

	/**
	 * Assign the interpretation of the given atomic concepts to the specified
	 * elements. The previous assignment for this concept is overwritten.
	 * 
	 * @param c
	 *                     an atomic concept
	 * @param elements
	 *                     the elements by which the concept must be interpreted
	 * @return the resulting interpretation after the assignment
	 */
	public DLInterpretation<D> assign(DLConceptName c, Stream<D> elements) {
		Set<D> conceptInterpretation = new HashSet<D>();
		elements.forEach(d -> conceptInterpretation.add(d));
		conceptAssignment_.put(c, conceptInterpretation);
		return this;
	}

	/**
	 * Assign the interpretation of the given atomic role to the specified pairs
	 * of elements. The previous assignment for this role is overwritten.
	 * 
	 * @param r
	 *                  a role
	 * @param pairs
	 *                  the pairs of elements by which the role must be
	 *                  interpreted
	 * @return the resulting interpretation after the assignment
	 */
	public DLInterpretation<D> assign(DLRoleName r, Stream<Pair<D, D>> pairs) {
		roleAssignment_.put(r, new BinaryRelation<>(pairs));
		return this;
	}

	/**
	 * Assign the interpretation of the given individual to the specified
	 * element. The previous assignment for this individual is overwritten.
	 * 
	 * @param i
	 *                    an individual
	 * @param element
	 *                    the element by which the individual must be
	 *                    interpreted
	 * @return the resulting interpretation after the assignment
	 */
	public DLInterpretation<D> assign(DLIndividual i, D element) {
		individualAssignment_.put(i, Objects.requireNonNull(element));
		return this;
	}

}
