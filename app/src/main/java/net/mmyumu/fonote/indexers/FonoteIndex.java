package net.mmyumu.fonote.indexers;

public class FonoteIndex<T> {
	private final int index;
	private final T section;
	private final int position;

	public FonoteIndex(int index, T section, int position) {
		super();
		this.index = index;
		this.section = section;
		this.position = position;
	}

	public int getIndex() {
		return index;
	}

	public T getSection() {
		return section;
	}

	public int getPosition() {
		return position;
	}

}
