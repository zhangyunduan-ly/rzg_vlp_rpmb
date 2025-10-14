SUMMARY = "Example of how to build an external Linux kernel module"
LICENSE = "CLOSED"

inherit allarch

SRC_URI = "file://wifi \
           file://bt"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}"

do_install() {
   
	install -d ${D}${nonarch_base_libdir}/firmware

	install ${S}/wifi/txpower/* /${D}${nonarch_base_libdir}/firmware

	install -d ${D}${nonarch_base_libdir}/firmware/rtlbt

	install ${S}/bt/* /${D}${nonarch_base_libdir}/firmware/rtlbt
}

FILES_${PN} += "/usr/bin"
FILES:${PN} += " \
    ${nonarch_base_libdir}/firmware/PHY_REG_PG.txt \
    ${nonarch_base_libdir}/firmware/TXPWR_LMT.txt \
    ${nonarch_base_libdir}/firmware/rtlbt/rtl8821c_config \
    ${nonarch_base_libdir}/firmware/rtlbt/rtl8821c_fw \
"

INSANE_SKIP_${PN} += "arch already-stripped file-rdeps"
INHIBIT_SYSROOT_STRIP = "1"
