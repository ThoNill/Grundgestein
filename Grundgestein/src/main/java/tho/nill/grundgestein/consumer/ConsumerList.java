package tho.nill.grundgestein.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerList<T> implements Consumer<T> {

	private List<Consumer<T>> consumers ;

	public ConsumerList(Consumer<T>... consumers) {
		createList(consumers);
	}

	private void createList(Consumer<T>[] consumers) {
		this.consumers = new ArrayList<Consumer<T>>(consumers.length);
		for(Consumer<T> c: consumers) {
			addConsumer(c);
		}
	}

	public void addConsumer(Consumer<T> consumer) {
		consumers.add(consumer);
	}

	@Override
	public void accept(T t) {
		for (Consumer<T> c : consumers) {
			c.accept(t);
		}
	}

}
