package com.gotech.gotechfactorytest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import org.apache.http.conn.util.InetAddressUtils;

import com.gotech.gotechfactorytest.DvbCmdHandle;

import org.json.JSONException;
import org.json.JSONObject;

import com.gotech.gotechfactorytest.R;
import com.gotech.gotechfactorytest.R.color;
import com.gotech.gotechfactorytest.R.id;
import com.gotech.gotechfactorytest.R.layout;
import com.gotech.gotechfactorytest.R.raw;
import com.gotech.gotechfactorytest.R.string;
import android.preference.CheckBoxPreference;
import android.net.NetworkInfo;
import android.preference.PreferenceCategory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Environment;
import android.os.StatFs;
import android.net.NetworkInfo.State;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.view.inputmethod.InputMethodManager;

public class ObbMountActivity extends Activity
{
	private static final String	TAG						= "ObbMountActivity";
	final static int			SD_MESSAGE_Ok			= 0;
	final static int			SD_MESSAGE_ERROR		= 1;
	final static int			USB1_MESSAGE_Ok			= 2;
	final static int			USB1_MESSAGE_ERROR		= 3;
	final static int			USB2_MESSAGE_Ok			= 4;
	final static int			USB2_MESSAGE_ERROR		= 5;
	final static int			USB3_MESSAGE_Ok			= 6;
	final static int			USB3_MESSAGE_ERROR		= 7;
	final static int			WIFI_MESSAGE_Ok			= 8;
	final static int			WIFI_MESSAGE_ERROR		= 9;
	final static int			LAN_MESSAGE_Ok			= 10;
	final static int			LAN_MESSAGE_ERROR		= 11;
	final static int			WIFI_MESSAGE_PROCESS	= 12;
	final static int			RETEST_MESSAGE_PROCESS	= 13;
	final static int			SN_MESSAGE_OK			= 14;
	final static int			SN_MESSAGE_ERROR		= 15;
	final static int			SET_MAC_MESSAGE_OK		= 16;
	
	private static Context mContext;

	private TextView			mWifiState;
	private TextView			mLanNetState;
	private TextView			mUSB1State;
	private TextView			mUSB2State;
	private TextView			mUSB3State;
	private TextView			mTfCardState;
	private VideoView			mVideoState;
	private TextView			mRCUState;
	private Button				mLEDState;
	private TextView			mUSBNote;

	private TextView			mMulticastAddr;
	private TextView			mMulticastPort;
	private TextView			mSSID;
	private TextView			mMemorySize;
	private TextView			mFirmwareVersion;
	private TextView			mSystemVersion;
	private TextView			mReleaseDate;
	private EditText			mLanNetMac;
	private TextView			mIP;
	private TextView			mSN;
	private TextView			mNoSNNote;

	private Button				mBurnInTest;
	private Button				mBreak;
	private Button				mChangeMac;

	MACEditDialog				mDialog;

	private TextView			mWriteMac;
	private TextView			mMacWriteStates;

	private TableLayout			mLeftTable;
	private TableLayout			mRightTable;

	private String				mUri;
	private MediaController		mMController;

	// private EthernetManager mEthManager;
	private WifiAdmin			wifiAdmin;

	private MediaPlayer			mediaPlayer;

	private int					mWifiCount				= 0;
	private int					mLedColor				= 0;								// 0
																							// ==
																							// off,1,green,2,blue
	private int					restartTest				= 0;
	private int					wifiStrength			= 0;
	private int					wifiProcess				= 0;
	private int					mMacInput				= 0;								// 0
																							// none
																							// //1;change
																							// input
	private int					mScanNum				= 0;
	private int					wifiStateBeforeTest		= WifiManager.WIFI_STATE_ENABLED;

	private String				mSSIDString				= "";
	private String				mScan					= "";
	private String				mMac					= "";

	private String				mLanMacValue			= "";
	private String				mLanIPAddr				= null;
	private String				mLanSNNumber			= "";
	private boolean				SNExistFlag				= false;
	private boolean				showRestartMsg			= false;
	
	public static String		configPlatform			= "factory.test.platfrom";
	public static String		configSN				= "factory.test.sn";
	public static String		configUSBNum			= "factory.test.usb.number";
	public static String		configMACBurn			= "factory.test.burn.mac";
	public static String		configShowUSBMsg		= "factory.test.show.usbmsg";
	public static String		configTFcard			= "factory.test.tf.card";
	public static String		configLEDTest			= "factory.test.led";

	public static String		configTrue				= "true";
	public static String		configFalse				= "false";

	/* device path */
	private static String		ledIOPath				= "";
	private static String		usb1Path				= "";
	private static String		usb2Path				= "";
	private static String		usb3Path				= "";
	private static String		sdcardPath				= "";
	private static String		mLanStatePath			= "";
	private static String		mLanMACPath				= "";

	/* platform */
	private static final String		allwinnerPlatform		= "allwinner";
	private static final String		amlogicPlatform			= "amlogic";
	private static final String		HAISI_PLATFORM			= "haisi";

	private DvbCmdHandle				cmdHandle				= null;

	private Thread				threadBurning;
	private Thread				threadGPIO;
	private Thread				threadSdUSB1;
	private Thread				threadSdUSB2;
	private Thread				threadSdUSB3;
	private Thread				threadSd;
	private Thread				threadLAN;
	private Thread				threadSN;
	private TableRow			mFirmwareView;
	private TableRow			mMacBurnNote;

	/* set config for diffrent platform */
	private void PlatformConfigInit()
	{
		if (System.getProperty(configPlatform).equals(allwinnerPlatform))
		{
			System.setProperty(configUSBNum, "1");
			System.setProperty(configMACBurn, configFalse);
			System.setProperty(configSN, configFalse);
			System.setProperty(configShowUSBMsg, configFalse);
			System.setProperty(configTFcard, configFalse);
			System.setProperty(configLEDTest, configFalse);

			ledIOPath = "/sys/class/gpio_sw/PH13/data";
			usb1Path = "/sys/bus/usb/devices/1-1";
			usb2Path = "/sys/bus/usb/devices/2-1";
			usb3Path = "/sys/bus/usb/devices/3-1";
			sdcardPath = "/sys/bus/mmc/devices";
			mLanStatePath = "/sys/devices/platform/wemac.0/net/eth0/operstate";
			mLanMACPath = "/sys/devices/platform/wemac.0/net/eth0/address";
		}
		else if (System.getProperty(configPlatform).equals(amlogicPlatform))
		{
			System.setProperty(configUSBNum, "2");
			System.setProperty(configMACBurn, configTrue);
			System.setProperty(configSN, configFalse);
			System.setProperty(configShowUSBMsg, configTrue);
			System.setProperty(configTFcard, configFalse);
			System.setProperty(configLEDTest, configTrue);

			ledIOPath = "/sys/devices/platform/aml-setio/io_val";
			usb1Path = "/sys/bus/usb/devices/1-1";
			usb2Path = "/sys/bus/usb/devices/2-1";
			usb3Path = "/sys/bus/usb/devices/3-1";
			sdcardPath = "/sys/bus/memorycard/devices/memorycard0:sd";
			mLanStatePath = "/sys/devices/platform/wemac.0/net/eth0/operstate";
			mLanMACPath = "/sys/devices/platform/wemac.0/net/eth0/address";
		}
		else if (System.getProperty(configPlatform).equals(HAISI_PLATFORM))
		{
			System.setProperty(configUSBNum, "2");
			System.setProperty(configMACBurn, configFalse);
			System.setProperty(configSN, configFalse);
			System.setProperty(configShowUSBMsg, configTrue);
			System.setProperty(configTFcard, configTrue);
			System.setProperty(configLEDTest, configFalse);

			ledIOPath = "/sys/devices/platform/aml-setio/io_val";
			usb1Path = "/sys/bus/usb/devices/1-1";
			usb2Path = "/sys/bus/usb/devices/1-2";
			usb3Path = "/sys/bus/usb/devices/1-3";
//			sdcardPath = "/sys/devices/platform/hi_mci.0/mmc_host/mmc1/mmc1:0002";
			sdcardPath = "/sys/block/mmcblk1";
			mLanStatePath = "/sys/class/net/eth0/operstate";
			mLanMACPath = "/sys/class/net/eth0/address";
		}
	}

	private USBExistFlag	mUSBExistFlag	= new USBExistFlag();

	class USBExistFlag
	{
		boolean	mUSB1;
		boolean	mUSB2;
		boolean	mUSB3;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mContext = ObbMountActivity.this;

		// setDisplayFullHD(); gotech@jesse20131216 fix this because of jimh
		// suggest me commont this,because the HDMI no problem but AV out can't
		// display.
        
		/* set your platform */
		System.setProperty(configPlatform, allwinnerPlatform);

		PlatformConfigInit();

		setSystemAudio();
		initView();
		initData();

		startFactoryTest();
		playVideoView();

		if (System.getProperty(configSN).equals(configTrue))
		{
			reciverCmdMessage();
		}
		else
		{
			getSystemInfo();
		}

		showLocalMac();

		mLEDState.requestFocus();

		// gotech @jesse 2013-12-10 revised for amlogic rcu type ok.
		mRCUState.setText(getString(R.string.str_normal));
		mRCUState.setBackgroundResource(R.color.green);
		if (System.getProperty(configPlatform).equals(HAISI_PLATFORM))
		{
			mFirmwareView.setVisibility(View.GONE);
			((TextView)findViewById(R.id.system_version_text)).setText(getString(R.string.str_android_ver));
		}
	}
	
	private Context getContext(){
		return mContext;
	}

	private void initView()
	{
		setContentView(R.layout.tabletest);
	}
	
	private boolean macChangeFlag = false;

	private void initData()
	{
		mFirmwareView = (TableRow) this.findViewById(R.id.firmware_layout_id);
		mWifiState = (TextView) this.findViewById(R.id.wifi);
		mLanNetState = (TextView) this.findViewById(R.id.line_net);
		mUSB1State = (TextView) this.findViewById(R.id.usb);
		mUSB2State = (TextView) this.findViewById(R.id.usb1);
		mUSB3State = (TextView) this.findViewById(R.id.usb2);
		mTfCardState = (TextView) this.findViewById(R.id.tf_card);
		mUSBNote = (TextView) this.findViewById(R.id.usb_brun_note);
		mVideoState = (VideoView) this.findViewById(R.id.vido_view);
		mRCUState = (TextView) this.findViewById(R.id.rcu);
		mLEDState = (Button) this.findViewById(R.id.led);

		mMulticastAddr = (TextView) this.findViewById(R.id.multicast_addr);
		mMulticastPort = (TextView) this.findViewById(R.id.multicast_port);
		mSSID = (TextView) this.findViewById(R.id.ssid);
		mMemorySize = (TextView) this.findViewById(R.id.stronge_size);
		mFirmwareVersion = (TextView) this.findViewById(R.id.soft_version);
		mSystemVersion = (TextView) this.findViewById(R.id.hard_version);
		mReleaseDate = (TextView) this.findViewById(R.id.release_data);
		mLanNetMac = (EditText) this.findViewById(R.id.lan_mac);
		
		mIP = (TextView) this.findViewById(R.id.ip);

		mSN = (TextView) this.findViewById(R.id.sn);
		mNoSNNote = (TextView) this.findViewById(R.id.no_sn_note);

		mBurnInTest = (Button) this.findViewById(R.id.burn_in_test);
		mBreak = (Button) this.findViewById(R.id.break_ok);
		mChangeMac = (Button) this.findViewById(R.id.change_mac);

		mWriteMac = (TextView) this.findViewById(R.id.write);
		mMacWriteStates = (TextView) this.findViewById(R.id.read);
		mMacBurnNote = (TableRow) this.findViewById(R.id.mac_brun_note);

		mLeftTable = (TableLayout) this.findViewById(R.id.tableLayout);
		mRightTable = (TableLayout) this.findViewById(R.id.tableLayout1);

		mLEDState.setOnClickListener(ledBtnClickListener);
		mBurnInTest.setOnClickListener(burnInTestBtnClickListener);
		mBreak.setOnClickListener(breakBtnClickListener);
		mChangeMac.setOnClickListener(changeMacBtnClickListener);

		wifiAdmin = new WifiAdmin(ObbMountActivity.this);
		wifiStateBeforeTest = wifiAdmin.checkState();

		if (System.getProperty(configMACBurn).equals(configFalse)) {
/*			mWriteMac.setVisibility(View.GONE);
			mMacWriteStates.setVisibility(View.GONE);*/
			mMacBurnNote.setVisibility(View.INVISIBLE);
		}else {
/*			mWriteMac.setVisibility(View.VISIBLE);
			mMacWriteStates.setVisibility(View.VISIBLE);*/
			mMacBurnNote.setVisibility(View.VISIBLE);
		}

		if (System.getProperty(configTFcard).equals(configFalse))
		{
			TableRow mTfCardTR = (TableRow) this
					.findViewById(R.id.tf_card_talbe);

			mTfCardTR.setVisibility(View.GONE);
		}

		if (System.getProperty(configSN).equals(configFalse))
		{
			findViewById(R.id.sn_row).setVisibility(View.GONE);
			mSN.setVisibility(View.GONE);
			mNoSNNote.setVisibility(View.GONE);
		}
		else
		{
			cmdHandle = new DvbCmdHandle(ObbMountActivity.this);
		}

		if (System.getProperty(configLEDTest).equals(configFalse))
		{
			TableRow mLEDTR = (TableRow) this.findViewById(R.id.led_table);
			mLEDTR.setVisibility(View.GONE);
		}

		if (System.getProperty(configShowUSBMsg).equals(configFalse))
		{
			mUSBNote.setVisibility(View.GONE);
		}

		switch (Integer.parseInt(System.getProperty(configUSBNum)))
		{
		case 0:
			mUSB1State.setVisibility(View.GONE);
			mUSB2State.setVisibility(View.GONE);
			mUSB3State.setVisibility(View.GONE);
			break;
		case 1:
			mUSB2State.setVisibility(View.GONE);
			mUSB3State.setVisibility(View.GONE);
			break;
		case 2:
			mUSB3State.setVisibility(View.GONE);
			break;
		}
		
	/*	mLanNetMac.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
	            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber() + "    " +
	                    Thread.currentThread().getStackTrace()[2].getFileName() + "  " + s + " start  " + start + 
	                    "  after " + after + "  count " + count);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
	            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber() + "    " +
	                    Thread.currentThread().getStackTrace()[2].getFileName() + "  " + s + " start  " + start + 
	                    "  before " + before + "  count " + count);
			}

			@Override
			public void afterTextChanged(Editable s)
			{
	            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber() + "    " +
	                    Thread.currentThread().getStackTrace()[2].getFileName() + "  " + s);
			}
		});

		mLanNetMac.setOnEditorActionListener(new EditText.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
	            if (actionId == EditorInfo.IME_ACTION_DONE)
				{
//					macChangeFlag = true;
					
					DeviceStatusToast toast = DeviceStatusToast.getInstance(ObbMountActivity.this);
					toast.setMessage("Please Wait!");

					toast.showAndSetShowTime(5000);
					
					
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

					imm.hideSoftInputFromWindow(mLanNetMac.getWindowToken(), 0);
			
					
					return false;
				}
	            return false;
			}
		});*/
		

	}

	private Handler	handler	= new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case USB1_MESSAGE_Ok:
				mUSB1State.setText(getString(R.string.str_normal_usb1));
				mUSB1State.setBackgroundResource(R.color.green);
				mUSBExistFlag.mUSB1 = true;
				break;
			case USB1_MESSAGE_ERROR:
				mUSB1State.setText(getString(R.string.str_unnormal_usb1));
				mUSB1State.setBackgroundResource(R.color.red);
				mUSBExistFlag.mUSB1 = false;
				break;
			case USB2_MESSAGE_Ok:
				mUSB2State.setText(getString(R.string.str_normal_usb2));
				mUSB2State.setBackgroundResource(R.color.green);
				mUSBExistFlag.mUSB2 = true;
				break;
			case USB2_MESSAGE_ERROR:
				mUSB2State.setText(getString(R.string.str_unnormal_usb2));
				mUSB2State.setBackgroundResource(R.color.red);
				mUSBExistFlag.mUSB2 = false;
				break;
			case USB3_MESSAGE_Ok:
				mUSB3State.setText(getString(R.string.str_normal_usb3));
				mUSB3State.setBackgroundResource(R.color.green);
				mUSBExistFlag.mUSB3 = true;
				break;
			case USB3_MESSAGE_ERROR:
				mUSB3State.setText(getString(R.string.str_unnormal_usb3));
				mUSB3State.setBackgroundResource(R.color.red);
				mUSBExistFlag.mUSB3 = false;
				break;
			case SD_MESSAGE_Ok:
				mTfCardState.setText(getString(R.string.str_normal));
				mTfCardState.setBackgroundResource(R.color.green);
				break;
			case SD_MESSAGE_ERROR:
				mTfCardState.setText(getString(R.string.str_unnormal));
				mTfCardState.setBackgroundResource(R.color.red);
				break;
			case WIFI_MESSAGE_PROCESS:
				wifiProcess++;
				int pr = wifiProcess * 100 / 15;
				mWifiState.setText(pr + "%");
				mWifiState.setText(getString(R.string.str_wifi_process) + pr + "%");
				break;
			case LAN_MESSAGE_Ok:
				mLanNetState.setText(getString(R.string.str_normal));
				mLanNetState.setBackgroundResource(R.color.green);
				mIP.setText(mLanIPAddr);
				// mLanNetMac.setText(mLanMacValue);
				break;
			case LAN_MESSAGE_ERROR:
				mLanNetState.setText(getString(R.string.str_unnormal));
				mLanNetState.setBackgroundResource(R.color.red);
				mIP.setText(getString(R.string.str_lan_notice));
				// mLanNetMac.setText(mLanMacValue);
				break;
			case WIFI_MESSAGE_ERROR:
				mWifiState.setText(getString(R.string.str_unnormal));
				mWifiState.setBackgroundResource(R.color.red);

				mMulticastPort.setText(getString(R.string.str_wifi_notice));
				mMulticastAddr.setText(getString(R.string.str_wifi_notice));
				mSSID.setText(getString(R.string.str_wifi_notice));
				break;
			case WIFI_MESSAGE_Ok:
				mWifiState.setText(getString(R.string.str_normal));
				mWifiState.setBackgroundResource(R.color.green);

				mMulticastPort.setText(wifiStrength+ "%");
				mMulticastAddr.setText(String.valueOf(mWifiCount));
				mSSID.setText(mSSIDString);
				break;
			case SN_MESSAGE_OK:
				if (cmdHandle != null)
				{
					cmdHandle.dvbMonitorStop();
					cmdHandle = null;
				}

				mSN.setText(mLanSNNumber);
				mNoSNNote.setVisibility(View.INVISIBLE);
				getSystemInfo();
				break;
			case SN_MESSAGE_ERROR:
				mNoSNNote.setVisibility(View.VISIBLE);
				break;
			case SET_MAC_MESSAGE_OK:
				
				DeviceStatusToast toast = DeviceStatusToast.getInstance(mContext);	
				toast.setMessage("设置成功，重启后生效!");
				toast.showAndSetShowTime(5000);
				break;
			}

			if (System.getProperty(configShowUSBMsg).equals(configTrue))
			{
				if (mUSBExistFlag.mUSB2
						|| mUSBExistFlag.mUSB1
						|| mUSBExistFlag.mUSB3)
				{
					mUSBNote.setVisibility(View.INVISIBLE);
				}
				else if ((mUSBExistFlag.mUSB2 == false)
						&& (mUSBExistFlag.mUSB1 == false)
						&& (mUSBExistFlag.mUSB3 == false))
				{
					mUSBNote.setVisibility(View.VISIBLE);
				}
			}

			super.handleMessage(msg);
		};
	};

	OnClickListener	ledBtnClickListener			= new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if (0 == mLedColor)
			{
				writeFile(ledIOPath,"0");

				mLEDState.setBackgroundResource(R.color.red);
				mLedColor = 1;
			}
			else if (1 == mLedColor)
			{
				writeFile(ledIOPath, "1");
				mLEDState.setBackgroundResource(R.color.blue);
				mLedColor = 0;
			}
		}
	};

	OnClickListener	burnInTestBtnClickListener	= new OnClickListener() {
	@Override
	public void onClick(View v) {
		// TODO Auto-generated
	// method stub
			if (mediaPlayer != null) {
				mediaPlayer.stop();
				mediaPlayer = null;
			}
			Intent intent = new Intent(ObbMountActivity.this, VideoBurning.class);
			startActivity(intent);
		}
	};

	OnClickListener	breakBtnClickListener		= new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated
			// method stub
			finish();
		}
	};
	
	OnClickListener	changeMacBtnClickListener	= new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated
			// method stub
			
			mDialog = new MACEditDialog(getContext(), mLanNetMac.getText().toString(), true);

			mDialog.show();

			mDialog.setNegativeButton(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
				}
			});

			mDialog.setPositiveButton(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String tempMac = null;

					mDialog.setMacaddress();
					tempMac = mDialog.getMacAddress();

					mDialog.dismiss();

					if (tempMac.length() == 17) {
						mLanMacValue = tempMac;
						macChangeFlag = true;
						
					}else {
						DeviceStatusToast toast = DeviceStatusToast.getInstance(ObbMountActivity.this);
						toast.setMessage("无效 MAC!");
						toast.showAndSetShowTime(5000);
					}
				}
			});
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(TAG, "jesse----------------onKeyDown------------keyCode=" + keyCode);

		if (System.getProperty(configPlatform).equals(allwinnerPlatform)) {

			String scanStr = processKey(keyCode);
			if (mScanNum == 17) {
				mScanNum = 0;
				mScan = "";
			}
			
			if (scanStr.length() > 0) {
				mScan = mScan + scanStr;
				mWriteMac.setText(mScan);
	            
				Log.d("#######", "xxxxxxxxx Scaner = " + mScan);
				mScanNum++;
				
				if (mScanNum == 17) {
					mLanMacValue = mScan;
					macChangeFlag = true;
				}
			}
			
		}else {
			String scanStr = processKey(keyCode);
			if (mScanNum == 12) {
				mScanNum = 0;
				mScan = "";
				mMac = "";
			}

			if (scanStr.length() > 0) {
				mScan = mScan + scanStr;
				mMac = mMac + keyCode;
				Log.d("#######", "xxxxxxxxx Scaner = " + mScan);
				mScanNum++;

				if (mScanNum == 12) {
					// TODO add code here to burning
					Log.d("#######", "mac write 1111 = " + mScan);
					processCommandInitVersion();
					Log.d("#######", "mac write 222 = " + mScan);
					processCommandKeyName();
					Log.d("#######", "mac write 3333 = " + mScan);

					String mMacAscii = convertMacToAsciiString(mScan);

					Log.d("#######", "ggggggggggg mMacAscii= " + mMacAscii);

					processCommandMacWrite(mMacAscii);
					if (System.getProperty(configMACBurn).equals(configTrue)) {
						processCommandReadMac();
						mWriteMac.setText(mScan);
					}

					mScan = "";
					mMac = "";
					mScanNum = 0;
				}
			}

		}

		if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private EditText	edittext;

	private void startFactoryTest()
	{
		threadBurning = new Thread()
		{
			public void run()
			{
				while (true)
				{
					if (mMacInput == 1)
					{
						String sText = edittext.getText().toString();
						String str = "";
						for (int i = 0; i < sText.length(); i++)
						{
							int ch = (int) sText.charAt(i);
							Log.d("xxxxxxxxx", "###########3scan i=" + i
									+ " ch=" + ch);
							str = str + (char) ch;
							Log.d("xxxxxxxxx", "###########3scan str=" + str);
						}
						Log.d("###############", "scan string=" + sText);
						mMacInput = 2;
					}
					
					if (showRestartMsg)
					{
						showRestartMsg = false;
						
						try {
							Thread.sleep(3000);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						handler.sendEmptyMessage(SET_MAC_MESSAGE_OK);
					}
				}
			}
		};

		threadGPIO = new Thread()
		{
			public void run()
			{
				String s = null;

				s = readFile(ledIOPath);

				if (s.equals("0"))
				{
					mLedColor = 0;
					mLEDState.setBackgroundResource(R.color.blue);
				}
				else if (s.equals("1"))
				{
					mLedColor = 1;
					mLEDState.setBackgroundResource(R.color.red);
				}
			}
		};

		threadSdUSB1 = new Thread()
		{
			public void run()
			{
				while (true)
				{
					File myFilePath = new File(usb1Path);
					try
					{
						if (!myFilePath.exists())
						{
							handler.sendEmptyMessage(USB1_MESSAGE_ERROR);
						}
						else
						{
							handler.sendEmptyMessage(USB1_MESSAGE_Ok);
						}
					}
					catch (Exception e)
					{
						handler.sendEmptyMessage(USB1_MESSAGE_ERROR);
						e.printStackTrace();
					}

					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						break;
					}
				}
			}
		};

		threadSdUSB2 = new Thread()
		{
			public void run()
			{
				while (true)
				{
					File myFilePath = new File(usb2Path);
					try
					{
						if (!myFilePath.exists())
						{
							handler.sendEmptyMessage(USB2_MESSAGE_ERROR);
						}
						else
						{
							handler.sendEmptyMessage(USB2_MESSAGE_Ok);
						}
					}
					catch (Exception e)
					{
						handler.sendEmptyMessage(USB2_MESSAGE_ERROR);
						e.printStackTrace();
					}

					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						break;
					}
				}
			}
		};

		threadSdUSB3 = new Thread()
		{
			public void run()
			{
				while (true)
				{
					File myFilePath = new File(usb3Path);
					try
					{
						if (!myFilePath.exists())
						{
							handler.sendEmptyMessage(USB3_MESSAGE_ERROR);
						}
						else
						{
							handler.sendEmptyMessage(USB3_MESSAGE_Ok);
						}
					}
					catch (Exception e)
					{
						handler.sendEmptyMessage(USB3_MESSAGE_ERROR);
						e.printStackTrace();
					}

					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						break;
					}
				}
			}
		};

		threadSd = new Thread()
		{
			public void run()
			{
				while (true)
				{
					File myFilePath = new File(sdcardPath);
					File[] files = myFilePath.listFiles();
					try
					{
						if (myFilePath.isDirectory())
						{
							if (files.length == 0)
							{
								// Log.d("TestSD","TEST SD fail!!!");
								handler.sendEmptyMessage(SD_MESSAGE_ERROR);
							}
							else
							{
								handler.sendEmptyMessage(SD_MESSAGE_Ok);
							}
						}
						else
						{
							handler.sendEmptyMessage(SD_MESSAGE_ERROR);
						}
					}
					catch (Exception e)
					{
						handler.sendEmptyMessage(SD_MESSAGE_ERROR);
						System.out.println("create new file fail!!!");
						e.printStackTrace();
					}

					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						break;
					}
				}
			}
		};

		threadLAN = new Thread()
		{
			public void run()
			{
				while (true)
				{
					String status = getEthernetDevStatus(mLanStatePath);

					if (status == null)
					{
						handler.sendEmptyMessage(LAN_MESSAGE_ERROR);
					}
					else
					{
						if (status.equals("down"))
						{
							handler.sendEmptyMessage(LAN_MESSAGE_ERROR);
						}
						else if (status.equals("up"))
						{
							mLanIPAddr = getLocalIpAddress();

							handler.sendEmptyMessage(LAN_MESSAGE_Ok);
						}
						else
						{
							handler.sendEmptyMessage(LAN_MESSAGE_ERROR);
						}
					}

					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try
					{
						Thread.sleep(500);
					}
					catch (Exception e)
					{
						// TODO: handle exception
					}

					if (wifiAdmin.checkState() == WifiManager.WIFI_STATE_ENABLED)
					{
						wifiAdmin.startScan();

						List<ScanResult> wifiList = wifiAdmin.getWifiList();

						try
						{
							if (wifiList.size() > 0)
							{
								mWifiCount = wifiList.size();
								mSSIDString = wifiList.get(0).SSID;
								wifiStrength = WifiManager.calculateSignalLevel(wifiList.get(0).level, 100);
								handler.sendEmptyMessage(WIFI_MESSAGE_Ok);
							}
							else
							{
								handler.sendEmptyMessage(WIFI_MESSAGE_ERROR);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						wifiAdmin.openWifi();
						handler.sendEmptyMessage(WIFI_MESSAGE_ERROR);
					}
					
					if (macChangeFlag) {
						macChangeFlag = false;
						showRestartMsg = true;
						// mLanMacValue = mLanNetMac.getText().toString();

						System.out.println("-------------" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "  ----------  " + 
								Thread.currentThread().getStackTrace()[2].getFileName());

						sendCmd("mmac -m " + mLanMacValue);
						
					}
				}
			}
		};

		threadSN = new Thread() {
			public void run() {
				while (!SNExistFlag) {
					handler.sendEmptyMessage(SN_MESSAGE_ERROR);

					cmdHandle.packSendCommand("Security", 0, "none");

					try {
						Thread.sleep(2000);
					}catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}

				if (SNExistFlag) {
					handler.sendEmptyMessage(SN_MESSAGE_OK);
				}
			}

		};

		threadBurning.start();

		if (System.getProperty(configLEDTest).equals(configTrue))
		{
			threadGPIO.start();
		}

		if (Integer.parseInt(System.getProperty(configUSBNum)) > 0)
		{
			threadSdUSB1.start();
			if (Integer.parseInt(System.getProperty(configUSBNum)) > 1)
			{
				threadSdUSB2.start();
				if (Integer.parseInt(System.getProperty(configUSBNum)) > 2)
				{
					threadSdUSB3.start();
				}
			}
		}

		if (System.getProperty(configTFcard).equals(configTrue))
		{
			threadSd.start();
		}

		threadLAN.start();

		if (System.getProperty(configSN).equals(configTrue))
		{
			threadSN.start();
		}

	}

	private void showLocalMac() {
		if (System.getProperty(configPlatform).equals(allwinnerPlatform)) {
			mLanMacValue = getLocalMac();

		}else {
			processCommandKeyName();
			mLanMacValue = convertAsciiStringToMac(readFile("/sys/class/aml_keys/aml_keys/key_read"));
		}

		mLanNetMac.setText(mLanMacValue);
	}

	private void getSystemInfo()
	{
		String[] attr = { "ro.build.version.release=", "ro.build.date=",
				"ro.product.firmware=" };

		File file = new File("/system/build.prop");
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
//				System.out.println("line " + line + ": " + tempString);
				for (int index = 0; index < attr.length; index++)
				{
					int posStart = tempString.indexOf(attr[index]);
					if (-1 != posStart)
					{
						String temp = tempString.substring(posStart+ attr[index].length());
						if (index == 0)
						{

							String sysTemp = "";
							if (temp.indexOf("-", 0) == -1)
							{
								sysTemp = temp;
							}
							else
							{
								sysTemp = temp.substring(0,
										temp.indexOf("-", 0));
							}
							mSystemVersion.setText(sysTemp);
						}
						else if (index == 1)
						{
							mReleaseDate.setText(temp);
						}
						else if (index == 2)
						{
							mFirmwareVersion.setText(temp);
						}
					}
				}
				line++;
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		mMemorySize.setText(getMemoryInfo());
	}

	private double getTotalDirectoryMemorySize(File path)
	{
		// File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		// Byte--->G jesse
		return (double) (totalBlocks * blockSize) / 1000 / 1000 / 1000;
	}

	private double getTotalInternalMemorySize()
	{
		return (double) (getTotalDirectoryMemorySize(Environment
				.getDataDirectory()) + getTotalDirectoryMemorySize(Environment
				.getRootDirectory()));

	}

	private double getAvailableInternalMemorySize()
	{
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		// Byte--->G jesse
		return (double) (availableBlocks * blockSize) / 1000 / 1000 / 1000;
	}

	private String getMemoryInfo()
	{
		String ret = getResources().getString(R.string.str_all)
				+ String.format("%.2f", getTotalInternalMemorySize()) + "G,"
				+ getResources().getString(R.string.str_remain)
				+ String.format("%.2f", getAvailableInternalMemorySize()) + "G";
		return ret;
	}

	private String processKey(int keycode) {
		String sRet = "";
		int code = 0;
		switch (keycode) {
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
				code = keycode - 7;
				sRet = sRet + code;
				break;
			case 29:
				sRet = "A";
				break;
			case 30:
				sRet = "B";
				break;
			case 31:
				sRet = "C";
				break;
			case 32:
				sRet = "D";
				break;
			case 33:
				sRet = "E";
				break;
			case 34:
				sRet = "F";
				break;
			case 59:
				sRet = ":";
				break;

		}
		return sRet;
	}

	private void processCommandInitVersion()
	{
		chmodFilePermision("/sys/class/aml_keys/aml_keys/version", 777);

		try
		{
			writeFile("/sys/class/aml_keys/aml_keys/version", "nand3");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		chmodFilePermision("/sys/class/aml_keys/aml_keys/version", 755);
	}

	private void processCommandKeyName()
	{
		chmodFilePermision("/sys/class/aml_keys/aml_keys/key_name", 777);

		String resultString;
		try
		{
			writeFile("/sys/class/aml_keys/aml_keys/key_name", "mac");
		}
		catch (Exception e)
		{
			Log.i(TAG, "GOTECH func processCommandKeyName error!");
			e.printStackTrace();
		}

		chmodFilePermision("/sys/class/aml_keys/aml_keys/key_name", 755);
	}

	private void processCommandMacWrite(String strMac)
	{
		chmodFilePermision("/sys/class/aml_keys/aml_keys/key_write", 777);

		String resultString;
		try
		{
			writeFile("/sys/class/aml_keys/aml_keys/key_write", strMac);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		chmodFilePermision("/sys/class/aml_keys/aml_keys/key_write", 755);
	}

	private void processCommandReadMac()
	{
		String resultString;
		resultString = readFile("/sys/class/aml_keys/aml_keys/key_read");

		String strMac = convertMacToAsciiString(mScan);
		Log.i("MACADD", "mac =" + mScan);
		Log.i("MACADD", "strMac =" + strMac);

		if (strMac.equalsIgnoreCase(resultString))
		{
			Log.d("@@@@@@@@@@@@@@@@", "mac write ok true");
			mLanNetMac.setText(mScan);

			if (System.getProperty(configMACBurn).equals(configTrue))
			{
				mMacWriteStates.setText(getString(R.string.str_mac_ok));
				mMacWriteStates.setBackgroundColor(Color.GREEN);
			}
		}
		else
		{
			Log.d("@@@@@@@@@@@@@@@@", "mac write false");
			if (System.getProperty(configMACBurn).equals(configTrue))
			{
				mMacWriteStates.setText(getString(R.string.str_mac_fault));
				mMacWriteStates.setBackgroundColor(Color.RED);
			}
		}

		if (strMac.equals("222222"))
		{
			Log.d("@@@@@@@@@@@@@@@@", "@@@@@@ test true");
		}
		else
		{
			Log.d("@@@@@@@@@@@@@@@@", "@@@@@@@@@ test ok");
		}

		Log.d("@@@@@@@@@@@@@@@@",
				"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ resultString = "
						+ resultString);
		Log.d("@@@@@@@@@@@@@@@@",
				"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ strMac = " + strMac);
	}

	private String convertMacToAsciiString(String strMac) {
		int i = 0;
		Boolean bconvert = true;
		char a = ':';
		String ascii = Integer.toHexString(a);

		Log.d("kkkkkkkkkkkkk", "ascii : =" + ascii);
		String asciiA = "";
		String sRet = "";
		while (bconvert) {
			if ((i > 0) && (i % 2 == 0)) {
				sRet = sRet + ascii;
			}

			a = strMac.charAt(i);

			asciiA = Integer.toHexString(a);

			Log.d("MACADD", "char  " + a + "=" + asciiA);
			sRet = sRet + asciiA;
			i++;
			if (i == 12) {
				bconvert = false;
			}
		}
		return sRet;
	}

	// jesse add method for convert module.
	private String convertAsciiStringToMac(String strAscii)
	{
		int length = strAscii.length();
		Log.i("MACADD", "length=" + length + ", src=" + strAscii);
		if (length <= 0)
		{
			return "";
		}

		Boolean bconvert = true;
		int i = 0;
		String ret = "";
		int asciiValue;
		char AsciiChange;
		String temp = "";
		while (bconvert)
		{
			temp += strAscii.charAt(i);
			i++;

			if (i % 2 == 0 && i != 0)
			{
				char tempH = temp.charAt(0);
				int intH = 0;
				if (tempH >= '0' && tempH <= '9')
				{
					intH = tempH - '0';
					intH *= 16;
				}
				else if (tempH >= 'a' && tempH <= 'z')
				{
					intH = tempH - 'a' + 10;
					intH *= 16;
				}

				char tempL = temp.charAt(1);
				int intL = 0;
				if (tempL >= '0' && tempL <= '9')
				{
					intL = tempL - '0';
				}
				else if (tempL >= 'a' && tempL <= 'z')
				{
					intL = tempL - 'a' + 10;
				}
				asciiValue = intH + intL;

				AsciiChange = (char) asciiValue;
				ret += String.valueOf(AsciiChange);
				Log.i("MACADD", "ret=" + ret);
				temp = "";
			}

			if (i == length)
				bconvert = false;
		}
		return ret;
	}

	private void chmodFilePermision(String filePath, int mode)
	{
		if (null != filePath)
		{
			try
			{
				String command = "chmod " + mode + " " + filePath;
				Runtime runtime = Runtime.getRuntime();
				Process proc = runtime.exec(command);
			}
			catch (IOException e)
			{
				// TODO: handle exception
				Log.i(TAG, "chmod file permision fail!");
				e.printStackTrace();
			}
		}
	}
	
	public static void sendCmd(String cmd)
	{
		Process process = null;
		DataOutputStream os = null;
		DataInputStream is = null;

		try {
			process = Runtime.getRuntime().exec("/system/xbin/su"); /*
																	 * 这里可能需要修改su的源代码
																	 * （注掉 if(myuid !=AID_ROOT&& myuid != AID_SHELL) {
																	 */

			os = new DataOutputStream(process.getOutputStream());
			is = new DataInputStream(process.getInputStream());
			os.writeBytes(cmd + " \n"); // 这里可以执行具有root 权限的程序了
			os.flush();
			process.waitFor();
		}catch (Exception e) {
			Log.e(TAG, "Unexpected error - Here is what I know:" + e.getMessage());
		}finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
				process.destroy();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getLocalIpAddress()
	{
		try
		{
			String ipv4;
			List netlist = Collections.list(NetworkInterface
					.getNetworkInterfaces());
			for (int index = 0; index < netlist.size(); index++)
			{
				if (((NetworkInterface) netlist.get(index)).getName()
						.toLowerCase().equals("eth0"))
				{
					List ialist = Collections.list(((NetworkInterface) netlist
							.get(index)).getInetAddresses());
					for (int count = 0; count < ialist.size(); count++)
					{
						if (!((InetAddress) ialist.get(count))
								.isLoopbackAddress()
								&& InetAddressUtils
										.isIPv4Address(ipv4 = ((InetAddress) ialist
												.get(count)).getHostAddress()))
						{
							return ipv4;
						}
					}
				}
			}
		}
		catch (SocketException ex)
		{
			Log.e(TAG, ex.toString());
		}
		return null;
	}

	private String getLocalMac()
	{
		String stMac = null;

		stMac = readFile(mLanMACPath);

		if (stMac == null)
		{
			return "00:00:00:00:00:00";
		}
		else
		{
			return stMac;
		}
	}

	private String getEthernetDevStatus(String eth0_parm)
	{
		String mEhernet_dev_status = null;
		FileReader fileReader = null;

		try
		{
			fileReader = new FileReader(eth0_parm);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		BufferedReader bufferedReader = null;
		bufferedReader = new BufferedReader(fileReader);
		try
		{
			mEhernet_dev_status = bufferedReader.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			bufferedReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			fileReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		Log.i("###############################", "mEhernet_dev_status = "
				+ mEhernet_dev_status);
		return mEhernet_dev_status;
	}

	private void playVideoView()
	{
		mUri = "android.resource://" + getPackageName() + "/" + R.raw.testvideo;
		mMController = new MediaController(this);
		mMController.setFocusable(false);
		mVideoState.setVideoURI(Uri.parse(mUri));

		mVideoState.start();

		mVideoState.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				mVideoState.setVideoURI(Uri.parse(mUri));
				mVideoState.start();
			}
		});
	}

	private void reciverCmdMessage()
	{
		cmdHandle.setMessageRecListenner(new DvbCmdHandle.MessageRecListener()
		{
			@Override
			public void handleMsg(String msg)
			{
				handleMsg(null, 0, null, msg);
			}

			public void handleMsg(String moduleName, int CmdType,
					String CmdMark, String Data)
			{
				// 在这里根据模块，命令类型，Mark标志（ERROR 和 SUCCESS）， 数据Data ，处理即可
				// 例如发送可请求sn的命令，在这里获取sn
				try
				{
					JSONObject root = new JSONObject(Data);
					if (root.has("SerialNumber"))
					{
						mLanSNNumber = root.getString("SerialNumber"); // 序列号

						SNExistFlag = true;

						if (root.has("CID"))
						{
							int CID = root.getInt("CID");// 客户id
						}
						if (root.has("HID"))
						{
							int HID = root.getInt("HID");// 硬件id
						}
						if (root.has("PID"))
						{
							int PID = root.getInt("PID");// 平台id
						}
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		});

		cmdHandle.dvbMonitorStart();// 开始监听

	}

	public String readFile(String file)
	{
		String content = "";
		chmodFilePermision(file, 777);
		File OutputFile = new File(file);
		if (!OutputFile.exists())
		{
			return content;
		}

		try
		{
			InputStream instream = new FileInputStream(file);
			if (instream != null)
			{
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				Log.i(TAG, "GOTECH func buffreader=" + buffreader.toString());
				String line;
				while ((line = buffreader.readLine()) != null)
				{
					content += line;
				}
				instream.close();
			}
		}
		catch (java.io.FileNotFoundException e)
		{
			Log.d("TestFile", "The File doesn't not exist.");
		}
		catch (IOException e)
		{
			Log.i(TAG, "GOTECH func readFile error!");
			Log.d("TestFile", e.getMessage());
		}

		chmodFilePermision(file, 755);
		return content;
	}

	private void setSystemAudio()
	{
		AudioManager audioManager;

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
	}

	private void setDisplayFullHD()
	{
		writeFile("/sys/class/display/mode", "1080p60");
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeFile("/sys/class/ppmgr/ppscaler_rect", "0 0 1920 1080 0");
	}

	public void writeFile(String file, String value)
	{
		File OutputFile = new File(file);
		if (!OutputFile.exists())
		{
			Log.d(TAG, "--------writeFile exit-----------");
			return;
		}
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(OutputFile),
					32);
			try
			{
				Log.d(TAG, "set" + file + ": " + value);
				out.write(value);
			}
			finally
			{
				out.close();
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			Log.e(TAG, "IOException when write " + OutputFile);
		}
	}

	@Override
	protected void onStop()
	{
		Log.d("TAG-onStop", "onStop()------------");
		if (mVideoState != null)
		{
			mVideoState.stopPlayback();

			mVideoState = null;
		}

		if (cmdHandle != null)
		{
			cmdHandle.dvbMonitorStop();
			cmdHandle = null;
		}
		// if(mEthManager!=null)
		// mEthManager.setEnabled(true);
		super.onStop();
	}

	@Override
	protected void onResume()
	{
		if (mVideoState != null)
		{
			mVideoState.start();
		}
		else
		{
			mMController = new MediaController(ObbMountActivity.this);
			mMController.setFocusable(false);
			mUri = "android.resource://" + getPackageName() + "/"+ R.raw.testvideo;
			mVideoState = (VideoView) ObbMountActivity.this.findViewById(R.id.vido_view);
			mVideoState.setVideoURI(Uri.parse(mUri));
			mVideoState.start();

			mVideoState.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
			{
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					mVideoState.setVideoURI(Uri.parse(mUri));
					mVideoState.start();
				}
			});
		}

		// registerReceiver(mEthernetReceiver, ethfilter);
		
		mChangeMac.requestFocus();

		super.onResume();
	}

	@Override
	public void onPause()
	{
		// unregisterReceiver(mEthernetReceiver);
		super.onPause();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub

		threadBurning.interrupt();
		threadGPIO.interrupt();
		threadSdUSB1.interrupt();
		threadSdUSB2.interrupt();
		threadSdUSB3.interrupt();
		threadSd.interrupt();
		threadLAN.interrupt();
		threadSN.interrupt();

		if (wifiStateBeforeTest != WifiManager.WIFI_STATE_ENABLED)
		{
			wifiAdmin.closeWifi();
		}

		writeFile(ledIOPath, "1");

		super.onDestroy();
	}
}
