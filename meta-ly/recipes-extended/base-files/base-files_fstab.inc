do_install:append() {
    echo "" >> ${D}${sysconfdir}/fstab
    echo "/dev/mmcblk0p3       /data                ext4       defaults              0  2" >> ${D}${sysconfdir}/fstab
    echo "/dev/mmcblk0p4       /backup              ext4       defaults              0  2" >> ${D}${sysconfdir}/fstab
}
