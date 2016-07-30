#
# Copyright (C) 2008 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
ifeq ($(strip $(VANZO_FEATURE_FP_GF318M)), yes)

LOCAL_PATH:= $(call my-dir)
#$(shell mkdir -p $(PRODUCT_OUT)/system/app/gxFpService/lib/arm)
#$(shell mkdir -p $(PRODUCT_OUT)/system/bin)
#$(shell mkdir -p $(PRODUCT_OUT)/system/lib)
$(shell cp -a $(LOCAL_PATH)/bin/gxFpDaemon $(PRODUCT_OUT)/system/bin/gxFpDaemon)
$(shell cp -a $(LOCAL_PATH)/lib/* $(PRODUCT_OUT)/system/lib/)

#$(shell cp -a $(LOCAL_PATH)/gxFpService/gxFpService.apk $(PRODUCT_OUT)/system/app/gxFpService/)
#$(shell cp -a $(LOCAL_PATH)/gxFpService/lib/arm/libFp.so $(PRODUCT_OUT)/system/app/gxFpService/lib/arm/libFp.so)

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-subdir-java-files, src) $(call all-Iaidl-files-under, aidl)
LOCAL_AIDL_INCLUDES := $(call all-Iaidl-files-under, aidl)
LOCAL_PACKAGE_NAME := GoodixNewFpSetting
#LOCAL_JNI_SHARED_LIBRARIES := libFp
LOCAL_PROGUARD_ENABLED := disabled
include $(BUILD_PACKAGE)
#include $(LOCAL_PATH)/jni/Android.mk
endif
