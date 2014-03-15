name 'base'
description 'Base role'

run_list [
    'recipe[ntp]'
]

default_attributes(
    ntp: {
        servers: %w(0.pool.ntp.org 1.pool.ntp.org)
    }
)
