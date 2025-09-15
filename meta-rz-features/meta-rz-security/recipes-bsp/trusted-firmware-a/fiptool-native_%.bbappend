
LIC_FILES_CHKSUM = "file://docs/license.rst;md5=b2c740efedc159745b9b31f88ff03dde"

# Use fiptool from TF-A v2.10.?
SRCREV:pn-fiptool-native                           = "19bc34cd1c4aeb4b391c6ab462fff4beeca01b59"
SRC_URI_TRUSTED_FIRMWARE_A:pn-fiptool-native      ?= "git://github.com/renesas-rz/rzg_trusted-firmware-a.git;protocol=https"
SRC_URI:pn-fiptool-native                          = "${SRC_URI_TRUSTED_FIRMWARE_A};destsuffix=fiptool-${PV};nobranch=1"