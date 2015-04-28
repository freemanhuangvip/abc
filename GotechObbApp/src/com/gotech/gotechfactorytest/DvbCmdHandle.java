package com.gotech.gotechfactorytest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;

import android.content.Context;
import android.util.Log;

//import com.alibaba.fastjson.JSON;

public class DvbCmdHandle implements Runnable
{
	private final boolean bTag = false;
	private final String TAG = DvbCmdHandle.class.getSimpleName();
	private DatagramSocket cmdSocket;
	private MessageRecListener msgRecListener = null;
	private Boolean bWork;
	// private PortMonitor dvbMonitor = null;
	private int iStackPort = 51021;
	private String localLoopAddr = "127.0.0.1";
	private  int MAX_CMD_SIZE = 1024 * 64;
	private final int MAX_SOCKET_BUFFER_SIZE = 1024 * 256;
	private byte[] receiveBuffer;
	private Thread receiveListener;
	private Context mContext;

	public DvbCmdHandle(Context context)
	{
		this.mContext = context;
		try
		{
			cmdSocket = new DatagramSocket();
			setSocketSendBufferSize(MAX_SOCKET_BUFFER_SIZE);
			setSocketReceiveBufferSize(MAX_SOCKET_BUFFER_SIZE);
			bWork = false;
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}

	public DvbCmdHandle(Context context, int Port)
	{
		this.mContext = context;
		iStackPort = Port;
		try
		{
			cmdSocket = new DatagramSocket();
			setSocketSendBufferSize(MAX_SOCKET_BUFFER_SIZE);
			setSocketReceiveBufferSize(MAX_SOCKET_BUFFER_SIZE);
			bWork = false;
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}

	public DvbCmdHandle(Context context, String ipAddr, int Port)
	{
		this.mContext = context;
		iStackPort = Port;
		localLoopAddr = ipAddr;
		try
		{
			cmdSocket = new DatagramSocket();
			setSocketSendBufferSize(MAX_SOCKET_BUFFER_SIZE);
			setSocketReceiveBufferSize(MAX_SOCKET_BUFFER_SIZE);
			bWork = false;
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}

	public void setSocketSendBufferSize(int iBufSize) throws SocketException
	{
		cmdSocket.setSendBufferSize(iBufSize);
	}

	public void setSocketReceiveBufferSize(int iBufSize) throws SocketException
	{
		cmdSocket.setReceiveBufferSize(iBufSize);
	}

	public void setMaxContentReceiveBuffer(int iBufSize)
	{
		this.MAX_CMD_SIZE = iBufSize;
	}
	
	public int getSocketSendBufferSize() throws SocketException
	{
		return cmdSocket.getSendBufferSize();
	}

	public int getSocketReceiveBufferSize() throws SocketException
	{
		return cmdSocket.getReceiveBufferSize();
	}

	public void serviceSendcmd(DatagramSocket cmdSocket, String hostName, int PORT, byte[] message)
	{
		DatagramPacket sendPacket = null;
		try
		{
			InetAddress ip = InetAddress.getByName(hostName);
			sendPacket = new DatagramPacket(message, message.length, ip, PORT);
			cmdSocket.send(sendPacket);
		} catch (UnknownHostException e)
		{
			Log.e(TAG, "send:" + e.getMessage());
		} catch (SocketException e)
		{
			Log.e(TAG, "send:" + e.getMessage());
		} catch (IOException e)
		{
			Log.e(TAG, "send:" + e.getMessage());
		}
	}

	public void packSendCommand(String moduleName, int moduleType, String data)
	{
		// short iCheckSum = 0;
		final String msg = String.format(Locale.US, "%s\n%04d\n%s", moduleName, moduleType, data);
		if (bTag)
			Log.d(TAG, "send == " + msg);

		Runnable run = new Runnable()
		{
			public void run()
			{
				serviceSendcmd(cmdSocket, localLoopAddr, iStackPort, msg.getBytes());
			}
		};
		if(mContext.getMainLooper().getThread().getId() == Thread.currentThread().getId())
		{
			new Thread(run).start();
		}
		else
		{
			run.run();
		}
	}

//	public String ObjectToJsonString(Object object)
//	{
//		String data = JSON.toJSONString(object);
//		return data;
//	}
//
//	public Object ObjectToJsonObject(Object object)
//	{
//		Object data = JSON.toJSON(object);
//		return data;
//	}

	public String receiveMessage()
	{
		byte[] message = new byte[MAX_CMD_SIZE];
		byte[] dataReceive = null;
		String msgStr = "";
		DatagramPacket receivePacket = null;
		try
		{
			receivePacket = new DatagramPacket(message, message.length);
			while (true)
			{
				cmdSocket.receive(receivePacket);
				dataReceive = receivePacket.getData();
				if (dataReceive == null)
				{
					continue;
				}
				msgStr = new String(dataReceive, 0, receivePacket.getLength());
				break;
			}
		} catch (SocketException e)
		{
			e.printStackTrace();
			Log.d(TAG, "SocketException = " + e.getMessage());
		} catch (IOException e)
		{
			e.printStackTrace();
			Log.d(TAG, "IOException = " + e.getMessage());
		}
		finally
		{
			message = null;// just tell gc ,it can release.
		}
		return msgStr;
	}

	public void dvbMonitorStart()
	{
		if (!bWork)
		{
			bWork = true;
			receiveListener = new Thread(this);
			receiveListener.start();
		}
	}

	public void dvbMonitorStop()
	{
		if (bWork)
		{
			bWork = false;

			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						serviceSendcmd(new DatagramSocket(), localLoopAddr, cmdSocket.getLocalPort(), "stop listener".getBytes());
					} catch (SocketException e)
					{
						e.printStackTrace();
					}
				}
			}).start();

		}
	}

	public boolean isPortMonitorAlive()
	{
		return receiveListener.isAlive();
	}

	public void setReceiveThreadPriority(int iPriority)
	{
		receiveListener.setPriority(iPriority);
	}

	public int getReceiveThreadPriority()
	{
		return receiveListener.getPriority();
	}

	public void setReceiveThreadName(String threadName)
	{
		receiveListener.setName(threadName);
	}

	public String getReceiveThreadName()
	{
		return receiveListener.getName();
	}

	public boolean getWorkStatus()
	{
		return bWork;
	}

	public int getLocalSocketPort()
	{
		return cmdSocket.getLocalPort();
	}

	public String getIpAddress()
	{
		return cmdSocket.getLocalAddress().getHostAddress();
	}

	public InetAddress getInetAddress()
	{
		return cmdSocket.getLocalAddress();
	}

	public interface MessageRecListener
	{
		public void handleMsg(String msg);
		public void handleMsg(String moduleName, int CmdType, String CmdMark, String Data);
	}

	public void setMessageRecListenner(MessageRecListener msgListener)
	{
		this.msgRecListener = msgListener;
	}

	public String getCmdModule(String msg)
	{
		String moduleName = null;
		String[] cmdSection = msg.split("\n");
		if (cmdSection.length > 2)
		{
			moduleName = cmdSection[0];
		}
		return moduleName;
	}

	public String getCmdContent(String msg)
	{
		String content = null;
		int iFlagIndex = msg.indexOf(DVBConstants.CMD_DIV_MARK_CHAR);
		if (iFlagIndex != -1)
		{
			iFlagIndex = msg.indexOf(DVBConstants.CMD_DIV_MARK_CHAR, iFlagIndex + 1);
			content = msg.substring(iFlagIndex + 1);
			if (content.startsWith(DVBConstants.CMD_SUCCESS_MARK_STRING))
			{
				content = content.substring(DVBConstants.CMD_SUCCESS_MARK_STRING.length());
			} else if (content.startsWith(DVBConstants.CMD_ERROR_MARK_STRING))
			{
				content = content.substring(DVBConstants.CMD_SUCCESS_MARK_STRING.length());
			}
		}
		return content;
	}

	public int getCmdType(String msg)
	{
		int iType = -1;
		String[] cmdSection = msg.split("\n");
		if (cmdSection.length > 2)
		{
			iType = Integer.parseInt(cmdSection[1]);
		}
		return iType;
	}

	@Override
	public void run()
	{
		try
		{
			while (bWork)
			{
				if (receiveBuffer == null)
				{
					receiveBuffer = new byte[MAX_CMD_SIZE];
				}
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, MAX_CMD_SIZE);
				cmdSocket.receive(receivePacket);
				String msg = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
				if (bWork)
				{
					HandleMessaageInner(msgRecListener, msg);
				} else
				{
					receiveBuffer = null;
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
			Log.e(TAG, "PortMonitor Thread has dead");
		}
	}
	
	void HandleMessaageInner(MessageRecListener listener, String msg)
	{
		if(listener == null || msg == null)
		{
			return;
		}
		String[] contents = msg.split("\n");
		if(contents != null && contents.length > 2)
		{
			if(contents[2].startsWith(DVBConstants.CMD_SUCCESS_MARK_STRING))
			{
				int CmdType = Integer.parseInt(contents[1]);
				String data = contents[2].substring(DVBConstants.CMD_SUCCESS_MARK_STRING.length());
				listener.handleMsg(contents[0], CmdType, DVBConstants.CMD_SUCCESS_MARK_STRING, data);
			}
			else if(contents[2].startsWith(DVBConstants.CMD_ERROR_MARK_STRING))
			{
				int CmdType = Integer.parseInt(contents[1]);
				String data = contents[2].substring(DVBConstants.CMD_ERROR_MARK_STRING.length());
				listener.handleMsg(contents[0], CmdType, DVBConstants.CMD_ERROR_MARK_STRING, data);
			}
			else
			{
				Log.e(TAG, "Content format error : "+msg);
			}
		}
		else
		{
			Log.e(TAG, "Cmd format error : "+msg);
		}
	}

}

