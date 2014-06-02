// Generated code from Butter Knife. Do not modify!
package com.svanda.amlocker.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FragmentTwo$$ViewInjector {
  public static void inject(Finder finder, final com.svanda.amlocker.fragments.FragmentTwo target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296274, "field 'spinner_shortcut1'");
    target.spinner_shortcut1 = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131296275, "field 'spinner_shortcut2'");
    target.spinner_shortcut2 = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131296277, "field 'spinner_shortcut3'");
    target.spinner_shortcut3 = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131296278, "field 'apps_save' and method 'saveClicked'");
    target.apps_save = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.saveClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296263, "field 'text_launcher' and method 'spinner1Clicked'");
    target.text_launcher = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.spinner1Clicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296273, "field 'spinner_launcher'");
    target.spinner_launcher = (android.widget.Spinner) view;
  }

  public static void reset(com.svanda.amlocker.fragments.FragmentTwo target) {
    target.spinner_shortcut1 = null;
    target.spinner_shortcut2 = null;
    target.spinner_shortcut3 = null;
    target.apps_save = null;
    target.text_launcher = null;
    target.spinner_launcher = null;
  }
}
