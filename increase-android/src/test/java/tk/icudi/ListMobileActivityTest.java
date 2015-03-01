package tk.icudi;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="./src/main/AndroidManifest.xml", emulateSdk=18)
public class ListMobileActivityTest {
   
    @Test
    public void should_have_application_name() throws Exception {
        String appName = new ListMobileActivity().getResources().getString(R.string.app_name);
        Assert.assertThat(appName, Matchers.equalTo("Increase"));
    }

}
