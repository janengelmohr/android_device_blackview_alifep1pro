# Release name
PRODUCT_RELEASE_NAME := alifep1pro

# Inherit some common CM stuff.
$(call inherit-product, vendor/cm/config/common_full_phone.mk)

# Inherit device configuration
$(call inherit-product, device/blackview/alifep1pro/device_alifep1pro.mk)

# Configure dalvik heap
$(call inherit-product, frameworks/native/build/phone-xxhdpi-2048-dalvik-heap.mk)

# Configure hwui memory
$(call inherit-product, frameworks/native/build/phone-xxhdpi-2048-hwui-memory.mk)

TARGET_SCREEN_HEIGHT := 1280
TARGET_SCREEN_WIDTH := 720

## Device identifier. This must come after all inclusions
PRODUCT_DEVICE := alifep1pro
PRODUCT_NAME := cm_alifep1pro
PRODUCT_BRAND := blackview
PRODUCT_MODEL := alifep1pro
PRODUCT_MANUFACTURER := blackview
