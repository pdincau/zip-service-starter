package zip;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZipServiceTest {

    @Mock
    private Locations locations;

    @Test
    public void when_both_country_and_zip_are_empty_throws_exception() {
        ZipService service = new ZipService(null);

        assertThatExceptionOfType(ZipServiceException.class)
                .isThrownBy(() -> service.dataFor("", ""))
                .withMessage("One of the parameters is not set");
    }

    @Test
    public void this_should_make_jacoco_happy() {
        ZipService service = new ZipService(null);

        assertThatExceptionOfType(ZipServiceException.class)
                .isThrownBy(() -> service.dataFor("jacoco", ""))
                .withMessage("One of the parameters is not set");
    }

    @Test
    public void returns_zip_info_for_given_country_and_zip_code() {
        ZipInfo aZipInfo = new ZipInfo("40100", "Italia", "it", Collections.EMPTY_LIST);
        when(locations.findBy(any(Query.class))).thenReturn(aZipInfo);

        ZipService service = new ZipService(locations);

        ZipInfo zipInfo = service.dataFor("it", "40100");

        assertThat(zipInfo.country, is("Italia"));
    }
}
