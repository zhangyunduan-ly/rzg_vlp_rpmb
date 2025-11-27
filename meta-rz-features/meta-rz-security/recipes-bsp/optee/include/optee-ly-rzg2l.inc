require optee-smarc-rzg2l.inc

SRC_URI:append:pn-optee-os = " \
	file://0001-Linyang-Support-g2l_smarc_1.patch \
	file://0001-optee-os-loads-keyupdatekey-from-otp.patch \
"

PLATFORM_FLAVOR = "g2l_smarc_1"