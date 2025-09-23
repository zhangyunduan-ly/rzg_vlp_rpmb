FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://ppp-off"

do_install:append() {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/ppp-off ${D}${bindir}/
}
