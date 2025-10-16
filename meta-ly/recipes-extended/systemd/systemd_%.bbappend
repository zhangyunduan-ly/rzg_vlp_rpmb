FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://10-eth0.network \
           		  file://20-eth1.network \
				  file://30-wlan0.network \
				  file://70-persistent-net.rules"

do_install:append() {
	install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/10-eth0.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/20-eth1.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/30-wlan0.network ${D}${sysconfdir}/systemd/network/
	install -d ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 ${WORKDIR}/70-persistent-net.rules ${D}${sysconfdir}/udev/rules.d/
}

SYSTEMD_SERVICE:${PN}:append = " systemd-networkd-wait-online.service"

do_install:append() {
    # 创建服务覆盖目录
    install -d ${D}${systemd_system_unitdir}/systemd-networkd-wait-online.service.d
    
    # 创建禁用配置
    cat > ${D}${systemd_system_unitdir}/systemd-networkd-wait-online.service.d/disable.conf << EOF
[Unit]
ConditionNull=

[Install]
WantedBy=
EOF
}