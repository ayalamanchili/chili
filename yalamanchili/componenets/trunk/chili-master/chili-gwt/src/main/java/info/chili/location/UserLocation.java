/**
 * System Soft Technologies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.location;

import com.google.gwt.core.client.Callback;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import java.util.logging.Logger;

/**
 *
 * @author ayalamanchili
 */
public class UserLocation {

    public static Logger log = Logger.getLogger(UserLocation.class.getName());

    public static void getUserLocation() {
        Geolocation geoLoc = Geolocation.getIfSupported();
        if (geoLoc != null) {

            Geolocation.PositionOptions options = new Geolocation.PositionOptions();
            options.setMaximumAge(0);
            options.setTimeout(18000);
            options.setHighAccuracyEnabled(false);
            geoLoc.getCurrentPosition(new Callback<Position, PositionError>() {
                @Override
                public void onSuccess(Position result) {  // never called
                    Coordinates coords = result.getCoordinates();
                    final double lat = coords.getLatitude();
                    final double lng = coords.getLongitude();
                    log.info("Coordinates: (" + lat + ", " + lng + ")");
                }

                @Override
                public void onFailure(PositionError error) { // always called
                    String message = "";
                    switch (error.getCode()) {
                        case PositionError.UNKNOWN_ERROR:
                            message = "Unknown Error";
                            break;
                        case PositionError.PERMISSION_DENIED:
                            message = "Permission Denied";
                            break;
                        case PositionError.POSITION_UNAVAILABLE:
                            message = "Position Unavailable";
                            break;
                        case PositionError.TIMEOUT:
                            message = "Time-out";
                            break;
                        default:
                            message = "Unknown error code.";
                    }
                    log.info("Can't Get Current Position: " + message); // ... Time-out
                }
            }, options);
        }
    }
}
