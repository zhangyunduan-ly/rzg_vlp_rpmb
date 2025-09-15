SRC_URI:append = " \
	${@oe.utils.conditional("ENABLE_SPD_OPTEE", "1", "file://g2l/0001-arm64-dts-renesas-r9a07g0-43-44-54-enable-OP-TEE.patch", "",d)} \
"

