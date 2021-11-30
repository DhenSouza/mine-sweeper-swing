package br.com.dhentech.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements ObserverField {

	private int lines;
	private int columns;
	private int mines;

	private final List<Field> fields = new ArrayList<>();
	private final List<Consumer<ResultEvent>> observers = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;

		generateFields();
		AssociateNeighbors();
		drawMinas();

	}

	public void addObservers(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}

	private void notifyObservers(Boolean result) {
		observers.stream().forEach(observer -> observer.accept(new ResultEvent(result)));
	}

	public void open(int line, int column) {
		fields.parallelStream().filter(f -> f.getLine() == line && f.getColumn() == column).findFirst()
				.ifPresent(f -> f.openField());
	}

	public void changeMarking(int line, int column) {
		fields.parallelStream().filter(f -> f.getLine() == line && f.getColumn() == column).findFirst()
				.ifPresent(f -> f.changeMarking());
	}

	private void generateFields() {
		for (int line = 0; line < this.lines; line++) {
			for (int column = 0; column < this.columns; column++) {
				Field field = new Field(line, column);
				field.addObservers(this);
				fields.add(field);
			}
		}
	}

	private void AssociateNeighbors() {
		for (Field c1 : fields) {
			for (Field c2 : fields) {
				c1.addNeighbor(c2);
			}
		}
	}

	/* Metodo que sorteia as minas no tabuleiro */
	private void drawMinas() {
		long armedMines = 0;

		Predicate<Field> mined = m -> m.isMined();

		do {
			int random = (int) (Math.random() * fields.size());
			fields.get(random).mineField();
			armedMines = fields.stream().filter(mined).count();
		} while (armedMines < mines);
	}

	/*
	 * Garantir que o objetivo seja alcançado quando todos os campos forem acionados
	 */
	public boolean objectiveAchieved() {
		return fields.stream().allMatch(f -> f.objectiveAchieved());
	}

	public void resetGame() {
		fields.stream().forEach(f -> f.resetGame());
		drawMinas();
	}

	@Override
	public void eventOccurred(Field field, EventField event) {
		if (event == EventField.EXPLODE) {
			viewMines();
			notifyObservers(false);
		} else if (objectiveAchieved()) {
			notifyObservers(true);
		}
	}

	private void viewMines() {
		fields.stream().filter(f -> f.isMined()).forEach(c -> c.setOpen(true));
	}

}
