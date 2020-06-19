package tho.nill.grundgestein.texter;

import static tho.nill.grundgestein.check.ArgumentCheck.checkIllegalArgument;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

import lombok.NonNull;
import tho.nill.grundgestein.annotations.Nullable;

public abstract class AbstractTexter<T extends AbstractTexter<?>> {
	private StringBuilder builder;
	private int shift;
	private int tab;

	private T parent = null;

	public AbstractTexter(int tab) {
		this(0, tab, null);
	}

	protected AbstractTexter(int shift, int tab, @Nullable T parent) {
		super();
		checkIllegalArgument(shift >= 0 && tab >= 0);

		this.builder = new StringBuilder();
		this.shift = shift;
		this.tab = tab;
		this.parent = parent;
	}

	public int getShift() {
		return shift;
	}

	public int getTab() {
		return tab;
	}

	public abstract T start();

	protected abstract T retThis();

	public T stop() {
		if (parent != null) {
			parent.a(this.toString());
			return parent;
		}
		return retThis();
	}

	private void doShift() {
		if (shift > 0) {
			char[] leerzeichen = new char[shift];
			Arrays.fill(leerzeichen, 0, shift - 1, ' ');
			builder.append(leerzeichen);
		}
	}

	public T nl() {
		builder.append('\n');
		doShift();
		return retThis();
	}

	public T tab() {
		return addShift(tab);
	}

	public T bat() {
		return subShift(tab);
	}

	public T addShift(int n) {
		checkIllegalArgument(n >= 0);
		shift += n;
		return retThis();
	}

	public T subShift(int n) {
		checkIllegalArgument(n >= 0);
		shift -= n;
		return retThis();
	}

	public T a(@NonNull Collection<?> liste, @NonNull String start, @NonNull String trenner, @NonNull String stop,
			@NonNull Function<Object, String> f) {
		if (liste.isEmpty()) {
			return retThis();
		}
		boolean setzeTrenner = false;
		a(start);
		Iterator iter = liste.iterator();
		while (iter.hasNext()) {
			if (setzeTrenner) {
				a(trenner);
			}
			a(f.apply(iter.next()));
			setzeTrenner = true;
		}
		a(stop);
		return retThis();

	}

	public int length() {
		return builder.length();
	}

	public T a(Object obj) {
		if (obj == null) {
			return retThis();
		}
		return a(obj.toString());
	}

	public T a(@NonNull String str) {
		return a(str.toCharArray());
	}

	public T a(@NonNull StringBuffer sb) {
		return a(sb.toString());
	}

	public T a(@NonNull CharSequence s) {
		return a(s, 0, s.length());
	}

	public T a(@NonNull CharSequence s, int start, int end) {
		checkIllegalArgument(start <= end && start >= 0 && end >= 0 && end <= s.length());

		int len = end - start;
		builder.ensureCapacity(builder.length() + len);
		for (int i = start; i < end; i++) {
			a(s.charAt(i));
		}
		return retThis();
	}

	public T a(@NonNull char[] str) {
		return a(str, 0, str.length);
	}

	public T a(@NonNull char[] str, int offset, int len) {
		checkIllegalArgument(offset >= 0 && len >= 0 && offset + len <= str.length);

		builder.ensureCapacity(builder.length() + len);
		int end = offset + len;
		for (int i = offset; i < end; i++) {
			a(str[i]);
		}
		return retThis();
	}



	public T a(char c) {
		if (c == '\n') {
			nl();
		} else {
			builder.append(c);
		}
		return retThis();
	}

	public T a(boolean b) {
		builder.append(b);
		return retThis();
	}

	public T a(int i) {
		builder.append(i);
		return retThis();
	}

	public T a(long lng) {
		builder.append(lng);
		return retThis();
	}

	public T a(float f) {
		builder.append(f);
		return retThis();
	}

	public T a(double d) {
		builder.append(d);
		return retThis();
	}

	public T delete(int start, int end) {
		checkIllegalArgument(start <= end && start >= 0 && end >= 0 && end < builder.length());
		builder.delete(start, end);
		return retThis();
	}

	public T deleteCharAt(int index) {
		checkIllegalArgument(index >= 0 && index < builder.length());
		builder.deleteCharAt(index);
		return retThis();
	}

	public char charAt(int index) {
		checkIllegalArgument(index >= 0 && index < builder.length());
		return builder.charAt(index);
	}

	public int indexOf(@NonNull String str) {
		return builder.indexOf(str);
	}

	public int indexOf(@NonNull String str, int fromIndex) {
		checkIllegalArgument(fromIndex >= 0);
		return builder.indexOf(str, fromIndex);
	}

	public T reverse() {
		builder.reverse();
		return retThis();
	}

	public String toString() {
		return builder.toString();
	}

	public String substring(int start) {
		checkIllegalArgument(start >= 0);
		return builder.substring(start);
	}

	public String substring(int start, int end) {
		checkIllegalArgument(start >= 0 && end >= 0 && start < end);
		return builder.substring(start, end);
	}

	public String clear() {
		String text = builder.toString();
		builder = new StringBuilder();
		return text;
	}
}
