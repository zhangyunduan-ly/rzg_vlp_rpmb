FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://mosquitto.conf \
                  file://mosquitto.service"

do_install:append() {
	install -d ${D}${sysconfdir}/mosquitto
	install -m 0644 ${WORKDIR}/mosquitto.conf ${D}${sysconfdir}/mosquitto/
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/mosquitto.service ${D}${systemd_system_unitdir}/
}