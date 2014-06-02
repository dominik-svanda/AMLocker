package com.svanda.amlocker.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.svanda.amlocker.R;
/**
 * Fragment where user can change master password for lockscreen
 * @author Dominik Svanda
 *	
 */
public class FragmentThree extends Fragment {
	
	//PasswordFiled element
	EditText password_old;
	EditText password_new;
	EditText password_new_again;
	private boolean old_flag = false;
	private boolean new_flag = false;
	
	@InjectView(R.id.save_password)
	Button save_password;

	public static FragmentThree newInstance() {
		FragmentThree fragment = new FragmentThree();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_three, container, false);
		ButterKnife.inject(this, view);
		password_old = (EditText) view.findViewById(R.id.old_pass);
		password_new = (EditText) view.findViewById(R.id.new_pass);
		password_new_again = (EditText) view.findViewById(R.id.new_pass_again);
		
		password_old.addTextChangedListener(new TextWatcher(){

		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){}
				@Override
				public void afterTextChanged(Editable s) {
					SharedPreferences settings = getActivity().getSharedPreferences("Password", 0);
					if(password_old.getText().toString().equals(settings.getString("password", ""))){
						password_old.setBackgroundColor(0xFF00E500);
						old_flag = true;
					}else{//Password is different
						password_old.setBackgroundColor(0xFFFF3232);
						old_flag = false;
					}
				}
		    }); 
		password_new_again.addTextChangedListener(new TextWatcher(){

	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
			@Override
			public void afterTextChanged(Editable s) {
				SharedPreferences settings = getActivity().getSharedPreferences("Password", 0);
				if((password_new_again.getText().toString().equals(password_new.getText().toString()))){
					password_new_again.setBackgroundColor(0xFF00E500);
					password_new.setBackgroundColor(0xFF00E500);
					new_flag = true;
				}else{//Password is different
					password_new.setBackgroundColor(0xFFFF3232);
					password_new_again.setBackgroundColor(0xFFFF3232);
					new_flag = false;
				}
			}
	    }); 
		ButterKnife.inject(this, view);
		
		return view;
	}
	
	@OnClick(R.id.save_password)
	void SavePass(View v){    	
		SharedPreferences settings = getActivity().getSharedPreferences("Password", 0);
		if (old_flag && new_flag){
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("password",password_new.getText().toString());
			editor.commit();
			Toast.makeText(getActivity(), String.format("Nastavenia uložené"), Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getActivity(), String.format("Heslo neuložené, CHYBA"), Toast.LENGTH_LONG).show();
		}
     }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	boolean pass_compare (String password1, String password2){
		return password1.equals(password2);
	}
}
