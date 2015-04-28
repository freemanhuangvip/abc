LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)


LOCAL_MODULE_TAGS := optional

# Only compile source java files in this apk.
LOCAL_SRC_FILES := $(call all-subdir-java-files)

#LOCAL_STATIC_JAVA_LIBRARIES := fastjson 

LOCAL_PACKAGE_NAME := GotechObbApp

LOCAL_SDK_VERSION := current

LOCAL_CERTIFICATE := platform

LOCAL_DEX_PREOPT := false

include $(BUILD_PACKAGE)
include $(call all-makefiles-under,$(LOCAL_PATH))

