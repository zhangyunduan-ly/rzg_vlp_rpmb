DESCRIPTION = "OP-TEE OS"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173 \
"

S = "${WORKDIR}/git"

require include/optee-common.inc
require include/optee-${MACHINE}.inc

DEPENDS = " python3-pyelftools-native python3-cryptography-native "
DEPENDS:append = " ${@oe.utils.conditional("ENABLE_RZ_SCE", "1", "libsecureip", "",d)}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit python3native

export CROSS_COMPILE64="${TARGET_PREFIX}"

EXTRA_OEMAKE = " \
	PLATFORM=${PLATFORM} PLATFORM_FLAVOR=${PLATFORM_FLAVOR} \
	CFG_ARM64_core=y CFG_REE_FS=n CFG_RPMB_FS=y CFG_CRYPTO_WITH_CE=n \
	CROSS_COMPILE64=${TARGET_PREFIX} \
	CRYPTOGRAPHY_OPENSSL_NO_LEGACY=1 \
"
EXTRA_OEMAKE:append = " \
	${@oe.utils.conditional("ENABLE_RZ_SCE", "1", "CFG_RZ_SCE=y CFG_RZ_SCE_LIB_DIR=${STAGING_LIBDIR}", "",d)} \
"

CFLAGS:prepend = "--sysroot=${STAGING_DIR_HOST} "

# Let the Makefile handle setting up the flags as it is a standalone application
LD[unexport] = "1"
LDFLAGS[unexport] = "1"
export CCcore="${CC}"
export LDcore="${LD}"
libdir[unexport] = "1"

do_compile() {
	oe_runmake
}

do_install() {
	#install TA devkit
	install -d ${D}/usr/include/optee/export-user_ta/
	for f in  ${B}/out/arm-plat-${PLATFORM}/export-ta_arm64/*; do
		cp -aR $f ${D}/usr/include/optee/export-user_ta/
	done

	# install firmware images
	install -d ${D}/boot
	install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee.elf     ${D}/boot/tee-${MACHINE}.elf
	install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee-raw.bin ${D}/boot/tee-${MACHINE}.bin
}

FILES:${PN} = "/boot"
FILES:${PN}-dev = "/usr/include/optee"
INSANE_SKIP:${PN}-dev = "staticdev"
SYSROOT_DIRS += "/boot"
