FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://10-eth0.network \
           		  file://20-eth1.network \
				  file://30-wlan0.network \
				  file://70-persistent-net.rules"

do_install:append() {
	install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/10-eth0.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/20-eth1.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/30-wlan0.network ${D}${sysconfdir}/systemd/network/
	install -d ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 ${WORKDIR}/70-persistent-net.rules ${D}${sysconfdir}/udev/rules.d/
}

SYSTEMD_SERVICE_DISABLE += "systemd-networkd-wait-online.service"
