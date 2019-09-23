package it.unibz.inf.rulemlrr19.dl.semantics;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A binary relation on elements. Consists of pairs {@code <d1,d2>} where
 * {@code d1} and {@code d2} are elements of the domain on which the relation is
 * defined. Provides some helper methods to access these tuples and modify them.
 * 
 * @author Yevgeny Kazakov
 *
 * @param <D>
 *                the type of domain elements of this relation
 */
public class BinaryRelation<D> {

	/**
	 * The set of all pairs defining this relation
	 */
	private final Set<Pair<D, D>> pairs_ = new HashSet<>();

	/**
	 * A convenience map to access for a given element {@code d1} all elements
	 * {@code d2} for which the pair {@code <d1, d2>} belongs to this relation.
	 */
	private final Map<D, Set<D>> predecessorMap_ = new HashMap<>();

	/**
	 * Creates a new binary relation from the given set of pairs
	 * 
	 * @param pairs
	 */
	public BinaryRelation(Stream<Pair<D, D>> pairs) {
		pairs.forEach(p -> add(p));
	}

	/**
	 * @return all pairs contained in this relation
	 */
	public Set<Pair<D, D>> getPairs() {
		return Collections.unmodifiableSet(pairs_);
	}

	/**
	 * @param d2
	 * @return the set of elements {@code d1} such that the pair
	 *         {@code <d1, d2>} belongs to this relation
	 */
	public Set<D> getPredecessorsOf(D d2) {
		Set<D> predecessors = predecessorMap_.get(d2);
		if (predecessors == null) {
			return Collections.emptySet();
		}
		// else
		return Collections.unmodifiableSet(predecessors);
	}

	/**
	 * Adds a given pair to this relation
	 * 
	 * @param p
	 *              the pair to be added
	 * @return the resulting relation after the addition
	 */
	BinaryRelation<D> add(Pair<D, D> p) {
		pairs_.add(p);
		Set<D> predecessor = predecessorMap_.get(p.getSecond());
		if (predecessor == null) {
			predecessor = new HashSet<>();
			predecessorMap_.put(p.getSecond(), predecessor);
		}
		predecessor.add(p.getFirst());
		return this;
	}

	@Override
	public String toString() {
		return pairs_.toString();
	}

}
