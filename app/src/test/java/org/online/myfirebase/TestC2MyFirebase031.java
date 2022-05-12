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
import org.online.myfirebase.activity.seller.SellerHomeActivity;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestC2MyFirebase031 extends ViewTest {
    ActivityScenario<SellerHomeActivity> scenario;
    private String packageName = "org.online";
    private String targetDevice = "9";
    private int minSDK = 21;
    private String actName = "SellerHomeActivity";
    private String layoutName = "activity_seller_home";
    private String backwardComp = "AppCompatActivity";
    //private String packName;
    //ResourceTest rsc;

    @Before
    public void initTest() {
        scenario = ActivityScenario.launch(SellerHomeActivity.class);
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
    public void check_03_TextView(){
        scenario.onActivity(activity -> {
            testViewExist("titleSellerHome","TextView", activity);
        });
    }

    @Test
    public void check_04_RecyclerView(){
        scenario.onActivity(activity -> {
            testViewExist("recyclerViewProducts","RecyclerView", activity);
        });
    }

    @Test
    public void check_05_Button(){
        scenario.onActivity(activity -> {
            testViewExist("ButtonAddProduct","Button", activity);
            testViewExist("ButtonRefreshProduct","Button", activity);
            testViewExist("ButtonBuyerCart","Button", activity);
            testViewExist("ButtonLogout","Button", activity);
        });
    }

    @Test
    public void check_06_Product_Class() {
        String packname = "org.online.myfirebase.model";
        String cname = "Product";
        try {
            Class<?> c = Class.forName(packname+"."+cname);
            Constructor<?> ctor = c.getConstructor();
            Object obj = ctor.newInstance();
            assertEquals("Class "+cname+" should be in 'model' directory", packname, obj.getClass().getPackage().getName());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            failTest("Class " + cname + " is not defined ("+ e.toString() +")");
        }
    }



    private String getAppName(String packName) {
        String[] list = packName.split("\\.");
        String res = list[list.length-1];
        return res;
    }

}
