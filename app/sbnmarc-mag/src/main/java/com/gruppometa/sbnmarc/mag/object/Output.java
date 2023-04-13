package com.gruppometa.sbnmarc.mag.object;


import java.util.List;


public interface Output {
	
	public List<OutItem> getItems();

	public void addItem(OutItem desc);
}
