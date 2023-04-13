package com.gruppometa.metaoaicat.util;

import com.gruppometa.culturaitalia.admin.objects.Geoname;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;

public class GeonameLookup {
	public String getAbout(String city){
		Geoname geoname = ObjectFactory.getGeoname(city);
		if(geoname!=null)
			return "http://www.geonames.org/"+geoname.getId();
		else
			return null;
	}
}
