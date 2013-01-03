/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayalamanchili
 */
public class CountryFactory {

    public static List<String> getCountries() {
        List<String> countries = new ArrayList<String>();
        countries.add("USA");
        countries.add("INDIA");
        return countries;
    }
}
