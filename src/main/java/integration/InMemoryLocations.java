package integration;

import zip.Locations;
import zip.Place;
import zip.Query;
import zip.ZipInfo;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class InMemoryLocations implements Locations {

    Map<Query, ZipInfo> map;

    public InMemoryLocations() {
        this.map = new HashMap<>();
    }

    @Override
    public ZipInfo findBy(Query query) {
        return map.get(query);
    }

    public void setup() {
        Place vathorst = new Place("Vathorst", "Utrecht", "UT");
        Place beverlyHills = new Place("Beverly Hills", "California", "CA");
        Place schenectady = new Place("Schenectady", "New York", "NY");
        Place ampflwang = new Place("Ampflwang im Hausruckwald", "Ober\\u00f6sterreich", "04");
        Place darzo = new Place("Darzo", "Trentino-Alto Adige", "04");
        Place storo = new Place("Storo", "Trentino-Alto Adige", "04");
        Place lodrone = new Place("Storo", "Trentino-Alto Adige", "04");

        map.put(new Query("nl", "3825"), new ZipInfo("3825", "Netherlands", "NL", singletonList(vathorst)));
        map.put(new Query("us", "90210"), new ZipInfo("90210", "United States", "US", singletonList(beverlyHills)));
        map.put(new Query("us", "12345"), new ZipInfo("12345", "United States", "US", singletonList(schenectady)));
        map.put(new Query("at", "4843"), new ZipInfo("4843", "Austria", "AT", singletonList(ampflwang)));
        map.put(new Query("it", "38089"), new ZipInfo("38089", "Italy", "IT", asList(darzo, storo, lodrone)));
    }
}
