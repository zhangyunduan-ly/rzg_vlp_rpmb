export NP2_MODULE_DIR=/usr/share/yang/modules/netopeer2
export NP2_MODULE_PERMS=600
export LN2_MODULE_DIR=/usr/share/yang/modules/libnetconf2
export NP2_MODULE_OWNER=root
export NP2_MODULE_GROUP=root
export NP2_VERSION=2.4.5

/usr/share/netopeer2/scripts/remove.sh && \
/usr/share/netopeer2/scripts/setup.sh && \
/usr/share/netopeer2/scripts/merge_hostkey.sh && \
/usr/share/netopeer2/scripts/merge_config.sh && \
touch /var/lib/netopeer2_install.lock && \
sync