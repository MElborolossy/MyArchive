package guiUtils;

import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.event.ActionEvent;

public class CopyTextAction extends TextAction {

	public CopyTextAction() {
		super("");
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed(ActionEvent e) {
		JTextComponent component = getFocusedComponent();
		component.copy();
	}

}
