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
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-subdir-java-files, src)
#add by hy for gxfp
LOCAL_SRC_FILES += \
	src/android/gxFP/IEnrollCallback.aidl \
	src/android/gxFP/IFingerprintManager.aidl \
	src/android/gxFP/IVerifyCallback.aidl 
#add by hy end
LOCAL_PACKAGE_NAME := GoodixFpService
LOCAL_JNI_SHARED_LIBRARIES := libFp
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_CERTIFICATE := platform
LOCAL_MULTILIB := 32
#LOCAL_JAVA_LIBRARIES := FpApiLib
include $(BUILD_PACKAGE)
include $(LOCAL_PATH)/jni/Android.mk
endif
