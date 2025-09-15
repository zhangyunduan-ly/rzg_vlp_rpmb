require include/dev-define.inc

DESCRIPTION = "Renesas Secure IP Library"
LICENSE = "CLOSED"
SRC_URI = "file://renesas-secureip-library-${DEV}.tar.gz"

S = "${WORKDIR}/renesas-secureip-library"

do_install () {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0644 ${S}/include/*.h ${D}${includedir}
	install -m 0644 ${S}/lib* ${D}${libdir}
}

FILES:${PN} = "${libdir} ${includedir}"
