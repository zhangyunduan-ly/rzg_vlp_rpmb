SUMMARY = "NETCONF server based on sysrepo and libnetconf2"
DESCRIPTION = "Netopeer2 is a NETCONF server and client suite based on sysrepo and libnetconf2."
HOMEPAGE = "https://github.com/CESNET/netopeer2"
#LICENSE = "BSD-3-Clause"
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=41daedff0b24958b2eba4f9086d782e1"

SRC_URI = "git://github.com/CESNET/netopeer2.git;protocol=https;branch=master \
           file://install.sh \
           file://netopeer2-install.service \
           "

SRCREV = "2549f8f73b61e94f031a84bf709cbb4e3d594a94"

S = "${WORKDIR}/git"

DEPENDS = "libyang libnetconf2 sysrepo libssh openssl cmake pkgconfig systemd"

inherit cmake pkgconfig systemd

EXTRA_OECMAKE = "\
    -DCMAKE_BUILD_TYPE=Release \
    -DSYSREPO_SETUP=OFF \
"

FILES:${PN} += "\
    ${bindir}/netopeer2-server \
    ${systemd_system_unitdir}/netopeer2-server.service \
    ${systemd_system_unitdir}/netopeer2-install.service \
    ${datadir}/yang \
    ${datadir}/yang/modules \
    ${datadir}/yang/modules/netopeer2 \
"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    if [ -f ${WORKDIR}/build/netopeer2-server.service ]; then
        install -m 0644 ${WORKDIR}/build/netopeer2-server.service ${D}${systemd_system_unitdir}/
    fi
	install -m 0644 ${WORKDIR}/netopeer2-install.service ${D}${systemd_system_unitdir}/
    install -d ${D}${datadir}/netopeer2/scripts/
    install -m 0755 ${WORKDIR}/install.sh ${D}${datadir}/netopeer2/scripts/
}

SYSTEMD_SERVICE:${PN} = "netopeer2-install.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

