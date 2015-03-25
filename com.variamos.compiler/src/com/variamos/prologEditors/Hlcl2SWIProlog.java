package com.variamos.prologEditors;

import java.util.List;
import java.util.Set;

import com.variamos.compiler.solverSymbols.LabelingOrder;
import com.variamos.compiler.solverSymbols.SWIPrologSymbols;
import com.variamos.core.exceptions.TechnicalException;
import com.variamos.hlcl.BooleanOperation;
import com.variamos.hlcl.Domain;
import com.variamos.hlcl.HlclProgram;
import com.variamos.hlcl.HlclUtil;
import com.variamos.hlcl.Identifier;
import com.variamos.hlcl.IntervalDomain;
import com.variamos.hlcl.RangeDomain;
import com.variamos.hlcl.ComposedDomain;

/**
 * @author Luisa Rinc�n
 * Modified by jcmunoz to support composed domains
 *
 */
public class Hlcl2SWIProlog extends Hlcl2Prolog implements SWIPrologSymbols {

	@Override
	protected void writeFooter(StringBuilder out) {

		StringBuilder footerExpression = new StringBuilder();
		StringBuilder insideLabeling = new StringBuilder();
		int idx = 0;
		if (params.isFdLabeling()) {
			footerExpression.append(LABELING);

			if (params.isFf()) {
				insideLabeling.append(FF);
			}
			if (params.isOrder()) {
				if (params.getLabelingOrder() == null
						|| params.getOrderExpressions() == null
						|| (params.getLabelingOrder().size() != params
								.getOrderExpressions().size())) {
					throw new TechnicalException("order params are missed");
				}

				// Add a comma after the FF instruction.
				if (params.isFf()) {
					insideLabeling.append(COMMA);
				}

				for (LabelingOrder labOrder : params.getLabelingOrder()) {
					if (labOrder.equals(LabelingOrder.MIN)) {
						insideLabeling.append(MIN);

					} else {
						insideLabeling.append(MAX);
					}
					insideLabeling.append(OPEN_PARENTHESIS);

					StringBuilder orderExpression = new StringBuilder();
					transformNumericExpression(params.getOrderExpressions()
							.get(idx), orderExpression);
					insideLabeling.append(orderExpression);
					insideLabeling.append(CLOSE_PARENHESIS);
					idx++;

					if (idx <= (params.getOrderExpressions().size() - 1)) {
						insideLabeling.append(COMMA);
					}
				}
			}

			footerExpression.append(OPEN_PARENTHESIS);
			if (insideLabeling.length() > 0) {
				footerExpression.append(OPEN_BRACKET);
				footerExpression.append(insideLabeling);
				footerExpression.append(CLOSE_BRACKET);
				footerExpression.append(COMMA);
			}
			footerExpression.append(INVOCATION);
			footerExpression.append(CLOSE_PARENHESIS);
			footerExpression.append(DOT);
			out.append(footerExpression);
		}

	}

	@Override
	protected void transformBooleanOperation(BooleanOperation e,
			StringBuilder out) {
		out.append(OPEN_PARENTHESIS);
		transformBooleanExpression(e.getLeft(), out);

		out.append(SPACE);
		switch (e.getOperator()) {
		case And:
			out.append(AND);
			break;
		case DoubleImplies:
			out.append(EQUIVALENT);
			break;
		case Implies:
			out.append(IMPLIES);
			break;
		case Or:
			out.append(OR);
			break;
		}
		out.append(SPACE);
		transformBooleanExpression(e.getRight(), out);
		out.append(CLOSE_PARENHESIS);
	}

	@Override
	protected void writeHeader(HlclProgram program, StringBuilder out) {
		Set<Identifier> ids = HlclUtil.getUsedIdentifiers(program);
		out.append(HEADER);
		out.append(makeDomainsAndVariables(ids));
	}

	private StringBuilder makeDomainsAndVariables(Set<Identifier> ids) {
		// Se contruye la lista de caracter�sticas y de dominios
		StringBuilder dommainAndVariables = new StringBuilder("L=[");
		StringBuilder variablesList = new StringBuilder();
		StringBuilder domainString = new StringBuilder();
		String id = "";
		for (Identifier identifier : ids) {
			id = identifier.getId();
			variablesList.append(id);
			variablesList.append(COMMA);

			if (identifier.getDomain() instanceof RangeDomain) {
				// Sample WidthResolution in 0..1
				// jcmunoz: new method for range domains
				domainString.append(getRangeDomain(identifier.getDomain(), id));
			} else if (identifier.getDomain() instanceof IntervalDomain) {

				// Sample WidthResolution in 0 \/ 800 \/ 1024 \/
				// 1366
				// jcmunoz: new method for interval domains
				domainString.append(getIntervalDomain(identifier.getDomain(),
						id));

				// jcmunoz: new condition for composed domains
			} else if (identifier.getDomain() instanceof ComposedDomain) {
				for (Domain domain : ((ComposedDomain) identifier.getDomain())
						.getDomains()) {
					if (domain instanceof RangeDomain) {
						// Sample WidthResolution in 0..1
						domainString.append(getRangeDomain(domain, id));
					} else if (domain instanceof IntervalDomain) {

						// Sample WidthResolution in 0 \/ 800 \/ 1024 \/
						// 1366
						domainString.append(getIntervalDomain(domain, id));
					}
					// set the id to null to define the variable only one time.
					id = null;
				}
			}

			domainString.append(COMMA);
		}
		variablesList.append("],");
		domainString.append(LF);
		dommainAndVariables.append(variablesList.toString().replace(",]",
				CLOSE_BRACKET));
		dommainAndVariables.append(LF);
		// add domain string
		dommainAndVariables.append(domainString);

		// TODO implements composed domain transformation

		return dommainAndVariables;
	}

	/**
	 * New method to support Range individual or composed domains
	 * @param domain
	 * @param id: is null for second and additional domain ranges
	 * @return
	 */
	private StringBuffer getRangeDomain(Domain domain, String id) {
		StringBuffer domainString = new StringBuffer();
		Integer lowerValue = ((RangeDomain) domain).getLowerValue();
		Integer upperValue = ((RangeDomain) domain).getUpperValue();
		if (id != null) {
			domainString.append(id);
			domainString.append(IN);
		} else
			domainString.append(ORDOMAIN);
		domainString.append(lowerValue);
		domainString.append(DOMAIN_INTERVAL);
		domainString.append(upperValue);
		return domainString;
	}
	/**
	 * @author jcmunoz {jcmunoz@gmail.com}
	 * New method to support Internal individual or composed domains
	 * @param domain
	 * @param id: is null for second and additional domain intervals
	 * @return
	 */
	private StringBuffer getIntervalDomain(Domain domain, String id) {
		StringBuffer domainString = new StringBuffer();
		List<Integer> domains = ((IntervalDomain) domain).getRangeValues();
		if (id != null) {
			domainString.append(id);
			domainString.append(IN);
		} else
			domainString.append(ORDOMAIN);
		for (int i = 0; i < domains.size(); i++) {
			Integer domainValue = domains.get(i);
			domainString.append(Integer.toString(domainValue));
			if (i < domains.size() - 1) {
				domainString.append(ORDOMAIN);

			}

		}
		return domainString;
	}

	@Override
	protected void writeHeaderWithDefinedDomains(HlclProgram program,
			List<String> domainList, StringBuilder out) {
		// TODO Auto-generated method stub

	}

}