require include/trusted-firmware-a.inc
include include/trusted-firmware-a-${MACHINE}.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

do_install:append:class-target:sboot () {
	install -d ${D}/boot
	install -m 0644 ${BUILD_DIR}/bl2.bin  ${D}/boot/bl2-${MACHINE}.bin
	install -m 0644 ${BUILD_DIR}/bl31.bin ${D}/boot/bl31-${MACHINE}.bin
}
 
FILES:${PN} += "/boot"
SYSROOT_DIRS += "/boot"