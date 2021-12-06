package br.com.dhentech.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

	private final Integer line;
	private final Integer column;

	private Integer idField;
	private boolean mined = false;
	private boolean open = false;
	private boolean marked = false;

	private List<Field> neighborhoods = new ArrayList<Field>();
	private List<ObserverField> observers = new ArrayList<>();

	public Field(Integer line, Integer column) {
		this.line = line;
		this.column = column;
	}

	// adiciona um observador na lista de observadores
	public void addObservers(ObserverField observer) {
		observers.add(observer);
	}

	// Percorre todos os observadores ele chama o metodo eventoOcorreu
	private void notifyObservers(EventField event) {
		observers.stream().forEach(observer -> observer.eventOccurred(this, event));
	}

	boolean addNeighbor(Field neighbor) {
		boolean lineDif = this.line != neighbor.line;
		boolean columnDif = this.column != neighbor.column;
		boolean diagonal = lineDif && columnDif;

		int deltaLine = Math.abs(this.line - neighbor.line);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int deltaGeneral = deltaColumn + deltaLine;

		if (deltaGeneral == 1 && !diagonal) {
			neighborhoods.add(neighbor);
			return true;
		} else if (deltaGeneral == 2 && diagonal) {
			neighborhoods.add(neighbor);
			return true;
		}

		return false;
	}

	/*
	 * Alterar a marks quando o camopo estiver fechado Change tag when field is
	 * closed
	 */
	public void changeMarking() {
		if (!open) {
			marked = !marked;

			if (marked) {
				notifyObservers(EventField.TOMARK);
			} else {
				notifyObservers(EventField.MARKOFF);
			}
		}
	}

	public boolean openField() {
		if (!open && !marked) {
			if (mined) {
				notifyObservers(EventField.EXPLODE);
				return true;
			}

			setOpen(true);

			if (safeneighborhood()) {
				neighborhoods.forEach(v -> v.openField());
			}

			return true;
		}
		return false;
	}

	/*
	 * Garante que quando clica em um campo, os campos ao lado sejam seguros, caso
	 * contrario nao é aceito
	 * 
	 * Ensures that when you click on a field, the fields beside it are safe,
	 * otherwise it is not accepted
	 */

	public boolean safeneighborhood() {
		return neighborhoods.stream().noneMatch(v -> v.mined);
	}

	void mineField() {
		mined = true;
	}

	public boolean isMined() {
		return mined;
	}

	public boolean isMarked() {
		return marked;
	}

	void setOpen(boolean open) {
		this.open = open;

		if (open) {
			notifyObservers(EventField.OPEN);
		}
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isClose() {
		return !open;
	}

	public Integer getLine() {
		return line;
	}

	public Integer getColumn() {
		return column;
	}

	/* Garantir que o objetivo seja alcançado nos campos */
	boolean objectiveAchieved() {
		boolean desvendado = !mined && open;
		boolean protegido = mined && marked;
		return desvendado || protegido;
	}

	/* Para saber a quantidade de minas que tem na vizinhança */
	public int minesInTheNeighborhood() {
		return (int) neighborhoods.stream().filter(v -> v.mined).count();
	}

	void resetGame() {
		open = false;
		mined = false;
		marked = false;
		
		notifyObservers(EventField.RESET);
	}

}
