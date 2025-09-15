DESCRIPTION = "OP-TEE sanity testsuite"
LICENSE = "BSD-2-Clause & GPLv2"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

require include/optee-common.inc
require include/optee-${MACHINE}.inc

DEPENDS:append = " optee-os optee-client"
DEPENDS:append = " python3-pyelftools-native python3-cryptography-native python3-idna-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit deploy python3native

S = "${WORKDIR}/git"

OPTEE_CLIENT_EXPORT = "${STAGING_DIR_HOST}${prefix}"
TA_DEV_KIT_DIR = "${STAGING_INCDIR}/optee/export-user_ta/"

CFLAGS:prepend = "--sysroot=${STAGING_DIR_HOST}"

EXTRA_OEMAKE = " \
	TA_DEV_KIT_DIR=${TA_DEV_KIT_DIR} \
	OPTEE_CLIENT_EXPORT=${OPTEE_CLIENT_EXPORT} \
	CROSS_COMPILE=${TARGET_PREFIX} \
	V=1 \
	CRYPTOGRAPHY_OPENSSL_NO_LEGACY=1 \
"

EXTRA_OECONF += "--libdir=/usr/lib"

do_compile() {
	oe_runmake xtest
	oe_runmake ta
	oe_runmake test_plugin
}

do_install() {
	oe_runmake install DESTDIR=${S}/tmp

	install -d ${D}/usr/bin
	install -d ${D}/usr/lib/optee_armtz/
	install -d ${D}/usr/lib/tee-supplicant/plugins/

	install -m 0755 ${S}/tmp/bin/* ${D}/usr/bin
	install -m 0755 ${S}/tmp/lib/optee_armtz/* ${D}/usr/lib/optee_armtz/
	install -m 0755 ${S}/tmp/usr/lib/tee-supplicant/plugins/* ${D}/usr/lib/tee-supplicant/plugins/
}

FILES:${PN} += "/usr/lib/optee_armtz/ /usr/lib/tee-supplicant/plugins/ /usr/bin"