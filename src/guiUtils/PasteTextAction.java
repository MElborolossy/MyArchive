package guiUtils;

import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.event.ActionEvent;

public class PasteTextAction extends TextAction {

	public PasteTextAction() {
		super("");
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed(ActionEvent e) {
		JTextComponent component = getFocusedComponent();
		component.paste();
	}
}