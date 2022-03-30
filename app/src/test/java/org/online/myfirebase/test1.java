package org.online.myfirebase;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.online.myfirebase.activity.LoginActivity;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;

import static org.junit.Assert.assertNotEquals;

import android.os.Build;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test1 extends ViewTest{
    ActivityScenario<LoginActivity> scenario;
    private String packageName = "org.online";
    private String targetDevice = "9";
    private int minSDK = 19;
    private  String actName ="LoginActivity";
    private String layoutName = "activity_login";
    private String backwardComp = "AppCompatActivity";

    @Before
    public void initTest(){
        scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
    }
    @Test
    public void check_01_AppName() {
        scenario.onActivity(activity -> {
            assertEquals("Application Name is Wrong", appName.toLowerCase(), getAppName(activity.getPackageName()));

        });
    }

    @Test
    public void check_02_PackageName() {
        scenario.onActivity(activity -> {
            String packName =packageName+"."+appName.toLowerCase();
            assertEquals("package Name is Wrong", packName,activity.getPackageName());

        });
    }
    @Test
    public void check_03_TargetDevice() {
        scenario.onActivity(activity -> {
            Assert.assertEquals("Target Device is Wrong",targetDevice, Build.VERSION.RELEASE);
        });
    }
    @Test
    public void check_04_MinimumSDK() {
        scenario.onActivity(activity -> {
            Assert.assertEquals("Minimum SDK Version is Wrong",minSDK,activity.getApplicationInfo().minSdkVersion);
        });
    }
    @Test
    public void check_05_ActivityName() { //Check Activity Name (Should be MyActivity)
        scenario.onActivity(activity -> {
            Assert.assertEquals("Activity Name is Wrong", actName, activity.getClass().getSimpleName());
        });
    }
    @Test
    public void check_06_LayoutName() { //Check Layout Name (Should be activity_layout)
        scenario.onActivity(activity -> {
            int resId = activity.getResources().getIdentifier(layoutName, "layout", activity.getPackageName());
            assertNotEquals("Layout Name is Wrong", 0, resId);
        });
    }
    @Test
    public void check_07_ActivityParent() { //Check Backward Compatibility (Should be AppCompatActivity)
        scenario.onActivity(activity -> {
            Assert.assertEquals("Activity Parent is Wrong", backwardComp, activity.getClass().getSuperclass().getSimpleName());
        });
    }
    @Test
    public void check_08_String_Resource() {
        //string check
        scenario.onActivity(activity -> {
            org.online.myfirebase.ResourceTest rsc = new org.online.myfirebase.ResourceTest (activity.getResources());
            rsc.testStringResource("app_name","MyFirebase");
            rsc.testStringResource("hint_name","Name");
            rsc.testStringResource("hint_username","Username");
            rsc.testStringResource("hint_password","Password");
            rsc.testStringResource("hint_confirm_password","Confirm Password");
            rsc.testStringResource("text_login","Login");
            rsc.testStringResource("text_register","Register");
            rsc.testStringResource("error_message_username","Enter Username");
            rsc.testStringResource("error_message_password","Enter Password");
            rsc.testStringResource("error_message_role","Select Your Role");
            rsc.testStringResource("success_message","Registration Successful");
            rsc.testStringResource("text_not_member","No account yet? Create one");
            rsc.testStringResource("text_already_member","Already a member? Login");
            rsc.testStringResource("error_username_exists","Username Already Exists");
            rsc.testStringResource("error_password_match","Password Does Not Matches");
            rsc.testStringResource("error_valid_email_password","Wrong Email or Password");
            rsc.testStringResource("radio_seller","Seller");
            rsc.testStringResource("radio_buyer","Buyer");
            rsc.testStringResource("hint_product_name","Product Name");
            rsc.testStringResource("hint_product_price","Product Price");
            rsc.testStringResource("hint_product_buyer","Product Buyer");
            rsc.testStringResource("hint_product_quantity","Quantity");
            rsc.testStringResource("text_add_product","Add Product");
            rsc.testStringResource("text_remove_product","Remove Product");
            rsc.testStringResource("text_add_to_cart","Add To Cart");
            rsc.testStringResource("text_back","Back");
            rsc.testStringResource("error_message_product_name","Enter Product Name");
            rsc.testStringResource("error_message_product_price","Enter Product Price");
            rsc.testStringResource("product_success_message","Add Product Successful");
            rsc.testStringResource("error_product_exists","Product Already Exists");
            String[] data = {"---You Want to Be A?---", "Seller", "Buyer"};
            rsc.testStringArrayResource("roles",data);

        });
    }
    @Test
    public void check_09_Color_Resources() {
        scenario.onActivity(activity -> {
            org.online.myfirebase.ResourceTest rsc = new org.online.myfirebase.ResourceTest (activity.getResources());
            rsc.testColorResource(activity, "black", "#000000");
            rsc.testColorResource(activity, "white", "#FFFFFF");
            rsc.testColorResource(activity, "colorPrimary", "#51d8c7");
            rsc.testColorResource(activity, "colorPrimaryDark", "#51d8c7");
            rsc.testColorResource(activity, "colorAccent", "#FFFFFF");
            rsc.testColorResource(activity, "colorBackground", "#001a33");
            rsc.testColorResource(activity, "colorText", "#FFFFFF");
            rsc.testColorResource(activity, "colorTextHint", "#51d8c7");
        });

    }
    @Test
    public void check_10_Image_Resource(){
        scenario.onActivity(activity -> {
            org.online.myfirebase.ResourceTest rsc = new org.online.myfirebase.ResourceTest (activity.getResources());
            rsc.testImgResource("myshop");
        });

    }
    @Test
    public void check_11_ImageView(){
        scenario.onActivity(activity -> {
            testViewExist("imgLogo","ImageView", activity);
        });
    }

    @Test
    public void check_12_EditText(){
        scenario.onActivity(activity -> {
            testViewExist("textInputEditTextUsername","EditText", activity);
            testViewExist("textInputEditTextPassword","EditText", activity);
        });
    }

    @Test
    public void check_13_Button(){
        scenario.onActivity(activity -> {
            testViewExist("appCompatButtonLogin","Button", activity);
        });
    }

    @Test
    public void check_14_TextView(){
        scenario.onActivity(activity -> {
            testViewExist("textViewLinkRegister","TextView", activity);
        });
    }

    private String getAppName(String packName) {
        String[] list = packName.split("\\.");
        String res = list[list.length-1];
        return res;
    }
}
