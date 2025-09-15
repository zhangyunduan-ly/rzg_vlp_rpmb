TFA_PMIC_EXTRA_OEMAKE:append = "${SEC_FLAGS}"

SRC_URI:append:sboot = " \
				file://g2l/0001-plat-renesas-rz-Disable-unused-CRYPTO_SUPPORT.patch \
				file://g2l/0002-fix-certification-alignment.patch \
"

do_compile[cleandirs] = "${B} ${PMIC_BUILD_DIR}"

do_install:append:class-target:sboot () {
	install -d ${D}/boot
	if [ "${PMIC_SUPPORT}" = "1" ]; then
		install -m 0644 ${PMIC_BUILD_DIR}/bl2.bin  ${D}/boot/bl2-${MACHINE}_pmic.bin
		install -m 0644 ${PMIC_BUILD_DIR}/bl31.bin ${D}/boot/bl31-${MACHINE}_pmic.bin
	fi
}