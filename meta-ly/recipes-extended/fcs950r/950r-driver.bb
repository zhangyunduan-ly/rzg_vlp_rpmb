SUMMARY = "Example of how to build an external Linux kernel module"
LICENSE = "CLOSED"

inherit module

SRC_URI = "file://950r-driver.tar.gz"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}"

INSANE_SKIP:${PN} += "installed-vs-shipped"
do_configure () {
    bbnote skip do_configure
}

EXTRA_OEMAKE = " \
    KERNEL_SRC=${STAGING_KERNEL_DIR} \
    KERNEL_PATH=${STAGING_KERNEL_DIR} \
    KERNEL_VERSION=${KERNEL_VERSION} \
    ARCH=arm64 \
    CROSS_COMPILE=${TARGET_PREFIX} \
    CC="${KERNEL_CC}" \
    LD="${KERNEL_LD}" \
    INSTALL_MOD_PATH=${D} \
"

do_compile() {
    oe_runmake -C "${STAGING_KERNEL_DIR}" \
    M="${S}/wlan_src" \
    modules \
    ARCH=arm64 \
    CROSS_COMPILE=aarch64-poky-linux-

    oe_runmake -C "${STAGING_KERNEL_DIR}" \
    M="${S}/bluetooth_uart_driver" \
    modules \
    ARCH=arm64 \
    CROSS_COMPILE=aarch64-poky-linux-
}

do_install() {
    install -d ${D}${base_sbindir}
    install -d ${D}${nonarch_base_libdir}/modules
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 755 ${WORKDIR}/wlan_src/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 755 ${WORKDIR}/bluetooth_uart_driver/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}

KERNEL_MODULE_AUTOLOAD += "8821cs"
KERNEL_MODULE_AUTOLOAD += "hci_uart"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.


RPROVIDES_${PN} += "kernel-module-8821cs"
RPROVIDES_${PN} += "kernel-module-hci_uart"
