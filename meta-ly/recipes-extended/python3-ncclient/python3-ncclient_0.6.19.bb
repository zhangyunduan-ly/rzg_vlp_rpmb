DESCRIPTION = "ncclient - Python NETCONF client"
HOMEPAGE = "https://github.com/ncclient/ncclient"
#LICENSE = "Apache-2.0"
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=fd0120fc2e9f841c73ac707a30389af5"

SRC_URI = "git://github.com/ncclient/ncclient.git;branch=master;protocol=https"

SRC_URI[sha256sum] = "de7a796221910cbd0f32eb20f7dd7c94cfe61aa170fc5f0c5941c557f835c312"

SRCREV = "cdf54a49159d8192ae6d6410fad207d3489a9015"

S = "${WORKDIR}/git"

inherit pypi setuptools3

RDEPENDS:${PN} += " \
    python3-paramiko \
    python3-lxml \
    python3-xml \
"
