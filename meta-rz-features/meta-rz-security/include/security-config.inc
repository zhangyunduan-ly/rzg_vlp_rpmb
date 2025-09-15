# For Security Features purpose

# Enable Secure Boot
TRUSTED_BOARD_BOOT      ?= '0'
# Enable OP-TEE OS
ENABLE_SPD_OPTEE        ?= '0'
ENABLE_RZ_SCE           ?= '0'

OVERRIDES:append = "${@bb.utils.contains('TRUSTED_BOARD_BOOT', '1', ':sboot', '',d)}"
