package br.com.dhentech.cm.model;

@FunctionalInterface
public interface ObserverField {
	
	public void eventOccurred(Field field, EventField event);

}
