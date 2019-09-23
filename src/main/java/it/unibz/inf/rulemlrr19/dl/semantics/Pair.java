package it.unibz.inf.rulemlrr19.dl.semantics;

import java.util.Objects;

/**
 * A object identified by two elements. The order of the elements is important,
 * i.e., <a,b> is different from <b,a>.
 * 
 * @author Yevgeny Kazakov
 *
 * @param <X>
 *                the type of the first elements of this pair
 * @param <Y>
 *                the type of the second elements of this pair
 */
public class Pair<X, Y> {

	/**
	 * The first component of the pair.
	 */
	private final X first_;

	/**
	 * The first component of the pair.
	 */
	private final Y second_;

	public Pair(X first, Y second) {
		this.first_ = Objects.requireNonNull(first);
		this.second_ = Objects.requireNonNull(second);
	}

	public X getFirst() {
		return first_;
	}

	public Y getSecond() {
		return second_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Pair.class, first_, second_);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof Pair<?, ?>) {
			Pair<?, ?> other = (Pair<?, ?>) obj;
			return first_.equals(other.first_) && second_.equals(other.second_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "<" + first_ + ", " + second_ + ">";
	}

}
