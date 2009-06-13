package com.ziscloud.zcdiagram.strategy;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.IStructuredSelection;

public final class DoubleClickColumnViewerEditorActivationStrategy extends
		ColumnViewerEditorActivationStrategy {
	public DoubleClickColumnViewerEditorActivationStrategy(ColumnViewer viewer) {
		super(viewer);
	}

	/** */
	/**
	 * Create at Jan 18, 2008 5:38:15 PM<br>
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy#isEditorActivationEvent(org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent)
	 */
	@Override
	protected boolean isEditorActivationEvent(
			ColumnViewerEditorActivationEvent event) {
		boolean singleSelect = ((IStructuredSelection) getViewer()
				.getSelection()).size() == 1;
		return singleSelect
				&& (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC || event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL);
	}
}
