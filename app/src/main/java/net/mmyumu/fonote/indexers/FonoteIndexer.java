package net.mmyumu.fonote.indexers;

import java.util.ArrayList;
import java.util.List;

public class FonoteIndexer<T> {
	private final List<FonoteIndex<T>> indexes;

	public FonoteIndexer() {
		super();
		indexes = new ArrayList<FonoteIndex<T>>();
	}

	public boolean add(int index, T section, int position) {
		return indexes.add(new FonoteIndex<T>(index, section, position));
	}

	public boolean push(T section, int position) {
		return indexes
				.add(new FonoteIndex<T>(indexes.size(), section, position));
	}

	public int getPositionByIndex(int sectionIndex) {
		for (FonoteIndex<T> index : indexes) {
			if (index.getIndex() == sectionIndex) {
				return index.getPosition();
			}
		}

		return 0;
	}

	public int getSectionForPosition(int position) {
		for (FonoteIndex<T> index : indexes) {
			if (index.getPosition() == position) {
				return index.getIndex();
			}
		}

		return 0;
	}

	public Object[] getSections() {
		List<T> sections = new ArrayList<T>();
		for (FonoteIndex<T> index : indexes) {
			sections.add(index.getSection());
		}
		return sections.toArray();
	}
}
