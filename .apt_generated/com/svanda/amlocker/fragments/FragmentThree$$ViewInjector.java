// Generated code from Butter Knife. Do not modify!
package com.svanda.amlocker.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FragmentThree$$ViewInjector {
  public static void inject(Finder finder, final com.svanda.amlocker.fragments.FragmentThree target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296261, "field 'save_password' and method 'SavePass'");
    target.save_password = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.SavePass(p0);
        }
      });
  }

  public static void reset(com.svanda.amlocker.fragments.FragmentThree target) {
    target.save_password = null;
  }
}
