$(call inherit-product, device/mediatek/mt6753_common/device_mt6753.mk)
$(call inherit-product, vendor/blackview/alifep1pro/alifep1pro-vendor.mk)

DEVICE_PACKAGE_OVERLAYS += device/blackview/alifep1pro/overlay

# Device uses high-density artwork where available
PRODUCT_AAPT_CONFIG := normal xhdpi xxhdpi
PRODUCT_AAPT_PREF_CONFIG := xxhdpi

# Recovery allowed devices
TARGET_OTA_ASSERT_DEVICE := alifep1pro,x5602,X5602

# init.rc's
PRODUCT_COPY_FILES += \
	device/blackview/alifep1pro/rootdir/init.mt6735.rc:root/init.mt6735.rc \
	device/blackview/alifep1pro/rootdir/init.ssd.rc:root/init.ssd.rc \
	device/blackview/alifep1pro/rootdir/init.xlog.rc:root/init.xlog.rc \
	device/blackview/alifep1pro/rootdir/init.rc:root/init.rc \
	device/blackview/alifep1pro/rootdir/init.mt6735.usb.rc:root/init.mt6735.usb.rc \
	device/blackview/alifep1pro/rootdir/init.project.rc:root/init.project.rc \
	device/blackview/alifep1pro/rootdir/init.modem.rc:root/init.modem.rc \
	device/blackview/alifep1pro/recovery/root/fstab.mt6735:root/fstab.mt6735  \
	device/blackview/alifep1pro/rootdir/ueventd.rc:root/ueventd.mt6735.rc \
	device/blackview/alifep1pro/rootdir/factory_init.rc:root/factory_init.rc \
	device/blackview/alifep1pro/rootdir/factory_init.project.rc:root/factory_init.project.rc \
	device/blackview/alifep1pro/rootdir/meta_init.project.rc:root/meta_init.project.rc \
	device/blackview/alifep1pro/rootdir/meta_init.modem.rc:root/meta_init.modem.rc \
	device/blackview/alifep1pro/rootdir/meta_init.rc:root/meta_init.rc 

# TWRP thanks to Hanuma50
PRODUCT_COPY_FILES += device/blackview/alifep1pro/recovery/twrp.fstab:recovery/root/etc/twrp.fstab

# Goodix Fingerprint
PRODUCT_PACKAGES += \
	GoodixNewFpSetting \
	GoodixFpService

# limit dex2oat threads to improve thermals
PRODUCT_PROPERTY_OVERRIDES += \
    	dalvik.vm.boot-dex2oat-threads=4 \
    	dalvik.vm.dex2oat-threads=2 \
    	dalvik.vm.image-dex2oat-threads=4

$(call inherit-product, build/target/product/full.mk)
