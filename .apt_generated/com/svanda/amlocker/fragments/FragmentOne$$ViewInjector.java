// Generated code from Butter Knife. Do not modify!
package com.svanda.amlocker.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FragmentOne$$ViewInjector {
  public static void inject(Finder finder, final com.svanda.amlocker.fragments.FragmentOne target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296260, "field 'gestureDropdown'");
    target.gestureDropdown = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131296262, "field 'gestureTrain' and method 'gestureTrainClicked'");
    target.gestureTrain = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.gestureTrainClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296264, "field 'gestureTolerance'");
    target.gestureTolerance = (android.widget.SeekBar) view;
    view = finder.findRequiredView(source, 2131296261, "field 'gestureDelete' and method 'gestureDeleteClicked'");
    target.gestureDelete = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.gestureDeleteClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296265, "field 'gestureAroundStart'");
    target.gestureAroundStart = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131296267, "field 'gestureTrainProgress'");
    target.gestureTrainProgress = (android.widget.ProgressBar) view;
  }

  public static void reset(com.svanda.amlocker.fragments.FragmentOne target) {
    target.gestureDropdown = null;
    target.gestureTrain = null;
    target.gestureTolerance = null;
    target.gestureDelete = null;
    target.gestureAroundStart = null;
    target.gestureTrainProgress = null;
  }
}
