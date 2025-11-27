SUMMARY = "NETCONF protocol library for communication between NETCONF client and server"
DESCRIPTION = "libnetconf2 is a NETCONF protocol library implemented in C, providing functions to implement NETCONF client and server."
HOMEPAGE = "https://github.com/CESNET/libnetconf2"
SECTION = "libs"
#LICENSE = "BSD-3-Clause"
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=08a5578c9bab06fb2ae84284630b973f"

SRC_URI = "git://github.com/CESNET/libnetconf2.git;protocol=https;branch=master"

SRCREV = "61fbe731908809f88a187f223d43718479a7e0da"

S = "${WORKDIR}/git"

DEPENDS = "libyang libssh cmake pkgconfig"

inherit cmake pkgconfig

EXTRA_OECMAKE = "\
    -DENABLE_SSH_TLS=ON \
    -DENABLE_DNSSEC=OFF \
"

FILES:${PN} += "${libdir}/*.so* ${datadir}/yang ${datadir}/yang/modules ${datadir}/yang/modules/libnetconf2"
FILES:${PN}-dev += "${includedir}"
