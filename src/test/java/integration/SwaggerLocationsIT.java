package integration;

import org.junit.Test;
import zip.Query;
import zip.ZipInfo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SwaggerLocationsIT {

    @Test
    public void it_retrieves_data_from_remote_api() {
        SwaggerLocations locations = new SwaggerLocations();

        ZipInfo zipInfo = locations.findBy(new Query("us", "90210"));

        assertThat(zipInfo.country, is("US"));
    }
}