package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A collection of {@link DLAxiom} objects
 * 
 * @author Yevgeny Kazakov
 *
 */
public class DLOntology {

	/**
	 * The {@link DLTBoxAxiom}s of this ontology
	 */
	private final Set<DLTBoxAxiom> tBox_ = new HashSet<>();

	/**
	 * The {@link DLABoxAxiom}s of this ontology
	 */
	private final Set<DLABoxAxiom> aBox_ = new HashSet<>();

	/**
	 * @return all {@link DLTBoxAxiom}s of this ontology
	 */
	public Stream<? extends DLTBoxAxiom> tBox() {
		return tBox_.stream();
	}

	/**
	 * @return all {@link DLABoxAxiom}s of this ontology
	 */
	public Stream<? extends DLABoxAxiom> aBox() {
		return aBox_.stream();
	}

	public Stream<? extends DLAxiom> axioms() {
		return Stream.concat(tBox(), aBox());
	}

	public DLSignature getSignature() {
		DLSignature s = new DLSignature();
		axioms().forEach(ax -> s.addSymbolsOf(ax));
		return s;
	}

	/**
	 * Adds the given {@link DLTBoxAxiom} to this ontology
	 * 
	 * @param axiom
	 */
	public void add(DLTBoxAxiom axiom) {
		tBox_.add(axiom);
	}

	/**
	 * Adds the given {@link DLABoxAxiom} to this ontology
	 * 
	 * @param axiom
	 */
	public void add(DLABoxAxiom axiom) {
		aBox_.add(axiom);
	}

	/**
	 * Removes the given {@link DLTBoxAxiom} from this ontology
	 * 
	 * @param axiom
	 */
	public void remove(DLTBoxAxiom axiom) {
		tBox_.remove(axiom);
	}

	/**
	 * Removes the given {@link DLABoxAxiom} from this ontology
	 * 
	 * @param axiom
	 */
	public void remove(DLABoxAxiom axiom) {
		aBox_.remove(axiom);
	}

	/**
	 * Adds the given {@link DLAxiom} to this ontology
	 * 
	 * @param axiom
	 * @return the resulting ontology with the added axiom
	 */
	public DLOntology add(DLAxiom axiom) {
		axiom = Objects.requireNonNull(axiom);
		axiom.accept(new DLAxiom.Visitor() {

			@Override
			public void visit(DLConceptEquivalence axiom) {
				add(axiom);
			}

			@Override
			public void visit(DLConceptInclusion axiom) {
				add(axiom);
			}

			@Override
			public void visit(DLRoleAssertion axiom) {
				add(axiom);
			}

			@Override
			public void visit(DLConceptAssertion axiom) {
				add(axiom);
			}
		});
		return this;
	}

	/**
	 * Removes the given {@link DLAxiom} from this ontology
	 * 
	 * @param axiom
	 * @return the resulting ontology after removal of the axiom
	 */
	public DLOntology remove(DLAxiom axiom) {
		axiom = Objects.requireNonNull(axiom);
		axiom.accept(new DLAxiom.Visitor() {

			@Override
			public void visit(DLConceptEquivalence axiom) {
				remove(axiom);
			}

			@Override
			public void visit(DLConceptInclusion axiom) {
				remove(axiom);
			}

			@Override
			public void visit(DLRoleAssertion axiom) {
				remove(axiom);
			}

			@Override
			public void visit(DLConceptAssertion axiom) {
				remove(axiom);
			}
		});
		return this;
	}

}
