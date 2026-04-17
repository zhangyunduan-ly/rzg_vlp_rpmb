SUMMARY = "SiWT917 RCP WiFi driver"
DESCRIPTION = "SiWT917 RCP out-of-tree kernel driver"
LICENSE = "CLOSED"

SRC_URI = "file://si91x-rcp-driver.tar.gz"

SRCREV = "HEAD"

S = "${WORKDIR}/si91x-rcp-driver"

inherit module

do_compile() {
    cd ${S}
    oe_runmake KERNELDIR="${STAGING_KERNEL_DIR}" M="${S}" modules
}

KERNEL_MODULE_NAME = "rsi_sdio rsi_91x"
KERNEL_MODULE_AUTOLOAD = "rsi_sdio rsi_91x"

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR}"