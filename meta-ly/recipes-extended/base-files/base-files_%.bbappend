require base-files_fstab.inc

do_install:append() {
    rm -f ${D}${sysconfdir}/motd
}
