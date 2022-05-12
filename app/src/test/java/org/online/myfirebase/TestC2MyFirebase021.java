package org.online.myfirebase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.online.myfirebase.activity.RegisterActivity;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestC2MyFirebase021 extends ViewTest {
    ActivityScenario<RegisterActivity> scenario;
    private String packageName = "org.online";
    private String targetDevice = "9";
    private int minSDK = 21;
    private String actName = "RegisterActivity";
    private String layoutName = "activity_register";
    private String backwardComp = "AppCompatActivity";
    //private String packName;
    //ResourceTest rsc;

    @Before
    public void initTest() {
        scenario = ActivityScenario.launch(RegisterActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void check_01_ActivityName() { //Check Activity Name (Should be MyActivity)
        scenario.onActivity(activity -> {
            assertEquals("Activity Name is Wrong", actName, activity.getClass().getSimpleName());
        });
    }


    @Test
    public void check_02_LayoutName() { //Check Layout Name (Should be activity_layout)
        scenario.onActivity(activity -> {
            int resId = activity.getResources().getIdentifier(layoutName, "layout", activity.getPackageName());
            assertNotEquals("Layout Name is Wrong", 0, resId);
        });
    }

    @Test
    public void check_03_ImageView() { //Check Backward Compatibility (Should be AppCompatActivity)
        scenario.onActivity(activity -> {
            testViewExist("imgLogo","ImageView", activity);
        });
    }

    @Test
    public void check_04_EditText(){
        scenario.onActivity(activity -> {
            testViewExist("textInputEditTextName","EditText", activity);
            testViewExist("textInputEditTextPassword","EditText", activity);
            testViewExist("textInputEditTextConfirmPassword","EditText", activity);
        });
    }


    @Test
    public void check_05_Spinner(){
        scenario.onActivity(activity -> {
            String spinner = "listRole";
        });
    }

    @Test
    public void check_06_Button(){
        scenario.onActivity(activity -> {
            testViewExist("appCompatButtonRegister","Button", activity);
        });
    }

    @Test
    public void check_07_TextView(){
        scenario.onActivity(activity -> {
            testViewExist("appCompatTextViewLoginLink","TextView", activity);
        });
    }

    private String getAppName(String packName) {
        String[] list = packName.split("\\.");
        String res = list[list.length-1];
        return res;
    }

}
