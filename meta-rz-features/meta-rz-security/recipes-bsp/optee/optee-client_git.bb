DESCRIPTION = "OP-TEE Client"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

require include/optee-common.inc
require include/optee-${MACHINE}.inc

SRC_URI:pn-optee-client += " \
	file://optee.service \
"

DEPENDS:append = "util-linux"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit python3native systemd pkgconfig

SYSTEMD_SERVICE:${PN} = "optee.service"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "RPMB_EMU=0"

do_install () {
	# Create destination directories
	install -d ${D}/${libdir}
	install -d ${D}/${includedir}

	# Install library
	install -m 0755 ${S}/out/export/usr/lib/libteec.so.1.0 ${D}/${libdir}

	# Create symbolic link
	cd ${D}/${libdir}
	ln -sf libteec.so.1.0 libteec.so.1
	ln -sf libteec.so.1 libteec.so

	# Install header files
	install -m 0644 ${S}/out/export/usr/include/* ${D}/${includedir}

	# Install systemd service configure file for OP-TEE client
	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}/${systemd_system_unitdir}
		install -m 0644 ${WORKDIR}/optee.service ${D}/${systemd_system_unitdir}
	fi
}

# install the tee-supplicant for 64 bit only.
do_install:append:aarch64 () {
	# Create destination directory
	install -d ${D}/${bindir}

	# Install binary to bindir
	install -m 0755 ${S}/out/export/usr/sbin/tee-supplicant ${D}/${bindir}
}

RPROVIDES:${PN} += "optee-client"

FILES:${PN} += "${libdir} ${includedir}"
