SUMMARY = "Realtek HCI Attach Tool"
LICENSE = "CLOSED"

SRC_URI = "file://rtk_hciattach.tar.gz \
           file://rtk-hciattach.service"

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
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/rtk-hciattach.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} = "${bindir}/rtk_hciattach"
FILES:${PN} += "${systemd_system_unitdir}/rtk-hciattach.service"

SYSTEMD_SERVICE:${PN} = "rtk-hciattach.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
