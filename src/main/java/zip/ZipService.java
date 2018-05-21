package zip;

import org.apache.commons.lang.StringUtils;

public class ZipService {

    private Locations locations;

    public ZipService(Locations locations) {
        this.locations = locations;
    }

    public ZipInfo dataFor(String country, String zipCode) {
        validateInput(country, zipCode);
        return locations.findBy(new Query(country, zipCode));
    }

    private void validateInput(String country, String zipCode) {
        if (StringUtils.isEmpty(country) && StringUtils.isEmpty(zipCode)) {
            throw new ZipServiceException("One of the parameters is not set");
        }
    }

}