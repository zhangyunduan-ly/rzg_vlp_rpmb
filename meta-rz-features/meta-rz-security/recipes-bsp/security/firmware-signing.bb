require include/dev-define.inc
include include/firmware-signing-${MACHINE}.inc

inherit deploy python3native

DESCRIPTION = "Firmware Signing"
LICENSE = "CLOSED"
SRC_URI = "file://manifest_generation_tool.tar.gz "
SRC_URI:append = "file://keys/ file://info/${DEV}/ "

DEPENDS = " python3-pycryptodome-native python3-pycryptodomex-native"
DEPENDS:append = " trusted-firmware-a u-boot flash-writer"
DEPENDS:append = " ${@oe.utils.conditional("ENABLE_SPD_OPTEE", "1", "optee-os", "",d)}"

S = "${WORKDIR}/manifest_generation_tool"

KEYDIR = "${WORKDIR}/keys"
INFDIR = "${WORKDIR}/info/${DEV}"

# Trusted Board Boot Verification Mode: "encsign" or "sign"
IMG_AUTH_MODE = "encsign"

KCERT_COMMON_OPTION = "-halgo SHA2-256 -salgo ECDSA-P256 -mskey ${KEYDIR}/root_of_trust_key.pem"
CCERT_COMMON_OPTION = "-halgo SHA2-256 -salgo ECDSA-P256"
CCERT_COMMON_OPTION:append  = " \
	${@oe.utils.conditional("IMG_AUTH_MODE", "encsign", " -encimage -ealgo AES-CBC", "",d)} \
"

do_compile () {

	python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
		-info ${INFDIR}/bl2_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout bl2-kcert.bin
	python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
		-info ${INFDIR}/bl2_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout bl2-ccert.bin \
		-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/bl2-${MACHINE}.bin -imgout bl2-tbb.bin

	python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
		-info ${INFDIR}/bl31_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl31_key.pem -certout bl31-kcert.bin
	python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
		-info ${INFDIR}/bl31_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl31_key.pem -certout bl31-ccert.bin \
		-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/bl31-${MACHINE}.bin -imgout bl31-tbb.bin

	python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
		-info ${INFDIR}/bl33_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl33_key.pem -certout bl33-kcert.bin
	python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
		-info ${INFDIR}/bl33_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl33_key.pem -certout bl33-ccert.bin \
		-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/u-boot.bin -imgout u-boot-tbb.bin

	if [ "${ENABLE_SPD_OPTEE}" = "1" ]; then
		python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
			-info ${INFDIR}/bl32_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl32_key.pem -certout bl32-kcert.bin
		python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
			-info ${INFDIR}/bl32_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl32_key.pem -certout bl32-ccert.bin \
			-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/tee-${MACHINE}.bin -imgout tee-tbb.bin
	fi

	python3 manifest_generation_tool.py genkcert ${KCERT_COMMON_OPTION} \
		-info ${INFDIR}/flash_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout flash-kcert.bin
	python3 manifest_generation_tool.py genccert ${CCERT_COMMON_OPTION} \
		-info ${INFDIR}/flash_${IMG_AUTH_MODE}_info.xml -iskey ${KEYDIR}/bl2_key.pem -certout flash-ccert.bin \
		-iekey ${KEYDIR}/cmn_key_idx0.txt -imgin ${RECIPE_SYSROOT}/boot/flash-writer.bin -imgout flash-tbb.bin

	objcopy -I binary -O srec --srec-forceS3 root_of_trust_key_pk.hash root_of_trust_key_pk.hash.srec
}

do_install () {
	install -d ${D}/boot

	install -m 0644 bl2-kcert.bin ${D}/boot/bl2-kcert-${MACHINE}.bin
	install -m 0644 bl2-ccert.bin ${D}/boot/bl2-ccert-${MACHINE}.bin
	install -m 0644 bl2-tbb.bin   ${D}/boot/bl2-tbb-${MACHINE}.bin

	install -m 0644 bl31-kcert.bin ${D}/boot/bl31-kcert-${MACHINE}.bin
	install -m 0644 bl31-ccert.bin ${D}/boot/bl31-ccert-${MACHINE}.bin
	install -m 0644 bl31-tbb.bin   ${D}/boot/bl31-tbb-${MACHINE}.bin

	install -m 0644 bl33-kcert.bin ${D}/boot/bl33-kcert-${MACHINE}.bin
	install -m 0644 bl33-ccert.bin ${D}/boot/bl33-ccert-${MACHINE}.bin
	install -m 0644 u-boot-tbb.bin ${D}/boot/u-boot-tbb-${MACHINE}.bin

	if [ "${ENABLE_SPD_OPTEE}" = "1" ]; then
		install -m 0644 bl32-kcert.bin ${D}/boot/bl32-kcert-${MACHINE}.bin
		install -m 0644 bl32-ccert.bin ${D}/boot/bl32-ccert-${MACHINE}.bin
		install -m 0644 tee-tbb.bin    ${D}/boot/tee-tbb-${MACHINE}.bin
	fi

	install -m 0644 flash-kcert.bin ${D}/boot/flash-kcert-${MACHINE}.bin
	install -m 0644 flash-ccert.bin ${D}/boot/flash-ccert-${MACHINE}.bin
	install -m 0644 flash-tbb.bin   ${D}/boot/flash-tbb-${MACHINE}.bin
}

do_deploy () {
	install -d ${DEPLOYDIR}
	install -m 0644 root_of_trust_key_pk.hash      ${DEPLOYDIR}/root_of_trust_key_pk.hash
	install -m 0644 root_of_trust_key_pk.hash.srec ${DEPLOYDIR}/root_of_trust_key_pk.hash.srec
}
addtask deploy before do_build after do_compile

FILES:${PN} = "/boot"
SYSROOT_DIRS += "/boot"
