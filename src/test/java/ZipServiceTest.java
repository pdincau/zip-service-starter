import zip.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import zip.Locations;
import zip.ZipInfo;
import zip.ZipService;
import zip.ZipServiceException;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZipServiceTest {

    @Mock
    private Locations locations;

    private ZipService service;

    @Before
    public void setUp() {
        service = new ZipService(locations);
    }

    @Test
    public void fails_when_needed_parameters_are_missing() {
        assertThatExceptionOfType(ZipServiceException.class)
                .isThrownBy(() -> service.dataFor("", ""))
                .withMessage("One of the parameters is not set");
    }

    @Test
    public void returns_given_zip_info() {
        ZipInfo expectedZipInfo = new ZipInfo("90210", "United States", "US", emptyList());
        when(locations.findBy(any(Query.class))).thenReturn(expectedZipInfo);

        ZipInfo zipInfo = service.dataFor("United States", "90210");

        assertThat(zipInfo, is(expectedZipInfo));
    }

}
