package com.variamos.gui.perspeditor.actions;

import java.awt.event.ActionEvent;

import com.mxgraph.util.mxResources;
import com.variamos.gui.core.viewcontrollers.AbstractVariamoGUIAction;
import com.variamos.gui.maineditor.VariamosGraphEditor;

@SuppressWarnings("serial")
public class VerificationAction extends AbstractVariamoGUIAction {

	public VerificationAction() {
		this.putValue(SHORT_DESCRIPTION, mxResources.get("verifyElements"));
	}

	/**
		 * 
		 */
	public void actionPerformed(ActionEvent e) {
		VariamosGraphEditor editor = getEditor(e);
		editor.clearNotificationBar();
		//editor.verifyErrors();
		editor.verify();
	}
} 