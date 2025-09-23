FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://hostapd.conf"

do_install:append() {
	install -d ${D}${sysconfdir}/
	install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}/
}
