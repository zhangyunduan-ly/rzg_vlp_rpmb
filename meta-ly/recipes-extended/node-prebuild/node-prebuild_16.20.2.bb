SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "CLOSED"

PV = "16.20.2"

SRC_URI = "file://node-prebuild_16.20.2.tar.gz"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/node ${D}${bindir}/node
}

FILES:${PN} += "${bindir}/node"
