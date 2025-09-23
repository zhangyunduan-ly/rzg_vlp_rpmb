FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://dnsmasq.conf"

do_install:append() {
	install -d ${D}${sysconfdir}/
	install -m 0644 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}/
}
