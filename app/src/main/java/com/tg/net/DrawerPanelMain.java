package com.tg.net;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import android.content.pm.PackageInfo;
import com.tg.net.util.Utils;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Build;
import android.content.Intent;
import android.net.Uri;

import androidx.core.view.GravityCompat;
import com.tg.net.activities.ConfigGeralActivity;

public class DrawerPanelMain
	implements NavigationView.OnNavigationItemSelectedListener
{
	private AppCompatActivity mActivity;
	
	public DrawerPanelMain(AppCompatActivity activity) {
		mActivity = activity;
	}
	

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle toggle;

	public void setDrawer(Toolbar toolbar) {
		NavigationView drawerNavigationView = (NavigationView) mActivity.findViewById(R.id.drawerNavigationView);
		drawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawerLayoutMain);

		// set drawer
		toggle = new ActionBarDrawerToggle(mActivity,
			drawerLayout, toolbar, R.string.open, R.string.cancel);

        drawerLayout.setDrawerListener(toggle);

		toggle.syncState();

		// set app info
		PackageInfo pinfo = Utils.getAppInfo(mActivity);
		if (pinfo != null) {
			String version_nome = pinfo.versionName;
			int version_code = pinfo.versionCode;
			String header_text = String.format("v. %s (%d)", version_nome, version_code);

			View view = drawerNavigationView.getHeaderView(0);

			TextView app_info_text = view.findViewById(R.id.nav_headerAppVersion);
			app_info_text.setText(header_text);
		}

		// set navigation view
		drawerNavigationView.setNavigationItemSelectedListener(this);
	}
	
	public ActionBarDrawerToggle getToogle() {
		return toggle;
	}
	
	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();

		switch(id)
		{
			case R.id.miPhoneConfg:
				PackageInfo app_info = Utils.getAppInfo(mActivity);
				if (app_info != null) {
					String aparelho_marca = Build.BRAND.toUpperCase();

					if (aparelho_marca.equals("") || aparelho_marca.equals("")) {
						Toast.makeText(mActivity, R.string.error_no_supported, Toast.LENGTH_SHORT)
							.show();
					}
					else {
						try {
							Intent in = new Intent(Intent.ACTION_MAIN);
							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							in.setClassName("com.android.settings", "com.android.settings.RadioInfo");
							mActivity.startActivity(in);
						} catch(Exception e) {
							Toast.makeText(mActivity, R.string.error_no_supported, Toast.LENGTH_SHORT)
								.show();
						}
					}
				}
				break;

			case R.id.miSettings:
				Intent intent = new Intent(mActivity, ConfigGeralActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(intent);
				break;

			case R.id.miContato:
				String url1 = "https://www.app-tg-net.online/";
				Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
				intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(Intent.createChooser(intent4, mActivity.getText(R.string.open_with)));
				break;

			case R.id.miapnset:
				Intent intentapn = new Intent();
				intentapn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentapn.setAction(android.provider.Settings.ACTION_APN_SETTINGS);
				mActivity.startActivity(intentapn);
				break;

			/*case R.id.miAvaliarPlaystore:
				String url = "https://play.google.com/store/apps/details?id=com.tg.net";
				Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(Intent.createChooser(intent3, mActivity.getText(R.string.open_with)));
				break;

			case R.id.miSendFeedback:
				if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					try {
						GoogleFeedbackUtils.bindFeedback(mActivity);
					} catch (Exception e) {
						Toast.makeText(mActivity, "Não disponível em seu aparelho", Toast.LENGTH_SHORT)
							.show();
						SkStatus.logDebug("Error: " + e.getMessage());
					}
				}
				else {
					Intent email = new Intent(Intent.ACTION_SEND);  
					email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					email.putExtra(Intent.EXTRA_EMAIL, new String[]{"slipkprojects@gmail.com"});  
					email.putExtra(Intent.EXTRA_SUBJECT, "TGNETVPN - " + mActivity.getString(R.string.feedback));
					//email.putExtra(Intent.EXTRA_TEXT, "");  

					//need this to prompts email client only  
					email.setType("message/rfc822");  

					mActivity.startActivity(Intent.createChooser(email, "Choose an Email client:"));
				}
				break;
				
			case R.id.miAbout:
				if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            		drawerLayout.closeDrawers();
        		}
				Intent aboutIntent = new Intent(mActivity, AboutActivity.class);
				mActivity.startActivity(aboutIntent);
				break;*/
		}

		return true;
	}
	
}
