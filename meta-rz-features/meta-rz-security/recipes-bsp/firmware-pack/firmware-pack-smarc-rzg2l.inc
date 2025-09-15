FLASH_NAME		?= "Flash_Writer_SCIF_TBB_RZG2L_SMARC_DDR4_2GB_1PCS"
FLASH_NAME_PMIC	?= "Flash_Writer_SCIF_TBB_RZG2L_SMARC_PMIC_DDR4_2GB_1PCS"

do_compile:append () {
	if [ "${ENABLE_SPD_OPTEE}" = "1" ]; then
		if [ "${PMIC_SUPPORT}" = "1" ]; then
			fiptool update --align 16 --tos-fw ${RECIPE_SYSROOT}/boot/tee-${MACHINE}.bin ${S}/fip_pmic.bin
			objcopy -I binary -O srec --adjust-vma=0x0000 --srec-forceS3 ${S}/fip_pmic.bin ${S}/fip_pmic.srec
		fi
	fi
}

do_compile:append:sboot () {
	# BL2
	objcopy -I binary -O elf64-little \
		--add-section .ccert=${RECIPE_SYSROOT}/boot/bl2-ccert-${MACHINE}.bin --set-section-flags .ccert=alloc --adjust-section-vma .ccert=0x12400 \
		--add-section .kcert=${RECIPE_SYSROOT}/boot/bl2-kcert-${MACHINE}.bin --set-section-flags .kcert=alloc --adjust-section-vma .kcert=0x12000 \
		--adjust-vma 0x13000 ${RECIPE_SYSROOT}/boot/bl2-tbb-${MACHINE}.bin ${S}/bl2-tbb-${MACHINE}.elf
	objcopy -I elf64-little -O binary ${S}/bl2-tbb-${MACHINE}.elf ${S}/bl2-tbb.bin

	bptool ${S}/bl2-tbb.bin ${S}/bp.bin 0 spi

	objcopy -I binary -O elf64-little \
		--add-section .btprm=${S}/bp.bin --set-section-flags .btprm=alloc --adjust-section-vma .btprm=0x11E00 \
		--adjust-vma 0x12000 ${S}/bl2-tbb.bin ${S}/bl2-tbb_bp.elf
	objcopy -I elf64-little -O srec --srec-forceS3 ${S}/bl2-tbb_bp.elf ${S}/bl2-tbb_bp.srec
	objcopy -I elf64-little -O binary ${S}/bl2-tbb_bp.elf ${S}/bl2-tbb_bp.bin

	if [ "${PMIC_SUPPORT}" = "1" ]; then
		objcopy -I binary -O elf64-little \
			--add-section .ccert=${RECIPE_SYSROOT}/boot/bl2-ccert-${MACHINE}_pmic.bin --set-section-flags .ccert=alloc --adjust-section-vma .ccert=0x12400 \
			--add-section .kcert=${RECIPE_SYSROOT}/boot/bl2-kcert-${MACHINE}_pmic.bin --set-section-flags .kcert=alloc --adjust-section-vma .kcert=0x12000 \
			--adjust-vma 0x13000 ${RECIPE_SYSROOT}/boot/bl2-tbb-${MACHINE}_pmic.bin ${S}/bl2-tbb-${MACHINE}_pmic.elf
		objcopy -I elf64-little -O binary ${S}/bl2-tbb-${MACHINE}_pmic.elf ${S}/bl2-tbb_pmic.bin

		bptool ${S}/bl2-tbb_pmic.bin ${S}/bp_pmic.bin 0 spi

		objcopy -I binary -O elf64-little \
			--add-section .btprm=${S}/bp_pmic.bin --set-section-flags .btprm=alloc --adjust-section-vma .btprm=0x11E00 \
			--adjust-vma 0x12000 ${S}/bl2-tbb_pmic.bin ${S}/bl2-tbb_bp_pmic.elf
		objcopy -I elf64-little -O srec --srec-forceS3 ${S}/bl2-tbb_bp_pmic.elf ${S}/bl2-tbb_bp_pmic.srec
		objcopy -I elf64-little -O binary ${S}/bl2-tbb_bp_pmic.elf ${S}/bl2-tbb_bp_pmic.bin
	fi

	# FIP
	fiptool create --align 16 --soc-fw ${RECIPE_SYSROOT}/boot/bl31-tbb-${MACHINE}.bin \
		--soc-fw-key-cert ${RECIPE_SYSROOT}/boot/bl31-kcert-${MACHINE}.bin \
		--soc-fw-cert ${RECIPE_SYSROOT}/boot/bl31-ccert-${MACHINE}.bin ${S}/fip-tbb.bin

	if [ "${ENABLE_SPD_OPTEE}" = "1" ]; then
		fiptool update --align 16 --tos-fw ${RECIPE_SYSROOT}/boot/tee-tbb-${MACHINE}.bin \
			--tos-fw-key-cert ${RECIPE_SYSROOT}/boot/bl32-kcert-${MACHINE}.bin \
			--tos-fw-cert ${RECIPE_SYSROOT}/boot/bl32-ccert-${MACHINE}.bin ${S}/fip-tbb.bin
	fi

	fiptool update --align 16 --nt-fw ${RECIPE_SYSROOT}/boot/u-boot-tbb-${MACHINE}.bin \
		--nt-fw-key-cert ${RECIPE_SYSROOT}/boot/bl33-kcert-${MACHINE}.bin \
		--nt-fw-cert ${RECIPE_SYSROOT}/boot/bl33-ccert-${MACHINE}.bin ${S}/fip-tbb.bin

	objcopy -I binary -O srec --srec-forceS3 ${S}/fip-tbb.bin ${S}/fip-tbb.srec

	if [ "${PMIC_SUPPORT}" = "1" ]; then
		fiptool create --align 16 --soc-fw ${RECIPE_SYSROOT}/boot/bl31-tbb-${MACHINE}_pmic.bin \
			--soc-fw-key-cert ${RECIPE_SYSROOT}/boot/bl31-kcert-${MACHINE}_pmic.bin \
			--soc-fw-cert ${RECIPE_SYSROOT}/boot/bl31-ccert-${MACHINE}_pmic.bin ${S}/fip-tbb_pmic.bin

		if [ "${ENABLE_SPD_OPTEE}" = "1" ]; then
			fiptool update --align 16 --tos-fw ${RECIPE_SYSROOT}/boot/tee-tbb-${MACHINE}.bin \
				--tos-fw-key-cert ${RECIPE_SYSROOT}/boot/bl32-kcert-${MACHINE}.bin \
				--tos-fw-cert ${RECIPE_SYSROOT}/boot/bl32-ccert-${MACHINE}.bin ${S}/fip-tbb_pmic.bin
		fi

		fiptool update --align 16 --nt-fw ${RECIPE_SYSROOT}/boot/u-boot-tbb-${MACHINE}.bin \
			--nt-fw-key-cert ${RECIPE_SYSROOT}/boot/bl33-kcert-${MACHINE}.bin \
			--nt-fw-cert ${RECIPE_SYSROOT}/boot/bl33-ccert-${MACHINE}.bin ${S}/fip-tbb_pmic.bin

		objcopy -I binary -O srec --srec-forceS3 ${S}/fip-tbb_pmic.bin ${S}/fip-tbb_pmic.srec
	fi


	# FlashWriter
	objcopy -I binary -O elf64-little \
		--add-section .ccert=${RECIPE_SYSROOT}/boot/flash-ccert-${MACHINE}.bin --set-section-flags .ccert=alloc --adjust-section-vma .ccert=0x12400 \
		--add-section .kcert=${RECIPE_SYSROOT}/boot/flash-kcert-${MACHINE}.bin --set-section-flags .kcert=alloc --adjust-section-vma .kcert=0x12000 \
		--adjust-vma 0x13000 ${RECIPE_SYSROOT}/boot/flash-tbb-${MACHINE}.bin ${S}/flash-writer-tbb.elf
	objcopy -I elf64-little -O binary ${S}/flash-writer-tbb.elf ${S}/flash-writer-tbb.bin

	bptool ${S}/flash-writer-tbb.bin ${S}/bp_scif.bin 0 scif

	objcopy -I binary -O elf64-little \
		--add-section .btprm=${S}/bp_scif.bin --set-section-flags .btprm=alloc --adjust-section-vma .btprm=0x11E00 \
		--adjust-vma 0x12000 ${S}/flash-writer-tbb.bin ${S}/flash-writer-tbb.elf
	objcopy -I elf64-little -O srec --srec-forceS3 ${S}/flash-writer-tbb.elf ${S}/flash-tbb.mot

	if [ "${PMIC_SUPPORT}" = "1" ]; then
		objcopy -I binary -O elf64-little \
			--add-section .ccert=${RECIPE_SYSROOT}/boot/flash-ccert-${MACHINE}_pmic.bin --set-section-flags .ccert=alloc --adjust-section-vma .ccert=0x12400 \
			--add-section .kcert=${RECIPE_SYSROOT}/boot/flash-kcert-${MACHINE}_pmic.bin --set-section-flags .kcert=alloc --adjust-section-vma .kcert=0x12000 \
			--adjust-vma 0x13000 ${RECIPE_SYSROOT}/boot/flash-tbb-${MACHINE}_pmic.bin ${S}/flash-writer-tbb_pmic.elf
		objcopy -I elf64-little -O binary ${S}/flash-writer-tbb_pmic.elf ${S}/flash-writer-tbb_pmic.bin

		bptool ${S}/flash-writer-tbb_pmic.bin ${S}/bp_scif_pmic.bin 0 scif

		objcopy -I binary -O elf64-little \
			--add-section .btprm=${S}/bp_scif_pmic.bin --set-section-flags .btprm=alloc --adjust-section-vma .btprm=0x11E00 \
			--adjust-vma 0x12000 ${S}/flash-writer-tbb_pmic.bin ${S}/flash-writer-tbb_pmic.elf
		objcopy -I elf64-little -O srec --srec-forceS3 ${S}/flash-writer-tbb_pmic.elf ${S}/flash-tbb_pmic.mot
	fi

}

do_deploy:append:sboot () {
	# Copy trusted boot board images
	install -m 0644 ${S}/bl2-tbb_bp.bin             ${DEPLOYDIR}/bl2-tbb_bp-${MACHINE}.bin
	install -m 0644 ${S}/bl2-tbb_bp.srec            ${DEPLOYDIR}/bl2-tbb_bp-${MACHINE}.srec

	# Copy eSD boot image
	install -m 0644 ${S}/bp.bin                     ${DEPLOYDIR}/bl2-tbb_bp_esd-${MACHINE}.bin
	install -m 0644 ${S}/bl2-tbb.bin                ${DEPLOYDIR}/bl2-tbb_${MACHINE}.bin

	install -m 0644 ${S}/fip-tbb.bin                ${DEPLOYDIR}/fip-tbb-${MACHINE}.bin
	install -m 0644 ${S}/fip-tbb.srec               ${DEPLOYDIR}/fip-tbb-${MACHINE}.srec

	install -m 0644 ${S}/flash-tbb.mot              ${DEPLOYDIR}/${FLASH_NAME}.mot

	if [ "${PMIC_SUPPORT}" = "1" ]; then
		install -m 0644 ${S}/bl2-tbb_bp_pmic.bin    ${DEPLOYDIR}/bl2-tbb_bp-${MACHINE}_pmic.bin
		install -m 0644 ${S}/bl2-tbb_bp_pmic.srec   ${DEPLOYDIR}/bl2-tbb_bp-${MACHINE}_pmic.srec

		# Copy eSD boot image
		install -m 0644 ${S}/bp_pmic.bin            ${DEPLOYDIR}/bl2-tbb_bp_esd-${MACHINE}_pmic.bin
		install -m 0644 ${S}/bl2-tbb_pmic.bin       ${DEPLOYDIR}/bl2-tbb_${MACHINE}_pmic.bin

		install -m 0644 ${S}/fip-tbb_pmic.bin       ${DEPLOYDIR}/fip-tbb-${MACHINE}_pmic.bin
		install -m 0644 ${S}/fip-tbb_pmic.srec      ${DEPLOYDIR}/fip-tbb-${MACHINE}_pmic.srec

		install -m 0644 ${S}/flash-tbb_pmic.mot     ${DEPLOYDIR}/${FLASH_NAME_PMIC}.mot
	fi
}
