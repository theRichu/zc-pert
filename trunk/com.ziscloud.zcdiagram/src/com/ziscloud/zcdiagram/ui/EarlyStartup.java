package com.ziscloud.zcdiagram.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class EarlyStartup implements IStartup {

	@Override
	public void earlyStartup() {
		/*
		 * The registration of the listener should have been done in the UI
		 * thread since PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		 * returns null if it is called outside of the UI thread.
		 */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				final IWorkbenchWindow workbenchWindow = PlatformUI
						.getWorkbench().getActiveWorkbenchWindow();
				if (workbenchWindow != null) {

				}
			}
		});
	}
}
