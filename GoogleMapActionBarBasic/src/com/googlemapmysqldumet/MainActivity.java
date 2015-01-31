package com.googlemapmysqldumet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends ActionBarActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.exit) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements LocationListener{

		/*
		 * membuat variabel global
		 */
		final int RQS_GooglePlayServices = 1;
		private GoogleMap googleMap;

		double latitude, longitude;
		
		
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			CheckGPS();
			
			
			return rootView;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			SupportMapFragment fm = (SupportMapFragment) getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.map);
			
				

			googleMap = fm.getMap();
			googleMap.setMyLocationEnabled(true);
			
			LatLng posisi = new LatLng(latitude, longitude); 

			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posisi,
					14));
			
		}
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			LatLng posisi = new LatLng(latitude, longitude); 

			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posisi,
					14));
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			
			super.onActivityResult(requestCode, resultCode, data);
			
		}

		@Override
		public void onLocationChanged(Location location) {
			
			try {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			} catch (Exception e) {
				
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
		
		public void CheckGPS() {
			try {
				LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				
				if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("info");
					builder.setMessage("Apakah anda akan mengaktifkan GPS?");
					builder.setPositiveButton("Ya",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									Intent i = new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(i);

								}
							});
					
					builder.setNegativeButton("Tidak",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int arg1) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					builder.create().show();
				}
				
				
			} catch (Exception e) {
				

			}
			
			int status = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(getActivity().getBaseContext());
			
			if (status != ConnectionResult.SUCCESS) {
				
				int requestCode = 10;
				
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(),
						requestCode);
				
				dialog.show();
				
			} else {
				Criteria criteria = new Criteria();
				
				LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				
				String provider = locationmanager.getBestProvider(criteria, true);
				
				Location location = locationmanager.getLastKnownLocation(provider);
				
				if (location != null) {
					onLocationChanged(location);
				}
				
				locationmanager.requestLocationUpdates(provider, 500, 0, this);
				
				
			}
		}
		
		

		
		
		
	}
	
	
	

}
