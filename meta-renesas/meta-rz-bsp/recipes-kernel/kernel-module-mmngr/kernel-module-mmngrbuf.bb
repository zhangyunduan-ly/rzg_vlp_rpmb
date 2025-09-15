DESCRIPTION = "Memory Manager Buffer Kernel module for Renesas RZG2L/RZG3E"

require mmngr_drv.inc

PN = "kernel-module-mmngrbuf"
PR = "r0"

SRC_URI:append = " \
	file://0001-mmngrbuf-Add-support-dmabuf_vmap-api.patch \
	file://0002-mmngrbuf-Update-following-kernel-6.1.patch \
	file://0003-mmngrbuf-Update-following-kernel-6.12.patch \
"

S = "${WORKDIR}/git"
MMNGRBUF_DRV_DIR = "mmngr_drv/mmngrbuf/mmngrbuf-module/files/mmngrbuf"

includedir = "/usr/local/include"
SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_INCDIR}"

# Build Memory Manager Buffer kernel module without suffix
KERNEL_MODULE_PACKAGE_SUFFIX = ""

do_compile() {
    cd ${S}/${MMNGRBUF_DRV_DIR}/drv
    install -d ${INCSHARED}
    make all
}

do_install () {
    # Create destination directories
    install -d ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -d ${D}/${includedir}

    # Install shared library to KERNELSRC(STAGING_KERNEL_DIR) for reference from other modules
    # This file installed in SDK by kernel-devsrc pkg.
    install -m 644 ${S}/${MMNGRBUF_DRV_DIR}/drv/Module.symvers ${KERNELSRC}/include/mmngrbuf.symvers

    # Install kernel module
    install -m 644 ${S}/${MMNGRBUF_DRV_DIR}/drv/mmngrbuf.ko ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/

    # Install shared header files to KERNELSRC(STAGING_KERNEL_DIR)
    # This file installed in SDK by kernel-devsrc pkg.
    install -m 644 ${S}/${MMNGRBUF_DRV_DIR}/include/mmngr_buf_private.h ${KERNELSRC}/include/
    install -m 644 ${S}/${MMNGRBUF_DRV_DIR}/include/mmngr_buf_private_cmn.h ${KERNELSRC}/include/

    # Install shared header files to ${includedir}
    install -m 644 ${S}/${MMNGRBUF_DRV_DIR}/include/mmngr_buf_private_cmn.h ${D}/${includedir}/
}

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-dbg \
"

FILES:${PN} = " \
    ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/mmngrbuf.ko \
"

RPROVIDES:${PN} += "kernel-module-mmngrbuf"
KERNEL_MODULE_AUTOLOAD = "mmngrbuf"

INSANE_SKIP:${PN}-dbg = "buildpaths"
