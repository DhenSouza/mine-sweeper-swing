package br.com.dhentech.cm.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.dhentech.cm.model.EventField;
import br.com.dhentech.cm.model.Field;
import br.com.dhentech.cm.model.ObserverField;

public class ButtonField extends JButton implements ObserverField, MouseListener {
	private static final long serialVersionUID = 1L;

	private final Color BG_DEFAULT = new Color(184, 184, 184);
	private final Color BG_MARKED = new Color(8, 179, 247);
	private final Color BG_EXPLODE = new Color(189, 66, 68);
	private final Color TEXTG_GREEN = new Color(0, 100, 0);

	private Field field;

	public ButtonField(Field field) {
		this.field = field;
		setBackground(BG_DEFAULT);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));

		addMouseListener(this);
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
			aplicatedStyleDefault();
			break;
		}
		
		// For?a o reset na tela, uma garantia
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}

	private void aplicatedStyleDefault() {
		setBackground(BG_DEFAULT);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicatedStyleExplode() {
		setBackground(BG_EXPLODE);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void aplicatedStyleMark() {
		setBackground(BG_MARKED);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void aplicatedStyleOpen() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if (field.isMined()) {
			setBackground(BG_EXPLODE);
			return;
		}

		setBackground(BG_DEFAULT);

		switch (field.minesInTheNeighborhood()) {
		case 1:
			setForeground(TEXTG_GREEN);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}

		String value = !field.safeneighborhood() ? field.minesInTheNeighborhood() + "" : "";
		setText(value);
	}

	// Interface do eventos do mouse

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			field.openField();
		} else {
			field.changeMarking();
		}

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
