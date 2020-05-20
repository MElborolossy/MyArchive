package guiUtils;

import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.event.ActionEvent;

public class PasteTextAction extends TextAction {

	public PasteTextAction() {
		super("");
	}

	public void actionPerformed(ActionEvent e) {
		JTextComponent component = getFocusedComponent();
		component.paste();
	}
}