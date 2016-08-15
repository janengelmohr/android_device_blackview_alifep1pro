#!/bin/bash
cd ../../../..
cd frameworks/base
git apply -v ../../device/blackview/alifep1pro/patches/framework_base/frameworks_base_goodix_1.patch
cd ../..
echo Patches Applied Successfully!
