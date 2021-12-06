package br.com.dhentech.cm.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.dhentech.cm.model.EventField;
import br.com.dhentech.cm.model.Field;
import br.com.dhentech.cm.model.ObserverField;

public class ButtonField extends JButton implements ObserverField {
	private static final long serialVersionUID = 1L;
	
	private final Color BG_DEFAULT = new Color(184,184,184);
	private final Color BG_MARKED = new Color(8,179,247);
	private final Color BG_EXPLODE = new Color(189,66,68);
	private final Color TEXTG_GREEN = new Color(0,100,0);
	
	private Field field;

	public ButtonField(Field field) {
		this.field = field;
		setBackground(BG_DEFAULT);
		setBorder(BorderFactory.createBevelBorder(0));
		field.addObservers(this);
	}

	@Override
	public void eventOccurred(Field field, EventField event) {
		switch (event) {
		case OPEN:
			aplicatedStyleOpen();
			break;
		case TOMARK:
			aplicatedStyleMark();
			break;
		case EXPLODE:
			aplicatedStyleExplode();
			break;
		default:
			aplicatedStyleDefaul();
			break;
		}
	}

	private void aplicatedStyleDefaul() {

	}

	private void aplicatedStyleExplode() {

	}

	private void aplicatedStyleMark() {

	}

	private void aplicatedStyleOpen() {

	}

}
