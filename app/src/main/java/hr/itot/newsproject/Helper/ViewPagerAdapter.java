package hr.itot.newsproject.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IgorTot on 20.7.2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> titleOfFragments=new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addFragments(Fragment fragment, String title){
        fragments.add(fragment);
        titleOfFragments.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleOfFragments.get(position);
    }


}
