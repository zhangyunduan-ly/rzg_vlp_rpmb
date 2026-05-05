SUMMARY = "SiWT917 RCP WiFi driver"
DESCRIPTION = "SiWT917 RCP out-of-tree kernel driver"
LICENSE = "CLOSED"

SRC_URI = "file://si91x-rcp-driver.tar.gz \
           file://wifi-connect"

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
    install -m 0644 ${S}/rsi_sdio.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${S}/rsi_91x.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/Firmware/pmemdata_wlan_bt_9117 ${D}${nonarch_base_libdir}/firmware/
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/wifi-connect ${D}${bindir}/
}

PACKAGES += "${PN}-firmware"
FILES:${PN}-firmware += "${nonarch_base_libdir}/firmware"
RDEPENDS:${PN} += "${PN}-firmware"

PACKAGES += "${PN}-script"
FILES:${PN}-script += "${bindir}"
RDEPENDS:${PN} += "${PN}-script"

KERNEL_MODULE_NAME = "rsi_sdio rsi_91x"
KERNEL_MODULE_AUTOLOAD = "rsi_sdio rsi_91x"

KERNEL_MODULE_PROBECONF += "rsi_91x"
module_conf_rsi_91x = "options rsi_91x dev_oper_mode=3"

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR} CFLAGS='${CFLAGS}'"