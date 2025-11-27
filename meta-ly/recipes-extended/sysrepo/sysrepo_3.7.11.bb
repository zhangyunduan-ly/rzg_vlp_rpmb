SUMMARY = "Sysrepo - YANG-based configuration and operational state datastore"
HOMEPAGE = "https://github.com/sysrepo/sysrepo"
#LICENSE = "Apache-2.0"
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=ef345f161efb68c3836e6f5648b2312f"

SRC_URI = "git://github.com/sysrepo/sysrepo.git;protocol=https;branch=master"
SRCREV = "1b720b196f630f348d9e0c131d326b3fb8c6aca7"

S = "${WORKDIR}/git"

DEPENDS = "libyang cmake pkgconfig systemd"

inherit cmake pkgconfig

EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Release"

do_install:append() {
    # 安装 sysrepoctl
    install -d ${D}${bindir}
    install -m 0755 ${B}/sysrepoctl ${D}${bindir}
}

FILES:${PN} += "\
    ${libdir}/*.so* \
    ${bindir}/* \
    ${systemd_system_unitdir}/sysrepo-plugind.service \
    ${sysconfdir}/sysrepo \
    ${libdir}/sysrepo-plugind \
    ${libdir}/sysrepo-plugind/plugins \
    ${libdir}/sysrepo-plugind/plugins/*.so \
    ${datadir}/yang \
    ${datadir}/yang/modules \
    ${datadir}/yang/modules/sysrepo \
"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    if [ -f ${WORKDIR}/build/sysrepo-plugind.service ]; then
        install -m 0644 ${WORKDIR}/build/sysrepo-plugind.service ${D}${systemd_system_unitdir}/
    fi
}

SYSTEMD_SERVICE:${PN} = "sysrepo-plugind.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
