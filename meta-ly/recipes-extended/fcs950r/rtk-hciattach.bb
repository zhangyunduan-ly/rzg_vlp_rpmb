SUMMARY = "Realtek HCI Attach Tool"
LICENSE = "CLOSED"

SRC_URI = "file://rtk_hciattach.tar.gz"

S = "${WORKDIR}/rtk_hciattach"

do_compile() {
    oe_runmake \
    CC="${CC}" \
    CFLAGS="${CFLAGS}" \
    LDFLAGS="${LDFLAGS}"
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 rtk_hciattach ${D}${bindir}/
}

FILES:${PN} = "${bindir}/rtk_hciattach"
