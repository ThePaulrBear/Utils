package paul.wintz.uioptiontypes;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.*;

public class OptionGroup implements OptionItem, Iterable<OptionItem> {

	protected String description;
	
	protected final List<OptionItem> options = new ArrayList<>();
	private final List<OnListChangeListener> changeListeners = new ArrayList<>();

	private static final int NO_CHANGE = -1;
	int firstIndexChanged = NO_CHANGE;

	public interface OnListChangeListener {
		void onListChange();
	}

	public OptionGroup(OptionItem... opts) {
		this("", opts);
		description = this.getClass().getSimpleName();
	}

	public OptionGroup(String description, OptionItem... opts) {
		this.description = checkNotNull(description);

		addOptions(opts);
	}

	@Override
	public final String getDescription() {
		return description;
	}
	
	public final void clearOptions(){
		if(options.isEmpty()) return;
		
		options.clear();
		notifyOnChangeListeners();
	}

	public final void addOptions(final OptionItem... opts) {
		addOptions(Arrays.asList(opts));
	}

	public final void addOptions(final List<? extends OptionItem> opts) {
		for (final OptionItem opt : opts) {
			options.add(checkNotNull(opt));
		}
		notifyOnChangeListeners();
	}

	public final void replace(final int index, final OptionItem option){
		options.set(index, checkNotNull(option));
		notifyOnChangeListeners();
	}
	
	public final void remove(final int index) {
		options.remove(index);
		notifyOnChangeListeners();
	}
	
	public final void remove(OptionItem item) {
		if(options.remove(item)) {
			notifyOnChangeListeners();
		}
	}

	public final OptionItem get(final int index){
		return options.get(index);
	}

	public int size() {
		return options.size();
	}

	@Override
	public final Iterator<OptionItem> iterator() {
		return options.iterator();
	}

	public final void addListChangeListener(final OnListChangeListener changeListener){
		changeListeners.add(checkNotNull(changeListener));
	}

	public final void notifyOnChangeListeners(){
		for(final OnListChangeListener l : changeListeners){
			l.onListChange();
		}
	}

	@Override
	public String toString() {
		return String.format("%s (%d items)", description, options.size());
	}
}
