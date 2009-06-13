package com.ziscloud.zcdiagram.validator;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.util.SWTHelper;

public class DecimalVerifyListener implements VerifyListener {

	@Override
	public void verifyText(VerifyEvent e) {
		if (SWTHelper.isDecimal(new StringBuilder(((Text) (e.getSource()))
				.getText()).insert(e.start, e.text).toString())) {
			e.doit = true;
		} else {
			e.doit = false;
		}
	}

}
