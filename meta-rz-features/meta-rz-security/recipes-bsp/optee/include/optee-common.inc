
# OP-TEE OS
PV:pn-optee-os              = "3.19.0+git${SRCPV}"
BRANCH:pn-optee-os          = "3.19.0/rz"
SRCREV:pn-optee-os          = "775f68ace6f4650add42395af33a0dd643bc0fde"
SRC_URI:pn-optee-os         = "git://github.com/renesas-rz/rzg_optee-os.git;branch=${BRANCH};protocol=https"

# OP-TEE Client
PV:pn-optee-client          = "3.19.0+git${SRCPV}"
BRANCH:pn-optee-client      = "master"
SRCREV:pn-optee-client      = "140bf463046071d3ca5ebbde3fb21ee0854e1951"
SRC_URI:pn-optee-client     = "git://github.com/OP-TEE/optee_client.git;branch=${BRANCH};protocol=https"

# OP-TEE Test
PV:pn-optee-test            = "3.19.0+git${SRCPV}"
BRANCH:pn-optee-test        = "master"
SRCREV:pn-optee-test        = "ab9863cc187724e54c032b738c28bd6e9460a4db"
SRC_URI:pn-optee-test       = "git://github.com/OP-TEE/optee_test.git;branch=${BRANCH};protocol=https"

# OP-TEE Example
PV:pn-optee-example         = "3.19.0+git${SRCPV}"
BRANCH:pn-optee-example     = "master"
SRCREV:pn-optee-example     = "f301ee9df2129c0db683e726c91dc2cefe4cdb65"
SRC_URI:pn-optee-example    = "git://github.com/linaro-swg/optee_examples.git;branch=${BRANCH};protocol=https"