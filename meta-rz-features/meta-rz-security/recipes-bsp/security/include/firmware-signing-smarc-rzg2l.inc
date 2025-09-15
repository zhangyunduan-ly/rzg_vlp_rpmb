do_compile:append () {
	if [ "${PMIC_SUPPORT}" = "1" ]; then
		python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
			-info ${INFDIR}/bl2_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout bl2-kcert_pmic.bin
		python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
			-info ${INFDIR}/bl2_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout bl2-ccert_pmic.bin \
			-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/bl2-${MACHINE}_pmic.bin -imgout bl2-tbb_pmic.bin

		python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
			-info ${INFDIR}/bl31_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl31_key.pem -certout bl31-kcert_pmic.bin
		python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
			-info ${INFDIR}/bl31_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl31_key.pem -certout bl31-ccert_pmic.bin \
			-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/bl31-${MACHINE}_pmic.bin -imgout bl31-tbb_pmic.bin

		python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
			-info ${INFDIR}/flash_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout flash-kcert_pmic.bin
		python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
			-info ${INFDIR}/flash_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout flash-ccert_pmic.bin \
			-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/flash-writer_pmic.bin -imgout flash-tbb_pmic.bin
	fi
}


do_install:append () {
	if [ "${PMIC_SUPPORT}" = "1" ]; then
		install -m 0644 bl2-kcert_pmic.bin   ${D}/boot/bl2-kcert-${MACHINE}_pmic.bin
		install -m 0644 bl2-ccert_pmic.bin   ${D}/boot/bl2-ccert-${MACHINE}_pmic.bin
		install -m 0644 bl2-tbb_pmic.bin     ${D}/boot/bl2-tbb-${MACHINE}_pmic.bin

		install -m 0644 bl31-kcert_pmic.bin  ${D}/boot/bl31-kcert-${MACHINE}_pmic.bin
		install -m 0644 bl31-ccert_pmic.bin  ${D}/boot/bl31-ccert-${MACHINE}_pmic.bin
		install -m 0644 bl31-tbb_pmic.bin    ${D}/boot/bl31-tbb-${MACHINE}_pmic.bin

		install -m 0644 flash-kcert_pmic.bin ${D}/boot/flash-kcert-${MACHINE}_pmic.bin
		install -m 0644 flash-ccert_pmic.bin ${D}/boot/flash-ccert-${MACHINE}_pmic.bin
		install -m 0644 flash-tbb_pmic.bin   ${D}/boot/flash-tbb-${MACHINE}_pmic.bin
	fi
}
