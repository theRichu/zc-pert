package com.ziscloud.zcdiagram.gef;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.Resource;

public class Connection extends Element implements IPropertySource {
	private static final long serialVersionUID = -8246136359267738220L;
	private Activity activitiy;
	private String name;
	private Node source;
	private Node target;
	private boolean isCirtical;
	private boolean isVirtual;
	private static final String NAME = "name";
	private static final String SYBOL = "sybol";
	private static final String P_PERIOD = "p_period";
	private static final String P_COST = "p_cost";
	private static final String OUTPUT = "output";
	private static final String P_START = "p_start";
	private static final String P_END = "p_end";
	private static final String M_START = "m_start";
	private static final String M_END = "m_end";
	private static final String L_START = "l_start";
	private static final String L_END = "l_end";
	private static final String E_START = "e_start";
	private static final String E_END = "e_end";
	private static final String A_START = "a_start";
	private static final String A_END = "a_end";
	private static final String A_PERIOD = "a_period";
	private static final String A_COST = "a_cost";
	private static final String BUILDER = "builder";
	private static final String R_DAYS = "r_days";
	private static final String R_COST = "r_cost";
	private static final String RMARKS = "rmarks";

	/**
	 * @param source
	 * @param target
	 */
	public Connection(int id, Activity activitiy, Node source, Node target,
			String name, boolean isCirtical, boolean isVirtual) {
		super();
		this.id = id;
		this.activitiy = activitiy;
		this.source = source;
		this.target = target;
		this.name = name;
		this.isCirtical = isCirtical;
		this.isVirtual = isVirtual;
		source.addOutput(this);
		target.addInput(this);
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public Node getTarget() {
		return this.target;
	}

	public Node getSource() {
		return this.source;
	}

	public String getLabel() {
		return name;
	}

	public void setLabel(String label) {
		name = label;
	}

	public boolean isCirtical() {
		return isCirtical;
	}

	public void setCirtical(boolean isCirtical) {
		this.isCirtical = isCirtical;
	}

	public boolean isVirtual() {
		return isVirtual;
	}

	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public Activity getActivitiy() {
		return activitiy;
	}

	public void setActivitiy(Activity activitiy) {
		this.activitiy = activitiy;
	}

	// implement the method for IPropertySource
	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(NAME, Resource.get(Resource.A_NAME)),
				new TextPropertyDescriptor(SYBOL, Resource
						.get(Resource.A_SYBOL)),
				new TextPropertyDescriptor(P_PERIOD, Resource
						.get(Resource.A_P_PERIOD)),
				new TextPropertyDescriptor(P_COST, Resource
						.get(Resource.A_P_COST)),
				new TextPropertyDescriptor(OUTPUT, Resource
						.get(Resource.A_OUTPUT)),
				new TextPropertyDescriptor(P_START, Resource
						.get(Resource.A_P_START)),
				new TextPropertyDescriptor(P_END, Resource
						.get(Resource.A_P_END)),
				new TextPropertyDescriptor(M_START, Resource
						.get(Resource.A_M_START)),
				new TextPropertyDescriptor(M_END, Resource
						.get(Resource.A_M_END)),
				new TextPropertyDescriptor(L_START, Resource
						.get(Resource.A_L_START)),
				new TextPropertyDescriptor(L_END, Resource
						.get(Resource.A_L_END)),
				new TextPropertyDescriptor(E_START, Resource
						.get(Resource.A_E_START)),
				new TextPropertyDescriptor(E_END, Resource
						.get(Resource.A_E_END)),
				new TextPropertyDescriptor(A_START, Resource
						.get(Resource.A_A_START)),
				new TextPropertyDescriptor(A_END, Resource
						.get(Resource.A_A_END)),
				new TextPropertyDescriptor(A_PERIOD, Resource
						.get(Resource.A_A_PERIOD)),
				new TextPropertyDescriptor(A_COST, Resource
						.get(Resource.A_A_COST)),
				new TextPropertyDescriptor(BUILDER, Resource
						.get(Resource.A_BUILDER)),
				new TextPropertyDescriptor(R_DAYS, Resource
						.get(Resource.A_R_DAYS)),
				new TextPropertyDescriptor(R_COST, Resource
						.get(Resource.A_R_COST)),
				new TextPropertyDescriptor(RMARKS, Resource
						.get(Resource.A_RMARKS)) };
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (NAME.equals(id))
			return getActivitiy().getName();
		if (SYBOL.equals(id))
			return getActivitiy().getSymbol();
		if (P_PERIOD.equals(id))
			return getActivitiy().getPlanPeriod();
		if (P_COST.equals(id))
			return getActivitiy().getPlanCost();
		if (OUTPUT.equals(id))
			return getActivitiy().getOutput();
		if (P_START.equals(id))
			return getActivitiy().getPlanStartDate();
		if (P_END.equals(id))
			return getActivitiy().getPlanEndDate();
		if (M_START.equals(id))
			return getActivitiy().getMustStartDate();
		if (M_END.equals(id))
			return getActivitiy().getMustEndDate();
		if (L_START.equals(id))
			return getActivitiy().getLaterStartDate();
		if (L_END.equals(id))
			return getActivitiy().getLaterEndDate();
		if (E_START.equals(id))
			return getActivitiy().getEarlyStartDate();
		if (E_END.equals(id))
			return getActivitiy().getEarlyEndDate();
		if (A_START.equals(id))
			return getActivitiy().getActualStartDate();
		if (A_END.equals(id))
			return getActivitiy().getActualEndDate();
		if (A_PERIOD.equals(id))
			return getActivitiy().getActualPeriod();
		if (A_COST.equals(id))
			return getActivitiy().getActualCost();
		if (BUILDER.equals(id))
			return getActivitiy().getBuilder();
		if (R_DAYS.equals(id))
			return getActivitiy().getRarDays();
		if (R_COST.equals(id))
			return getActivitiy().getRarCost();
		if (RMARKS.equals(id))
			return getActivitiy().getRemarks();
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		
	}

}