package com.gotech.gotechfactorytest;

import android.view.KeyEvent;

public class DVBConstants
{
	public static final int DEFAULT_CUSTOMER = 0x00;
	public static final int STARSAT_CUSTOMER = 0x03;
	public static final int BLUESTAR_CUSTOMER = 0x04;
	//	public static final int FG_TURKEY_CUSTOMER         = 0x04;
	public static final int SMART_CUSTOMER = 0x09;
	public static final int ARGO_TECHNOLOGY_CUSTOMER = 0x0A;
	public static final int TECHNOSAT_CUSTOMER = 0x11;
	public static final int SATINTEGRAL_CUSTOMER = 0x12;
	public static final int HORIZON_CUSTOMER = 0x14;
	public static final int STARWAY_CUSTOMER = 0x20;
	public static final int DAWOOD_CUSTOMER = 0x25;
	public static final int GEANT_CUSTOMER = 0x40;
	public static final int QMAX_CUSTOMER = 0x41;
	public static final int RAKISAT_CUSTOMER = 0x42;
	public static final int RAPITRON_CUSTOMER = 0x4e;
	public static final int STARFOX_CUSTOMER = 0x4f;
	public static final int YOOSTAR_CUSTOMER = 0x50;
	public static final int RAFI_CUSTOMER = 0x52;
	public static final int TECHNOCOMS_CUSTOMER = 0x55;
	public static final int TORAYGROUP_ROWELL_CUSTOMER = 0x56;
	public static final int LINBOX_CUSTOMER = 0x71;
	public static final int DRAGONX_CUSTOMER = 0x72;
	public static final int FORTECSTAR_CUSTOMER = 0x73;
	public static final int GOTECH_CUSTOMER = 0x74;
	public static final int ASIASTAR_CUSTOMER = 0x75;
	public static final int STARMAX_CUSTOMER = 0x77;
	public static final int MEGSAT_CUSTOMER  = 0x9c;
	public static final int SKYPRIME_CUSTOMER = 0xa6;
	public static final int MINOR_CUSTOMER = 0xba; //spanish customer
	public static final int VISION_CUSTOMER = 0xF0;
	public static final int STRONG_CUSTOMER = 0xF2;
	public static final int ECHOSTAR_CUSTOMER = 0xF3;
	public static final int TV_CUSTOMER = 0xF6;
	public static final int MAX_CUSTOMER = 250;
	public static final int FG_TURKEY_CUSTOMER = 251; //not use it
	public static final int SWTEST_CUSTOMER = 0xFF;

	/* Transponder type */
	public static final int DVB_S_TRANSPONDER = 0;
	public static final int DVB_T_TRANSPONDER = 1;
	public static final int DVB_ISDBT_TRANSPONDER = 2;
	public static final int DVB_C_TRANSPONDER = 3;
	public static final int DVB_TRANSPONDER_MAX_TYPE = 4;

	/* define PIPE_TUNER_TYPE */
	public static final int PIPE_BASEBAND_TUNER_TYPE = 0; /* Baseband */
	public static final int PIPE_SAT_TUNER_TYPE = 1; /* Satellite */
	public static final int PIPE_CABLE_TUNER_TYPE = 2; /* Cable */
	public static final int PIPE_DVBT_TUNER_TYPE = 3; /* DVB-T */
	public static final int PIPE_ATSC_TUNER_TYPE = 4; /* ATSC */

	/* PIPE_TUNER_MODULATION */
	public static final int PIPE_TUNER_MOD_TYPE_8PSK_TURBO = 0;
	public static final int PIPE_TUNER_MOD_TYPE_QPSK_TURBO = 1;
	public static final int PIPE_TUNER_MOD_TYPE_QPSK = 2;
	public static final int PIPE_TUNER_MOD_TYPE_BPSK = 3;
	public static final int PIPE_TUNER_MOD_TYPE_8PSK = 4;
	public static final int PIPE_TUNER_MOD_TYPE_DCII_COMBINED = 5;
	public static final int PIPE_TUNER_MOD_TYPE_DCII_SPLIT_I = 6;
	public static final int PIPE_TUNER_MOD_TYPE_DCII_SPLIT_Q = 7;
	public static final int PIPE_TUNER_MOD_TYPE_DVB_S2 = 8;
	public static final int PIPE_TUNER_MOD_TYPE_4QAM = 9;
	public static final int PIPE_TUNER_MOD_TYPE_8QAM = 10;
	public static final int PIPE_TUNER_MOD_TYPE_16QAM = 11;
	public static final int PIPE_TUNER_MOD_TYPE_32QAM = 12;
	public static final int PIPE_TUNER_MOD_TYPE_64QAM = 13;
	public static final int PIPE_TUNER_MOD_TYPE_128QAM = 14;
	public static final int PIPE_TUNER_MOD_TYPE_256QAM = 15;

	/* #define TUNER_PO L */
	public static final int TUNER_POL_HORIZ = 0x00;
	public static final int TUNER_POL_VERT = 0x01;
	public static final int TUNER_POL_LEFT = 0x02;
	public static final int TUNER_POL_RIGHT = 0x03;

	/* #define FEC */
	public static final int FEC_AUTO = 0x00;
	public static final int FEC_12 = 0x01;
	public static final int FEC_23 = 0x02;
	public static final int FEC_34 = 0x03;
	public static final int FEC_56 = 0x04;
	public static final int FEC_78 = 0x05;
	public static final int FEC_910 = 0x06;

	/* #define LNB_POWER */
	public static final int LNB_POWER_13_18V = 0;
	public static final int LNB_POWER_13V = 1;
	public static final int LNB_POWER_18V = 2;
	public static final int LNB_POWER_14_19V = 3;
	public static final int LNB_POWER_14V = 4;
	public static final int LNB_POWER_19V = 5;
	public static final int LNB_POWER_0V = 6;

	/* #define DVB_MODULATION */
	public static final int DVB_MODULATION_QPSK = 0;
	public static final int DVB_MODULATION_8PSK = 1;

	/* #define DVBS_MODULATION */
	public static final int DVB_MODULATION_AUTO = 0;
	public static final int DVB_MODULATION_DVBS = 1;
	public static final int DVB_MODULATION_DVBS2 = 2;

	/* define tv type */
	public static final int TV = 0;
	public static final int RADIO = 1;

	/* define tv classification */
	public static final int PROGRAM_ALL = 0;
	public static final int PROGRAM_FTA = 1;
	public static final int PROGRAM_SCRAMBLE = 2;
	public static final int PROGRAM_H264_MPEG4 = 3;

	/* cable info */
	//		QAM_4,
	//		QAM_8,
	public static final int QAM_AUTO = 0;
	public static final int QAM_16 = 1;
	public static final int QAM_32 = 2;
	public static final int QAM_64 = 3;
	public static final int QAM_128 = 4;
	public static final int QAM_256 = 5;
	public static final int VSB_8 = 6;
	public static final int VSB_16 = 7;

	/* define qam_mode */
	public static final int QAM_A = 0;
	public static final int QAM_B = 1;
	public static final int QAM_TOTAL = 2;

	/* define BIT_MASK */
	public static final int VIA_BIT_MASK = 0x00000001;
	public static final int NAGRA_BIT_MASK = 0x00000002;
	public static final int SECA_BIT_MASK = 0x00000004;
	public static final int IRDETO_BIT_MASK = 0x00000008;
	public static final int CONAX_BIT_MASK = 0x00000010;
	public static final int BETA_BIT_MASK = 0x00000020;
	public static final int NDS_BIT_MASK = 0x00000040;
	public static final int CRYPTWORKS_BIT_MASK = 0x00000080;
	public static final int BISS_BIT_MASK = 0x00000100;
	public static final int XCRYPT_BIT_MASK = 0x00000200;
	public static final int CRYPTOON_BIT_MASK = 0x00000400;
	public static final int DREAMCRYPT_BIT_MASK = 0x00000800;
	public static final int DRECRYPT_BIT_MASK = 0x00001000;
	public static final int OTHER_CA_BIT_MASK = 0x80000000;

	//GS_MODULE_TYPE
	public static final int GS_SECURITY_MODULE = 0;
	public static final int GS_INIT_MODULE = 1;
	public static final int GS_TUNER_MODULE = 2;
	public static final int GS_PANEL_MODULE = 3;
	public static final int GS_PLAY_CH_MODULE = 4;
	public static final int GS_SEARCH_MODULE = 5;
	public static final int GS_EPG_TDT_MODULE = 6;
	public static final int GS_SETTING_MODULE = 7;
	public static final int GS_SUB_MODULE = 8;
	public static final int GS_TELE_MODULE = 9;
	public static final int GS_PVR_MODULE = 10;
	public static final int GS_UPDATE_MODULE = 11;
	public static final int GS_FAC_TEST_MODULE = 12;
	public static final int GS_CA_MODULE = 13;
	public static final int GS_NOTIFY_MODULE = 14;
	public static final int GS_SPEC_CMD_MODULE = 15;
	public static final int GS_CARDSHARE_MODULE = 16;
	public static final int GS_SOFTCAS_MODULE = 17;
	public static final int GS_SDS_MODULE = 18;
	public static final int GS_VPN_MODULE = 19;
	public static final int GS_IPTV_MODULE = 20;
	public static final int GS_CS_SERVER_MODULE = 21;
	public static final int GS_MAX_MODULE = 22;

	//GS_MODULE_TYPE
	public static final String acModule[] = 
		{
			"Security", //0
			"Init", 
			"Tuner", 
			"Fpanel", 
			"Play", 
			"Search", //5
			"EPG_TDT", 
			"Setting", 
			"Subtitle", 
			"Teletext",
			"Pvr",  //10
			"Update", 
			"Fac_test", 
			"CA_Module", 
			"GS_Notify_Port",
			"GS_Spec_CMD",  //15
			"CardShare", 
			"Softcas", 
			"SDS", 
			"VPN",  //19
			"IPTV", 
			"CS_Server",
		};

	public static final String CMD_ERROR_MARK_STRING = "ERROR:";
	public static final String CMD_SUCCESS_MARK_STRING = "SUCCESS:";
	public static final char CMD_DIV_MARK_CHAR = '\n';

	//DVB_CMD_STATUS	
	public static final int DVB_CMD_ERR_INTERNAL = 5;
	public static final int DVB_CMD_ERR_CHECKSUM = 4;
	public static final int DVB_CMD_ERR_MODULE_FIND = 3;
	public static final int DVB_CMD_ERR_CMD_NOT_SUPPORT = 2;
	public static final int DVB_CMD_ERR_NULL = 1;
	public static final int DVB_CMD_OK = 0;

	//EPG_TDT MODULE CMD
	public static final int START_GET_TDT = 0;
	public static final int STOP_GET_TDT = 1;
	public static final int GET_EPG_INFO = 2;
	public static final int GET_EPG_CHANNEL_INFO = 3;

	/* define menu_language */
	public static final int ENGLISH_LANGUAGE = 0;
	public static final int ARABIC_LANGUAGE = 1;
	public static final int FARSI_LANGUAGE = 2;
	public static final int GERMAN_LANGUAGE = 3;
	public static final int FRANCE_LANGUAGE = 4;
	public static final int SPANISH_LANGUAGE = 5;
	public static final int ITALY_LANGUAGE = 6;
	public static final int RUSSIAN_LANGUAGE = 7;
	public static final int PORTUGUESE_LANGUAGE = 8;
	// add by order
	public static final int TURKEY_LANGUAGE = 9;
	public static final int CHINESE_LANGUGAE = 10;
	public static final int TOTAL_TYPES_OF_MENULANGUAGE = 11;

	public static final int MAX_NAME_CHARACTOR = 64;
	public static final int MAX_RECALL_CHANNEL = 16;

	/* stream type */
	public static final int MPEG_ES_TYPE_RESERVED = 0x00;
	public static final int MPEG_ES_TYPE_MPEG1_VIDEO = 0x01;
	public static final int MPEG_ES_TYPE_MPEG2_VIDEO = 0x02;
	public static final int MPEG_ES_TYPE_MPEG1_AUDIO = 0x03;
	public static final int MPEG_ES_TYPE_MPEG2_AUDIO = 0x04;
	public static final int MPEG_ES_TYPE_PRIVATE_SEC = 0x05;
	public static final int MPEG_ES_TYPE_PRIVATE_PES = 0x06;
	public static final int MPEG_ES_TYPE_MHEG = 0x07;
	public static final int MPEG_ES_TYPE_DSM_CC = 0x08;
	public static final int MPEG_ES_TYPE_H222 = 0x09;
	public static final int MPEG_ES_TYPE_13818_6_A = 0x0A;
	public static final int MPEG_ES_TYPE_13818_6_B = 0x0B;
	public static final int MPEG_ES_TYPE_13818_6_C = 0x0C;
	public static final int MPEG_ES_TYPE_13818_6_D = 0x0D;
	public static final int MPEG_ES_TYPE_AUX = 0x0E;
	public static final int MPEG_ES_TYPE_13818_7 = 0x0F;
	public static final int MPEG_ES_TYPE_14496_3 = 0x11;
	public static final int MPEG_AVS_TYPE = 0x43;
	public static final int MPEG_ES_TYPE_H264_VIDEO = 0x1B;
	public static final int ATSC_ES_TYPE_VIDEO = 0x80;
	public static final int ATSC_DOLBY_DIGITAL = 0x81;
	public static final int ATSC_DOLBY_DIGITAL_PLUS = 0x87;
	public static final int DTS = 0x91;

	public static final int OUTPUT_INVALID = 0;
	public static final int OUTPUT_AREA = 1;
	public static final int OUTPUT_BRIGHTNESS = 2;
	public static final int OUTPUT_CONTRAST = 3;
	public static final int OUTPUT_SATURATION = 4;
	public static final int OUTPUT_SCREEN_RATIO = 5;
	public static final int OUTPUT_SCREEN_FORMAT = 6;
	public static final int OUTPUT_TV_SYSTEM = 7;
	public static final int OUTPUT_HDMI_MODE = 8;
	public static final int OUTPUT_SCART_MODE = 9;

	public static final int FPANEL_MSG_DISP_LED = 0;
	public static final int FPANEL_MSG_DISP_LED_LOCK = 1;
	public static final int FPANEL_MSG_DISP_NAME = 2;
	public static final int FPANEL_MSG_DISP_STATE = 3;
	public static final int FPANEL_MSG_DISP_INFO = 4;
	public static final int FPANEL_READ_KEY = 5;

	// fpanel state
	public static final int VFD_STB_USB = 1;
	public static final int VFD_STB_PLAY = 2;
	public static final int VFD_STB_REC = 4;

	// fpanel info
	public static final int VFD_STATE_AUDR = 0x1;
	public static final int VFD_STATE_AUDL = 0x2;
	public static final int VFD_STATE_AUDS = 0x4;
	public static final int VFD_STATE_RADIO = 0x8;
	public static final int VFD_STATE_TV = 0x10;
	public static final int VFD_STATE_CYCLE = 0x20;
	public static final int VFD_STATE_ALL = 0x40;
	public static final int VFD_STATE_JPG = 0x80;
	public static final int VFD_STATE_MP3 = 0x100;
	public static final int VFD_STATE_MOVIE = 0x200;
	public static final int VFD_STATE_TSHIFT = 0x400;

	// Scan type
	public static final int TP_SEARCH = 0;
	public static final int SAT_SEARCH = 1;
	public static final int BLIND_SCAN = 2;
	public static final int FAST_SCAN = 3;

	/* Rcu Type */
	public static final int RCU_037V11 = 0;
	public static final int RCU_013V73 = 1;
	public static final int RCU_046V01 = 2;
	public static final int RCU_013V8 = 3;
	public static final int RCU_040V15 = 4;
	public static final int RCU_013V57 = 5;
	public static final int RCU_037V3 = 6;
	public static final int RCU_039V13 = 7;
	//	public static final int RCU_040V12  = 8;//not found code ,maybe same as 040V1.5
	public static final int RCU_049V1 = 8;

	/* key value map */
	public static final int KEY_UNKNOWN = KeyEvent.KEYCODE_UNKNOWN;
	public static final int KEY_1 = KeyEvent.KEYCODE_1;
	public static final int KEY_2 = KeyEvent.KEYCODE_2;
	public static final int KEY_3 = KeyEvent.KEYCODE_3;
	public static final int KEY_4 = KeyEvent.KEYCODE_4;
	public static final int KEY_5 = KeyEvent.KEYCODE_5;
	public static final int KEY_6 = KeyEvent.KEYCODE_6;
	public static final int KEY_7 = KeyEvent.KEYCODE_7;
	public static final int KEY_8 = KeyEvent.KEYCODE_8;
	public static final int KEY_9 = KeyEvent.KEYCODE_9;
	public static final int KEY_0 = KeyEvent.KEYCODE_0;
	public static final int KEY_TVRADIO = KeyEvent.KEYCODE_TV;
	public static final int KEY_MUTE = KeyEvent.KEYCODE_MUTE;
	public static final int KEY_DISPLAY = KeyEvent.KEYCODE_D;
	public static final int KEY_MODE = KeyEvent.KEYCODE_M;
	public static final int KEY_F1 = KeyEvent.KEYCODE_F1;
	public static final int KEY_RED = KeyEvent.KEYCODE_PROG_RED;
	public static final int KEY_GREEN = KeyEvent.KEYCODE_PROG_GREEN;
	public static final int KEY_YELLOW = KeyEvent.KEYCODE_PROG_YELLOW;
	public static final int KEY_BLUE = KeyEvent.KEYCODE_PROG_BLUE;
	public static final int KEY_MENU = KeyEvent.KEYCODE_MENU;
	public static final int KEY_EXIT = KeyEvent.KEYCODE_BACK;
	public static final int KEY_LEFT = KeyEvent.KEYCODE_DPAD_LEFT;
	public static final int KEY_RIGHT = KeyEvent.KEYCODE_DPAD_RIGHT;
	public static final int KEY_UP = KeyEvent.KEYCODE_DPAD_UP;
	public static final int KEY_DOWN = KeyEvent.KEYCODE_DPAD_DOWN;
	public static final int KEY_OK = KeyEvent.KEYCODE_ENTER;
	public static final int KEY_SUB = KeyEvent.KEYCODE_CAPTIONS;
	public static final int KEY_EPG = KeyEvent.KEYCODE_GUIDE;
	public static final int KEY_FAV = KeyEvent.KEYCODE_F;
	public static final int KEY_TXT = KeyEvent.KEYCODE_T;
	public static final int KEY_VOLUP = KeyEvent.KEYCODE_VOLUME_UP;
	public static final int KEY_VOLDOWN = KeyEvent.KEYCODE_VOLUME_DOWN;
	public static final int KEY_FIND = KeyEvent.KEYCODE_SEARCH;
	public static final int KEY_SLEEP = KeyEvent.KEYCODE_S;
	public static final int KEY_PIP = KeyEvent.KEYCODE_WINDOW;
	public static final int KEY_PGUP = KeyEvent.KEYCODE_PAGE_UP;
	public static final int KEY_PGDN = KeyEvent.KEYCODE_PAGE_DOWN;
	public static final int KEY_BACKWARD = KeyEvent.KEYCODE_MEDIA_REWIND;
	public static final int KEY_FORWARD = KeyEvent.KEYCODE_MEDIA_FAST_FORWARD;
	public static final int KEY_PLAY = KeyEvent.KEYCODE_MEDIA_PLAY;
	public static final int KEY_STOP = KeyEvent.KEYCODE_MEDIA_STOP;
	public static final int KEY_PREVIOUS = KeyEvent.KEYCODE_MEDIA_PREVIOUS;
	public static final int KEY_NEXT = KeyEvent.KEYCODE_MEDIA_NEXT;
	public static final int KEY_PVR = KeyEvent.KEYCODE_MEDIA_PAUSE;
	public static final int KEY_RECORD = KeyEvent.KEYCODE_MEDIA_RECORD;
	public static final int KEY_RECALL = KeyEvent.KEYCODE_BOOKMARK;
	public static final int KEY_POWER = KeyEvent.KEYCODE_STB_POWER;
	public static final int KEY_SAT = KeyEvent.KEYCODE_STAR;
	public static final int KEY_USB = KeyEvent.KEYCODE_DVR;
	public static final int KEY_F2 = KeyEvent.KEYCODE_F2;
	public static final int KEY_BACK = KeyEvent.KEYCODE_DEL;
	public static final int KEY_HELP = KeyEvent.KEYCODE_INFO;
	public static final int KEY_TIMER = KeyEvent.KEYCODE_NOTIFICATION;
	public static final int KEY_FUNC = KeyEvent.KEYCODE_FUNCTION;
	public static final int KEY_CH_UP = KeyEvent.KEYCODE_CHANNEL_UP;
	public static final int KEY_CH_DOWN = KeyEvent.KEYCODE_CHANNEL_DOWN;
	public static final int KEY_GREEN_RECALL = KeyEvent.KEYCODE_F6;

	public static final int KEY_AUDIO = 210;
	public static final int KEY_PAUSE = 211;
	public static final int KEY_ZOOM = 212;
	public static final int KEY_INFO = 213;
	public static final int KEY_USALS = 214;
	public static final int KEY_MOUSE = 230;

	public static final int PIPE_VP_DISPLAY_MODE_NTSC = 0;
	public static final int PIPE_VP_DISPLAY_MODE_PAL = 7;
	public static final int PIPE_VP_DISPLAY_MODE_SECAM = 8;
	public static final int NTSCIPE_VP_DISPLAY_MODE_480P = 18;
	public static final int PIPE_VP_DISPLAY_MODE_576P = 19;
	public static final int PIPE_VP_DISPLAY_MODE_720P = 20;
	public static final int PIPE_VP_DISPLAY_MODE_1080I = 21;
	public static final int PIPE_VP_DISPLAY_MODE_1080P_5994Hz = 31;
	public static final int PIPE_VP_DISPLAY_MODE_1080P_50Hz = 34;

	//sync android system font size
	public static final float[] system_font_size = {0.85f, 1.00f, 1.15f, 1.30f};

	//for other app get some cfg info
	public static final String BROADCAST_REQUEST_CFG = "com.excellence.appmarket.BROADCAST_SW";
	public static final String BROADCAST_RETURN_CFG = "com.dvb.spring.IS_FACTORY_SW";
	public static final String KEY_CUSTOMER_ID = "CUSTOMER_ID";
	public static final String KEY_IS_FAC = "IS_FACTORY_SW";

	//for init KeyEventService mouse
	public static final String BROADCAST_INIT_MOUSE = "com.dvb.spring.SPRING_CREATED";
	//keyevent service apk send to here
	public static final String BROADCAST_KEY_EVENT = "com.dvb.spring.KEY_BROADCAST";
	
	public static final int CRYPTO_IC_ERROR = 1000;
	public static final int CUSTOMER_MATCH_OSD_ERROR = 1001;
	
	
	public static final String SUPER_PASSWORD = "9876";
	
	///****************about storage mount info******start**********///
	public final static String ROOT_PATH = "/mnt";	
	public static final String USB_FAT_PATH = "/mnt/usb-vfat";
	public static final String USB_NTFS_PATH = "/mnt/usb-ntfs";
	public final static String SDCARD_PATH = "/mnt/sdcard";
	public final static String PART_FILE = "/proc/partitions";
	public final static String SDCARD_NAME = "mmcblk";
	public final static String USB_NAME = "sd";
	///****************about storage mount info******end**********///
	
	public static boolean TestCurstomMenu = false;
}
