package com.gotech.gotechfactorytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class DeviceStatusToast
{
	private static DeviceStatusToast instance = null;
	public Context mContext;
	private TextView text;
	private View toastView;
	private Toast toast;

	private DeviceStatusToast(Context context)
	{
		// TODO Auto-generated constructor stub
		this.mContext = context;
		setViews();
	}

	public static synchronized DeviceStatusToast getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new DeviceStatusToast(context);
		}
		return instance;
	}

	private void setViews()
	{
		toastView = LayoutInflater.from(mContext).inflate(R.layout.device_status_toast, null);
		text = (TextView) toastView.findViewById(R.id.textStatus);
//		setContentView(toastView);
//		WindowManager.LayoutParams lp = getWindow().getAttributes();
//		lp.gravity = Gravity.CENTER;
//		lp.x = 0;
//		lp.y = 40;
//		lp.windowAnimations = R.style.AnimationFade;
//		getWindow().setAttributes(lp);
		toast = new Toast(mContext);
		toast.setView(toastView);
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//		// TODO Auto-generated method stub
//		dismiss();
//		((Callback) HomeActivity.getmContext()).onKeyDown(keyCode, event);
//		return super.onKeyDown(keyCode, event);
//	}

	public void setMessage(int id)
	{
		text.setText(mContext.getString(id));
	}

	public boolean isMessageNull()
	{
		String str = text.getText().toString();
		if(str == null || str.equals(""))
		{
			return true;
		}
		return false;
	}

	public void setMessage(CharSequence ch)
	{
		text.setText(ch);
	}

	public void showAndSetShowTime(int iShowTime)// ms
	{
		if (iShowTime > 2000) {
			toast.setDuration(Toast.LENGTH_LONG);
		}else {
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}
