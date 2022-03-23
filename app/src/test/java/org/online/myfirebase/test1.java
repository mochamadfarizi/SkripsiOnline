package org.online.myfirebase;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.online.myfirebase.activity.LoginActivity;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test1 extends ViewTest{
    ActivityScenario<LoginActivity> scenario;
    private String packageName = "org.online";
    private String targetDevice = "9";
    private int minSDK = 19;
    private  String actName ="MainActivity";
    private String layoutName = "activity_main";
    private String backwardComp = "Activity";

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

    private String getAppName(String packName) {
        String[] list = packName.split("\\.");
        String res = list[list.length-1];
        return res;
    }
}
