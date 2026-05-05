SUMMARY = "Telit modems firmware updating tool."
LICENSE = "CLOSED"

SRC_URI = "file://uxfp-1.14.5-0.tar.gz"
S = "${WORKDIR}/uxfp-1.14.5-0"

B = "${S}"

inherit autotools pkgconfig

DEPENDS += "pkgconfig-native systemd autoconf-archive-native"

EXTRA_AUTORECONF += "-I ${STAGING_DATADIR}/aclocal"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 uxfp ${D}${bindir}/uxfp
}

FILES:${PN} = "${bindir}/uxfp"  