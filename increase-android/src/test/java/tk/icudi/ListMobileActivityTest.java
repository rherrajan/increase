//package tk.icudi;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.notNullValue;
//
//import org.hamcrest.Matchers;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;
//
//import android.widget.ListAdapter;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(manifest = "./src/main/AndroidManifest.xml", emulateSdk = 18)
//public class ListMobileActivityTest {
//
//	@Test
//	public void should_have_application_name() throws Exception {
//		ListMobileActivity activity = new ListMobileActivity();
//		
//		String appName = activity.getResources().getString(R.string.app_name);
//		Assert.assertThat(appName, Matchers.equalTo("Increase"));
//	}
//	
//	@Test
//	@Ignore("i dont know whats wrong")
//	public void should_have_list_after_refresh() throws Exception {
//		final ListMobileActivity activity = Robolectric.buildActivity(ListMobileActivity.class).create().start().resume().visible().get();
//
//		ListAdapter listAdapter = activity.getListAdapter();
//		Assert.assertThat(listAdapter, notNullValue());
//		Assert.assertThat(listAdapter.getCount(), is(0));
//		
////		Button button = (Button) activity.findViewById(R.id.button_refresh);
////		button.performClick();
//
//		Assert.assertThat(listAdapter.getCount(), Matchers.greaterThan(0));
//	}
//
//
//
//}
