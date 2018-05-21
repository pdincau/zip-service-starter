package integration;

import org.junit.Test;
import zip.Query;
import zip.ZipInfo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SwaggerLocationsIT {

    @Test
    public void retrieves_locations_from_remote() {
        ZipInfo zipInfo = new SwaggerLocations().findBy(new Query("US", "90210"));

        assertThat(zipInfo.country, is("US"));
    }
}