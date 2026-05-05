SUMMARY = "GSM 07.10 Driver Implementation"
LICENSE = "CLOSED"

SRC_URI = "file://gsmmux.tar.gz"

S = "${WORKDIR}/gsmmux"

EXTRA_OEMAKE += "CC='${CC}' CFLAGS='${CFLAGS}' LDFLAGS='${LDFLAGS}'"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 gsmMuxd ${D}${bindir}/
}

FILES:${PN} = "${bindir}/gsmMuxd"
