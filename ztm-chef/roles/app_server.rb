name 'app_server'
description 'Application server'

run_list [
             'role[base]',
             'recipe[java]',
             'recipe[mongodb::10gen_repo]',
             'recipe[mongodb]'
         ]

default_attributes(
    java: {
        accept_license_agreement: true,
        oracle: {
            accept_oracle_download_terms: true
        },
        install_flavor: 'oracle',
        jdk_version: '7'
    },
    mongodb: {
        package_version: '2.4.9'
    }
)
