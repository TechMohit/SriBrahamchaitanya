package grossary.cyron.com.grossarybrch.utility;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentHelper {
    public static void replaceFragment(FragmentActivity activity, int id, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentActivity activity, int id, Fragment fragment, boolean backStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Fragment recentFragment = getFragment(activity, id);
        if (recentFragment != null)
            transaction.remove(recentFragment); // added because replace was not working in below 23 devices
        transaction.replace(id, fragment);
        if (backStack)
            transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }
    public static void replaceFragment(FragmentActivity activity, int id, Fragment fragment, boolean backStack,String tag) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Fragment recentFragment = getFragment(activity, id);
        if (recentFragment != null)
            transaction.remove(recentFragment); // added because replace was not working in below 23 devices
        transaction.replace(id, fragment,tag);
        if (backStack)
            transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }
    /**
     * @param activity   {@link FragmentActivity}
     * @param container
     * @param fragment   {@link Fragment}
     * @param backStack, true if {@link Fragment} needs to be added to back stack.
     */
    public static void addFragment(FragmentActivity activity, int container, Fragment fragment, boolean backStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment);
        if (backStack) transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }
    public static void addFragment(final FragmentActivity activity, final int containerId, final Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment,"drawer");
        transaction.commitAllowingStateLoss();
    }
    /**
     * @param activity
     * @param container
     * @param fragment
     * @param backStack
     * @param tag
     */
    public static void addFragment(FragmentActivity activity, int container, Fragment fragment, boolean backStack, String tag) {
        if (fragment==null)
            return;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        if (backStack) transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    public static void clearBackStack(final FragmentActivity activity) {
        try {
            activity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }
    public static void clearBackStackAll(final FragmentActivity activity) {
        try {
            for(int i=getStackCount(activity);i>0;i--){
                clearBackStack(activity);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        getStackCount(activity);

    }

    public static int getStackCount(final FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.getBackStackEntryCount();
    }

    @Nullable
    public static Fragment getFragment(final FragmentActivity activity, final int containerId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.findFragmentById(containerId);
    }

    @Nullable
    public static Fragment getFragment(final FragmentActivity activity, final String TAG) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(TAG);
    }
}
