SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "CLOSED"

PV = "0.0.2"

SRC_URI = "file://system-monitor.service \
           file://system-monitor.sh"

inherit systemd

S = "${WORKDIR}"

do_install() {
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${S}/system-monitor.service ${D}${systemd_system_unitdir}/
	install -d ${D}${sysconfdir}
	install -m 0755 ${S}/system-monitor.sh ${D}${sysconfdir}/
}

FILES:${PN} += "${systemd_system_unitdir}/system-monitor.service"
FILES:${PN} += "${sysconfdir}/system-monitor.sh"

SYSTEMD_SERVICE_${PN} = "system-monitor.service"
