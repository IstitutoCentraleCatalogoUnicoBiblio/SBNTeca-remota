package com.gruppometa.metaoaicat.util;

import junit.framework.TestCase;

public class ViafLookupTest extends TestCase {

	public void testGetAbout() {
		System.out.print("Out: "+new ViafLookup().getAbout("personalNames",
				"V. M. Blinovsky",null,null));
	}

}
