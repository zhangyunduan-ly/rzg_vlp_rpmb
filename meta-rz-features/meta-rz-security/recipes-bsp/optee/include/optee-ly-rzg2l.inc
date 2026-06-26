require optee-smarc-rzg2l.inc

SRC_URI:append:pn-optee-os = " \
	file://0001-Linyang-Support-g2l_smarc_1.patch \
	file://0001-optee-os-loads-keyupdatekey-from-otp.patch \
	file://0001-optee-os-add-linyang-ecc-rpmb-ta-interface.patch \
	file://0002-optee-os-modify-the-logging-print-level.patch \
"

PLATFORM_FLAVOR = "g2l_smarc_1"