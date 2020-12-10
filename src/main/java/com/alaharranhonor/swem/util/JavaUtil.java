package com.alaharranhonor.swem.util;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaUtil {
	@SafeVarargs
	public static <E> Object[] unpack(E... objects) {
		List<Object> list = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof Object[]) {
				list.addAll(Arrays.asList((Object[]) object));
			} else {
				list.add(object);
			}

		}

		return list.toArray(new Object[list.size()]);
	}

}
