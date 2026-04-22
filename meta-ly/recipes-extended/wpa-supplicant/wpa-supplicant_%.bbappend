FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://wpa_supplicant-nl80211-wlan0.conf"

do_install:append() {
	install -d ${D}${sysconfdir}/wpa_supplicant
	install -m 0644 ${WORKDIR}/wpa_supplicant-nl80211-wlan0.conf ${D}${sysconfdir}/wpa_supplicant/
}
