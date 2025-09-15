
unset do_install[noexec]

BUILD_TBB_DIR = "${S}/build_tbb"
PMIC_BUILD_TBB_DIR = "${S}/build_pmic_tbb"

do_compile:append:sboot () {
	oe_runmake OUTPUT_DIR=${BUILD_TBB_DIR} clean
	oe_runmake BOARD=${FLASH_WRITER_BOARD} OUTPUT_DIR=${BUILD_TBB_DIR} TRUSTED_BOARD_BOOT=ENABLE
	if [ "${PMIC_SUPPORT}" = "1" ]; then
		oe_runmake OUTPUT_DIR=${PMIC_BUILD_TBB_DIR} clean
		oe_runmake BOARD=${FLASH_WRITER_PMIC_BOARD} OUTPUT_DIR=${PMIC_BUILD_TBB_DIR} TRUSTED_BOARD_BOOT=ENABLE
	fi
}

do_install:sboot () {
	install -d ${D}/boot
	install -m 0644 ${BUILD_TBB_DIR}/*.bin ${D}/boot/flash-writer.bin
	if [ "${PMIC_SUPPORT}" = "1" ]; then
		install -m 0644 ${PMIC_BUILD_TBB_DIR}/*PMIC*.bin ${D}/boot/flash-writer_pmic.bin
	fi
}

FILES:${PN} = "/boot"
SYSROOT_DIRS += "/boot"
