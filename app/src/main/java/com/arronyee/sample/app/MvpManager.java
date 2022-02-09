
package com.arronyee.sample.app;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Pair;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MvpManager implements ActivityLifecycleCallbacks {
    private static final ArrayList<Pair<Activity, MvpPresenter>> mActivityLifecycleCallbacks = new ArrayList();
    private static final ArrayList<Pair<Fragment, MvpPresenter>> mFragmentLifecycleCallbacks = new ArrayList();

    public MvpManager() {
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        MvpPresenter activityPresenter = findPresenter(activity);
        if (activityPresenter != null) {
            activityPresenter.onActivityCreated(activity, savedInstanceState);
        }

    }

    public void onActivityStarted(Activity activity) {
        MvpPresenter activityPresenter = findPresenter(activity);
        if (activityPresenter != null) {
            activityPresenter.onActivityStarted(activity);
        }

    }

    public void onActivityResumed(Activity activity) {
        MvpPresenter activityPresenter = findPresenter(activity);
        if (activityPresenter != null) {
            activityPresenter.onActivityResumed(activity);
        }

    }

    public void onActivityPaused(Activity activity) {
        MvpPresenter activityPresenter = findPresenter(activity);
        if (activityPresenter != null) {
            activityPresenter.onActivityPaused(activity);
        }

    }

    public void onActivityStopped(Activity activity) {
        MvpPresenter activityPresenter = findPresenter(activity);
        if (activityPresenter != null) {
            activityPresenter.onActivityStopped(activity);
        }

    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        MvpPresenter activityPresenter = findPresenter(activity);
        if (activityPresenter != null) {
            activityPresenter.onActivitySaveInstanceState(activity, outState);
        }

    }

    public void onActivityDestroyed(Activity activity) {
        onActivityDestroyedAndRemovePair(activity);
    }

    public static void bindPresenter(Activity activity, String presenter) {
        try {
            MvpPresenter mvpPresenter = (MvpPresenter)Class.forName(presenter).newInstance();
            Pair<Activity, MvpPresenter> pair = new Pair(activity, mvpPresenter);
            synchronized(mActivityLifecycleCallbacks) {
                mActivityLifecycleCallbacks.add(pair);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static MvpPresenter findPresenter(Activity activity) {
        for(int i = mActivityLifecycleCallbacks.size() - 1; i >= 0; --i) {
            Pair<Activity, MvpPresenter> pair = (Pair)mActivityLifecycleCallbacks.get(i);
            if (pair != null && pair.first == activity) {
                return (MvpPresenter)pair.second;
            }
        }

        return null;
    }

    public static Activity findActivity(MvpPresenter presenter) {
        for(int i = mActivityLifecycleCallbacks.size() - 1; i >= 0; --i) {
            Pair<Activity, MvpPresenter> pair = (Pair)mActivityLifecycleCallbacks.get(i);
            if (pair != null && pair.second == presenter) {
                return (Activity)pair.first;
            }
        }

        return null;
    }

    private static void onActivityDestroyedAndRemovePair(Activity activity) {
        for(int i = mActivityLifecycleCallbacks.size() - 1; i >= 0; --i) {
            Pair<Activity, MvpPresenter> pair = (Pair)mActivityLifecycleCallbacks.get(i);
            if (pair != null && pair.first == activity) {
                if (pair.second != null) {
                    MvpPresenter presenter = (MvpPresenter)pair.second;
                    presenter.onActivityDestroyed(activity);
                    presenter = null;
                }

                synchronized(mActivityLifecycleCallbacks) {
                    mActivityLifecycleCallbacks.remove(pair);
                    break;
                }
            }
        }

    }

    public static void bindPresenter(Fragment fragment, String presenter) {
        try {
            MvpPresenter mvpPresenter = (MvpPresenter)Class.forName(presenter).newInstance();
            Pair<Fragment, MvpPresenter> pair = new Pair(fragment, mvpPresenter);
            synchronized(mFragmentLifecycleCallbacks) {
                mFragmentLifecycleCallbacks.add(pair);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static MvpPresenter findPresenter(Fragment fragment) {
        for(int i = mFragmentLifecycleCallbacks.size() - 1; i >= 0; --i) {
            Pair<Fragment, MvpPresenter> pair = (Pair)mFragmentLifecycleCallbacks.get(i);
            if (pair != null && pair.first == fragment) {
                return (MvpPresenter)pair.second;
            }
        }

        return null;
    }

    public static Fragment findFragment(MvpPresenter presenter) {
        for(int i = mFragmentLifecycleCallbacks.size() - 1; i >= 0; --i) {
            Pair<Fragment, MvpPresenter> pair = (Pair)mFragmentLifecycleCallbacks.get(i);
            if (pair != null && pair.second == presenter) {
                return (Fragment)pair.first;
            }
        }

        return null;
    }


    public static void unbindFragment(Fragment fragment) {
        if (fragment != null) {
            for(int i = mFragmentLifecycleCallbacks.size() - 1; i >= 0; --i) {
                Pair<Fragment, MvpPresenter> pair = (Pair)mFragmentLifecycleCallbacks.get(i);
                if (pair.first == fragment) {
                    synchronized(mFragmentLifecycleCallbacks) {
                        mFragmentLifecycleCallbacks.remove(pair);
                    }
                }

                MvpPresenter mvpPresenter = (MvpPresenter)pair.second;
                mvpPresenter = null;
            }
        }

    }

    public static void unbindActivity(Activity activity) {
        if (activity != null) {
            for(int i = mActivityLifecycleCallbacks.size() - 1; i >= 0; --i) {
                Pair<Activity, MvpPresenter> pair = (Pair)mActivityLifecycleCallbacks.get(i);
                if (pair.first == activity) {
                    synchronized(mActivityLifecycleCallbacks) {
                        mActivityLifecycleCallbacks.remove(pair);
                    }
                }

                MvpPresenter mvpPresenter = (MvpPresenter)pair.second;
                mvpPresenter = null;
            }
        }

    }

    public static void clear() {
        mActivityLifecycleCallbacks.clear();
        mFragmentLifecycleCallbacks.clear();
    }
}
