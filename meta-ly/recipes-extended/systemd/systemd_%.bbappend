FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://10-end0.network \
           		  file://20-end1.network \
				  file://30-wlan0.network"

do_install:append() {
	install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/10-end0.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/20-end1.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/30-wlan0.network ${D}${sysconfdir}/systemd/network/
}
