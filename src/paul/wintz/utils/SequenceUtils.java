package paul.wintz.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.Array;
import java.util.List;

import org.mockito.internal.util.collections.ArrayUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;

public class SequenceUtils {
	
	@Deprecated
	public static int indexOfLastTrueElement(boolean[] booleanList) {
		return indexOfLastTrueElement(Booleans.asList(booleanList));
	}
	
	public static int indexOfLastTrueElement(List<Boolean> booleanList) {
		checkArgument(!booleanList.isEmpty());
		for (int i = booleanList.size() - 1; i >= 0; i--) {
			if (booleanList.get(i))
				return i;
		}
		return -1;
	}

	public static boolean doLengthsMatch(Object... arrays){
		final int length = SequenceUtils.length(arrays[0]);
	
		for(final Object a : arrays){
			if(length != SequenceUtils.length(a)) return false;
		}
	
		return true;
	}

	public static void assertLengthsMatch(Object... arrays){
		checkArgument(doLengthsMatch(arrays), "The length of the arrays do not match");
	}

	public static int[] toIntArray(String[] strings) {
		final int[] ints = new int[strings.length];
		for (int i = 0; i < strings.length; i++) {
			ints[i] = Integer.valueOf(strings[i].trim());
		}
		return ints;
	}

	public static boolean[] toBooleanArray(String[] strings) {
		final boolean[] bools = new boolean[strings.length];
		for (int i = 0; i < strings.length; i++) {
			bools[i] = Boolean.valueOf(strings[i].trim());
		}
		return bools;
	}
	
	private static int length(Object array){
		return java.lang.reflect.Array.getLength(array);
	}
}
