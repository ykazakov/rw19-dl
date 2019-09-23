package it.unibz.inf.rulemlrr19.dl.normalization;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptBottom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptConjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptNegation;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptTop;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptUniversalRestiction;

/**
 * The transformation to negation normal form (NNF). See Definition 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
public class NNF {

	private static DLConcept RESULT_;

	public static DLConcept getNNF(DLConcept c) {
		c.accept(NNF_CONVERTER);
		return RESULT_;
	}

	/**
	 * Converts the visited concept to NNF
	 */
	private static final DLConcept.Visitor NNF_CONVERTER = new DLConcept.Visitor() {

		@Override
		public void visit(DLConceptTop concept) {
			RESULT_ = concept;
		}

		@Override
		public void visit(DLConceptBottom concept) {
			RESULT_ = concept;
		}

		@Override
		public void visit(DLConceptName concept) {
			RESULT_ = concept;
		}

		@Override
		public void visit(DLConceptConjunction concept) {
			concept.getFirstConjunct().accept(this);
			DLConcept firstConjunctNNF = RESULT_;
			concept.getSecondConjunct().accept(this);
			DLConcept secondConjunctNNF = RESULT_;
			RESULT_ = new DLConceptConjunction(firstConjunctNNF,
					secondConjunctNNF);
		}

		@Override
		public void visit(DLConceptDisjunction concept) {
			concept.getFirstDisjunct().accept(this);
			DLConcept firstDisjunctNNF = RESULT_;
			concept.getSecondDisjunct().accept(this);
			DLConcept secondDisjunctNNF = RESULT_;
			RESULT_ = new DLConceptDisjunction(firstDisjunctNNF,
					secondDisjunctNNF);
		}

		@Override
		public void visit(DLConceptExistentialRestiction concept) {
			concept.getFiller().accept(this);
			DLConcept fillerNNF = RESULT_;
			RESULT_ = new DLConceptExistentialRestiction(concept.getRelation(),
					fillerNNF);
		}

		@Override
		public void visit(DLConceptUniversalRestiction concept) {
			concept.getFiller().accept(this);
			DLConcept fillerNNF = RESULT_;
			RESULT_ = new DLConceptUniversalRestiction(concept.getRelation(),
					fillerNNF);
		}

		@Override
		public void visit(DLConceptNegation concept) {
			concept.getNegated().accept(NNF_CONVERTOR_NEGATION);
		}

	};

	/**
	 * 
	 * Converts the negation of the visited concept to NNF
	 */
	private static final DLConcept.Visitor NNF_CONVERTOR_NEGATION = new DLConcept.Visitor() {

		@Override
		public void visit(DLConceptTop concept) {
			RESULT_ = new DLConceptBottom();
		}

		@Override
		public void visit(DLConceptBottom concept) {
			RESULT_ = new DLConceptTop();
		}

		@Override
		public void visit(DLConceptConjunction concept) {
			concept.getFirstConjunct().accept(this);
			DLConcept firstConjunctNNF = RESULT_;
			concept.getSecondConjunct().accept(this);
			DLConcept secondConjunctNNF = RESULT_;
			RESULT_ = new DLConceptDisjunction(firstConjunctNNF,
					secondConjunctNNF);
		}

		@Override
		public void visit(DLConceptDisjunction concept) {
			concept.getFirstDisjunct().accept(this);
			DLConcept firstDisjunctNNF = RESULT_;
			concept.getSecondDisjunct().accept(this);
			DLConcept secondDisjunctNNF = RESULT_;
			RESULT_ = new DLConceptConjunction(firstDisjunctNNF,
					secondDisjunctNNF);
		}

		@Override
		public void visit(DLConceptExistentialRestiction concept) {
			concept.getFiller().accept(this);
			DLConcept fillerNNF = RESULT_;
			RESULT_ = new DLConceptUniversalRestiction(concept.getRelation(),
					fillerNNF);

		}

		@Override
		public void visit(DLConceptUniversalRestiction concept) {
			concept.getFiller().accept(this);
			DLConcept fillerNNF = RESULT_;
			RESULT_ = new DLConceptExistentialRestiction(concept.getRelation(),
					fillerNNF);
		}

		@Override
		public void visit(DLConceptName concept) {
			RESULT_ = new DLConceptNegation(concept);
		}

		@Override
		public void visit(DLConceptNegation concept) {
			concept.getNegated().accept(NNF_CONVERTER);
		}

	};

}
