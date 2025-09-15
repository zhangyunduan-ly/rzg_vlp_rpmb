DEPENDS:append = " ${@oe.utils.conditional("ENABLE_SPD_OPTEE", "1", "optee-os", "",d)}"
SEC_FLAGS:append = " ${@oe.utils.conditional("ENABLE_SPD_OPTEE", "1", "SPD=opteed BL32=${RECIPE_SYSROOT}/boot/tee-${MACHINE}.bin", "",d)}"
SEC_FLAGS:append:sboot = " TRUSTED_BOARD_BOOT=1 COT=tbbr"

EXTRA_OEMAKE:append = "${SEC_FLAGS}"
