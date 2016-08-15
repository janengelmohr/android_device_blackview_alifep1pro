# include proprietary libraries and binaries
-include vendor/blackview/alifep1pro/BoardConfigVendor.mk

# Bootloader
TARGET_BOOTLOADER_BOARD_NAME := 1450368016

# needed for mass storage mode
TARGET_USE_CUSTOM_LUN_FILE_PATH := /sys/class/android_usb/android0/f_mass_storage/lun/file
  
#extracted from /proc/partinfo
BOARD_BOOTIMAGE_PARTITION_SIZE := 16777216
BOARD_RECOVERYIMAGE_PARTITION_SIZE := 16777216
BOARD_SYSTEMIMAGE_PARTITION_SIZE := 2147483648
BOARD_USERDATAIMAGE_PARTITION_SIZE := 1107296256
BOARD_CACHEIMAGE_PARTITION_SIZE := 444596224
BOARD_FLASH_BLOCK_SIZE := 131072

# build kernel from source
TARGET_KERNEL_SOURCE := kernel/blackview/alifep1pro
TARGET_KERNEL_CONFIG := alifep1pro_defconfig
BOARD_KERNEL_CMDLINE := bootopt=64S3,32N2,64N2 androidboot.selinux=permissive
BOARD_KERNEL_BASE := 0x40078000
BOARD_RAMDISK_OFFSET := 0x03f88000
BOARD_KERNEL_OFFSET := 0x00008000
BOARD_TAGS_OFFSET := 0x0df88000
BOARD_KERNEL_PAGESIZE := 2048
BOARD_MKBOOTIMG_ARGS := --kernel_offset $(BOARD_KERNEL_OFFSET) --ramdisk_offset $(BOARD_RAMDISK_OFFSET) --tags_offset $(BOARD_TAGS_OFFSET)

# system.prop
TARGET_SYSTEM_PROP := device/blackview/alifep1pro/system.prop

# CyanogenMod Hardware Hooks
BOARD_HARDWARE_CLASS := device/blackview/alifep1pro/cmhw/

BOARD_BLUETOOTH_BDROID_BUILDCFG_INCLUDE_DIR := device/blackview/alifep1pro/bluetooth

# Fingerprint Sensor
VANZO_FEATURE_FP_GF318M := yes

# EGL settings
BOARD_EGL_CFG := device/blackview/alifep1pro/configs/egl.cfg
