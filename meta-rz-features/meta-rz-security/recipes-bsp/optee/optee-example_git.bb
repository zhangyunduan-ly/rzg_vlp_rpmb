DESCRIPTION = "OP-TEE example"
LICENSE = "BSD-2-Clause & GPLv2"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=cd95ab417e23b94f381dafc453d70c30"

require include/optee-common.inc
require include/optee-${MACHINE}.inc

DEPENDS = "optee-os optee-client"
DEPENDS += "python3-pyelftools-native python3-cryptography-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit deploy python3native

TEEC_EXPORT = "${STAGING_DIR_HOST}${prefix}"
TA_DEV_KIT_DIR = "${STAGING_INCDIR}/optee/export-user_ta/"

CFLAGS:prepend = "--sysroot=${STAGING_DIR_HOST}"

EXTRA_OEMAKE = " \
	TA_DEV_KIT_DIR=${TA_DEV_KIT_DIR} \
	TEEC_EXPORT=${TEEC_EXPORT} \
	HOST_CROSS_COMPILE=${TARGET_PREFIX} \
	PLUGIN_LDFLAGS="--sysroot=${STAGING_DIR_HOST} -shared" \
	CRYPTOGRAPHY_OPENSSL_NO_LEGACY=1 \
"

S = "${WORKDIR}/git"

do_compile () {
	oe_runmake examples
}

do_install () {
	oe_runmake prepare-for-rootfs OUTPUT_DIR=${S}/out

	install -d ${D}/${bindir}
	install -d ${D}/usr/lib/optee_armtz/

	install -m 0755 ${S}/out/ca/* ${D}/${bindir}
	install -m 0755 ${S}/out/ta/* ${D}/usr/lib/optee_armtz/
}

FILES:${PN} += "/usr/lib/optee_armtz"