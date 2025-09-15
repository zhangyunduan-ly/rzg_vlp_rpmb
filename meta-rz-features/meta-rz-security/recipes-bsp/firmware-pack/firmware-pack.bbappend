require firmware-pack-${MACHINE}.inc

DEPENDS:append:sboot = " firmware-signing"
DEPENDS:append = " fiptool-native"
DEPENDS:append = " ${@oe.utils.conditional("ENABLE_SPD_OPTEE", "1", "optee-os", "",d)}"

do_compile:append () {
	if [ "${ENABLE_SPD_OPTEE}" = "1" ]; then
		fiptool update --align 16 --tos-fw ${RECIPE_SYSROOT}/boot/tee-${MACHINE}.bin fip.bin
		objcopy -I binary -O srec --adjust-vma=0x0000 --srec-forceS3 fip.bin fip.srec
	fi
}