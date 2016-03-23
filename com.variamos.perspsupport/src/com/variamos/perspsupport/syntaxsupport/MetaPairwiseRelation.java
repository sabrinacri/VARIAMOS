package com.variamos.perspsupport.syntaxsupport;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.variamos.perspsupport.instancesupport.InstElement;
import com.variamos.perspsupport.opers.OpersPairwiseRel;
import com.variamos.perspsupport.opersint.IntOpersPairwiseRel;
import com.variamos.perspsupport.opersint.IntOpersRelType;

/**
 * A class to represented edges of the meta model. Extends from MetaElement
 * adding the allowed semantic concepts and edge types. An dynamic attribute for
 * selected semantic concept Part of PhD work at University of Paris 1 Refactor:
 * Merged with MetaEdge
 * 
 * @author Juan C. Mu�oz Fern�ndez <jcmunoz@gmail.com>
 * 
 * @version 1.1
 * @since 2014-12-10
 * @see com.variamos.perspsupport.syntaxsupport.MetaElement * @see
 *      com.variamos.semantic.semanticsupport.SemanticPairwiseRelation
 */
public class MetaPairwiseRelation extends MetaElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2665541567934067387L;
	private int iniLowCardinality;
	private int iniHighCardinality;
	private int endLowCardinality;
	private int endHighCardinality;
	private String iniDescription;
	private String endDescription;
	private boolean arrowDirection;
	private TypeOfLine typeOfLine;
	private String palette = "";
	/**
	 * 
	 */
	// private IntSemanticPairwiseRelation semanticPairwiseRelation;

	public static final/**
						 * CanonicalName of DirectSemanticEdge - no direct reference allowed
						 */
	String VAR_SEMANTICPAIRWISEREL_CLASS = IntOpersPairwiseRel.class
			.getCanonicalName(),
	/**
	 * Name of the semantic relation attribute
	 */
	VAR_SEMANTICPAIRWISEREL_IDEN = "semanticRelation",
	/**
	 * Display name of the semantic relation attribute
	 */
	VAR_SEMANTICPAIRWISEREL_NAME = "Semantic Relation",
	/**
			 * 
			 */
	VAR_METAGENERALCONSTRAINT = "generalConstraint",
	/**
			 * 
			 */
	VAR_METAGENERALCONSTRAINTNAME = "Constraint Expression";

	public String getPalette() {
		return palette;
	}

	public void setPalette(String palette) {
		this.palette = palette;
	}

	public MetaPairwiseRelation() {

	}

	public MetaPairwiseRelation(String identifier, boolean visible,
			boolean editable, String name, String style, String description,
			int width, int height, String image, int borderStroke,
			InstElement instSemanticElement) {
		this(identifier, visible, editable, name, style, description, width,
				height, image, borderStroke, instSemanticElement, 1, 1, 1, 1,
				"", "", false, TypeOfLine.solid);
	}

	public MetaPairwiseRelation(String identifier, boolean visible,
			boolean editable, String name, String style, String description,
			int width, int height, String image, int borderStroke,
			InstElement instSemanticElement, String palette) {
		this(identifier, visible, editable, name, style, description, width,
				height, image, borderStroke, instSemanticElement, 1, 1, 1, 1,
				"", "", false, TypeOfLine.solid);
		this.palette = palette;
	}

	public MetaPairwiseRelation(String identifier, boolean visible,
			boolean editable, String name, String style, String description,
			int width, int height, String image, int borderStroke) {
		this(identifier, visible, editable, name, style, description, width,
				height, image, borderStroke, null, 1, 1, 1, 1, "", "", false,
				TypeOfLine.solid);
	}

	public MetaPairwiseRelation(String identifier, boolean visible,
			boolean editable, String name, String style, String description,
			int width, int height, String image, int borderStroke,
			InstElement instSemanticElement, int iniLowCardinality,
			int iniHighCardinality, int endLowCardinality,
			int endHighCardinality, String iniDescription,
			String endDescription, boolean arrowDirection, TypeOfLine typeOfLine) {
		super(identifier, visible, editable, name, style, description, width,
				height, image, borderStroke, instSemanticElement);

		this.iniLowCardinality = iniLowCardinality;
		this.iniHighCardinality = iniHighCardinality;
		this.endLowCardinality = endLowCardinality;
		this.endHighCardinality = endHighCardinality;
		this.iniDescription = iniDescription;
		this.endDescription = endDescription;
		this.arrowDirection = arrowDirection;
		this.typeOfLine = typeOfLine;
	}

	public MetaPairwiseRelation(String identifier, boolean visible,
			boolean editable, String name, String style, String description,
			int width, int height, String image, int borderStroke,
			InstElement instSemanticElement,
			List<String> disPropVisibleAttributes,
			List<String> disPropEditableAttributes,
			List<String> disPanelVisibleAttributes,
			List<String> disPanelSpacersAttributes,
			Map<String, AbstractAttribute> modelingAttributes,
			int iniLowCardinality, int iniHighCardinality,
			int endLowCardinality, int endHighCardinality,
			String iniDescription, String endDescription,
			boolean arrowDirection, TypeOfLine typeOfLine) {
		super(identifier, visible, editable, name, style, description, width,
				height, image, borderStroke, instSemanticElement,
				disPropVisibleAttributes, disPropEditableAttributes,
				disPanelVisibleAttributes, disPanelSpacersAttributes,
				modelingAttributes);
		this.iniLowCardinality = iniLowCardinality;
		this.iniHighCardinality = iniHighCardinality;
		this.endLowCardinality = endLowCardinality;
		this.endHighCardinality = endHighCardinality;
		this.iniDescription = iniDescription;
		this.endDescription = endDescription;
		this.arrowDirection = arrowDirection;
		this.typeOfLine = typeOfLine;

	}

	public Set<String> getPropVisibleAttributesSet(
			List<InstElement> opersParents) {
		Set<String> modelingAttributesNames = new HashSet<String>();

		if (getTransInstSemanticElement() != null
				&& getTransInstSemanticElement().getEditableSemanticElement() != null) {
			modelingAttributesNames.addAll(getTransInstSemanticElement()
					.getEditableSemanticElement().getPropVisibleAttributes(
							opersParents));
		}

		modelingAttributesNames.addAll(super.getPropVisibleAttributesSet());
		return modelingAttributesNames;
	}

	public Set<String> getPropEditableAttributesSet(
			List<InstElement> opersParents) {
		Set<String> modelingAttributesNames = new HashSet<String>();

		if (getTransInstSemanticElement() != null
				&& getTransInstSemanticElement().getEditableSemanticElement() != null)
			modelingAttributesNames.addAll(getTransInstSemanticElement()
					.getEditableSemanticElement().getPropEditableAttributes(
							opersParents));

		modelingAttributesNames.addAll(super.getPropEditableAttributesSet());
		return modelingAttributesNames;
	}

	public Set<String> getPanelVisibleAttributesSet(
			List<InstElement> opersParents) {
		Set<String> modelingAttributesNames = new HashSet<String>();

		if (getTransInstSemanticElement() != null)
			modelingAttributesNames.addAll(getTransInstSemanticElement()
					.getEditableSemanticElement().getPanelVisibleAttributes(
							opersParents));

		modelingAttributesNames.addAll(super.getPanelVisibleAttributesSet());
		return modelingAttributesNames;
	}

	public Set<String> getPanelSpacersAttributesSet(
			List<InstElement> opersParents) {
		Set<String> modelingAttributesNames = new HashSet<String>();

		if (getTransInstSemanticElement() != null)
			modelingAttributesNames.addAll(getTransInstSemanticElement()
					.getEditableSemanticElement().getPanelSpacersAttributes(
							opersParents));

		modelingAttributesNames.addAll(super.getPanelSpacersAttributesSet());
		return modelingAttributesNames;
	}

	@Override
	public Set<String> getAllAttributesNames(List<InstElement> parents) {
		Set<String> modelingAttributesNames = new HashSet<String>();
		if (getTransInstSemanticElement() != null)
			modelingAttributesNames.addAll(getTransInstSemanticElement()
					.getAllSemanticAttributesNames(parents));
		return modelingAttributesNames;
	}

	public AbstractAttribute getSemanticAttribute(String name) {
		// FIXME
		if (getTransInstSemanticElement() != null)
			return getTransInstSemanticElement().getSemanticAttribute(name);
		return null;
	}

	public List<IntOpersRelType> getSemanticRelationTypes() {
		if (getTransInstSemanticElement() == null)
			return null;
		return ((OpersPairwiseRel) getTransInstSemanticElement()
				.getEditableSemanticElement()).getSemanticRelationTypes();
	}

	public void setIniLowCardinality(int iniLowCardinality) {
		this.iniLowCardinality = iniLowCardinality;
	}

	public void setIniHighCardinality(int iniHighCardinality) {
		this.iniHighCardinality = iniHighCardinality;
	}

	public void setEndLowCardinality(int endLowCardinality) {
		this.endLowCardinality = endLowCardinality;
	}

	public void setEndHighCardinality(int endHighCardinality) {
		this.endHighCardinality = endHighCardinality;
	}

	public void setIniDescription(String iniDescription) {
		this.iniDescription = iniDescription;
	}

	public void setEndDescription(String endDescription) {
		this.endDescription = endDescription;
	}

	public void setArrowDirection(boolean arrowDirection) {
		this.arrowDirection = arrowDirection;
	}

	public void setTypeOfLine(TypeOfLine typeOfLine) {
		this.typeOfLine = typeOfLine;
	}

	public int getIniLowCardinality() {
		return iniLowCardinality;
	}

	public int getIniHighCardinality() {
		return iniHighCardinality;
	}

	public int getEndLowCardinality() {
		return endLowCardinality;
	}

	public int getEndHighCardinality() {
		return endHighCardinality;
	}

	public String getIniDescription() {
		return iniDescription;
	}

	public String getEndDescription() {
		return endDescription;
	}

	public boolean isArrowDirection() {
		return arrowDirection;
	}

	public TypeOfLine getTypeOfLine() {
		return typeOfLine;
	}

	public String toString() {
		return "MetaGroupRelation";
	}

	public static String getClassId() {
		return "E";
	}

	public SemanticAttribute getSemanticRelation() {
		return (SemanticAttribute) getModelingAttribute(VAR_SEMANTICPAIRWISEREL_IDEN);
	}

	public char getType() {
		return 'P';
	};
}