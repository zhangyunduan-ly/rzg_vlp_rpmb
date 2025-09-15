include include/core-image-renesas-base-${MACHINE}.inc

# Support secure environment
IMAGE_INSTALL:append = " \
	${@oe.utils.conditional("ENABLE_SPD_OPTEE", "1", " optee-client optee-test optee-example optee-ta-sample", "",d)} \
"


TOOLCHAIN_HOST_TASK:append = " nativesdk-python3-pycryptodome nativesdk-python3-pycryptodomex"