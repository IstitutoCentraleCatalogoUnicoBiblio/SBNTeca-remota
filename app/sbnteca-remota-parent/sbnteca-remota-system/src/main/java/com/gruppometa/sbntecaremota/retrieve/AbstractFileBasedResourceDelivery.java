package com.gruppometa.sbntecaremota.retrieve;

public abstract class AbstractFileBasedResourceDelivery extends AbstractResourceDelivery {
	
	// tipo di memorizzazione
	protected String storeType = FileStorageDeliveryOptions.TYPE_USAGE;
	
	
	
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

}
