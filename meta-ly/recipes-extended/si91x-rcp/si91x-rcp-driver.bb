SUMMARY = "SiWT917 RCP WiFi driver"
DESCRIPTION = "SiWT917 RCP out-of-tree kernel driver"
LICENSE = "CLOSED"

SRC_URI = "file://si91x-rcp-driver.tar.gz"

SRCREV = "HEAD"

S = "${WORKDIR}/si91x-rcp-driver"

inherit module

do_compile() {
    cd ${S}
    sed -i '32s/.*/CONFIG_STA_PLUS_AP=y/' Makefile
    oe_runmake KERNELDIR="${STAGING_KERNEL_DIR}" M="${S}" modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 755 ${S}/rsi_sdio.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 755 ${S}/rsi_91x.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 755 ${S}/Firmware/pmemdata_wlan_bt_9117 ${D}${nonarch_base_libdir}/firmware/
}

PACKAGES += "${PN}-firmware"
FILES:${PN}-firmware += "${nonarch_base_libdir}/firmware"

INSANE_SKIP:${PN} += "buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"
INSANE_SKIP:kernel-module-rsi-91x += "buildpaths"
INSANE_SKIP:kernel-module-rsi-sdio += "buildpaths"

KERNEL_MODULE_NAME = "rsi_sdio rsi_91x"
KERNEL_MODULE_AUTOLOAD = "rsi_sdio rsi_91x"

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR}"