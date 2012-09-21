package info.chili.android.commons;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class LocationUtils {

	public static Location getCurrentLocation(LocationManager locationManager,
			int accuracy, int power) {
		String provider = locationManager.getBestProvider(
				getCoarseLocationCriteria(accuracy, power), false);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location == null) {
			throw new RuntimeException("Unable to get location");
		}
		return location;
	}

	public static Criteria getCoarseLocationCriteria(int accuracy, int power) {
		Criteria criteria = new Criteria();
		// Criteria.ACCURACY_COARSE
		criteria.setAccuracy(accuracy);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		// Criteria.POWER_MEDIUM
		criteria.setPowerRequirement(power);
		return criteria;
	}
}
