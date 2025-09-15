SUMMARY = "RZ OP-TEE TA Sample"
LICENSE = "BSD-3-Clause & GPLv2"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.md;md5=e39af0548166c775f6685c6b69ec94f8"

require include/optee-${MACHINE}.inc

DEPENDS = "optee-os optee-client "

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit deploy

S = "${WORKDIR}/optee-ta-sample"

OPTEE_CLIENT_EXPORT = "${STAGING_DIR_HOST}${prefix}"
TA_DEV_KIT_DIR = "${STAGING_INCDIR}/optee/export-user_ta/"

CFLAGS:prepend = "--sysroot=${STAGING_DIR_HOST} "

EXTRA_OEMAKE = " \
	TA_DEV_KIT_DIR=${TA_DEV_KIT_DIR} \
	OPTEE_CLIENT_EXPORT=${OPTEE_CLIENT_EXPORT} \
	CROSS_COMPILE=${TARGET_PREFIX} \
"

do_install () {
	install -d ${D}/${bindir}
	install -p -m 0755 ${S}/out/ca/* ${D}/${bindir}
}