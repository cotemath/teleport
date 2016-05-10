package com.appdirect.teleport.util;

import com.appdirect.teleport.domain.Location;

/**
 * The content of this class was copied from: https://www.geodatasource.com/developers/java
 * and modified as needed. See copyright information below
 * 
 * FYI: I am not a big fan of util classes with static methods as they reduce testability
 * but using it here to save some dev time
 */

/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::                                                                         :*/
/*::  This routine calculates the distance between two points (given the     :*/
/*::  latitude/longitude of those points). It is being used to calculate     :*/
/*::  the distance between two locations using GeoDataSource (TM) products  :*/
/*::                                                                         :*/
/*::  Definitions:                                                           :*/
/*::    South latitudes are negative, east longitudes are positive           :*/
/*::                                                                         :*/
/*::  Passed to function:                                                    :*/
/*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
/*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
/*::    unit = the unit you desire for results                               :*/
/*::           where: 'M' is statute miles (default)                         :*/
/*::                  'K' is kilometers                                      :*/
/*::                  'N' is nautical miles                                  :*/
/*::  Worldwide cities and other features databases with latitude longitude  :*/
/*::  are available at http://www.geodatasource.com                          :*/
/*::                                                                         :*/
/*::  For enquiries, please contact sales@geodatasource.com                  :*/
/*::                                                                         :*/
/*::  Official Web site: http://www.geodatasource.com                        :*/
/*::                                                                         :*/
/*::           GeoDataSource.com (C) All Rights Reserved 2015                :*/
/*::                                                                         :*/
/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

public class GeographicDistanceCalculator {
	
	private static final int HALF_CIRCLE = 180;
	
	/**
	 * Given 2 Points (latitude/logitude), calculate the distance in Kilometers
	 * @param point1 the first Point
	 * @param point2 the second Point
	 * @return the distance in Kilometers
	 */
	public static double distanceInKilometer(Location point1, Location point2) {
		double theta = point1.getLongitude() - point2.getLongitude();
		double dist = Math.sin(deg2rad(point1.getLatitude())) * Math.sin(deg2rad(point2.getLatitude())) + Math.cos(deg2rad(point1.getLatitude())) * Math.cos(deg2rad(point2.getLatitude())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		return (dist * 60 * 1.1515 * 1.609344);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians			:*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / HALF_CIRCLE);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees			:*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double rad2deg(double rad) {
		return (rad * HALF_CIRCLE / Math.PI);
	}
}
